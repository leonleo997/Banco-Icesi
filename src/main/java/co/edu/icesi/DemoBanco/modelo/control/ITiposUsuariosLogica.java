package co.edu.icesi.DemoBanco.modelo.control;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.DemoBanco.modelo.TiposUsuarios;

@Local
public interface ITiposUsuariosLogica {
	public void saveTiposUsuarios(TiposUsuarios entity) throws Exception;
	public void updateTiposUsuarios(TiposUsuarios entity) throws Exception;
	public void deleteTiposUsuarios(long codigo) throws Exception;
	public List<TiposUsuarios> getTiposUsuarios() throws Exception;
	public TiposUsuarios getTiposUsuarios(long codigo) throws Exception;
	public List<TiposUsuarios> consultarTiposUsuarios(long tusuCodigo, String tusuNombre)throws Exception;
}
