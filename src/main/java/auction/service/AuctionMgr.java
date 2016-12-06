package auction.service;

import auction.dao.ItemDAOJPAImpl;
import auction.dao.UserDAOJPAImpl;
import nl.fontys.util.Money;
import auction.domain.Bid;
import auction.domain.Item;
import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class AuctionMgr  {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("auctionUnit");

    private ItemDAOJPAImpl itemDAOJPA;

    public AuctionMgr() {}

   /**
     * @param id
     * @return het item met deze id; als dit item niet bekend is wordt er null
     *         geretourneerd
     */
    public Item getItem(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        itemDAOJPA = new ItemDAOJPAImpl(em);
        Item result = itemDAOJPA.find(id);
        if(result != null) return result;
        return null;
    }

  
   /**
     * @param description
     * @return een lijst met items met @desciption. Eventueel lege lijst.
     */
    public List<Item> findItemByDescription(String description) {
        EntityManager em = entityManagerFactory.createEntityManager();
        itemDAOJPA = new ItemDAOJPAImpl(em);
        return itemDAOJPA.findByDescription(description);
    }

    /**
     * @param item
     * @param buyer
     * @param amount
     * @return het nieuwe bod ter hoogte van amount op item door buyer, tenzij
     *         amount niet hoger was dan het laatste bod, dan null
     */
    public Bid newBid(Item item, User buyer, Money amount) {
        EntityManager em = entityManagerFactory.createEntityManager();
        itemDAOJPA = new ItemDAOJPAImpl(em);
        em.getTransaction().begin();
        Bid bid = item.newBid(buyer, amount);
        em.getTransaction().commit();
        return bid;
    }
}
