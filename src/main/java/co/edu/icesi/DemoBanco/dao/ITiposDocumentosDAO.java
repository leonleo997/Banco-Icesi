package co.edu.icesi.DemoBanco.dao;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.DemoBanco.modelo.TiposDocumentos;

@Local
public interface ITiposDocumentosDAO {
	public void save(TiposDocumentos entity);
	public void update(TiposDocumentos entity);
	public void delete(TiposDocumentos entity);
	public TiposDocumentos findById(Long id);
	public List<TiposDocumentos>  findAll();
	public List<TiposDocumentos> findByProperty(String property, Object value); 
	public List<TiposDocumentos> findByProperties(List<String> propertyValue); 
	
}
