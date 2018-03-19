package co.edu.icesi.demo.business;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.DemoBanco.modelo.Clientes;
import co.edu.icesi.DemoBanco.modelo.Consignaciones;
import co.edu.icesi.DemoBanco.modelo.Cuentas;
import co.edu.icesi.DemoBanco.modelo.Retiros;
import co.edu.icesi.DemoBanco.modelo.TiposDocumentos;
import co.edu.icesi.DemoBanco.modelo.TiposUsuarios;
import co.edu.icesi.DemoBanco.modelo.Usuarios;

@Local
public interface IBusinessDelegate {

	// USUARIOS

	public void saveUsuarios(Usuarios entity) throws Exception;

	public void updateUsuarios(Usuarios entity) throws Exception;

	public void deleteUsuarios(long id) throws Exception;

	public List<Usuarios> getUsuarios() throws Exception;

	public Usuarios getUsuarios(long id) throws Exception;

	public List<Usuarios> consultarUsuarios(long usuCedula, long tiposUsuarios, String usuNombre, String usuLogin,
			String usuClave);

	// TIPOS USUARIOS

	public void saveTiposUsuarios(TiposUsuarios entity) throws Exception;

	public void updateTiposUsuarios(TiposUsuarios entity) throws Exception;

	public void deleteTiposUsuarios(long codigo) throws Exception;

	public List<TiposUsuarios> getTiposUsuarios() throws Exception;

	public TiposUsuarios getTiposUsuarios(long codigo) throws Exception;

	public List<TiposUsuarios> consultarTiposUsuarios(long tusuCodigo, String tusuNombre) throws Exception;

	// CLIENTES

	public void saveClientes(Clientes entity) throws Exception;

	public void updateClientes(Clientes entity) throws Exception;

	public void deleteClientes(long id) throws Exception;

	public List<Clientes> getClientes() throws Exception;

	public Clientes getClientes(long id) throws Exception;

	public List<Clientes> consultarClientes(long cliId, long tiposDocumentos, String cliNombre, String cliDireccion,
			String cliTelefono, String cliMail) throws Exception;

	// CONSIGNACIONES

	public void saveConsignaciones(Consignaciones entity) throws Exception;

	public void updateConsignaciones(Consignaciones entity) throws Exception;

	public void deleteConsignaciones(long codigo, String numCuenta) throws Exception;

	public List<Consignaciones> getConsignaciones() throws Exception;

	public Consignaciones getConsignaciones(long codigo, String numCuenta) throws Exception;

	public void consignar100k(Clientes cliente, List<Cuentas> cuentas) throws Exception;

	public List<Consignaciones> consultarConsignaciones(long conCodigo, String cueNumero, long usuarios,
			BigDecimal conValor, Date conFecha, String conDescripcion) throws Exception;

	// CUENTAS

	public void saveCuentas(Cuentas entity) throws Exception;

	public void updateCuentas(Cuentas entity) throws Exception;

	public void deleteCuentas(String numCuenta) throws Exception;

	public List<Cuentas> getCuentas() throws Exception;

	public Cuentas getCuentas(String numCuenta) throws Exception;

	public boolean activacionCuenta(boolean activar, String codigo) throws Exception;

	public List<Cuentas> consultarCuentas(String cueNumero, long clientes, BigDecimal cueSaldo, String cueActiva,
			String cueClave) throws Exception;

	// RETIROS

	public void saveRetiros(Retiros entity) throws Exception;

	public void updateRetiros(Retiros entity) throws Exception;

	public void deleteRetiros(long codigo, String numCuenta) throws Exception;

	public List<Retiros> getRetiros() throws Exception;

	public Retiros getRetiros(long codigo, String numCuenta) throws Exception;

	public List<Retiros> consultarRetiros(long retCodigo, String cueNumero, long usuarios, BigDecimal retValor,
			Date retFecha, String retDescripcion) throws Exception;

	// TIPOS DOCUMENTOS

	public void saveTiposDocumentos(TiposDocumentos entity) throws Exception;

	public void updateTiposDocumentos(TiposDocumentos entity) throws Exception;

	public void deleteTiposDocumentos(long codigo) throws Exception;

	public List<TiposDocumentos> getTiposDocumentos() throws Exception;

	public TiposDocumentos getTiposDocumentos(long codigo) throws Exception;

	public List<TiposDocumentos> consultarTiposDocumentos(long tdocCodigo, String tdocNombre) throws Exception;


}
