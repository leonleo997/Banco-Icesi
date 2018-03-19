package co.edu.icesi.DemoBanco.dao;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.DemoBanco.modelo.TiposUsuarios;

@Local
public interface ITiposUsuariosDAO {
	public void save(TiposUsuarios entity);
	public void update(TiposUsuarios entity);
	public void delete(TiposUsuarios entity);
	public TiposUsuarios findById(Long id);
	public List<TiposUsuarios>  findAll();
	public List<TiposUsuarios> findByProperty(String property, Object value); 
	public List<TiposUsuarios> findByProperties(List<String> propertyValue); 
}
