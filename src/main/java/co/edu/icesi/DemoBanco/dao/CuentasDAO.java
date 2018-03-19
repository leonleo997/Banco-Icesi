package co.edu.icesi.DemoBanco.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.modelo.Cuentas;

@Stateless
public class CuentasDAO implements ICuentasDAO{

	@PersistenceContext
	private EntityManager entityManager;
	
	private static final Logger log = LoggerFactory.getLogger(CuentasDAO.class);

	
	@Override
	public void save(Cuentas entity) {
		entityManager.persist(entity);

	}

	@Override
	public void update(Cuentas entity) {
		entityManager.merge(entity);	
	}

	@Override
	public void delete(Cuentas entity) {
		entityManager.remove(entity);		
	}

	@Override
	public Cuentas findById(String id) {
		return entityManager.find(Cuentas.class, id);

	}

	@Override
	public List<Cuentas> findAll() {
		String jpql = "Select cu FROM Cuentas cu";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<Cuentas> findByProperty(String property, Object value) {
		String jpql = "Select cu FROM Cuentas cu WHERE cu."+property+"="+value;
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<Cuentas> findByProperties(List<String> propertyValue) {
		int tamanio=propertyValue.size();
		if(tamanio>0){
			String jpql = "Select ta FROM Cuentas ta WHERE ";
			for (int i = 0; i < tamanio; i++) {
				if(i!=(tamanio-1))
					jpql+="ta."+propertyValue.get(i)+" AND ";
				else
					jpql+="ta."+propertyValue.get(i);					
			}
			log.info(jpql);
			return entityManager.createQuery(jpql).getResultList();
		}else{
			return findAll();
		}
	}


}
