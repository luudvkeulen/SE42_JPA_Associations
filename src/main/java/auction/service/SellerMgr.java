package auction.service;

import auction.dao.ItemDAOJPAImpl;
import auction.dao.UserDAOJPAImpl;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SellerMgr {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("auctionUnit");

    private ItemDAOJPAImpl itemDAOJPA;

    public SellerMgr() {}

    /**
     * @param seller
     * @param cat
     * @param description
     * @return het item aangeboden door seller, behorende tot de categorie cat
     *         en met de beschrijving description
     */
    public Item offerItem(User seller, Category cat, String description) {
        Item item = new Item(seller, cat, description);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        itemDAOJPA = new ItemDAOJPAImpl(entityManager);
        entityManager.getTransaction().begin();
        itemDAOJPA.create(item);
        entityManager.getTransaction().commit();
        return item;
    }
    
     /**
     * @param item
     * @return true als er nog niet geboden is op het item. Het item word verwijderd.
     *         false als er al geboden was op het item.
     */
    public boolean revokeItem(Item item) {
        if(item.getHighestBid() != null) return false;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        itemDAOJPA = new ItemDAOJPAImpl(entityManager);
        entityManager.getTransaction().begin();
        Item dbItem = entityManager.merge(item);
        itemDAOJPA.remove(dbItem);
        entityManager.getTransaction().commit();
        return true;
    }
}
