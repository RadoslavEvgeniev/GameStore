package app.entities.dtos;

import app.annotations.Price;
import app.annotations.Size;

import java.math.BigDecimal;
import java.util.Date;

public class GameDto {

    private String title;
    @Price
    private BigDecimal price;
    @Size
    private Double size;
    private String trailer;
    private String imageUrl;
    private String description;
    private Date releaseDate;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Double getSize() {
        return this.size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getTrailer() {
        return this.trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
