package co.edu.icesi.DemoBanco.modelo.control;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.DemoBanco.modelo.Retiros;

@Local
public interface IRetirosLogica {
	public void saveRetiros(Retiros entity) throws Exception;
	public void updateRetiros(Retiros entity) throws Exception;
	public void deleteRetiros(long codigo, String numCuenta) throws Exception;
	public List<Retiros> getRetiros() throws Exception;
	public Retiros getRetiros(long codigo, String numCuenta) throws Exception;
	public List<Retiros> consultarRetiros(long retCodigo,String cueNumero, long usuarios, BigDecimal retValor, Date retFecha, String retDescripcion)throws Exception;

}
