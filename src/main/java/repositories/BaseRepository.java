package repositories;

import org.hibernate.Session;

import javax.ejb.Local;
import java.util.List;

@Local
public interface BaseRepository<T> {
    List<T> findAll(Session session);
    T findById(Long id, Session session);
    void save(T t, Session session);
    void update(T t, Session session);
    void delete(T t, Session session);
}
