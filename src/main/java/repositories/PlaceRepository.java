package repositories;


import entities.Place;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.util.List;

@Stateless(name="PlaceRepository")
public class PlaceRepository implements BaseRepository<Place>{
    @Override
    public List<Place> findAll(Session session) {
        return session.createQuery("SELECT a FROM Place a", Place.class).getResultList();
    }

    @Override
    public Place findById(Long id, Session session) {
        return session.get(Place.class, id);
    }

    @Override
    public void save(Place place, Session session) {
        session.save(place);
    }

    @Override
    public void update(Place place, Session session) {
        session.update(place);
    }

    @Override
    public void delete(Place place, Session session) {
        session.delete(place);
    }
}
