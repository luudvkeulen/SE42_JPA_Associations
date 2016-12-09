package auction.domain;

import nl.fontys.util.Money;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@NamedQueries({
        @NamedQuery(name = "Item.count", query = "select count(i) from Item i"),
        @NamedQuery(name = "Item.findById", query = "select i from Item i where i.id = :id"),
        @NamedQuery(name = "Item.findByDesc", query = "select i from Item i where i.description = :desc")
})
public class Item implements Comparable, Serializable {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private User seller;
    private Category category;
    private String description;
    @OneToOne
    private Bid highest;

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
        seller.addOfferedItem(this);
    }

    public Item() {}

    public Long getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Bid getHighestBid() {
        return highest;
    }

    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount, this);
        return highest;
    }

    public int compareTo(Object arg0) {
        return 0;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Item)) return false;
        Item other = (Item)o;
        if(other.getId() == this.id) return true;
        return false;
    }
}
