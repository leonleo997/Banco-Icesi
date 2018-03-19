package co.edu.icesi.DemoBanco.modelo.control;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.DemoBanco.modelo.Clientes;

@Local
public interface IClientesLogica {
	public void saveClientes(Clientes entity) throws Exception;
	public void updateClientes(Clientes entity) throws Exception;
	public void deleteClientes(long id) throws Exception;
	public List<Clientes> getClientes() throws Exception;
	public Clientes getClientes(long id) throws Exception;
	public List<Clientes> consultarClientes(long cliId, long tiposDocumentos, String cliNombre, String cliDireccion, String cliTelefono, String cliMail)throws Exception;
}
 