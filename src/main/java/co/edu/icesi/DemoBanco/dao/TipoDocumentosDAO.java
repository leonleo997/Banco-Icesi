package co.edu.icesi.DemoBanco.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.modelo.TiposDocumentos;

@Stateless
public class TipoDocumentosDAO implements ITiposDocumentosDAO {

	@PersistenceContext
	private EntityManager entityManager;
	private static final Logger log = LoggerFactory.getLogger(TipoDocumentosDAO.class);

	@Override
	public void save(TiposDocumentos entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(TiposDocumentos entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(TiposDocumentos entity) {
		entityManager.remove(entity);
	}

	@Override
	public TiposDocumentos findById(Long id) {
		return entityManager.find(TiposDocumentos.class, id);
	}

	@Override
	public List<TiposDocumentos> findAll() {
		String jpql = "Select td FROM TiposDocumentos td";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<TiposDocumentos> findByProperty(String property, Object value) {
		String jpql = "Select td FROM TiposDocumentos td WHERE td."+property+"="+value;
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<TiposDocumentos> findByProperties(List<String> propertyValue) {
		int tamanio=propertyValue.size();
		if(tamanio>0){
			String jpql = "Select ta FROM TiposDocumentos ta WHERE ";
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
