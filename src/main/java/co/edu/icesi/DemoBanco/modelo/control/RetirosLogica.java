package co.edu.icesi.DemoBanco.modelo.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.dao.ICuentasDAO;
import co.edu.icesi.DemoBanco.dao.IRetirosDAO;
import co.edu.icesi.DemoBanco.modelo.Cuentas;
import co.edu.icesi.DemoBanco.modelo.Retiros;
import co.edu.icesi.DemoBanco.modelo.RetirosId;

@Stateless
public class RetirosLogica implements IRetirosLogica {

	private static final Logger log = LoggerFactory.getLogger(Retiros.class);

	@EJB
	private IRetirosDAO retirosDAO;

	@EJB
	private ICuentasDAO cuentasDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveRetiros(Retiros entity) throws Exception {

		try {

			log.info(("inicia saveRetiros"));

			boolean parar = false;
			long nuevoCod = 0;
			while (!parar) {
				Random r = new Random();
				nuevoCod = r.nextInt(99999999);

				if (retirosDAO.findById(nuevoCod, entity.getId().getCueNumero()) == null)
					parar = true;
			}

			entity.setId(new RetirosId(nuevoCod, entity.getId().getCueNumero()));

			if (entity.getId() == null) {
				throw new Exception("el ID del retiro es obligatorio");
			}

			if (entity.getId().getRetCodigo() == 0)
				throw new Exception("debe haber un número de cuenta para el retiro");

			if (("" + entity.getId().getRetCodigo()).length() > 10) {
				throw new Exception("el tamaño del id de retiro no debe ser mayor a 10 numeros");
			}
			if (entity.getId().getCueNumero() == null || entity.getId().getCueNumero().equals(""))
				throw new Exception("Debe tener un número de cuenta");

			if (entity.getCuentas() == null || entity.getCuentas().equals("")) {
				throw new Exception("el numero de la cuenta es obligatorio");
			}

			if (entity.getId().getCueNumero().length() > 30) {
				throw new Exception("El tamaño del numero de la cuenta del retiro no debe ser mayor a 30 digitos");
			}

			if (entity.getRetValor() == null) {
				throw new Exception("debe existir el valor a retirar");
			}

			if ((entity.getRetValor() + "").length() > 10) {
				throw new Exception("el tamaño del valor debe ser menor a 10");
			}

			if (entity.getRetFecha() == null) {
				throw new Exception("la fecha debe ser registrada");
			}

			if (entity.getRetDescripcion().equals("") || entity.getRetDescripcion() == null) {
				throw new Exception(" la descripcion del retiro es necesaria");
			}

			if (entity.getRetDescripcion().length() > 50) {
				throw new Exception("los comentarios no debe superar los 50 caracteres");
			}
			Long codigo = entity.getId().getRetCodigo();
			if (retirosDAO.findById(entity.getId().getRetCodigo(), entity.getId().getCueNumero()) != null) {
				throw new Exception("ya existe un retiro con el código " + codigo);
			}
			if (entity.getUsuarios() == null)
				throw new Exception("Debe existir un usuario");

			if (entity.getCuentas() == null)
				throw new Exception("Debe existir una cuenta asociada");
			// Validaciones a cuenta
			Cuentas cuenta = cuentasDAO.findById(entity.getId().getCueNumero());
			BigDecimal sald = cuenta.getCueSaldo().subtract(entity.getRetValor());
			if(sald.signum()!=-1)
			cuenta.setCueSaldo(sald);
			else
				throw new Exception("El saldo no es suficiente para hacer el retiro");

			cuentasDAO.update(cuenta);
			retirosDAO.save(entity);
			log.info("guardo satisfactoriamente");

		} catch (Exception e) {
			log.error("SaveRetiros fallo", e);
			throw e;
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateRetiros(Retiros entity) throws Exception {

		try {

			log.info(("inicia updateRetiros"));

			if (entity.getId() == null) {
				throw new Exception("el ID del retiro es obligatorio");
			}

			if (entity.getId().getRetCodigo() == 0)
				throw new Exception("debe haber un número de cuenta para el retiro");

			if (entity.getId().getCueNumero() == null || entity.getId().getCueNumero().equals(""))
				throw new Exception("Debe tener un número de cuenta");

			if (entity.getCuentas() == null || entity.getCuentas().equals("")) {
				throw new Exception("el numero de la cuenta es obligatorio");
			}

			if (entity.getId().getCueNumero().length() > 30) {
				throw new Exception("El tamaño del numero de la cuenta del retiro no debe ser mayor a 30 digitos");
			}

			if (entity.getRetValor() == null) {
				throw new Exception("debe existir el valor a retirar");
			}

			if ((entity.getRetValor() + "").length() > 10) {
				throw new Exception("el tamaño del valor debe ser menor a 10");
			}

			if (entity.getRetFecha() == null) {
				throw new Exception("la fecha debe ser registrada");
			}

			if (entity.getRetDescripcion().equals("") || entity.getRetDescripcion() == null) {
				throw new Exception(" la descripcion del retiro es necesaria");
			}

			if (entity.getRetDescripcion().length() > 50) {
				throw new Exception("los comentarios no debe superar los 50 caracteres");
			}

			if (entity.getUsuarios() == null)
				throw new Exception("Debe existir un usuario");

			Long codigo = entity.getId().getRetCodigo();
			if (retirosDAO.findById(entity.getId().getRetCodigo(), entity.getId().getCueNumero()) == null) {
				throw new Exception("no existe un retiro con el código " + codigo);
			}
			if (entity.getCuentas() == null)
				throw new Exception("Debe existir una cuenta asociada");
			retirosDAO.update(entity);
			log.info("Se modificó satisfactoriamente");

		} catch (Exception e) {
			log.error("updateRetiros fallo", e);
			throw e;

		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteRetiros(long codigo, String numCuenta) throws Exception {
		try {

			log.info("Inicia deleteRetiros");

			if (codigo == 0) {
				throw new Exception(" el codigo de retiros es obligatorio");
			}

			if (numCuenta == null || numCuenta.equals("")) {
				throw new Exception(" el número de cuenta de retiros es obligatorio");
			}

			Retiros entity = getRetiros(codigo, numCuenta);

			if (entity == null) {
				throw new Exception("no existe un retiro con el codigo" + codigo);
			}

			retirosDAO.delete(entity);

			log.info("Se eliminó sartisfactoriamente");
		} catch (Exception e) {

			log.error("deleteRetiros fallo", e);
			throw e;
		}
	}

	@TransactionAttribute
	public List<Retiros> getRetiros() throws Exception {
		log.info("Inicia getRetiros");
		return retirosDAO.findAll();
	}

	@TransactionAttribute
	public Retiros getRetiros(long codigo, String numCuenta) throws Exception {
		log.info("Inicia getRetirosByid");

		Retiros retiros = null;

		try {
			if (codigo == 0) {
				throw new Exception(" el codigo de retiros es obligatorio");
			}

			if (numCuenta == null || numCuenta.equals("")) {
				throw new Exception(" el número de cuenta de retiros es obligatorio");
			}
			retiros = retirosDAO.findById(codigo, numCuenta);

		} catch (Exception e) {
			log.error("getRetiroById fallo", e);
			throw e;
		}
		return retiros;
	}

	@Override
	public List<Retiros> consultarRetiros(long retCodigo, String cueNumero, long usuarios, BigDecimal retValor,
			Date retFecha, String retDescripcion) throws Exception {
		List<String> propiedades = new ArrayList<String>();
		if (retCodigo > 0 && cueNumero != null && !cueNumero.equals("")) {
			ArrayList<Retiros> rets = new ArrayList<Retiros>();
			rets.add(retirosDAO.findById(retCodigo, cueNumero));
			return rets;
		}
		if (usuarios > 0)
			propiedades.add("usuarios=" + usuarios);
		if (cueNumero != null && !cueNumero.equals(""))
			propiedades.add("cuentas=" + "'" + cueNumero + "'");
		if (retValor != null)
			propiedades.add("retValor=" + retValor);
		if (retFecha != null)
			propiedades.add("retFecha=" + retFecha);
		if (propiedades.size() == 0 && retCodigo > 0)
			throw new Exception("No se puede consultar un retiro por solo el código de retiro");
		if (propiedades.size() == 0 && retCodigo > 0)
			throw new Exception("No se puede consultar un retiro por solo el número de cuenta");

		return retirosDAO.findByProperties(propiedades);
	}

}
