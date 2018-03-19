package co.edu.icesi.DemoBanco.modelo.control;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.DemoBanco.modelo.Usuarios;

@Local
public interface IUsuariosLogica {
	public void saveUsuarios(Usuarios entity) throws Exception;

	public void updateUsuarios(Usuarios entity) throws Exception;

	public void deleteUsuarios(long id) throws Exception;

	public List<Usuarios> getUsuarios() throws Exception;

	public Usuarios getUsuarios(long id) throws Exception;

	public List<Usuarios> consultarUsuarios(long usuCedula, long tiposUsuarios, String usuNombre,
			String usuLogin, String usuClave);

}
