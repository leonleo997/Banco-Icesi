package co.edu.icesi.DemoBanco.dao;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.DemoBanco.modelo.Cuentas;

@Local
public interface ICuentasDAO {
	public void save(Cuentas entity);

	public void update(Cuentas entity);

	public void delete(Cuentas entity);

	public Cuentas findById(String id);


	public List<Cuentas> findAll();

	public List<Cuentas> findByProperty(String property, Object value);

	public List<Cuentas> findByProperties(List<String> propertyValue);

}
