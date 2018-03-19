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

import co.edu.icesi.DemoBanco.dao.IConsignacionesDAO;
import co.edu.icesi.DemoBanco.dao.ICuentasDAO;
import co.edu.icesi.DemoBanco.modelo.Clientes;
import co.edu.icesi.DemoBanco.modelo.Consignaciones;
import co.edu.icesi.DemoBanco.modelo.ConsignacionesId;
import co.edu.icesi.DemoBanco.modelo.Cuentas;
import co.edu.icesi.DemoBanco.modelo.RetirosId;

@Stateless
public class ConsignacionesLogica implements IConsignacionesLogica {

	private static Logger log = LoggerFactory.getLogger(ClientesLogica.class);

	@EJB
	private IConsignacionesDAO consignacionesDAO;

	@EJB
	private IClientesLogica clientesLogica;

	@EJB
	private ICuentasLogica cuentasLogica;

	@EJB
	private ICuentasDAO cuentasDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveConsignaciones(Consignaciones entity) throws Exception {
		try {
			log.info("Inicia saveConsignaciones");

			boolean parar = false;
			long nuevoCod = 0;
			while (!parar) {
				Random r = new Random();
				nuevoCod = r.nextInt(99999999);

				if (consignacionesDAO.findById(nuevoCod, entity.getId().getCueNumero()) == null)
					parar = true;
			}

			entity.setId(new ConsignacionesId(nuevoCod, entity.getId().getCueNumero()));

			if (entity.getId() == null) {
				throw new Exception("El codigo del cliente es obligatorio");
			}
			if ((entity.getId().getConCodigo() + "").length() > 10) {
				throw new Exception("El tamaño del código de la consignación no debe ser mayor a 10 de lenght");
			}

			if (entity.getId().getCueNumero().length() > 30) {
				throw new Exception(
						"El tamaño de la cuenta número de la consignación no debe ser mayor a 30 de lenght");
			}

			if (entity.getConValor().toString().length() > 10)
				throw new Exception("La cantidad de caracteres del valor no debe ser mayor a 10");

			// if (entity.getConValor().precision() > 2)
			// throw new Exception("La precisión del valor no debe ser mayor a
			// 2");

			if (entity.getConValor() == null)
				throw new Exception("Debe haber un valor");

			if (entity.getConFecha() == null)
				throw new Exception("Debe tener fecha");

			if (entity.getConDescripcion() == null)
				throw new Exception("Debe tener descripción");

			if (entity.getConDescripcion().length() > 50)
				throw new Exception("La descripción debe ser menor a 51 caracteres");

			if (entity.getCuentas() == null)
				throw new Exception("Debe tener una cuenta asociada");

			Long codigo = entity.getId().getConCodigo();
			String cueNumero = entity.getId().getCueNumero();
			if (consignacionesDAO.findById(codigo, cueNumero) != null)
				throw new Exception(
						"ya existe una consignación con el código " + codigo + " y cuenta de número " + cueNumero);

			Cuentas cuenta = cuentasDAO.findById(entity.getId().getCueNumero());
			BigDecimal sald = cuenta.getCueSaldo().add(entity.getConValor());

			if (cuenta.getCueActiva().equals("N "))
				throw new Exception("La cuenta debe estar activa o pendiente por activar para hacer una consignación");
			if (entity.getConValor().intValue() < 100000 && cuenta.getCueActiva().equals("P "))
				throw new Exception("La primera consignación debe ser mínimo de $100.000");

			if (cuenta.getCueActiva().equals("S "))
				cuenta.setCueSaldo(sald);

			if (entity.getConValor().intValue() >= 100000 && cuenta.getCueActiva().equals("P ")) {
				cuenta.setCueSaldo(entity.getConValor());
				cuenta.setCueActiva("S");
			}

			cuentasDAO.update(cuenta);

			consignacionesDAO.save(entity);
			log.info("Guardó satisfactoriamente");

		} catch (Exception e) {
			log.info("saveConsignaciones falló", e.getMessage());
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateConsignaciones(Consignaciones entity) throws Exception {
		try {
			log.info("Inicia updateConsignaciones");
			if (entity.getId() == null) {
				throw new Exception("El codigo del cliente es obligatorio");
			}
			if ((entity.getId().getConCodigo() + "").length() > 10) {
				throw new Exception("El tamaño del código de la consignación no debe ser mayor a 10 de lenght");
			}

			if (entity.getId().getCueNumero().length() > 30) {
				throw new Exception(
						"El tamaño de la cuenta número de la consignación no debe ser mayor a 30 de lenght");
			}

			if (entity.getConValor().toString().length() > 10)
				throw new Exception("La cantidad de caracteres del valor no debe ser mayor a 10");

			// if (entity.getConValor().precision() > 2)
			// throw new Exception("La precisión del valor no debe ser mayor a
			// 2");

			if (entity.getConValor() == null)
				throw new Exception("Debe haber un valor");

			if (entity.getConFecha() == null)
				throw new Exception("Debe tener fecha");

			if (entity.getConDescripcion() == null)
				throw new Exception("Debe tener descripción");

			if (entity.getConDescripcion().length() > 50)
				throw new Exception("La descripción debe ser menor a 51 caracteres");

			if (entity.getCuentas() == null)
				throw new Exception("Debe tener una cuenta asociada");

			Long codigo = entity.getId().getConCodigo();
			String cueNumero = entity.getId().getCueNumero();
			if (consignacionesDAO.findById(codigo, cueNumero) == null)
				throw new Exception(
						"Debe existir una consignación con el código " + codigo + " y cuenta de número " + cueNumero);

			consignacionesDAO.update(entity);
			log.info("Actualizó satisfactoriamente");

		} catch (Exception e) {
			log.info("updateConsignaciones falló", e.getMessage());
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteConsignaciones(long codigo, String numCuenta) throws Exception {
		try {
			log.info("Inicia deleteConsignaciones");

			if (codigo == 0) {
				throw new Exception("El código de la consignación debe existir");
			}

			if ((codigo + "").length() > 10) {
				throw new Exception("El tamaño del código de la consignación no debe ser mayor a 10 de lenght");
			}

			if (numCuenta == null || numCuenta.equals("")) {
				throw new Exception("La cuenta número de la consignación debe existir");
			}

			if (numCuenta.length() > 30) {
				throw new Exception(
						"El tamaño de la cuenta número de la consignación no debe ser mayor a 30 de lenght");
			}

			Consignaciones consignaciones = consignacionesDAO.findById(codigo, numCuenta);
			consignacionesDAO.delete(consignaciones);
			log.info("Eliminó satisfactoriamente");

		} catch (Exception e) {
			log.error("deleteConsignaciones falló " + e.getMessage());
			throw e;
		}
	}

	@TransactionAttribute
	public List<Consignaciones> getConsignaciones() throws Exception {
		log.info("inicia getconsignaciones");
		return consignacionesDAO.findAll();
	}

	@TransactionAttribute
	public Consignaciones getConsignaciones(long codigo, String numCuenta) throws Exception {
		try {
			log.info("Inicia findByIdConsignaciones");

			if (codigo == 0) {
				throw new Exception("El código debe existir");
			}

			if (numCuenta.equals("") || numCuenta == null)
				throw new Exception("El número de cuenta debe existir");

			return consignacionesDAO.findById(codigo, numCuenta);
		} catch (Exception e) {
			log.error("error en el findByIdConsignaciones no existe una consignación con códiog " + codigo
					+ " y num de cuenta " + numCuenta + " " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void consignar100k(Clientes cliente, List<Cuentas> cuentas) throws Exception {
		try {
			log.info("Inicia consignar100k");
			if (clientesLogica.getClientes(cliente.getCliId()) == null)
				clientesLogica.saveClientes(cliente);
			else
				clientesLogica.updateClientes(cliente);

			for (int i = 0; i < cuentas.size(); i++) {
				Cuentas cu = cuentas.get(i);

				Random r = new Random();

				Consignaciones consignaciones = new Consignaciones();
				consignaciones.setId(new ConsignacionesId(r.nextInt(100000), cu.getCueNumero()));
				consignaciones.setConValor(new BigDecimal(100000));
				cu.setCueSaldo(cu.getCueSaldo().add(new BigDecimal(100000)));
				consignaciones.setConDescripcion("Descrip");
				consignaciones.setConFecha(new Date(2017, 9, 18));
				consignaciones.setCuentas(cu);

				if (cuentasLogica.getCuentas(cu.getCueNumero()) == null)
					cuentasLogica.saveCuentas(cu);
				else
					cuentasLogica.updateCuentas(cu);

				if (cu.getClientes().getCliId() != cliente.getCliId())
					throw new Exception(
							"La cuenta " + cu.getCueNumero() + " debe pertenecer al cliente " + cliente.getCliId());

				saveConsignaciones(consignaciones);
			}
			log.info("exito al consignar100k");

		} catch (Exception e) {
			log.error(" Error al intentar consignar 100000 " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Consignaciones> consultarConsignaciones(long conCodigo, String cueNumero, long usuarios,
			BigDecimal conValor, Date conFecha, String conDescripcion) throws Exception {
		List<String> propiedades = new ArrayList<String>();
		if (conCodigo > 0 && cueNumero != null && !cueNumero.equals("")) {
			List<Consignaciones> retorno = new ArrayList<Consignaciones>();
			retorno.add(consignacionesDAO.findById(conCodigo, cueNumero));
			return retorno;
		}
		if (usuarios > 0)
			propiedades.add("usuarios=" + usuarios);
		if (conValor != null)
			propiedades.add("conValor=" + conValor);
		if (conFecha != null)
			propiedades.add("conFecha=" + conFecha);
		if (cueNumero != null && !cueNumero.equals(""))
			propiedades.add("cueNumero=" + "'" + cueNumero + "'");
		if (conDescripcion != null && !conDescripcion.equals(""))
			propiedades.add("conDescripcion=" + "'" + conDescripcion + "'");
		return consignacionesDAO.findByProperties(propiedades);
	}
}
