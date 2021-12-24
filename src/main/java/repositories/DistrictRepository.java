package repositories;

import entities.District;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.util.List;

@Stateless(name="DistrictRepository")
public class DistrictRepository implements BaseRepository<District>{
    @Override
    public List<District> findAll(Session session) {
        return session.createQuery("SELECT a FROM District a", District.class).getResultList();
    }

    @Override
    public District findById(Long id, Session session) {
        return session.get(District.class, id);
    }

    @Override
    public void save(District district, Session session) {
        session.save(district);
    }

    @Override
    public void update(District district, Session session) {
        session.update(district);
    }

    @Override
    public void delete(District district, Session session) {
        session.delete(district);
    }
}
