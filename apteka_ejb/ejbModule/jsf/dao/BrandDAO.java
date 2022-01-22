package jsf.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsf.entities.Brand;

@Stateless
public class BrandDAO {
    private final static String UNIT_NAME = "aptekaPU";;
    
    Query query;
    
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(Brand brand) {
        em.persist(brand);
    }

    public Brand merge(Brand brand) {
        return em.merge(brand);
    }
    
    public void remove(Brand brand) {
        em.remove(em.merge(brand));
    }

    public Brand find(Object id) {
        return em.find(Brand.class, id);
    }
    
    public List<Brand> getFullList() {
        List<Brand> list = null;

        Query query = em.createQuery("SELECT b FROM Brand b");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<Brand> getLazyList(Map<String, Object> filterParams, int offset, int pageSize) {
    	List<Brand> list = null;

		String where = this.setFilter(filterParams);
		String join = "";
		query = em.createQuery("SELECT b FROM Brand b " + join + where);
		this.setFilterParam(filterParams);

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
    }
   
    public long countLazyList(Map<String, Object> filterParams) {
		long count = 0;

		String where = this.setFilter(filterParams);
		String join = "";
		query = em.createQuery("SELECT COUNT(b) FROM Brand b " + join + where);
		this.setFilterParam(filterParams);

		try {
			count = (long) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}
    
    private String setFilter(Map<String, Object> filterParams) {
		String where = "";
		return where;
	}

	private void setFilterParam(Map<String, Object> filterParams) {
	}
    
}