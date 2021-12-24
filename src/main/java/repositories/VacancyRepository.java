package repositories;

import entities.Vacancy;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.util.List;

@Stateless(name="VacancyRepository")
public class VacancyRepository implements BaseRepository<Vacancy>{
    @Override
    public List<Vacancy> findAll(Session session) {
        return session.createQuery("SELECT a FROM Vacancy a", Vacancy.class).getResultList();
    }

    @Override
    public Vacancy findById(Long id, Session session) {
        return session.get(Vacancy.class, id);
    }

    @Override
    public void save(Vacancy vacancy, Session session) {
        session.save(vacancy);
    }

    @Override
    public void update(Vacancy vacancy, Session session) {
        session.update(vacancy);
    }

    @Override
    public void delete(Vacancy vacancy, Session session) {
        session.delete(vacancy);
    }
}
