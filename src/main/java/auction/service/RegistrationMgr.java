package auction.service;

import java.util.*;

import auction.dao.UserDAOJPAImpl;
import auction.domain.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RegistrationMgr {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("auctionUnit");

    private UserDAOJPAImpl userDAO;

    public RegistrationMgr() {}

    /**
     * Registreert een gebruiker met het als parameter gegeven e-mailadres, mits
     * zo'n gebruiker nog niet bestaat.
     * @param email
     * @return Een Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres (nieuw aangemaakt of reeds bestaand). Als het e-mailadres
     * onjuist is ( het bevat geen '@'-teken) wordt null teruggegeven.
     */
    public User registerUser(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        userDAO = new UserDAOJPAImpl(entityManager);
        if (!email.contains("@")) {
            return null;
        }

        User user = userDAO.findByEmail(email);

        if (user != null) {
            return user;
        }
        user = new User(email);

        entityManager.getTransaction().begin();
        try {
            userDAO.create(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }

        return user;
    }

    /**
     *
     * @param email een e-mailadres
     * @return Het Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres of null als zo'n User niet bestaat.
     */
    public User getUser(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        userDAO = new UserDAOJPAImpl(entityManager);
        entityManager.getTransaction().begin();

        User user = null;

        try {
            user = userDAO.findByEmail(email);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }

        return user;
    }

    /**
     * @return Een iterator over alle geregistreerde gebruikers
     */
    public List<User> getUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        userDAO = new UserDAOJPAImpl(entityManager);
        entityManager.getTransaction().begin();

        List<User> users = new ArrayList<>();

        try {
            users = userDAO.findAll();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }

        return users;
    }
}
