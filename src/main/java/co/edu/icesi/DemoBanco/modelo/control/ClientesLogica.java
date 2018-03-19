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
import co.edu.icesi.DemoBanco.dao.ICuentasDAO;
import co.edu.icesi.DemoBanco.modelo.Clientes;
import co.edu.icesi.DemoBanco.modelo.Cuentas;
import co.edu.icesi.DemoBanco.modelo.utilities.Utilities;

@Stateless
public class ClientesLogica implements IClientesLogica {

	private static Logger log = LoggerFactory.getLogger(ClientesLogica.class);

	@EJB
	private IClientesDAO clientesDAO;

	@EJB
	private ICuentasDAO cuentasDAO;

	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveClientes(Clientes entity) throws Exception {
		try {
			log.info("Inicia saveClientes");
			if (entity.getCliId() == 0) {
				throw new Exception("El id del cliente es obligatorio");
			}
			if ((entity.getCliId() + "").length() > 10 || (entity.getCliId() + "").length() < 8) {
				throw new Exception("El tamaño id del cliente no debe ser mayor a 10 ni menor a 8 de lenght");
			}

			if (entity.getCliNombre() == null || entity.getCliNombre().equals("")) {
				throw new Exception("El nombre del cliente es obligatorio");
			}

			if (entity.getCliDireccion() == null || entity.getCliDireccion().equals("")) {
				throw new Exception("La dirección del cliente es obligatorio");
			}

			if (entity.getCliTelefono() == null || entity.getCliTelefono().equals("")) {
				throw new Exception("El teléfono del cliente es obligatorio");
			}

			if (Utilities.checkWordAndCheckWithlength(entity.getCliDireccion(), 50) == false) {
				throw new Exception("El tamaño de la dirección no debe ser mayor a 50 caracteres");
			}

			if (entity.getCliMail() != null
					&& Utilities.checkWordAndCheckWithlength(entity.getCliMail(), 50) == false) {
				throw new Exception("El tamaño de la dirección mail no debe ser mayor a 50 caracteres");
			}

			if (Utilities.checkWordAndCheckWithlength(entity.getCliNombre(), 50) == false) {
				throw new Exception("El tamaño del nombre no debe ser mayor a 50 caracteres");
			}

			if (Utilities.checkWordAndCheckWithlength(entity.getCliTelefono(), 50) == false) {
				throw new Exception("El tamaño del teléfono no debe ser mayor a 50 caracteres");
			}

			if (entity.getTiposDocumentos() == null)
				throw new Exception("Debe tener un tipo de documento asociado");

			if (clientesDAO.findById(entity.getCliId()) != null)
				throw new Exception("ya existe un cliente con ese ID");


			clientesDAO.save(entity);
			log.info("Guardó satisfactoriamente");

		} catch (Exception e) {
			log.info("SaveClientes falló", e.getMessage());
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateClientes(Clientes entity) throws Exception {
		try {
			log.info("Inicia updateClientes");
			if (entity.getCliId() == 0) {
				throw new Exception("El id del cliente es obligatorio");
			}
			if ((entity.getCliId() + "").length() > 10) {
				throw new Exception("El tamaño id del cliente no debe ser mayor a 10 de lenght");
			}

			if (entity.getCliNombre() == null || entity.getCliNombre().equals("")) {
				throw new Exception("El nombre del cliente es obligatorio");
			}

			if (entity.getCliDireccion() == null || entity.getCliDireccion().equals("")) {
				throw new Exception("La dirección del cliente es obligatorio");
			}

			if (entity.getCliTelefono() == null || entity.getCliTelefono().equals("")) {
				throw new Exception("El teléfono del cliente es obligatorio");
			}

			if (Utilities.checkWordAndCheckWithlength(entity.getCliDireccion(), 50) == false) {
				throw new Exception("El tamaño de la dirección no debe ser mayor a 50 caracteres");
			}

			if (entity.getCliMail() != null
					&& Utilities.checkWordAndCheckWithlength(entity.getCliMail(), 50) == false) {
				throw new Exception("El tamaño de la dirección mail no debe ser mayor a 50 caracteres");
			}

			if (Utilities.checkWordAndCheckWithlength(entity.getCliNombre(), 50) == false) {
				throw new Exception("El tamaño del nombre no debe ser mayor a 50 caracteres");
			}

			if (Utilities.checkWordAndCheckWithlength(entity.getCliTelefono(), 50) == false) {
				throw new Exception("El tamaño del teléfono no debe ser mayor a 50 caracteres");
			}

			if (entity.getTiposDocumentos() == null)
				throw new Exception("Debe tener un tipo de documento asociado");

			if (clientesDAO.findById(entity.getCliId()) == null)
				throw new Exception("no existe un cliente con ese ID");

			clientesDAO.update(entity);
			log.info("se modificó satisfactoriamente");

		} catch (Exception e) {
			log.info("updateClientes falló", e.getMessage());
			throw e;
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteClientes(long id) throws Exception {
		try {
			log.info("Inicia deleteClientes");

			if (id == 0) {
				throw new Exception("El id debe existir");
			}

			log.info("el id actual es " + id);
			
			List<Cuentas> cuentas = cuentasDAO.findByProperty("clientes.cliId", id);

			if (cuentas != null && cuentas.isEmpty() == false)
				throw new Exception("El tipo de documento no se puede eliminar porque tiene clientes asociados");

			Clientes entity = getClientes(id);
			if (entity == null)
				throw new Exception("No existe un cliente " + id);

			clientesDAO.delete(entity);
			log.info("Eliminó satisfactoriamente");
		} catch (Exception e) {
			log.error("deleteClientes falló " + e.getMessage());
			throw e;
		}
	}

	@TransactionAttribute
	public List<Clientes> getClientes() throws Exception {
		log.info("Inicia finAllClientes");
		return clientesDAO.findAll();
	}

	@TransactionAttribute
	public Clientes getClientes(long id) throws Exception {
		try {
			log.info("Inicia findByIdClientes");

			if (id == 0) {
				throw new Exception("El código debe existir");
			}
			return clientesDAO.findById(id);
		} catch (Exception e) {
			log.info("error en el findByIdClientes no existe un cliente con id " + id + " " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Clientes> consultarClientes(long cliId, long tiposDocumentos, String cliNombre, String cliDireccion,
			String cliTelefono, String cliMail) throws Exception {
		List<String> propiedades = new ArrayList<String>();
		if (cliId > 0)
			propiedades.add("cliId=" + cliId);
		if (tiposDocumentos > 0)
			propiedades.add("tiposDocumentos=" + tiposDocumentos);
		if (cliNombre != null && !cliNombre.equals(""))
			propiedades.add("cliNombre=" + "'" + cliNombre + "'");
		if (cliDireccion != null && !cliDireccion.equals(""))
			propiedades.add("cliDireccion=" + "'" + cliDireccion + "'");
		if (cliTelefono != null && !cliTelefono.equals(""))
			propiedades.add("cliTelefono=" + "'" + cliTelefono + "'");
		if (cliMail != null && !cliMail.equals(""))
			propiedades.add("cliMail=" + "'" + cliMail + "'");
		if (clientesDAO.findByProperties(propiedades).isEmpty())
			return clientesDAO.findAll();
		else
			return clientesDAO.findByProperties(propiedades);
	}

//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void saveCuentas(Cuentas entity) throws Exception {
//		try {
//			log.info("inicia saveCuentas");
//			entity.setCueNumero("" + System.currentTimeMillis());
//			String digitos = (entity.getClientes().getCliId() + "");
//			digitos = digitos.substring(digitos.length() - 5, digitos.length() - 1);
//			entity.setCueClave(entity.getCueNumero() + digitos);
//			entity.setCueActiva("P");
//
//			if (entity.getCueNumero() == null || entity.getCueNumero().equals("")) {
//				throw new Exception("La número de cuenta es obligatorio");
//			}
//			if (!Utilities.checkWordAndCheckWithlength(entity.getCueNumero(), 30)) {
//				throw new Exception("El tamaño del número de cuenta no debe ser mayor a 30 dígitos");
//			}
//			if (entity.getClientes() == null) {
//				throw new Exception("El cliente asociado a la cuenta es obligatorio");
//			}
//			if (entity.getCueSaldo() == null) {
//				throw new Exception("El saldo de la cuenta es obligatorio");
//			}
//			if (entity.getCueSaldo().toString().length() > 10) {
//				throw new Exception("El tamaño del saldo de la cuenta no debe ser mayor a 10 dígitos");
//			}
//			
//			if (entity.getCueActiva() == null || entity.getCueActiva().equals("")) {
//				throw new Exception("El estado de la cuenta debe ser obligatorio");
//			}
//			if (entity.getCueActiva().length() > 2) {
//				throw new Exception("El tamaño del estado de la cuenta no debe ser mayor a 2 dígitos");
//			}
//			if (entity.getCueClave() == null || entity.getCueClave().equals("")) {
//				throw new Exception("La clave de la cuenta debe ser obligatorio");
//			}
//			if (entity.getCueClave().length() > 50) {
//				throw new Exception("El tamaño de la clave de la cuenta no debe ser mayor a 50 dígitos");
//			}
//			if (getCuentas(entity.getCueNumero()) != null) {
//				throw new Exception("Ya existe una cuenta con número " + entity.getCueNumero());
//			}
//			cuentasDAO.save(entity);
//			log.info("Guardó satisfactoriamente");
//		} catch (Exception e) {
//			log.error("saveCuentas falló", e);
//			throw e;
//		}
//	}
//	
//	@TransactionAttribute
//	private Cuentas getCuentas(String codigo) throws Exception {
//		log.info("Inicia getcuentasById");
//		Cuentas cuentas = null;
//		try {
//			log.info("Inicia findByIdCuentas");
//			if (codigo == null || codigo.equals("")) {
//				throw new Exception("El código de la cuenta es obligatorio");
//			}
//			cuentas = cuentasDAO.findById(codigo);
//		} catch (Exception e) {
//			log.error("getCuentasById falló", e);
//			throw e;
//		}
//		return cuentas;
//	}

}
