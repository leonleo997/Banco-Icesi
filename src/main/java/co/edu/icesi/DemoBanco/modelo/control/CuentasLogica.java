package co.edu.icesi.DemoBanco.modelo.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.dao.IClientesDAO;
import co.edu.icesi.DemoBanco.dao.IConsignacionesDAO;
import co.edu.icesi.DemoBanco.dao.ICuentasDAO;
import co.edu.icesi.DemoBanco.dao.IRetirosDAO;
import co.edu.icesi.DemoBanco.modelo.Consignaciones;
import co.edu.icesi.DemoBanco.modelo.Cuentas;
import co.edu.icesi.DemoBanco.modelo.Retiros;
import co.edu.icesi.DemoBanco.modelo.utilities.Utilities;

@Stateless
public class CuentasLogica implements ICuentasLogica {

	@EJB
	private ICuentasDAO cuentasDAO;

	@EJB
	private IConsignacionesDAO consignacionesDAO;

	@EJB
	private IRetirosDAO retirosDAO;
	
	@EJB
	private IClientesDAO clientesDAO;

	private static final Logger log = LoggerFactory.getLogger(CuentasLogica.class);

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveCuentas(Cuentas entity) throws Exception {
		try {
			log.info("inicia saveCuentas");
			entity.setCueNumero("" + System.currentTimeMillis());
			String digitos = (entity.getClientes().getCliId() + "");
			digitos = digitos.substring(digitos.length()-4);
			entity.setCueClave(entity.getCueNumero()+digitos);
			entity.setCueActiva("P");
			entity.setCueSaldo(new BigDecimal(0));		
			
			if (entity.getCueNumero() == null || entity.getCueNumero().equals("")) {
				throw new Exception("La número de cuenta es obligatorio");
			}
			if (!Utilities.checkWordAndCheckWithlength(entity.getCueNumero(), 30)) {
				throw new Exception("El tamaño del número de cuenta no debe ser mayor a 30 dígitos");
			}
			if (entity.getClientes() == null) {
				throw new Exception("El cliente asociado a la cuenta es obligatorio");
			}
			if (entity.getCueSaldo() == null) {
				throw new Exception("El saldo de la cuenta es obligatorio");
			}
			if (entity.getCueSaldo().toString().length() > 10) {
				throw new Exception("El tamaño del saldo de la cuenta no debe ser mayor a 10 dígitos");
			}
			// if
			// (Utilities.checkNumberAndCheckWithPrecisionAndScale((""+entity.getCueSaldo()),
			// 32, 3)==false ){
			// throw new Exception("El saldo de la cuenta no debe tener más de 2
			// dígitos en la parte decimal ");
			// }
			if (entity.getCueActiva() == null || entity.getCueActiva().equals("")) {
				throw new Exception("El estado de la cuenta debe ser obligatorio");
			}
			if (entity.getCueActiva().length() > 2) {
				throw new Exception("El tamaño del estado de la cuenta no debe ser mayor a 2 dígitos");
			}
			if (entity.getCueClave() == null || entity.getCueClave().equals("")) {
				throw new Exception("La clave de la cuenta debe ser obligatorio");
			}
			if (entity.getCueClave().length() > 50) {
				throw new Exception("El tamaño de la clave de la cuenta no debe ser mayor a 50 dígitos");
			}
			if (getCuentas(entity.getCueNumero()) != null) {
				throw new Exception("Ya existe una cuenta con número " + entity.getCueNumero());
			}
			if(clientesDAO.findById(entity.getClientes().getCliId())==null)
				throw new Exception("El cliente que se ha ingresado no existe");
			
			cuentasDAO.save(entity);
			log.info("Guardó satisfactoriamente");
		} catch (Exception e) {
			log.error("saveCuentas falló", e);
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateCuentas(Cuentas entity) throws Exception {
		try {
			log.info("inicia updateCuentas");
			if (entity.getCueNumero() == null || entity.getCueNumero().equals("")) {
				throw new Exception("La número de cuenta es obligatorio");
			}
			if (!Utilities.checkWordAndCheckWithlength(entity.getCueNumero(), 30)) {
				throw new Exception("El tamaño del número de cuenta no debe ser mayor a 30 dígitos");
			}
			if (entity.getClientes() == null) {
				throw new Exception("El cliente asociado a la cuenta es obligatorio");
			}
			if (entity.getCueSaldo() == null) {
				throw new Exception("El saldo de la cuenta es obligatorio");
			}
			if (entity.getCueSaldo().toString().length() > 10) {
				throw new Exception("El tamaño del saldo de la cuenta no debe ser mayor a 10 dígitos");
			}
			// if (entity.getCueSaldo().precision() > 2) {
			// throw new Exception("El saldo de la cuenta no debe tener más de 2
			// dígitos en la parte decimal");
			// }
			if (entity.getCueActiva() == null || entity.getCueActiva().equals("")) {
				throw new Exception("El estado de la cuenta debe ser obligatorio");
			}
			if (entity.getCueActiva().length() > 2) {
				throw new Exception("El tamaño del estado de la cuenta no debe ser mayor a 2 dígitos");
			}
			if (entity.getCueClave() == null || entity.getCueClave().equals("")) {
				throw new Exception("La clave de la cuenta debe ser obligatorio");
			}
			if (entity.getCueClave().length() > 50) {
				throw new Exception("El tamaño de la clave de la cuenta no debe ser mayor a 50 dígitos");
			}
			if (getCuentas(entity.getCueNumero()) == null) {
				throw new Exception("No existe una cuenta con número " + entity.getCueNumero());
			}
			if(clientesDAO.findById(entity.getClientes().getCliId())==null)
				throw new Exception("El cliente que se ha ingresado no existe");
			
			cuentasDAO.update(entity);
			log.info("Modificó satisfactoriamente");
		} catch (Exception e) {
			log.error("updateCuentas falló", e);
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCuentas(String codigo) throws Exception {
		try {
			if (codigo == null || codigo.equals("")) {
				throw new Exception("El número de cuenta es obligatorio");
			}
			List<Consignaciones> consignaciones = consignacionesDAO.findByProperty("cuentas.cueNumero", codigo);
			if (consignaciones != null && !consignaciones.isEmpty()) {
				throw new Exception("La cuenta con número " + codigo
						+ " no se puede eliminar ya que tiene consignaciones asociadas");
			}

			List<Retiros> retiros = retirosDAO.findByProperty("cuentas.cueNumero", codigo);
			if (retiros != null && !retiros.isEmpty()) {
				throw new Exception(
						"La cuenta con número " + codigo + " no se puede eliminar ya que tiene retiros asociados");
			}
			log.info("pasaaaaaaaaaaaaaaaaaaaaaaaaa");
			Cuentas entity = cuentasDAO.findById(codigo);

			if (entity == null) {
				throw new Exception("No existe un cliente con id " + codigo);
			}
			cuentasDAO.delete(entity);
			log.info("eliminó satisfactoriamente");
		} catch (Exception e) {
			log.error("deleteCuentas falló", e);
			throw e;
		}
	}

	@TransactionAttribute
	public List<Cuentas> getCuentas() throws Exception {
		log.info("Inicia getcuentas");
		return cuentasDAO.findAll();
	}

	@TransactionAttribute
	public Cuentas getCuentas(String codigo) throws Exception {
		log.info("Inicia getcuentasById");
		Cuentas cuentas = null;
		try {
			log.info("Inicia findByIdCuentas");
			if (codigo == null || codigo.equals("")) {
				throw new Exception("El código de la cuenta es obligatorio");
			}
			cuentas = cuentasDAO.findById(codigo);
		} catch (Exception e) {
			log.error("getCuentasById falló", e);
			throw e;
		}
		return cuentas;
	}

	@Override
	public boolean activacionCuenta(boolean activar, String codigo) throws Exception {
		boolean isCorrecto = false;
		try {
			log.info("Inicio activación");
			Cuentas cuenta = getCuentas(codigo);
			if (activar)
				cuenta.setCueActiva("S");
			else
				cuenta.setCueActiva("N");
			isCorrecto = true;
		} catch (Exception e) {
			log.error("activación falló", e);
			throw e;
		}
		return isCorrecto;
	}

	@Override
	public List<Cuentas> consultarCuentas(String cueNumero, long cliId, BigDecimal cueSaldo, String cueActiva,
			String cueClave) throws Exception {
		log.info("Entro a consultarcuentas de cuentas logic");
		
		List<String> propiedades = new ArrayList<String>();
		if (cliId != 0) {
//			propiedades.add("clientes=" + clientes);
			propiedades.add("clientes=" + cliId);
	
		}
		if (cueSaldo != null)
			propiedades.add("cueSaldo=" + cueSaldo);
		if (cueNumero != null && !cueNumero.equals(""))
			propiedades.add("cueNumero=" + "'" + cueNumero + "'");
		if (cueActiva != null && !cueActiva.equals(""))
			propiedades.add("cueActiva=" + "'" + cueActiva + "'");
		if (cueClave != null && !cueClave.equals(""))
			propiedades.add("cueClave=" + "'" + cueClave + "'");
		
		log.info(""+propiedades.size());
		return cuentasDAO.findByProperties(propiedades);
	}
	
	@Override
	public boolean validarCuenta(String numCuenta, String password) throws Exception {
		boolean isValida=false;
		Cuentas cuenta=cuentasDAO.findById(numCuenta);
		if(cuenta==null)
			throw new Exception("No existe la cuenta "+numCuenta);
		if(cuenta.getCueClave().equals(password))
			isValida=true;
		return isValida;
	}
	
}
