package co.edu.icesi.DemoBanco.modelo.control;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.dao.IConsignacionesDAO;
import co.edu.icesi.DemoBanco.dao.IRetirosDAO;
import co.edu.icesi.DemoBanco.dao.IUsuariosDAO;
import co.edu.icesi.DemoBanco.modelo.Consignaciones;
import co.edu.icesi.DemoBanco.modelo.Retiros;
import co.edu.icesi.DemoBanco.modelo.Usuarios;

@Stateless
public class UsuariosLogica implements IUsuariosLogica {

	private static final Logger log = LoggerFactory.getLogger(UsuariosLogica.class);

	@EJB
	private IUsuariosDAO usuariosDAO;

	@EJB
	private IConsignacionesDAO consignacionesDAO;

	@EJB
	private IRetirosDAO retirosDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveUsuarios(Usuarios entity) throws Exception {

		try {

			log.info("inicio saveUsuarios");

			if (entity.getUsuCedula() == 0) {
				throw new Exception("La cédula del usuario es obligatoria");
			}

			if ((entity.getUsuCedula() + "").length() > 10) {
				throw new Exception("El tamaño de la cédula del usuario no debe ser mayor a 10 digitos");
			}

			if (entity.getUsuNombre() == null || entity.getUsuNombre().equals("")) {
				throw new Exception("El nombre del usuario es obligatorio");
			}

			if (entity.getUsuNombre().length() > 50) {
				throw new Exception("El tamaño del nombre del usuario no debe ser mayor a 50 caracteres");
			}

			if (getUsuarios(entity.getUsuCedula()) != null) {
				throw new Exception("Ya existe un usuario con la cédula " + entity.getUsuCedula());
			}

			if (entity.getUsuLogin() == null || entity.getUsuLogin().equals("")) {
				throw new Exception("El login del usuario es obligatorio");
			}

			if (entity.getUsuLogin().length() > 30) {
				throw new Exception("El login del usuario no debe ser mayor a 30 caracteres");
			}

			if (entity.getUsuClave() == null || entity.getUsuClave().equals("")) {
				throw new Exception("La clave del usuario es obligatoria");
			}

			if (entity.getUsuClave().length() > 50) {
				throw new Exception("La clave del usuario no debe ser mayor a 50 caracteres");
			}

			Usuarios usuarios = usuariosDAO.findById(entity.getUsuCedula());

			if (usuarios != null) {
				throw new Exception("El usuario ya existe");
			}

			usuariosDAO.save(entity);
			log.info("Guardó satisfactoriamente");

		} catch (Exception e) {
			log.error("saveUsuarios falló", e);
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateUsuarios(Usuarios entity) throws Exception {

		try {

			log.info("inicio updateUsuarios");

			if (entity.getUsuCedula() == 0) {
				throw new Exception("La cédula del usuario es obligatoria");
			}

			if ((entity.getUsuCedula() + "").length() > 10) {
				throw new Exception("El tamaño de la cédula del usuario no debe ser mayor a 10 digitos");
			}

			if (entity.getUsuNombre() == null || entity.getUsuNombre().equals("")) {
				throw new Exception("El nombre del usuario es obligatorio");
			}

			if (entity.getUsuNombre().length() > 50) {
				throw new Exception("El tamaño del nombre del usuario no debe ser mayor a 50 caracteres");
			}

			if (entity.getUsuLogin() == null || entity.getUsuLogin().equals("")) {
				throw new Exception("El login del usuario es obligatorio");
			}

			if (entity.getUsuLogin().length() > 30) {
				throw new Exception("El login del usuario no debe ser mayor a 30 caracteres");
			}

			if (entity.getUsuClave() == null || entity.getUsuClave().equals("")) {
				throw new Exception("La clave del usuario es obligatoria");
			}

			if (entity.getUsuClave().length() > 50) {
				throw new Exception("La clave del usuario no debe ser mayor a 50 caracteres");
			}

			Usuarios usuarios = usuariosDAO.findById(entity.getUsuCedula());

			if (usuarios == null) {
				throw new Exception("El usuario debe existir");
			}

			usuariosDAO.update(entity);
			log.info("Modificó satisfactoriamente");

		} catch (Exception e) {
			log.error("updateUsuarios falló", e);
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteUsuarios(long usuCedula) throws Exception {

		try {

			log.info("inició deleteUsuarios");
			if (usuCedula == 0) {
				throw new Exception("La cédula del usuario es obligatoria");
			}

			List<Consignaciones> consignaciones = consignacionesDAO.findByProperty("usuarios.usuCedula", usuCedula);

			if (consignaciones != null && consignaciones.isEmpty() == false) {
				throw new Exception("El usuario con la cédula " + usuCedula
						+ " no se puede eliminar porque tiene consignaciones asociadas");
			}

			List<Retiros> retiros = retirosDAO.findByProperty("usuarios.usuCedula", usuCedula);

			if (retiros != null && retiros.isEmpty() == false) {
				throw new Exception("El usuario con la cédula " + usuCedula
						+ " no se puede eliminar porque tiene retiros asociados");
			}

			Usuarios entity = getUsuarios(usuCedula);

			if (entity == null) {
				throw new Exception("No existe un usuario con la cédula " + usuCedula);
			}

			usuariosDAO.delete(entity);
			log.info("Eliminó satisfactoriamente");

		} catch (Exception e) {
			log.error("deleteUsuarios falló", e);
			throw e;
		}
	}

	@TransactionAttribute
	public List<Usuarios> getUsuarios() throws Exception {
		log.info("inicia get usuarios");
		return usuariosDAO.findAll();
	}

	@TransactionAttribute
	public Usuarios getUsuarios(long usuCedula) throws Exception {
		log.info("inició getUsuariosById");
		Usuarios usuarios = null;

		try {

			if (usuCedula == 0) {
				throw new Exception("La cédula del usuario es obligatoria");
			}

			usuarios = usuariosDAO.findById(usuCedula);
			if(usuarios==null)
				throw new Exception("El usuario no es válido");

		} catch (Exception e) {
			log.error("getUsuariosById falló", e);
			throw e;
		}
		return usuarios;
	}

	@TransactionAttribute
	public List<Usuarios> consultarUsuarios(long Cedula, long tipoUsuarios, String Nombre, String Login, String Clave) {
		List<String> propiedades = new ArrayList<String>();
		if (Cedula > 0)
			propiedades.add("usuCedula=" + Cedula);
		if (tipoUsuarios >0)
			propiedades.add("tiposUsuarios=" + tipoUsuarios);
		if (Nombre != null && !Nombre.equals(""))
			propiedades.add("usuNombre=" + "'" + Nombre + "'");
		if (Login != null && !Login.equals(""))
			propiedades.add("usuLogin=" + "'" + Login + "'");
		if (Clave != null && !Clave.equals(""))
			propiedades.add("usuClave=" + "'" + Clave + "'");
		return usuariosDAO.findByProperties(propiedades);
	}

}
