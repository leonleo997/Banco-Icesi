package co.edu.icesi.DemoBanco.modelo.control;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.DemoBanco.modelo.TiposDocumentos;

@Local
public interface ITiposDocumentosLogic {
	
	public void saveTiposDocumentos(TiposDocumentos entity) throws Exception;
	public void updateTiposDocumentos(TiposDocumentos entity) throws Exception;
	public void deleteTiposDocumentos(long codigo) throws Exception;
	public List<TiposDocumentos> getTiposDocumentos() throws Exception;
	public TiposDocumentos getTiposDocumentos(long codigo) throws Exception;
	public List<TiposDocumentos> consultarTiposDocumentos(long tdocCodigo, String tdocNombre)throws Exception;

	
}
