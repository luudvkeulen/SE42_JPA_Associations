package auction.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(name = "Account.getAll", query = "select a from Account as a"),
        @NamedQuery(name = "Account.count", query = "select count(a) from Account as a"),
        @NamedQuery(name = "Account.findByAccountNr", query = "select a from Account as a where a.accountNr = :accountNr")
})
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long accountNr;
    private Long balance;
    private Long threshold;
    private long id;

    public Account() {
    }

    public Account(Long accountNr){
        balance = 0L;
        threshold = 0L;
        this.accountNr = accountNr;
    }

    //<editor-fold defaultstate="collapsed" desc="getters and setters ....">

    public Boolean add(Long amount) {
        if (balance + amount >= threshold) {
            balance += amount;
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (this.accountNr != other.accountNr && (this.accountNr == null || !this.accountNr.equals(other.accountNr))) {
            return false;
        }
        if (this.balance != other.balance && (this.balance == null || !this.balance.equals(other.balance))) {
            return false;
        }
        if (this.threshold != other.threshold && (this.threshold == null || !this.threshold.equals(other.threshold))) {
            return false;
        }
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}