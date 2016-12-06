package auction.service;

import auction.domain.Account;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class Toetstest {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionUnit");
    EntityManager em = emf.createEntityManager();

    @Test
    public void test1() {
        Account account = new Account(111L);
        em.getTransaction().begin();

        em.persist(account);
        assertNull(account.getId());

        em.getTransaction().commit();

        assertTrue(account.getId() > 0L);
    }

    @Test
    public void test3() {
        Long expected = 100L;
        Account account = new Account(111L);
        account.setId(331L);
        Account merged;
        em.getTransaction().begin();

        merged = em.merge(account);
        assertEquals(merged.getId(), account.getId());
        em.getTransaction().commit();
        assertTrue(merged.getId() != account.getId());
        assertFalse(em.contains(merged));
        assertFalse(em.contains(account));

        em.close();

        assertSame(merged, account);
        assertEquals(merged, account);
    }
}
