package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private int priceFrom;
    private int priceTo;
    private String companyName;
    @ManyToMany
    @JoinTable(
            name = "vacancy_jobseekers",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "jobseeker_id"))
    @JsonIgnore
    private List<Jobseeker> jobseekers;

    public Vacancy(String vacancyName, String description, int priceFrom, int priceTo, String companyName) {
        this.vacancyName = vacancyName;
        this.description = description;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.companyName = companyName;
        this.jobseekers = new ArrayList<>();
    }
}
