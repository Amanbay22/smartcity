package services;

import database.HibernateSessionFactoryUtil;
import entities.District;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repositories.BaseRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class DistrictService {
    @EJB(name = "DistrictRepository")
    private BaseRepository<District> districtRepository;

    public District getDistrictById(Long id) {
        return districtRepository.findById(id, HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public List<District> getAllDistricts() {
        return districtRepository.findAll(HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public void createNewDistrict(District district) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        districtRepository.save(district, session);
        tx1.commit();
        session.close();
    }

    public void updateDistrict(District district) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        districtRepository.update(district,session);
        tx1.commit();
        session.close();
    }

    public void deleteDistrictById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        District district = districtRepository.findById(id,session);
        districtRepository.delete(district, session);
        tx1.commit();
        session.close();
    }

    public District getDistrictByName(String name) {
        List<District> all = districtRepository.findAll(HibernateSessionFactoryUtil.getSessionFactory().openSession());
        return all.stream().filter(el -> el.getDistrictName().toLowerCase().equals(name)).findFirst().orElse(null);
    }
}
