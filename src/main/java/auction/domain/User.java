package auction.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.count", query = "select count(u) from User u"),
        @NamedQuery(name = "User.findByEmail", query = "select u from User u where u.email = :email")
})
public class User implements Serializable {

    //@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    //private Integer id;
    @Id
    private String email;
    @OneToMany (mappedBy = "seller")
    private Set<Item> offeredItems;

    public User(String email) {
        this.email = email;
        this.offeredItems = new LinkedHashSet<>();
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        return (this.email.equals(((User)o).getEmail()));
    }

    public Iterator<Item> getOfferedItems() {
        return this.offeredItems.iterator();
    }

    public void addOfferedItem(Item i) {
        this.offeredItems.add(i);
    }

    public int numberOfOfferedItems() {
        return offeredItems.size();
    }
}
