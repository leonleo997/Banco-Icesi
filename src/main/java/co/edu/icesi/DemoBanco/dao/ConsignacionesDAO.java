package co.edu.icesi.DemoBanco.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.modelo.Consignaciones;
import co.edu.icesi.DemoBanco.modelo.ConsignacionesId;

@Stateless
public class ConsignacionesDAO implements IConsignacionesDAO {

	@PersistenceContext
	private EntityManager entityManager;
	private static final Logger log = LoggerFactory.getLogger(ConsignacionesDAO.class);


	@Override
	public void save(Consignaciones entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(Consignaciones entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(Consignaciones entity) {
		entityManager.remove(entity);
	}

	@Override
	public Consignaciones findById(long conCodigo, String cueNumero) {
		return entityManager.find(Consignaciones.class, new ConsignacionesId(conCodigo, cueNumero));
	}

	@Override
	public List<Consignaciones> findAll() {
		String jpql = "Select co FROM Consignaciones co";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<Consignaciones> findByProperty(String property, Object value) {
		String jpql = "Select co FROM Consignaciones co WHERE co." + property + "=" + value;
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<Consignaciones> findByProperties(List<String> propertyValue) {
		int tamanio=propertyValue.size();
		if(tamanio>0){
			String jpql = "Select ta FROM Consignaciones ta WHERE ";
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
