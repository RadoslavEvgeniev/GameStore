package app.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    private Long id;
    private String email;
    private String password;
    private String fullName;
    private Boolean isAdmin;
    private Order order;
    private Set<OwnedGame> games;

    public User() {
        this.games = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "email", unique = true)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "full_name")
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "is_admin")
    public Boolean getAdmin() {
        return this.isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public Set<OwnedGame> getGames() {
        return this.games;
    }

    public void setGames(Set<OwnedGame> games) {
        this.games = games;
    }
}
