package app.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game {

    private Long id;
    private String title;
    private String trailer;
    private String imageUrl;
    private Double size;
    private BigDecimal price;
    private String description;
    private Date releaseDate;
    private Set<Order> orders;

    public Game() {
        this.orders = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "trailer")
    public String getTrailer() {
        return this.trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Column(name = "poster")
    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Column(name = "size")
    public Double getSize() {
        return this.size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "release_date")
    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "orders_games")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
