package co.edu.icesi.DemoBanco.modelo.control;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.DemoBanco.modelo.Clientes;
import co.edu.icesi.DemoBanco.modelo.Consignaciones;
import co.edu.icesi.DemoBanco.modelo.Cuentas;

@Local
public interface IConsignacionesLogica {
	public void saveConsignaciones(Consignaciones entity) throws Exception;

	public void updateConsignaciones(Consignaciones entity) throws Exception;

	public void deleteConsignaciones(long codigo, String numCuenta) throws Exception;

	public List<Consignaciones> getConsignaciones() throws Exception;

	public Consignaciones getConsignaciones(long codigo, String numCuenta) throws Exception;

	public void consignar100k(Clientes cliente, List<Cuentas> cuentas) throws Exception;

	public List<Consignaciones> consultarConsignaciones(long conCodigo, String cueNumero, long usuarios,
			BigDecimal conValor, Date conFecha, String conDescripcion) throws Exception;
}
