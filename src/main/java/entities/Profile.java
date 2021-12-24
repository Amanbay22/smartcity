package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String email;
    private String phoneNumber;
    private String password;
    @ManyToOne
    @JoinColumn(name = "district_id")
    @JsonIgnore
    private District district;

    public Profile(String firstName, String lastName, int age, Role role, String email, String phoneNumber, String password, District district) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.district = district;
    }
}
