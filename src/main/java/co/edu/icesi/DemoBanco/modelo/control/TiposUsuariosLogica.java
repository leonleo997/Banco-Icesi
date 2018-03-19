package co.edu.icesi.DemoBanco.modelo.control;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.dao.ITiposUsuariosDAO;
import co.edu.icesi.DemoBanco.dao.IUsuariosDAO;
import co.edu.icesi.DemoBanco.modelo.TiposUsuarios;
import co.edu.icesi.DemoBanco.modelo.Usuarios;

@Stateless
public class TiposUsuariosLogica implements ITiposUsuariosLogica {

	private static Logger log = LoggerFactory.getLogger(TiposUsuariosLogica.class);

	@EJB
	private ITiposUsuariosDAO tiposUsuariosDAO;

	@EJB
	private IUsuariosDAO usuariosDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveTiposUsuarios(TiposUsuarios entity) throws Exception {
		try {
			log.info("Inici� saveTiposUsuarios");

			if (entity.getTusuCodigo() == 0)
				throw new Exception("Debe existir un c�digo para el  tipo de usuario");
			if (("" + entity.getTusuCodigo()).length() > 10)
				throw new Exception("la c�ntidad de d�gitos del c�digo no debe exceder a 10");
			if (entity.getTusuNombre() == null || entity.getTusuNombre().equals(""))
				throw new Exception("Debe haber un nombre para el  tipo de usuario");
			if (entity.getTusuNombre().length() > 50)
				throw new Exception("la c�ntidad de caracteres del nombre no debe exceder a 50");

			TiposUsuarios tiposUsuarios = tiposUsuariosDAO.findById(entity.getTusuCodigo());
			if (tiposUsuarios != null)
				throw new Exception("Ya existe un tipo de usuario con c�digo " + entity.getTusuCodigo());

			tiposUsuariosDAO.save(entity);
			log.info("Se guard� exitosamente");
		} catch (Exception e) {
			log.error("Error al guardar un tipo de usuario " + e.getMessage());
			throw e;
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateTiposUsuarios(TiposUsuarios entity) throws Exception {
		try {
			log.info("Inici� updateTiposUsuarios");

			if (entity.getTusuCodigo() == 0)
				throw new Exception("Debe existir un c�digo para el  tipo de usuario");
			if (("" + entity.getTusuCodigo()).length() > 10)
				throw new Exception("la c�ntidad de d�gitos del c�digo no debe exceder a 10");
			if (entity.getTusuNombre() == null || entity.getTusuNombre().equals(""))
				throw new Exception("Debe haber un nombre para el  tipo de usuario");
			if (entity.getTusuNombre().length() > 50)
				throw new Exception("la c�ntidad de caracteres del nombre no debe exceder a 50");

			TiposUsuarios tiposUsuarios = tiposUsuariosDAO.findById(entity.getTusuCodigo());
			if (tiposUsuarios == null)
				throw new Exception("No existe un tipo de usuario con c�digo " + entity.getTusuCodigo());

			tiposUsuariosDAO.update(entity);
			log.info("Se modific� exitosamente");
		} catch (Exception e) {
			log.error("Error al modificar un tipo de usuario " + e.getMessage());
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteTiposUsuarios(long codigo) throws Exception {
		try {
			log.info("inici� deleteTiposUsuarios");

			if (codigo == 0)
				throw new Exception("Debe existir un c�digo para el  tipo de usuario");

			List<Usuarios> users = usuariosDAO.findByProperty("tiposUsuarios.tusuCodigo", "" + codigo);
			if (users != null && users.isEmpty() == false)
				throw new Exception("Existen Usuarios asociados al tipo de usuario");

			TiposUsuarios entity = tiposUsuariosDAO.findById(codigo);
			if (entity == null)
				throw new Exception("No existe tipo de usuario con c�digo" + codigo);
			tiposUsuariosDAO.delete(entity);

			log.info("termin� de eliminar exitosamente");

		} catch (Exception e) {
			log.error("error al eliminar " + e.getMessage());
			throw e;
		}
	}

	@TransactionAttribute
	public List<TiposUsuarios> getTiposUsuarios() throws Exception {
		log.info("inici� getTiposUsuarios");
		return tiposUsuariosDAO.findAll();
	}

	@TransactionAttribute
	public TiposUsuarios getTiposUsuarios(long codigo) throws Exception {
		log.info("inici� getTiposUsuariosById");
		try {
			if (codigo == 0)
				throw new Exception("Debe existir el c�digo");
			return tiposUsuariosDAO.findById(codigo);

		} catch (Exception e) {
			log.error("Fall� el getTiposUsuariosID");
			throw e;
		}
	}

	@Override
	public List<TiposUsuarios> consultarTiposUsuarios(long tusuCodigo, String tusuNombre) throws Exception {
		List<String> propiedades = new ArrayList<String>();
		if (tusuCodigo > 0)
			propiedades.add("tusuCodigo=" + tusuCodigo);
		if (tusuNombre != null && !tusuNombre.equals(""))
			propiedades.add("tusuNombre=" + "'" + tusuNombre + "'");
		return tiposUsuariosDAO.findByProperties(propiedades);
	}

}
