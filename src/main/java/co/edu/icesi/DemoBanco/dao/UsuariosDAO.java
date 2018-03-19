package co.edu.icesi.DemoBanco.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.modelo.Usuarios;


@Stateless
public class UsuariosDAO implements IUsuariosDAO{

	@PersistenceContext
	private EntityManager entityManager;
	private static final Logger log = LoggerFactory.getLogger(UsuariosDAO.class);

	@Override
	public void save(Usuarios entity) {
		entityManager.persist(entity);
		
	}

	@Override
	public void update(Usuarios entity) {
		entityManager.merge(entity);
		
	}

	@Override
	public void delete(Usuarios entity) {
		entityManager.remove(entity);
		
	}

	@Override
	public Usuarios findById(Long id) {
		return entityManager.find(Usuarios.class, id);

	}

	@Override
	public List<Usuarios> findAll() {
		String jpql = "Select u FROM Usuarios u";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<Usuarios> findByProperty(String property, Object value) {
		String jpql = "Select us FROM Usuarios us WHERE us."+property+"="+value;
		return entityManager.createQuery(jpql).getResultList();

	}

	@Override
	public List<Usuarios> findByProperties(List<String> propertyValue) {
		int tamanio=propertyValue.size();
		if(tamanio>0){
			String jpql = "Select us FROM Usuarios us WHERE ";
			for (int i = 0; i < tamanio; i++) {
				if(i!=(tamanio-1))
					jpql+="us."+propertyValue.get(i)+" AND ";
				else
					jpql+="us."+propertyValue.get(i);					
			}
			log.info(jpql);
			return entityManager.createQuery(jpql).getResultList();
		}else{
			return findAll();
		}
	}

}
