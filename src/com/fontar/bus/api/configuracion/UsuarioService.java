/*
 * Copyright 2005-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fontar.bus.api.configuracion;

import java.util.List;
import java.util.Set;

import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.ldap.GrupoAbstracto;
import com.fontar.data.impl.domain.ldap.SearchCriteria;
import com.fontar.data.impl.domain.ldap.Usuario;
import com.fontar.seguridad.NoValidoComoPasswordException;
import com.fontar.seguridad.cripto.PasswordInvalidaException;
import com.fontar.web.action.configuracion.seguridad.UsuarioDTO;
import com.fontar.web.action.configuracion.seguridad.VisualizarGrupoDTO;
import com.pragma.data.api.assembler.Assembler;
import com.pragma.util.event.Event;
import com.pragma.util.event.Notification;

/**
 * Servicios para la administración de usuarios.
 * 
 */
public interface UsuarioService {
	
	public static final int MIN_PASSWORD_LENGTH = 4;
	//FIXME Encontrar el valor adecuado para esta constante segun el framework de seguridad
	public static final int MAX_PASSWORD_LENGTH = 1000;
	
	/**
	 * Elimina el usuario
	 * @param usuario
	 */
    public void delete(Usuario usuario);
    
    /** 
     * Eliminar el usuario por el nombre
     **/
    public void delete(String name);
    
    /** 
     * Obtiene el usuario por el nombre
     * 
     */
    public Usuario findByPrimaryKey(String name);
    
    /**
     * Obtiene una lista de usuarios en funcion de un criterio de busqueda.
     * @param criteria criterio de busqueda
     * @return
     */
    public List find(SearchCriteria criteria);
    
    /** 
     * Obtiene la lista de todos los usuarios
     ***/
    public List<UsuarioDTO> findAll();

    /** 
     * Obtiene y ensambla con el assembler dado la 
     * lista de todos los usuarios.
     */
    public <DTO> List<DTO> findAll(Assembler<Usuario, DTO> assembler);

    /**
     * Crea un nuevo usuario con una password default y sin grupos asociados
     * @param userName
     * @param userLastName
     * @param userId
     */
    public void create(String nombre, String apellido, String userId,  String password, String[] grupos, boolean enableCryptoGraphicServices , String appPasword, Long idPersona);
    
    /**
     * Define un nuevo usuario. Este servicio requiere la contraseña de la aplicación para poder registrar el nuevo usuario. 
     * @param nombre nombre del usuario
     * @param apellido apellido del usuario
     * @param userId identificador de login del usuario
     * @param password contraseña de ingreso para el nuevo usuario
     * @param grupos grupos asignados.
     * @param enableCryptoGraphicServices
     * @param appPasword contraseña de la aplicación
     * @param idPersona identificador de persona relacionada (opcional)
     */
    public void create(String nombre, String apellido, String userId,  String password, Set<GrupoAbstracto> grupos, boolean enableCryptoGraphicServices , String appPasword, Long idPersona);
	
    /**
     * Define un nuevo usuario. Este servicio requiere la contraseña de la aplicación para poder registrar el nuevo usuario.
     * @param nombre nombre del usuario
     * @param apellido apellido del usuario
     * @param userId identificador de login del usuario
     * @param password contraseña de ingreso para el nuevo usuario
     * @param grupos grupos asignados.
     * @param appPasword contraseña de la aplicación
     * @param idPersona identificador de persona relacionada (opcional)
     */
    public void create(String nombre, String apellido, String userId,  String password, String[] grupos, String appPasword, Long idPersona);
	
	/**
	 * Obtiene el usuario en funcion del identificador.
	 * @param userId
	 * @return
	 */
    public UsuarioDTO getUsuarioDTO(String userId);

    /**
     * 
     * @param <DTO>
     * @param userId
     * @param assembler
     * @return
     */
	public <DTO> DTO getUsuarioDTO(String userId, Assembler<Usuario, DTO> assembler);
	
	/**
	 * Registra contraseña privada del usuario.
	 * @param password
	 */
	public void registrarClavePrivada(String password);
	
	/**
	 * Actualiza la contraseña privada del usuario. 
	 * @param currentPassword
	 * @param newPassword
	 * @throws PasswordInvalidaException
	 */public void actualizarClavePrivada(String currentPassword, String newPassword) throws PasswordInvalidaException;
	
	/**
	 * Obtiene el usuario actual de la sesion sistema. 
	 * @return
	 */
	 public Usuario getCurrentUser();

	 /**
	  * Actualiza los datos del usuario.
	  * @param userId
	  * @param userName
	  * @param userLastName
	  * @param groups
	  * @param idPersona
	  */
	public void update(String userId, String userName, String userLastName, String[] groups, Long idPersona);
	
	/**
	* 
	* @param groups
	* @return
	*/
	public Set<GrupoAbstracto> getGroupSet(String[] groups);
	
	
	/**
	 * 
	 * @param groups
	 * @return
	 */
	public Set<VisualizarGrupoDTO> getGroupDTOSet(String[] groups);

	/** 
	 * Chequea que el @userIdentifier no este asginado.
	 **/
	public boolean availableIdentifier(String userIdentifier);

	/** 
	 * Asigna una nueva clave de autenticacion al usuario
	 **/
	public void resetearClaveAutenticacion(String userId, String password);

	/**
	 * Informa la persona relacionada con un usuario dado.
	 * @param userId
	 * @return
	 */
	public PersonaBean personaVinculada(String userId);

	/**
	 * Modifica la contraseña de un usaurio. 
	 * @param userId
	 * @param passwordAnterior
	 * @param passwordNuevo
	 * @throws PasswordInvalidaException
	 * @throws NoValidoComoPasswordException
	 */public void cambiarPassword(
			String userId, 
			String passwordAnterior, 
			String passwordNuevo) 
	throws PasswordInvalidaException, NoValidoComoPasswordException;
	/**
	 * Valida que una cadena pueda ser usada como password. Actualmente
	 * se chequea solamente que mida entre 4 y 1000 caracteres.
	 * @param passwordCandidato
	 * @return
	 */
	public boolean esValidoComoPassword(String passwordCandidato);
	/**
	 * Valida si una cadena puede ser usada como password. Si no
	 * cumple con las condiciones dispara NoValidoComoPasswordException.
	 * @param passwordCandidate
	 * @throws NoValidoComoPasswordException 
	 */
	public void validarQueLaCadenaSeaValidaComoPassword(String passwordCandidate) throws NoValidoComoPasswordException;
	/**
	 * Evento que se dispara al cambiar la vinculacion de un usuario con una
	 * persona o al cortar con la vinculacion. El evento envia una notificacion
	 * de tipo CambioDeAsignacionDeUsuarioAPersonaNotification.
	 * @return
	 */
	public Event<CambioDeAsignacionDeUsuarioAPersona> onCambioDeAsignacionDeUsuarioAPersona();
	/**
	 * Notificacion de cambio en la vinculacion entre un usuario y una persona.
	 * @author llobeto
	 *
	 */
	public interface CambioDeAsignacionDeUsuarioAPersona extends Notification {
		public PersonaBean personaAfectada(); 
		public String usuarioAnterior(); 
		public String usuarioNuevo(); 
	}
}
