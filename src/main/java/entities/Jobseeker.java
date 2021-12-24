package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Jobseeker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jobseeker_id")
    private Long id;
    private String degree;
    private String about;
    private int yearJob;
    @ManyToMany(mappedBy = "jobseekers")
    @JsonIgnore
    private List<Vacancy> vacancies;

    public Jobseeker(String degree, String about, int yearJob) {
        this.degree = degree;
        this.about = about;
        this.yearJob = yearJob;
        this.vacancies = new ArrayList<>();
    }
}
