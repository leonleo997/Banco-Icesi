package co.edu.icesi.DemoBanco.modelo.control;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.DemoBanco.modelo.Cuentas;

@Local
public interface ICuentasLogica {
	public void saveCuentas(Cuentas entity) throws Exception;
	public void updateCuentas(Cuentas entity) throws Exception;
	public void deleteCuentas(String numCuenta) throws Exception;
	public List<Cuentas> getCuentas() throws Exception;
	public Cuentas getCuentas(String numCuenta) throws Exception;
	public boolean activacionCuenta(boolean activar, String codigo)throws Exception;
	public List<Cuentas> consultarCuentas(String cueNumero, long clientes, BigDecimal cueSaldo, String cueActiva, String cueClave) throws Exception;
	boolean validarCuenta(String numCuenta, String password) throws Exception;
}
