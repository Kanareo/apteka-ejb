package jsf.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.print.attribute.UnmodifiableSetException;

import jsf.entities.User;

@Stateless
public class UserDAO {
	Query query;
    private final static String UNIT_NAME = "aptekaPU";;
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;
    private User user;

    public void create(User user) {
        em.persist(user);
    }

    public User merge(User user) {
        return em.merge(user);
    }
    
    public void remove(User user) {
        em.remove(em.merge(user));
    }

    public User find(Object id) {
        return em.find(User.class, id);
    }
    
    public User getUser(String email) {
    	User user = null;
    	
    	Query query = em.createQuery("FROM User u where u.email=:email");
		query.setParameter("email", email);
		
		try {
			user = (User)query.getSingleResult();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	return user;
    }
    
    public List<User> getFullList() {
        List<User> list = null;

        Query query = em.createQuery("SELECT u FROM User u");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<User> getLazyList(Map<String, Object> filterParams, int offset, int pageSize) {
		List<User> list = null;

		String where = this.setFilter(filterParams);
		String join = "";
		query = em.createQuery("SELECT u FROM User u " + join + where).setFirstResult(offset).setMaxResults(pageSize);
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
		query = em.createQuery("SELECT COUNT(u) FROM User u " + join + where);
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
			user = (User)filterParams.get("user");
			if(user.getIdUser() != null && user.getIdUser() != 0) {
				where = this.createWhere("idUser", user.getIdUser().toString(), where);
			}
			if(user.getFirstName() != null && user.getFirstName().length() > 0) {
				where = this.createWhere("firstName", user.getFirstName(), where);
			}
			if(user.getSecondName() != null && user.getSecondName().length() > 0) {
				where = this.createWhere("secondName", user.getSecondName(), where);
			}
			if(user.getEmail() != null && user.getEmail().length() > 0) {
				where = this.createWhere("email", user.getEmail(), where);
			}
			if(user.getPhone() != null && user.getPhone().length() > 0) {
				where = this.createWhere("phone", user.getPhone(), where);
			}
			if(user.getBlocked() != (byte)2) {
				where = this.createWhere("blocked", String.valueOf(user.getBlocked()), where);
			}
			if(!user.getRole().equals("all")) {
				where = this.createWhere("role", user.getRole(), where);
			}
			
		where = this.createWhere("productName", (String)filterParams.get("productName"), where);
		
		return where;
	}

	private void setFilterParam(Map<String, Object> filterParams) {
		if(user.getIdUser() != null && user.getIdUser() != 0) {
			query.setParameter("idUser", user.getIdUser());
		}
		if(user.getFirstName() != null && user.getFirstName().length() > 0) {
			query.setParameter("firstName", user.getFirstName() + "%");
		}
		if(user.getSecondName() != null && user.getSecondName().length() > 0) {
			query.setParameter("secondName", user.getSecondName() + "%");
		}
		if(user.getEmail() != null && user.getEmail().length() > 0) {
			query.setParameter("email", user.getEmail() + "%");
		}
		if(user.getPhone() != null && user.getPhone().length() > 0) {
			query.setParameter("phone", user.getPhone() + "%");
		}
		if(user.getBlocked() != (byte)2) {
			query.setParameter("blocked", user.getBlocked());
		}
		if(!user.getRole().equals("all")) {
			query.setParameter("role", user.getRole());
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
			where += "u." + paramName + " like :" + paramName + " ";
		}

		return where;
	}
}