package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Long id;
    private String districtName;
    private int population;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "district")
    private List<Profile> profiles;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "district")
    private List<Place> places;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "district")
    private List<BusinessInformation> businessInformations;

    public District(String districtName, int population) {
        this.districtName = districtName;
        this.population = population;
        this.profiles = new ArrayList<>();
        this.places = new ArrayList<>();
        this.businessInformations = new ArrayList<>();
    }

    public void addPlace(Place place) {
        place.setDistrict(this);
        places.add(place);
    }

    public void addProfile(Profile profile) {
        profile.setDistrict(this);
        profiles.add(profile);
    }

    public void addInfo(BusinessInformation info) {
        info.setDistrict(this);
        businessInformations.add(info);
    }
}
