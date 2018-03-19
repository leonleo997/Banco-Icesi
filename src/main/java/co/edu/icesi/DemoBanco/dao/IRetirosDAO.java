package co.edu.icesi.DemoBanco.dao;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.DemoBanco.modelo.Retiros;

@Local
public interface IRetirosDAO {
	public void save(Retiros entity);
	public void update(Retiros entity);
	public void delete(Retiros entity);
	public Retiros findById(long retCodigo, String cueNumero);
	public List<Retiros>  findAll();
	public List<Retiros> findByProperty(String property, Object value); 
	public List<Retiros> findByProperties(List<String> propertyValue); 

}
