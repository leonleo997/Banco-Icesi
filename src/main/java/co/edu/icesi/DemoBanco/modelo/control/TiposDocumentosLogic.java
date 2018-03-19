package co.edu.icesi.DemoBanco.modelo.control;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.dao.IClientesDAO;
import co.edu.icesi.DemoBanco.dao.ITiposDocumentosDAO;
import co.edu.icesi.DemoBanco.modelo.Clientes;
import co.edu.icesi.DemoBanco.modelo.TiposDocumentos;
import co.edu.icesi.DemoBanco.modelo.utilities.Utilities;

@Stateless
public class TiposDocumentosLogic implements ITiposDocumentosLogic {

	private static Logger log = LoggerFactory.getLogger(TiposDocumentos.class);

	@EJB
	private ITiposDocumentosDAO tipoDocumentosDAO;

	@EJB
	private IClientesDAO clientesDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveTiposDocumentos(TiposDocumentos entity) throws Exception {
		try {
			log.info("Inicia saveTiposDocumentos");
			if (entity.getTdocCodigo() == 0) {
				throw new Exception("El código del tipo de documento es obligatorio");
			}
			if ((entity.getTdocCodigo() + "").length() > 10) {
				throw new Exception("El tamaño código del tipo de documento no debe ser mayor a 10 de lenght");
			}
			if (entity.getTdocNombre() == null || entity.getTdocNombre().equals("")) {
				throw new Exception("El código del tipo de documento es obligatorio");
			}

			if (Utilities.checkWordAndCheckWithlength(entity.getTdocNombre(), 50) == false) {
				throw new Exception("El tamaño del nombre no debe ser mayor a 50 caracteres");
			}

			if (getTiposDocumentos(entity.getTdocCodigo()) != null)
				throw new Exception("ya existe un tipo de docuento con el código");

			tipoDocumentosDAO.save(entity);
			log.info("Guardó satisfactoriamente");

		} catch (Exception e) {
			log.info("SaveTiposDocumentos falló", e.getMessage());
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateTiposDocumentos(TiposDocumentos entity) throws Exception {
		try {
			log.info("Inicia saveTiposDocumentos");
			if (entity.getTdocCodigo() == 0) {
				throw new Exception("El código del tipo de documento es obligatorio");
			}
			if ((entity.getTdocCodigo() + "").length() > 10) {
				throw new Exception("El tamaño código del tipo de documento no debe ser mayor a 10 de lenght");
			}

			if (entity.getTdocNombre() == null || entity.getTdocNombre().equals("")) {
				throw new Exception("El código del tipo de documento es obligatorio");
			}

			if (Utilities.checkWordAndCheckWithlength(entity.getTdocNombre(), 50) == false) {
				throw new Exception("El tamaño del nombre no debe ser mayor a 50 caracteres");
			}

			if (getTiposDocumentos(entity.getTdocCodigo()) == null)
				throw new Exception("no existe un tipo de documento con el código");

			tipoDocumentosDAO.update(entity);
			log.info("modificó satisfactoriamente");

		} catch (Exception e) {
			log.info("Update TiposDocumentos falló", e.getMessage());
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteTiposDocumentos(long codigo) throws Exception {
		try {
			log.info("Inicia delete TiposDocumentos");

			if (codigo == 0) {
				throw new Exception("El código debe existir");
			}

			List<Clientes> clientes = clientesDAO.findByProperty("tiposDocumentos.tdocCodigo", codigo);

			if (clientes != null && clientes.isEmpty() == false)
				throw new Exception("El tipo de documento no se puede eliminar porque tiene clientes asociados");

			TiposDocumentos entity = getTiposDocumentos(codigo);
			if (entity == null)
				throw new Exception("No existe un tipo de documento " + codigo);

			tipoDocumentosDAO.delete(entity);
			log.info("Eliminó satisfactoriamente");
		} catch (Exception e) {
			log.error("deleteTiposDocumentos falló" + e.getMessage());
			throw e;
		}

	}

	@TransactionAttribute
	public List<TiposDocumentos> getTiposDocumentos() throws Exception {
		log.info("Inicia findAll TiposDocumentos");
		return tipoDocumentosDAO.findAll();
	}

	@TransactionAttribute
	public TiposDocumentos getTiposDocumentos(long codigo) throws Exception {
		try {
			log.info("Inicia findbyidTiposDocumentos");

			if (codigo == 0) {
				throw new Exception("El código debe existir");
			}

			return tipoDocumentosDAO.findById(codigo);
		} catch (Exception e) {
			log.error("no existe un tipo de documento asociado al id " + codigo + " " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<TiposDocumentos> consultarTiposDocumentos(long tdocCodigo, String tdocNombre) throws Exception {
		List<String> propiedades = new ArrayList<String>();
		if (tdocCodigo > 0)
			propiedades.add("tdocCodigo=" + tdocCodigo);
		if (tdocNombre != null && !tdocNombre.equals(""))
			propiedades.add("tdocNombre=" + "'" + tdocNombre + "'");
		return tipoDocumentosDAO.findByProperties(propiedades);
	}

}
