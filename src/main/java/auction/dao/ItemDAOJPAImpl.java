package auction.dao;

import auction.domain.Item;
import auction.domain.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ItemDAOJPAImpl implements ItemDAO {

    private final EntityManager entityManager;

    public ItemDAOJPAImpl (EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public int count() {
        Query q = entityManager.createNamedQuery("Item.count", Item.class);
        return ((Long)q.getSingleResult()).intValue();
    }

    @Override
    public void create(Item item) {
        if (find(item.getId()) != null) {
            throw new EntityExistsException();
        }
        entityManager.persist(item);
    }

    @Override
    public void edit(Item item) {
        if (find(item.getId()) == null) {
            throw new IllegalArgumentException();
        }
        entityManager.merge(item);
    }

    @Override
    public Item find(Long id) {
        Query q = entityManager.createNamedQuery("Item.findById", Item.class);
        q.setParameter("id", id);
        q.setFirstResult(0);
        q.setMaxResults(1);
        List<Item> result = (List<Item>)q.getResultList();
        if(result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Item> findAll() {
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Item.class));
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Item> findByDescription(String description) {
        Query q = entityManager.createNamedQuery("Item.findByDesc", Item.class);
        q.setParameter("desc", description);
        return (List<Item>)q.getResultList();
    }

    @Override
    public void remove(Item item) {
        entityManager.remove(item);
    }
}
