package co.edu.icesi.demo.business;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.edu.icesi.DemoBanco.modelo.Clientes;
import co.edu.icesi.DemoBanco.modelo.Consignaciones;
import co.edu.icesi.DemoBanco.modelo.Cuentas;
import co.edu.icesi.DemoBanco.modelo.Retiros;
import co.edu.icesi.DemoBanco.modelo.TiposDocumentos;
import co.edu.icesi.DemoBanco.modelo.TiposUsuarios;
import co.edu.icesi.DemoBanco.modelo.Usuarios;
import co.edu.icesi.DemoBanco.modelo.control.IClientesLogica;
import co.edu.icesi.DemoBanco.modelo.control.IConsignacionesLogica;
import co.edu.icesi.DemoBanco.modelo.control.ICuentasLogica;
import co.edu.icesi.DemoBanco.modelo.control.IRetirosLogica;
import co.edu.icesi.DemoBanco.modelo.control.ITiposDocumentosLogic;
import co.edu.icesi.DemoBanco.modelo.control.ITiposUsuariosLogica;
import co.edu.icesi.DemoBanco.modelo.control.IUsuariosLogica;

@Stateless
public class BusinessDelegate implements IBusinessDelegate {

	@EJB
	private IUsuariosLogica usuarioLogica;

	@EJB
	private ITiposUsuariosLogica tiposUsuarios;
	
	@EJB
	private IClientesLogica clientesLogica;
	
	@EJB
	private IConsignacionesLogica consignacionesLogica;
	
	@EJB
	private ICuentasLogica cuentasLogica;
	
	@EJB
	private IRetirosLogica retirosLogica;
	
	@EJB
	private ITiposDocumentosLogic tiposDocumentosLogica;

	@Override
	public void saveUsuarios(Usuarios entity) throws Exception {
		usuarioLogica.saveUsuarios(entity);
	}

	@Override
	public void updateUsuarios(Usuarios entity) throws Exception {
		usuarioLogica.updateUsuarios(entity);
	}

	@Override
	public void deleteUsuarios(long id) throws Exception {
		usuarioLogica.deleteUsuarios(id);
	}

	@Override
	public List<Usuarios> getUsuarios() throws Exception {
		return usuarioLogica.getUsuarios();
	}

	@Override
	public Usuarios getUsuarios(long id) throws Exception {
		return usuarioLogica.getUsuarios(id);
	}

	@Override
	public void saveTiposUsuarios(TiposUsuarios entity) throws Exception {
		tiposUsuarios.saveTiposUsuarios(entity);
	}

	@Override
	public void updateTiposUsuarios(TiposUsuarios entity) throws Exception {
		tiposUsuarios.updateTiposUsuarios(entity);
	}

	@Override
	public void deleteTiposUsuarios(long codigo) throws Exception {
		tiposUsuarios.deleteTiposUsuarios(codigo);
	}

	@Override
	public List<TiposUsuarios> getTiposUsuarios() throws Exception {
		return tiposUsuarios.getTiposUsuarios();
	}

	@Override
	public TiposUsuarios getTiposUsuarios(long codigo) throws Exception {
		return tiposUsuarios.getTiposUsuarios(codigo);
	}

	@Override
	public List<Usuarios> consultarUsuarios(long usuCedula, long tiposUsuarios, String usuNombre, String usuLogin,
			String usuClave) {
		return usuarioLogica.consultarUsuarios(usuCedula, tiposUsuarios, usuNombre, usuLogin, usuClave);
	}

	@Override
	public void saveClientes(Clientes entity) throws Exception {
		clientesLogica.saveClientes(entity);
		
	}

	@Override
	public void updateClientes(Clientes entity) throws Exception {
		clientesLogica.updateClientes(entity);		
	}

	@Override
	public void deleteClientes(long id) throws Exception {
clientesLogica.deleteClientes(id);		
	}

	@Override
	public List<Clientes> getClientes() throws Exception {
		return clientesLogica.getClientes();
	}

	@Override
	public Clientes getClientes(long id) throws Exception {
		return clientesLogica.getClientes(id);
	}

	@Override
	public List<Clientes> consultarClientes(long cliId, long tiposDocumentos, String cliNombre, String cliDireccion,
			String cliTelefono, String cliMail) throws Exception {
		return clientesLogica.consultarClientes(cliId, tiposDocumentos, cliNombre, cliDireccion, cliTelefono, cliMail);
	}

	@Override
	public void saveConsignaciones(Consignaciones entity) throws Exception {
		consignacionesLogica.saveConsignaciones(entity);
	}

	@Override
	public void updateConsignaciones(Consignaciones entity) throws Exception {
		consignacionesLogica.updateConsignaciones(entity);
		
	}

	@Override
	public void deleteConsignaciones(long codigo, String numCuenta) throws Exception {
		consignacionesLogica.deleteConsignaciones(codigo, numCuenta);
	}

	@Override
	public List<Consignaciones> getConsignaciones() throws Exception {
		return consignacionesLogica.getConsignaciones();
	}

	@Override
	public Consignaciones getConsignaciones(long codigo, String numCuenta) throws Exception {
		return consignacionesLogica.getConsignaciones(codigo, numCuenta);
	}

	@Override
	public void consignar100k(Clientes cliente, List<Cuentas> cuentas) throws Exception {
		consignacionesLogica.consignar100k(cliente, cuentas);
		
	}

	@Override
	public List<Consignaciones> consultarConsignaciones(long conCodigo, String cueNumero, long usuarios,
			BigDecimal conValor, Date conFecha, String conDescripcion) throws Exception {
		return consignacionesLogica.consultarConsignaciones(conCodigo, cueNumero, usuarios, conValor, conFecha, conDescripcion);
	}

	@Override
	public void saveCuentas(Cuentas entity) throws Exception {
		cuentasLogica.saveCuentas(entity);
	}

	@Override
	public void updateCuentas(Cuentas entity) throws Exception {
		cuentasLogica.updateCuentas(entity);
	}

	@Override
	public void deleteCuentas(String numCuenta) throws Exception {
		cuentasLogica.deleteCuentas(numCuenta);		
	}

	@Override
	public List<Cuentas> getCuentas() throws Exception {
		return cuentasLogica.getCuentas();
	}

	@Override
	public Cuentas getCuentas(String numCuenta) throws Exception {
		return cuentasLogica.getCuentas(numCuenta);
	}

	@Override
	public boolean activacionCuenta(boolean activar, String codigo) throws Exception {
		return cuentasLogica.activacionCuenta(activar, codigo);
	}

	@Override
	public List<Cuentas> consultarCuentas(String cueNumero, long clientes, BigDecimal cueSaldo, String cueActiva,
			String cueClave) throws Exception {
		System.out.println("entro a consultarcuentas del business delegate");
		
		return cuentasLogica.consultarCuentas(cueNumero, clientes, cueSaldo, cueActiva, cueClave);
	}
	

	@Override
	public void saveRetiros(Retiros entity) throws Exception {
		retirosLogica.saveRetiros(entity);
		
	}

	@Override
	public void updateRetiros(Retiros entity) throws Exception {
		retirosLogica.updateRetiros(entity);
		
	}

	@Override
	public void deleteRetiros(long codigo, String numCuenta) throws Exception {
		retirosLogica.deleteRetiros(codigo, numCuenta);
	}

	@Override
	public List<Retiros> getRetiros() throws Exception {
		return retirosLogica.getRetiros();
	}

	@Override
	public Retiros getRetiros(long codigo, String numCuenta) throws Exception {
		return retirosLogica.getRetiros(codigo, numCuenta);
	}

	@Override
	public List<Retiros> consultarRetiros(long retCodigo, String cueNumero, long usuarios, BigDecimal retValor,
			Date retFecha, String retDescripcion) throws Exception {
		return retirosLogica.consultarRetiros(retCodigo, cueNumero, usuarios, retValor, retFecha, retDescripcion);
	}

	@Override
	public void saveTiposDocumentos(TiposDocumentos entity) throws Exception {
		tiposDocumentosLogica.saveTiposDocumentos(entity);
	}

	@Override
	public void updateTiposDocumentos(TiposDocumentos entity) throws Exception {
		tiposDocumentosLogica.updateTiposDocumentos(entity);
	}

	@Override
	public void deleteTiposDocumentos(long codigo) throws Exception {
		tiposDocumentosLogica.deleteTiposDocumentos(codigo);		
	}

	@Override
	public List<TiposDocumentos> getTiposDocumentos() throws Exception {
		return tiposDocumentosLogica.getTiposDocumentos();
	}

	@Override
	public TiposDocumentos getTiposDocumentos(long codigo) throws Exception {
		return tiposDocumentosLogica.getTiposDocumentos(codigo);
	}

	@Override
	public List<TiposDocumentos> consultarTiposDocumentos(long tdocCodigo, String tdocNombre) throws Exception {
		return tiposDocumentosLogica.consultarTiposDocumentos(tdocCodigo, tdocNombre);
	}

	@Override
	public List<TiposUsuarios> consultarTiposUsuarios(long tusuCodigo, String tusuNombre) throws Exception {
		// TODO Auto-generated method stub
		return tiposUsuarios.consultarTiposUsuarios(tusuCodigo, tusuNombre);
	}

	

}
