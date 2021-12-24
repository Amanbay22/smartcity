package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @OneToOne
    private Profile profile;
    private String degree;
    private String about;
    private int yearJob;
    @ManyToMany(mappedBy = "jobseekers")
    private List<Vacancy> vacancies;
}
