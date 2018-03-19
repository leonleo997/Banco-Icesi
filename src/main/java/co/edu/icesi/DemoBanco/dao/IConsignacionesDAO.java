package co.edu.icesi.DemoBanco.dao;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.DemoBanco.modelo.Consignaciones;
@Local
public interface IConsignacionesDAO {
	public void save(Consignaciones entity);
	public void update(Consignaciones entity);
	public void delete(Consignaciones entity);
	public Consignaciones findById(long conCodigo, String cueNumero);
	public List<Consignaciones>  findAll();
	public List<Consignaciones> findByProperty(String property, Object value); 
	public List<Consignaciones> findByProperties(List<String> propertyValue); 

}
