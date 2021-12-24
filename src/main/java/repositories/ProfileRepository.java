package repositories;

import entities.Profile;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.util.List;

@Stateless(name="ProfileRepository")
public class ProfileRepository implements BaseRepository<Profile>{
    @Override
    public List<Profile> findAll(Session session) {
        return session.createQuery("SELECT a FROM Profile a", Profile.class).getResultList();
    }

    @Override
    public Profile findById(Long id, Session session) {
        return session.get(Profile.class, id);
    }

    @Override
    public void save(Profile profile, Session session) {
        session.save(profile);
    }

    @Override
    public void update(Profile profile, Session session) {
        session.update(profile);
    }

    @Override
    public void delete(Profile profile, Session session) {
        session.delete(profile);
    }
}
