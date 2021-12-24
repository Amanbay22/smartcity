package repositories;

import entities.BusinessInformation;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.util.List;

@Stateless(name="BusinessInformationRepository")
public class BusinessInformationRepository implements BaseRepository<BusinessInformation>{

    @Override
    public List<BusinessInformation> findAll(Session session) {
        return session.createQuery("SELECT a FROM BusinessInformation a", BusinessInformation.class).getResultList();
    }

    @Override
    public BusinessInformation findById(Long id, Session session) {
        return session.get(BusinessInformation.class, id);
    }

    @Override
    public void save(BusinessInformation businessInformation, Session session) {
        session.save(businessInformation);
    }

    @Override
    public void update(BusinessInformation businessInformation, Session session) {
        session.update(businessInformation);
    }

    @Override
    public void delete(BusinessInformation businessInformation, Session session) {
        session.delete(businessInformation);
    }
}
