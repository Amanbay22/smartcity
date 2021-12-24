package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;
    private String placeName;
    private String address;
    private String type;
    private BigDecimal rating;
    private String phone;
    @ManyToOne
    @JoinColumn(name = "district_id")
    @JsonIgnore
    private District district;

    public Place(String placeName, String address, String type, double rating, String phone) {
        this.placeName = placeName;
        this.address = address;
        this.type = type;
        this.rating = BigDecimal.valueOf(BigDecimal.valueOf(rating).setScale(1, RoundingMode.HALF_UP).floatValue());
        this.phone = phone;
    }

}
