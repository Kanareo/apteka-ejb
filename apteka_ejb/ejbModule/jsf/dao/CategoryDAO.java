package jsf.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsf.entities.Category;

@Stateless
public class CategoryDAO {
    private final static String UNIT_NAME = "aptekaPU";;
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(Category category) {
        em.persist(category);
    }

    public Category merge(Category category) {
        return em.merge(category);
    }
    
    public void remove(Category category) {
        em.remove(em.merge(category));
    }

    public Category find(Object id) {
        return em.find(Category.class, id);
    }
    
    public List<Category> getFullList() {
        List<Category> list = null;

        Query query = em.createQuery("SELECT c FROM Category c");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}