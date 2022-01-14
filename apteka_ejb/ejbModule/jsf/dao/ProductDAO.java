package jsf.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsf.entities.Brand;
import jsf.entities.Category;
import jsf.entities.Product;


@Stateless
public class ProductDAO {
	Query query;
	private final static String UNIT_NAME = "aptekaPU";;
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Product product) {
		em.persist(product);
	}

	public Product merge(Product product) {
		return em.merge(product);
	}

	public void remove(Product product) {
		em.remove(em.merge(product));
	}

	public Product find(Object id) {
		return em.find(Product.class, id);
	}

	public List<Product> getFullList() {
		List<Product> list = null;

		Query query = em.createQuery("SELECT p FROM Product p");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Product> getLazyList(Map<String, Object> filterParams, int offset, int pageSize) {
		List<Product> list = null;

		String where = this.setFilter(filterParams);
		String join = "";
		query = em.createQuery("SELECT p FROM Product p " + join + where).setFirstResult(offset).setMaxResults(pageSize);
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
		query = em.createQuery("SELECT COUNT(p) FROM Product p " + join + where);
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
		
		if((int)filterParams.get("brandName") != 0) {
			where += "where p.brand =:idBrand ";
		}
		
		if((int)filterParams.get("categoryName") != 0) {
			if(where.equals("")) {
				where += "where p.category =:idCategory ";
			}else {
				where += "and p.category =:idCategory ";
			}
		}
			
		where = this.createWhere("productName", (String)filterParams.get("productName"), where);
		
		return where;
	}

	private void setFilterParam(Map<String, Object> filterParams) {
		if (filterParams.get("productName") != null) {
			query.setParameter("productName", "%" + filterParams.get("productName") + "%");
		}	
		if(filterParams.get("brandName") != null && (int)filterParams.get("brandName") != 0) {
			Brand brand = new Brand();
			brand.setIdBrand((int)filterParams.get("brandName"));
			query.setParameter("idBrand", brand);
		}
		
		if(filterParams.get("categoryName") != null && (int)filterParams.get("categoryName") != 0) {
			Category category = new Category();
			category.setIdCategory((int)filterParams.get("categoryName"));
			query.setParameter("idCategory", category);
		}
	}

	private String createWhere(String paramName, String param, String currentWhere) {
		String where = currentWhere;

		if (param != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p." + paramName + " like :" + paramName + " ";
		}

		return where;
	}
}