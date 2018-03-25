package app.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    private Long id;
    private User user;
    private Set<Game> products;

    public Order() {
        this.products = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(mappedBy = "order", fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToMany(mappedBy = "orders", fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public Set<Game> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Game> products) {
        this.products = products;
    }
}
