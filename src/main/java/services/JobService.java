package services;

import database.HibernateSessionFactoryUtil;
import entities.Jobseeker;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repositories.BaseRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JobService {
    @EJB(name = "ProfileRepository")
    private BaseRepository<Jobseeker> jobseekerRepository;

    public Jobseeker getJobseekerById(Long id) {
        return jobseekerRepository.findById(id, HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public List<Jobseeker> getAllJobseekers() {
        return jobseekerRepository.findAll(HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public void createNewJobseeker(Jobseeker jobseeker) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        jobseekerRepository.save(jobseeker, session);
        tx1.commit();
        session.close();
    }

    public void updateJobseeker(Jobseeker jobseeker) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        jobseekerRepository.update(jobseeker,session);
        tx1.commit();
        session.close();
    }

    public void deleteJobseekerById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Jobseeker jobseeker = jobseekerRepository.findById(id,session);
        jobseekerRepository.delete(jobseeker, session);
        tx1.commit();
        session.close();
    }

}
