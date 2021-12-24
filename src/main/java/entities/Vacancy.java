package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vacancy{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacancy_id")
    private Long id;
    private String vacancyName;
    private String description;
    private String phoneNumber;
    private String email;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    @ManyToOne
    @JoinColumn(name = "place_id")
    @JsonIgnore
    private Place place;
    @ManyToMany
    @JoinTable(
            name = "vacancy_jobseekers",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "jobseeker_id"))
    private List<Jobseeker> jobseekers;
}
