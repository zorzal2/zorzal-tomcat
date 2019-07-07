package com.fontar.bus.impl.configuracion;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.ldap.EntryNotFoundException;

import com.fontar.bus.api.configuracion.GrupoService;
import com.fontar.bus.api.configuracion.UsuarioService;
import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.impl.assembler.UsuarioDTOAssembler;
import com.fontar.data.impl.dao.ldap.UsuarioDao;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.ldap.GrupoAbstracto;
import com.fontar.data.impl.domain.ldap.SearchCriteria;
import com.fontar.data.impl.domain.ldap.UserStatus;
import com.fontar.data.impl.domain.ldap.Usuario;
import com.fontar.seguridad.AuthenticationService;
import com.fontar.seguridad.NoValidoComoPasswordException;
import com.fontar.seguridad.SecurityConfigInterceptor;
import com.fontar.seguridad.UserUpdateEvent;
import com.fontar.seguridad.acegi.SecuredService;
import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.fontar.seguridad.cripto.PasswordInvalidaException;
import com.fontar.web.action.configuracion.seguridad.UsuarioDTO;
import com.fontar.web.action.configuracion.seguridad.VisualizarGrupoDTO;
import com.pragma.data.api.assembler.Assembler;
import com.pragma.util.event.Event;
import com.pragma.util.event.Notification;
import com.pragma.util.event.NotifyLater;


@SecuredService
public class UsuarioServiceImpl implements UsuarioService {
	
	private UsuarioDao usuarioDao;
	
    private PersonaDAO personaDao;

    private SecurityConfigInterceptor interceptor;
    
    private FontarCryptographicService cryptographicService;
    
    private AuthenticationService authenticationService;
    
    private GrupoService grupoService;
    
    public GrupoService getGrupoService() {
		return grupoService;
	}

	public void setGrupoService(GrupoService grupoService) {
		this.grupoService = grupoService;
	}

	public AuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public SecurityConfigInterceptor getInterceptor() {
		return interceptor;
	}

	public void setInterceptor(SecurityConfigInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	public void setUsuarioDao (UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }


	public FontarCryptographicService getCryptographicService() {
		return cryptographicService;
	}


	public void setCryptographicService(FontarCryptographicService cryptographicService) {
		this.cryptographicService = cryptographicService;
	}


	/*
     * @see org.springframework.ldap.samples.person.service.GrupoService#create(java.lang.String,
     *      java.util.Set)
     */
	public void create(
			String nombre, 
			String apellido, 
			String userId,  
			String password, 
			Set<GrupoAbstracto> grupos, 
			boolean enableCryptoGraphicServices , 
			String appPassword, 
			Long idPersona) {
    	
    	NotifyLater notifyLater = new NotifyLater();

    	if(enableCryptoGraphicServices){
	    		
	        Usuario.Essence essence = new Usuario.Essence();
	        essence.setNombre(nombre);
	        essence.setApellido(apellido);        
	        essence.setUserId(userId);
	        essence.setPassword(password);
			essence.setGrupos(grupos);
	        
	        //Claves
	        KeyPair keyPair = this.cryptographicService.generateUserKeys();
	        PublicKey publicKey = keyPair.getPublic();
	        essence.setPublicKey( publicKey );
	        essence.setCertifiedPublicKeyByteArray( this.cryptographicService.certifyPublicKey(publicKey , appPassword) );
	        
	        Usuario usuario = essence.createUserDetails();
	        usuarioDao.create(usuario);
	        
	        //Actualizo la persona
	        if(idPersona!=null) {
	        	PersonaBean persona = personaDao.read(idPersona);
	        	String oldUserId = persona.getUserId();
	        	persona.setUserId(userId);
	        	personaDao.update(persona);
	        	//Notifico el cambio en la asignacion
	        	notifyLater.addNotification(
        			new CambioDeAsignacionDeUsuarioAPersonaImpl(persona, oldUserId, userId),
        			onCambioDeAsignacionDeUsuarioAPersona());
	        }
	        
	        UserUpdateEvent userUpdateEvent = new UserUpdateEvent(userId, grupos);
	        usuarioDao.update(usuario);
	        
	        /*Con el cambio de que la Bandeja de Entrada la obtiene con los permisos asignados en el LDAP (y no x PooledActor)
			//Se cambio para que no actualice los PooledActor de todas las tareas realacionadas del usuario.
			*/
			//this.interceptor.update(userUpdateEvent);
	        
	        this.cryptographicService.initialiazeRequiredEncryptionData( usuario.getUserId() , publicKey , appPassword );
	        
	        //Se guarda la clave privada temporalmente encriptada con la clave de la applicacion
	        //FIXME encriptar con la clave privada de aplicacion que debe estar disponible
	        this.cryptographicService.saveUserTmpPrivateKey(usuario.getUserId(), keyPair.getPrivate());
    	}else{
    		//FIXME esto esta muy malo
    		this.newUserWithoutEcnryptionServices(nombre,apellido, userId,password, grupos, idPersona);
    	}
    	notifyLater.notifyAllEvents(); 
    }
    
	private void newUserWithoutEcnryptionServices(String nombre, String apellido, String userId,  String password, Set<GrupoAbstracto> grupos, Long idPersona) {

        Usuario.Essence essence = new Usuario.Essence();
        essence.setNombre(nombre);
        essence.setApellido(apellido);        
        essence.setUserId(userId);
        essence.setPassword(password);
        essence.setGrupos(grupos);
        essence.setStatus(UserStatus.INISTIALIZED);
        Usuario usuario = essence.createUserDetails();
        usuarioDao.create(usuario);

        //Actualizo la persona
        if(idPersona!=null) {
        	PersonaBean persona = personaDao.read(idPersona);
        	String oldUserId = persona.getUserId();
        	persona.setUserId(userId);
        	personaDao.update(persona);
        	//Disparo el evento de actualizacion del vinculo
        	onCambioDeAsignacionDeUsuarioAPersona().notifyEvent(new CambioDeAsignacionDeUsuarioAPersonaImpl(persona, oldUserId, userId));
        }
    }

    /*
     * @see org.springframework.ldap.samples.person.service.UsuarioService#delete(org.springframework.ldap.samples.person.domain.Usuario)
     */
    public void delete(Usuario usuario) {
        usuarioDao.delete(usuario);
        this.cryptographicService.removeRequiredEncryptionData(usuario.getUserId());
        
        //Elimino la vinculacion de persona que pudiera haber
		List<PersonaBean> personas = personaDao.findByUserId(usuario.getUserId());
		if(!personas.isEmpty()) {
			PersonaBean persona = personas.get(0);
			persona.setUserId(null);
			personaDao.update(persona);
        	onCambioDeAsignacionDeUsuarioAPersona().notifyEvent(new CambioDeAsignacionDeUsuarioAPersonaImpl(persona, usuario.getUserId(), null));
		}
    }

    /*
     * @see org.springframework.ldap.samples.person.service.UsuarioService#findByPrimaryKey(java.lang.String)
     */
    public Usuario findByPrimaryKey(String name) {
    	try{
    		return usuarioDao.findByPrimaryKey(name);	
    	}catch (EntryNotFoundException e) {
    		return null;
		}
    }

    /*
     * @see org.springframework.ldap.samples.person.service.UsuarioService#findAll()
     */
    public List<UsuarioDTO> findAll() {
    	return findAll(UsuarioDTOAssembler.getInstance());
    }

    /*
     * @see org.springframework.ldap.samples.person.service.UsuarioService#find(org.springframework.ldap.samples.person.domain.SearchCriteria)
     */
    public List find(SearchCriteria criteria) {
        return usuarioDao.find(criteria);
    }

	public void delete(String name) {
		Usuario usuario =  this.findByPrimaryKey( name );
		this.delete( usuario );
	}

	//Obtiene un set de permisos a partir de la lista de ids
	public Set<GrupoAbstracto> getGroupSet(String[] groups){
		Set<GrupoAbstracto> groupSet = new HashSet<GrupoAbstracto>();
		for (String groupId : groups) {
			GrupoAbstracto grupo  = this.grupoService.getGrupo( groupId );
			groupSet.add( grupo );
		}
		return groupSet;
	}
	
//	Obtiene un set de permisos a partir de la lista de ids
	public Set<VisualizarGrupoDTO> getGroupDTOSet(String[] groups){
		Set<VisualizarGrupoDTO> groupSet = new HashSet<VisualizarGrupoDTO>();
		for (String groupId : groups) {
			GrupoAbstracto grupo  = this.grupoService.getGrupo( groupId );
			groupSet.add( new VisualizarGrupoDTO(grupo ));
		}
		return groupSet;
	}
/*
	public void update(Usuario usuario, String[] groups) {
		usuario.setGrupos( this.getGroupSet( groups) );
		usuarioDao.update(usuario);
	}*/

	public void registrarClavePrivada(String password) {
		Usuario current = this.getCurrentUser();
		this.cryptographicService.encryptTmpPrivateKey( current.getUserId(),password);
		current.setStatus( UserStatus.INISTIALIZED);
		usuarioDao.update(current);
	}
	
	//Actualiza la clave con la cual se blinda la private key del usuario
	public void actualizarClavePrivada(String currentPassword, String newPassword) throws PasswordInvalidaException {
		String userId = this.getCurrentUser().getUserId();
		this.cryptographicService.updatePrivateKey( userId,currentPassword, newPassword);
	}
	
	public Usuario getCurrentUser(){
		return this.usuarioDao.findByPrimaryKey( this.getUserId() );
	}

	public boolean availableIdentifier(String userIdentifier) {
		return this.findByPrimaryKey( userIdentifier) == null;
	}
	
	public String getUserId(){
		return this.authenticationService.getUserId();
	}
	
	public void resetearClaveAutenticacion(String userId, String password) {
		this.usuarioDao.updateAuthenticationPassword( userId  , password);
	}

	public void cambiarPassword(
			String userId, 
			String passwordAnterior, 
			String passwordNuevo) 
	throws PasswordInvalidaException, NoValidoComoPasswordException	{
		
		Usuario usuario = this.usuarioDao.findByPrimaryKey(userId);
		if(!usuario.getPassword().equals(passwordAnterior))
			throw new PasswordInvalidaException("El password no es valido");
		validarQueLaCadenaSeaValidaComoPassword(passwordNuevo);
		this.usuarioDao.updateAuthenticationPassword( userId  , passwordNuevo);
	}
	/**
	 * Valida que una cadena pueda ser usada como password. Actualmente
	 * se chequea solamente que mida entre 4 y 1000 caracteres.
	 * @param passwordCandidato
	 * @return
	 */
	public boolean esValidoComoPassword(String passwordCandidato) {
		return 
			passwordCandidato!=null &&
			passwordCandidato.length()<=MAX_PASSWORD_LENGTH && 
			passwordCandidato.length()>=MIN_PASSWORD_LENGTH; 
	}	
	/**
	 * Valida si una cadena puede ser usada como password. Si no
	 * cumple con las condiciones dispara NoValidoComoPasswordException.
	 * @param passwordCandidate
	 * @throws NoValidoComoPasswordException 
	 */
	public void validarQueLaCadenaSeaValidaComoPassword(String passwordCandidate) throws NoValidoComoPasswordException {
		if(!esValidoComoPassword(passwordCandidate)) 
			throw new NoValidoComoPasswordException("El password debe tener entre "+MIN_PASSWORD_LENGTH+" y "+MAX_PASSWORD_LENGTH+" caracteres.");
	}

	public PersonaDAO getPersonaDao() {
		return personaDao;
	}

	public void setPersonaDao(PersonaDAO personaDao) {
		this.personaDao = personaDao;
	}

	public void create(String nombre, String apellido, String userId, String password, String[] grupos, String appPasword, Long idPersona) {
		this.create(nombre, apellido, userId, password, grupos, true, appPasword, idPersona);		
	}

	public void update(String userId, String userName, String userLastName, String[] groups, Long idPersona) {
		Usuario usuario = this.findByPrimaryKey( userId );
		usuario.setNombre( userName );
		usuario.setApellido( userLastName );
		Set<GrupoAbstracto> assignedGroups = this.getGroupSet( groups);
		UserUpdateEvent userUpdateEvent = new UserUpdateEvent(usuario, assignedGroups);
		usuario.setGrupos( assignedGroups );
		usuarioDao.update(usuario);
		
		//Actualizo la persona
		List<PersonaBean> personas = personaDao.findByUserId(userId);
		if(!personas.isEmpty()) {
			PersonaBean persona = personas.get(0);
			if(idPersona!=null) {
				if(!persona.getId().equals(idPersona)) {
					//Desvinculo esta persona
					persona.setUserId(null);
					//Busco la persona nueva
					PersonaBean personaNueva = personaDao.read(idPersona);
					String oldUserId = personaNueva.getUserId();
					personaNueva.setUserId(userId);
					personaDao.update(personaNueva);
					personaDao.update(persona);
					onCambioDeAsignacionDeUsuarioAPersona().notifyEvent(
							new CambioDeAsignacionDeUsuarioAPersonaImpl(persona, userId, null)
					);
		        	onCambioDeAsignacionDeUsuarioAPersona().notifyEvent(
		        		new CambioDeAsignacionDeUsuarioAPersonaImpl(personaNueva, oldUserId, userId)
		        	);
				}
			} else {
				//Desvinculo esta persona
				persona.setUserId(null);
				personaDao.update(persona);
	        	onCambioDeAsignacionDeUsuarioAPersona().notifyEvent(
		        		new CambioDeAsignacionDeUsuarioAPersonaImpl(persona, userId, null)
		        	);
			}
		} else {
			if(idPersona!=null) {
				//Busco la persona nueva
				PersonaBean personaNueva = personaDao.read(idPersona);
				String oldUserId = personaNueva.getUserId();
				personaNueva.setUserId(userId);
				personaDao.update(personaNueva);
	        	onCambioDeAsignacionDeUsuarioAPersona().notifyEvent(
		        		new CambioDeAsignacionDeUsuarioAPersonaImpl(personaNueva, oldUserId, userId)
		        	);
			}
		}
		
		/*Con el cambio de que la Bandeja de Entrada la obtiene con los permisos asignados en el LDAP (y no x PooledActor)
		//Se cambio para que no actualice los PooledActor de todas las tareas realacionadas con el usuario.
		*/
		//this.interceptor.update(userUpdateEvent);
	}

	public PersonaBean personaVinculada(String userId) {
		List<PersonaBean> personas = personaDao.findByUserId(userId);
		if(!personas.isEmpty()) {
			return personas.get(0);
		} else return null;
	}

	public UsuarioDTO getUsuarioDTO(String userId) {
		return getUsuarioDTO(userId, UsuarioDTOAssembler.getInstance());
	}

	private static Event<CambioDeAsignacionDeUsuarioAPersona> onCambioDeAsignacionDeUsuarioAPersona = null;
	protected static class CambioDeAsignacionDeUsuarioAPersonaImpl 
		implements CambioDeAsignacionDeUsuarioAPersona {

		private PersonaBean personaAfectada;
		private String usuarioAnterior;
		private String usuarioNuevo;
		

		public CambioDeAsignacionDeUsuarioAPersonaImpl(
				PersonaBean personaAfectada, 
				String usuarioAnterior,
				String usuarioNuevo
		) {
			this.personaAfectada=personaAfectada;
			this.usuarioAnterior = usuarioAnterior;
			this.usuarioNuevo = usuarioNuevo;
		}

		public PersonaBean personaAfectada() {
			return personaAfectada;
		}

		public String usuarioAnterior() {
			return usuarioAnterior;
		}

		public String usuarioNuevo() {
			return usuarioNuevo;
		}

		public Event<? extends Notification> event() {
			return UsuarioServiceImpl.onCambioDeAsignacionDeUsuarioAPersona;
		}
	}
	public Event<CambioDeAsignacionDeUsuarioAPersona> onCambioDeAsignacionDeUsuarioAPersona() {
		if(onCambioDeAsignacionDeUsuarioAPersona==null)
			onCambioDeAsignacionDeUsuarioAPersona = Event.create();
		return onCambioDeAsignacionDeUsuarioAPersona;
	}

	@SuppressWarnings("unchecked")
	public <DTO> List<DTO> findAll(Assembler<Usuario, DTO> assembler) {
    	List<Usuario> usuarios = usuarioDao.findAll();
    	Set<Usuario> usuariosOrdenados = new TreeSet<Usuario>(UsuarioComparator.instance());
    	usuariosOrdenados.addAll(usuarios);
    	usuarios = new ArrayList<Usuario>(usuariosOrdenados.size());
    	usuarios.addAll(usuariosOrdenados);
        return assembler.buildDto(usuarios);
	}

	public <DTO> DTO getUsuarioDTO(String userId, Assembler<Usuario, DTO> assembler) {
		return assembler.buildDto(findByPrimaryKey(userId));
	}

	public void create(String nombre, String apellido, String userId, String password, String[] grupos, boolean enableCryptoGraphicServices, String appPasword, Long idPersona) {
		create(nombre, apellido, userId, password, getGroupSet(grupos), enableCryptoGraphicServices, appPasword, idPersona);
	}
}
