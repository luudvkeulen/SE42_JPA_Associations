package auction.dao;

import auction.domain.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDAOJPAImpl implements UserDAO {

    private final EntityManager entityManager;

    public UserDAOJPAImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public int count() {
        Query q = entityManager.createNamedQuery("User.count", User.class);
        return ((Long)q.getSingleResult()).intValue();
    }

    @Override
    public void create(User user) {
         if (findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }
        entityManager.persist(user);
    }

    @Override
    public void edit(User user) {
        if (findByEmail(user.getEmail()) == null) {
            throw new IllegalArgumentException();
        }
        entityManager.merge(user);
    }


    @Override
    public List<User> findAll() {
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public User findByEmail(String email) {
        Query q = entityManager.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", email);
        q.setFirstResult(0);
        q.setMaxResults(1);
        List<User> result = (List<User>)q.getResultList();
        if(result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void remove(User user) {
        entityManager.remove(user);
    }
}
