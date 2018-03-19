package co.edu.icesi.DemoBanco.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.modelo.Retiros;
import co.edu.icesi.DemoBanco.modelo.RetirosId;

@Stateless
public class RetirosDAO implements IRetirosDAO{

	@PersistenceContext
	private EntityManager entityManager;
	private static final Logger log = LoggerFactory.getLogger(RetirosDAO.class);

	@Override
	public void save(Retiros entity) {
		entityManager.persist(entity);	
	}

	@Override
	public void update(Retiros entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(Retiros entity) {
		entityManager.remove(entity);	
	}


	@Override
	public List<Retiros> findAll() {
		String jpql = "Select ret FROM Retiros ret";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public Retiros findById(long retCodigo, String cueNumero) {
		return entityManager.find(Retiros.class, new RetirosId(retCodigo, cueNumero));
	}

	@Override
	public List<Retiros> findByProperty(String property, Object value) {
		String jpql = "Select re FROM Retiros re WHERE re."+property+"="+value;
		return entityManager.createQuery(jpql).getResultList();

	}

	@Override
	public List<Retiros> findByProperties(List<String> propertyValue) {
		int tamanio=propertyValue.size();
		if(tamanio>0){
			String jpql = "Select ta FROM Retiros ta WHERE ";
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
