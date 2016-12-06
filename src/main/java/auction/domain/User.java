package auction.domain;

import javax.persistence.*;
import java.io.Serializable;

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

    public User(String email) {
        this.email = email;
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
}
