package services;

import database.HibernateSessionFactoryUtil;
import entities.BusinessInformation;
import entities.District;
import entities.Place;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repositories.BaseRepository;
import repositories.PlaceRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class PlaceService {
    @EJB(name = "PlaceRepository")
    private BaseRepository<Place> placeRepository;
    @EJB
    private BaseRepository<District> districtRepository;
    public Place getPlaceById(Long id) {
        return placeRepository.findById(id, HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public List<Place> getAllPlaces() {
        return placeRepository.findAll(HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public void createNewPlace(Place place, Long districtId) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        District byId = districtRepository.findById(districtId, session);
        byId.addPlace(place);
        districtRepository.update(byId, session);
        tx1.commit();
        session.close();
    }

    public void updatePlace(Place place) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        placeRepository.update(place,session);
        tx1.commit();
        session.close();
    }

    public void deletePlaceById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Place place = placeRepository.findById(id,session);
        placeRepository.delete(place, session);
        tx1.commit();
        session.close();
    }

    public Place getPlaceByName(String name) {
        List<Place> all = placeRepository.findAll(HibernateSessionFactoryUtil.getSessionFactory().openSession());
        return all.stream().filter(el -> el.getPlaceName().toLowerCase().equals(name)).findFirst().orElse(null);
    }
}
