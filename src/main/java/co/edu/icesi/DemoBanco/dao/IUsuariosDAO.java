package co.edu.icesi.DemoBanco.dao;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.DemoBanco.modelo.Cuentas;
import co.edu.icesi.DemoBanco.modelo.Usuarios;

@Local
public interface IUsuariosDAO {
	public void save(Usuarios entity);
	public void update(Usuarios entity);
	public void delete(Usuarios entity);
	public Usuarios findById(Long id);
	public List<Usuarios>  findAll();
	public List<Usuarios> findByProperty(String property, Object value); 
	public List<Usuarios> findByProperties(List<String> propertyValue); 

}
