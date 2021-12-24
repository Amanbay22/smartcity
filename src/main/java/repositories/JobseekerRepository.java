package repositories;

import entities.BusinessInformation;
import entities.Jobseeker;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.util.List;

@Stateless(name="JobseekerRepository")
public class JobseekerRepository implements BaseRepository<Jobseeker>{
    @Override
    public List<Jobseeker> findAll(Session session) {
        return session.createQuery("SELECT a FROM Jobseeker a", Jobseeker.class).getResultList();
    }

    @Override
    public Jobseeker findById(Long id, Session session) {
        return session.get(Jobseeker.class, id);
    }

    @Override
    public void save(Jobseeker jobseeker, Session session) {
        session.save(jobseeker);
    }

    @Override
    public void update(Jobseeker jobseeker, Session session) {
        session.update(jobseeker);
    }

    @Override
    public void delete(Jobseeker jobseeker, Session session) {
        session.delete(jobseeker);
    }
}
