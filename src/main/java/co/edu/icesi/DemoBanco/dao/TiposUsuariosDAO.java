package co.edu.icesi.DemoBanco.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.modelo.TiposUsuarios;

@Stateless
public class TiposUsuariosDAO implements ITiposUsuariosDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	private static final Logger log = LoggerFactory.getLogger(TiposUsuariosDAO.class);


	@Override
	public void save(TiposUsuarios entity) {
		entityManager.persist(entity);

	}

	@Override
	public void update(TiposUsuarios entity) {
		entityManager.merge(entity);

	}

	@Override
	public void delete(TiposUsuarios entity) {
		entityManager.remove(entity);

	}

	@Override
	public TiposUsuarios findById(Long id) {
		return entityManager.find(TiposUsuarios.class, id);

	}

	@Override
	public List<TiposUsuarios> findAll() {
		String jpql = "Select tu FROM TiposUsuarios tu";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<TiposUsuarios> findByProperty(String property, Object value) {
		String jpql = "Select tu FROM TiposUsuarios tu WHERE cli."+property+"="+value;
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<TiposUsuarios> findByProperties(List<String> propertyValue) {
		int tamanio=propertyValue.size();
		if(tamanio>0){
			String jpql = "Select ta FROM TiposUsuarios ta WHERE ";
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
