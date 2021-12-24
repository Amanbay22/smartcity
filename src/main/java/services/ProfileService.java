package services;

import database.HibernateSessionFactoryUtil;
import entities.Profile;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repositories.BaseRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ProfileService {
    @EJB(name = "ProfileRepository")
    private BaseRepository<Profile> profileRepository;

    public Profile getProfileById(Long id) {
        return profileRepository.findById(id, HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll(HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public void createNewProfile(Profile profile) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        profileRepository.save(profile, session);
        tx1.commit();
        session.close();
    }

    public void updateProfile(Profile profile) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        profileRepository.update(profile,session);
        tx1.commit();
        session.close();
    }

    public void deleteCityById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Profile profile = profileRepository.findById(id,session);
        profileRepository.delete(profile, session);
        tx1.commit();
        session.close();
    }

    public Profile getProfileByEmailAndPassword(String email, String pass) {
        return profileRepository.findAll(HibernateSessionFactoryUtil.getSessionFactory().openSession())
                .stream().filter(el->el.getEmail().equals(email) && el.getPassword().equals(pass))
                .findFirst().orElse(null);
    }
}
