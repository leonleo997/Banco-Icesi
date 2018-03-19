package co.edu.icesi.DemoBanco.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.modelo.Clientes;

@Stateless
public class ClientesDAO implements IClientesDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	private static final Logger log = LoggerFactory.getLogger(ClientesDAO.class);

	
	@Override
	public void save(Clientes entity) {
		entityManager.persist(entity);

	}

	@Override
	public void update(Clientes entity) {
		entityManager.merge(entity);

	}

	@Override
	public void delete(Clientes entity) {
		entityManager.remove(entity);

	}

	@Override
	public Clientes findById(Long id) {
		
		return entityManager.find(Clientes.class, id);
	}

	@Override
	public List<Clientes> findAll() {
		String jpql = "Select cli FROM Clientes cli";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<Clientes> findByProperty(String property, Object value) {
		String jpql = "Select cli FROM Clientes cli WHERE cli."+property+"="+value;
		return entityManager.createQuery(jpql).getResultList();

	}

	@Override
	public List<Clientes> findByProperties(List<String> propertyValue) {
		int tamanio=propertyValue.size();
		if(tamanio>0){
			String jpql = "Select ta FROM Clientes ta WHERE ";
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
