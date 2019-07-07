package com.fontar.bus.impl.configuracion;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.map.UnmodifiableMap;
import org.apache.commons.lang.math.RandomUtils;

import com.fontar.bus.api.configuracion.GrupoService;
import com.fontar.data.impl.dao.ldap.GrupoAbstractoDao;
import com.fontar.data.impl.dao.ldap.GrupoConInstrumentoDao;
import com.fontar.data.impl.dao.ldap.GrupoDao;
import com.fontar.data.impl.dao.ldap.GrupoInstrumentableDao;
import com.fontar.data.impl.dao.ldap.PermisoDao;
import com.fontar.data.impl.domain.ldap.Grupo;
import com.fontar.data.impl.domain.ldap.GrupoAbstracto;
import com.fontar.data.impl.domain.ldap.GrupoConInstrumento;
import com.fontar.data.impl.domain.ldap.GrupoInstrumentable;
import com.fontar.data.impl.domain.ldap.Permiso;
import com.fontar.data.impl.domain.ldap.SearchCriteria;
import com.fontar.data.impl.domain.ldap.Usuario;
import com.fontar.seguridad.GroupUpdateEvent;
import com.fontar.seguridad.SecurityConfigInterceptor;


public class GrupoServiceImpl implements GrupoService {

    
	private GrupoAbstractoDao grupoAbstractoDao;
	
	
	private GrupoDao grupoDao;

    private GrupoInstrumentableDao grupoInstrumentableDao;
    
    private GrupoConInstrumentoDao grupoConInstrumentoDao;
    
    private PermisoDao permisoDao;
    
    private Map<String,SortedSet<Permiso>> permisos;
    
    private SecurityConfigInterceptor interceptor;
    
    public GrupoAbstractoDao getGrupoAbstractoDao() {
		return grupoAbstractoDao;
	}

	public void setGrupoAbstractoDao(GrupoAbstractoDao grupoAbstractoDao) {
		this.grupoAbstractoDao = grupoAbstractoDao;
	}

	public GrupoConInstrumentoDao getGrupoConInstrumentoDao() {
		return grupoConInstrumentoDao;
	}

	public void setGrupoConInstrumentoDao(GrupoConInstrumentoDao grupoConInstrumentoDao) {
		this.grupoConInstrumentoDao = grupoConInstrumentoDao;
	}

	public GrupoInstrumentableDao getGrupoInstrumentableDao() {
		return grupoInstrumentableDao;
	}

	public void setGrupoInstrumentableDao(GrupoInstrumentableDao grupoInstrumentableDao) {
		this.grupoInstrumentableDao = grupoInstrumentableDao;
	}

	public GrupoDao getGrupoDao() {
		return grupoDao;
	}

	public PermisoDao getPermisoDao() {
		return permisoDao;
	}

	public void setPermisoDao(PermisoDao permisoDao) {
		this.permisoDao = permisoDao;
	}

	public void setGrupoDao(GrupoDao groupDao) {
        this.grupoDao = groupDao;
    }

    /*
     * @see org.springframework.ldap.samples.person.service.GrupoService#create(java.lang.String,
     *      java.util.Set)
     */
    public void create(String name, String idGrupo, Set<Usuario> usuarios, Set<Permiso> permisos) {

        Grupo group = new Grupo();
        group.setNombre(name);
        group.setIdGrupo(idGrupo);
        //group.setIdInstrumento(idInstrumento); 
        group.setUsuarios(usuarios);
        group.setPermisos(permisos);        

        grupoDao.create(group);
    }

    /*
     * @see org.springframework.ldap.samples.person.service.GrupoService#update(org.springframework.ldap.samples.person.domain.Grupo)
     */
    public void update(Grupo group) {
        grupoDao.update(group);
    }

    /*
     * @see org.springframework.ldap.samples.person.service.GrupoService#delete(org.springframework.ldap.samples.person.domain.Grupo)
     */
    public void delete(Grupo group) {
        grupoDao.delete(group);
    }

    /*
     * @see org.springframework.ldap.samples.person.service.GrupoService#findByPrimaryKey(java.lang.String)
     */
    public Grupo findByPrimaryKey(String name) {
        return grupoDao.findByPrimaryKey(name);
    }

    
    public GrupoAbstracto findByName(String name) {
        return grupoAbstractoDao.findByName(name);
    }
    
    public GrupoAbstracto getGrupo(String name) {
    	return this.grupoAbstractoDao.get( name );
    }
    
    @SuppressWarnings("unchecked")
	public Collection<Grupo> findAll() {
    	Collection<Grupo> grupos = new TreeSet<Grupo>( new GrupoComparator() );
    	grupos.addAll( grupoDao.findAll() );
    	grupos.addAll( grupoConInstrumentoDao.findAll() );
        return grupos;
    }
    
    @SuppressWarnings("unchecked")
	public Collection<Grupo> filter(Predicate predicate) {
    	Collection<Grupo> grupos = new TreeSet<Grupo>( new GrupoComparator() );
    	grupos.addAll( grupoDao.findAll() );
    	grupos.addAll( grupoConInstrumentoDao.findAll() );
    	CollectionUtils.filter(grupos , predicate);
        return grupos;
    }

    /*
     * @see org.springframework.ldap.samples.person.service.GrupoService#find(org.springframework.ldap.samples.person.domain.SearchCriteria)
     */
    public List find(SearchCriteria criteria) {
        return grupoDao.find(criteria);
    }

	public void delete(String name) {
		Grupo grupo = this.findByPrimaryKey( name );
		this.delete( grupo);
	}

	public void create(String name) {
		String id = String.valueOf(RandomUtils.nextLong());
		this.create(name, id, new HashSet<Usuario>(), new HashSet<Permiso>());
	}

	public List<Permiso> getPermissionsAsignable(Grupo group) {
		if(this.permisos==null)
			this.initializePermisos();
		List<Permiso> asignable = new LinkedList<Permiso>();
		for (String key : this.permisos.keySet()) {
			asignable.addAll( this.permisos.get(key));
		}
		return asignable;
	}

	public List<Permiso> getPermissionsAsignable(String grupo) {
		return this.permisoDao.findAll();
	}

	//Crea una nuevo Grupo
	public void create(String name, String[] permisos) {
		String id = String.valueOf(RandomUtils.nextLong());
		this.create(name,id, new HashSet<Usuario>() , this.getPermissionSet( permisos));
	}

	
	/*
	 * crea una nuevo grupo, si el instrumento es null entonces crea un grupo simple
	 * caso contrario crea un grupo instrumentable y un grupo con instrumento 
	 **/
	public void create(String nombre, Long instrumento, String[] permisosSeleccionados) {
		if(instrumento!=null)
			this.createGrupoConIsntrumento( nombre, instrumento,permisosSeleccionados);
		else
			this.create(nombre, permisosSeleccionados);
	}
	
	//Crea un nuevo grupo instrumentable y un grupo con instrumento 
	public void createGrupoConIsntrumento(String nombre, Long instrumento, String[] permisosSeleccionados) {
		GrupoConInstrumento grupoConInstrumento = new GrupoConInstrumento( );
		grupoConInstrumento.setIdGrupo( this.generateId() );
		grupoConInstrumento.setIdInstrumento( instrumento );
		grupoConInstrumento.setNombre( nombre);
		grupoConInstrumento.setUsuarios( new HashSet<Usuario>());
		grupoConInstrumento.setPermisos( this.getPermissionSet( permisosSeleccionados));
		this.grupoConInstrumentoDao.create( grupoConInstrumento);
	}
	
	//Obtiene un set de permisos a partir de la lista de ids
	private Set<Permiso> getPermissionSet(String[] permissions){
		Set<Permiso> permissionsSet = new HashSet<Permiso>();
		for (String permissionId : permissions) {
			Permiso permiso = this.permisoDao.findByPrimaryKey( permissionId);
			permissionsSet.add( permiso);
		}
		return permissionsSet;
	}
	
	private String generateId(){
		return String.valueOf(RandomUtils.nextLong());	
	}
	
	public GrupoInstrumentable createInstrumentable(String nombre, Set<Permiso> permisos){
		GrupoInstrumentable grupoInstrumentable = new GrupoInstrumentable();
		grupoInstrumentable.setIdGrupo( this.generateId());
		grupoInstrumentable.setPermisos( permisos );
		grupoInstrumentable.setUsuarios( new HashSet<Usuario>());
		grupoInstrumentable.setNombre( nombre );
		this.grupoInstrumentableDao.create( grupoInstrumentable );
		return grupoInstrumentable;
	}

	public GrupoConInstrumento findInstrumentableByPrimaryKey(String name) {
		return this.grupoConInstrumentoDao.findByPrimaryKey( name );
	}

	public void deleteInstrumentable(String grupo) {
		this.grupoConInstrumentoDao.delete( grupo );
	}

	public void update(GrupoAbstracto grupo) {
		if(grupo instanceof GrupoConInstrumento)
			this.grupoConInstrumentoDao.update( (GrupoConInstrumento) grupo );
		else
			this.grupoDao.update( (Grupo) grupo );
	}

	@SuppressWarnings("unchecked")
	public Collection getPermissionsAsignable(GrupoAbstracto grupo) {
		if(this.permisos==null)
			this.initializePermisos();
		List<Permiso> asignable = new LinkedList<Permiso>();
		for (String key : this.permisos.keySet()) {
			asignable.addAll( this.permisos.get(key));
		}
		return asignable;
	}

	public void update(String idGrupo, String[] permisosSeleccionados) {
		GrupoAbstracto grupoAbstracto = this.getGrupo( idGrupo );
		Set<Permiso> assignment = this.getPermissionSet( permisosSeleccionados );
		/*Con el cambio de que la Bandeja de Entrada la obtiene con los permisos asignados en el LDAP (y no x PooledActor)
		//Se cambio para que no actualice los PooledActor de todas las tareas realacionadas con el grupo.
		*/
		//GroupUpdateEvent updateEvent = new GroupUpdateEvent(grupoAbstracto, assignment);
		grupoAbstracto.setPermisos( assignment );
		this.update( grupoAbstracto );
		//this.interceptor.update( updateEvent );
	}

	
	public void removeMemberShip(Usuario usuario){
    	Collection<GrupoAbstracto> grupos = this.grupoAbstractoDao.getGroupMembership( usuario.getDnAsString());
    	for (GrupoAbstracto grupo: grupos) {
			grupo.remove( usuario );
			this.update( grupo );
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addMemberShip(Usuario usuario, Collection<GrupoAbstracto> grupos){
		for (GrupoAbstracto grupo: grupos) {
			grupo.getUsuarios().add( usuario );
			this.update( grupo );
		}
	}

	public void update(String idGrupo, String nombre, String[] permisosSeleccionados) {
		GrupoAbstracto grupoAbstracto = this.getGrupo( idGrupo );
		Set<Permiso> assignment = this.getPermissionSet( permisosSeleccionados );
		/*Con el cambio de que la Bandeja de Entrada la obtiene con los permisos asignados en el LDAP (y no x PooledActor)
		//Se cambio para que no actualice los PooledActor de todas las tareas realacionadas con el grupo.
		*/
		//GroupUpdateEvent updateEvent = new GroupUpdateEvent(grupoAbstracto, assignment);
		grupoAbstracto.setPermisos( assignment);
		grupoAbstracto.setNombre( nombre );
		this.update( grupoAbstracto );
		//this.interceptor.update( updateEvent );
	}
	
	@SuppressWarnings("unchecked")
	private void initializePermisos(){
		Map<String,SortedSet<Permiso>> instance  = new TreeMap<String,SortedSet<Permiso>>();
		List<Permiso> permisos = this.permisoDao.findAll();
		for (Permiso permiso: permisos) {
			SortedSet<Permiso> set = instance.get(permiso.getModulo());
			if(set==null){
				set = new TreeSet<Permiso>();
				instance.put(permiso.getModulo(), set);
			}
			set.add(permiso);
		}
		this.permisos =  UnmodifiableMap.decorate( instance );
	}

	public SecurityConfigInterceptor getInterceptor() {
		return interceptor;
	}

	public void setInterceptor(SecurityConfigInterceptor interceptor) {
		this.interceptor = interceptor;
	}
	
	
}
