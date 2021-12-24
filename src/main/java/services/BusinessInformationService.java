package services;

import database.HibernateSessionFactoryUtil;
import entities.BusinessInformation;
import entities.District;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repositories.BaseRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class BusinessInformationService {
    @EJB(name = "BusinessInformationRepository")
    private BaseRepository<BusinessInformation> businessInformationRepository;
    @EJB
    private BaseRepository<District> districtRepository;
    public BusinessInformation getInfoById(Long id) {
        return businessInformationRepository.findById(id, HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public List<BusinessInformation> getAllInfo() {
        return businessInformationRepository.findAll(HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public void createNewInfo(BusinessInformation info, Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        District byId = districtRepository.findById(id, session);
        byId.addInfo(info);
        districtRepository.update(byId, session);
        tx1.commit();
        session.close();
    }

    public void updateInfo(BusinessInformation info) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        businessInformationRepository.update(info,session);
        tx1.commit();
        session.close();
    }

    public void deleteInfoById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        BusinessInformation info = businessInformationRepository.findById(id,session);
        businessInformationRepository.delete(info, session);
        tx1.commit();
        session.close();
    }

    public BusinessInformation getInfoByName(String name) {
        List<BusinessInformation> all = businessInformationRepository.findAll(HibernateSessionFactoryUtil.getSessionFactory().openSession());
        return all.stream().filter(el -> el.getBusinessName().toLowerCase().equals(name)).findFirst().orElse(null);
    }
}
