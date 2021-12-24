package services;

import database.HibernateSessionFactoryUtil;
import entities.BusinessInformation;
import entities.Profile;
import entities.Vacancy;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repositories.BaseRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class VacancyService {
    @EJB(name="VacancyRepository")
    private BaseRepository<Vacancy> vacancyRepository;
    public Vacancy getProfileById(Long id) {
        return vacancyRepository.findById(id, HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public List<Vacancy> getAllVacancies() {
        return vacancyRepository.findAll(HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public void createNewVacancy(Vacancy vacancy) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        vacancyRepository.save(vacancy, session);
        tx1.commit();
        session.close();
    }

    public void updateVacancy(Vacancy vacancy) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        vacancyRepository.update(vacancy,session);
        tx1.commit();
        session.close();
    }

    public void deleteVacancyById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Vacancy vacancy = vacancyRepository.findById(id,session);
        vacancyRepository.delete(vacancy, session);
        tx1.commit();
        session.close();
    }

    public Vacancy getVacancyByCompany(String name) {
        List<Vacancy> all = vacancyRepository.findAll(HibernateSessionFactoryUtil.getSessionFactory().openSession());
        return all.stream().filter(el -> el.getCompanyName().toLowerCase().equals(name)).findFirst().orElse(null);
    }
}
