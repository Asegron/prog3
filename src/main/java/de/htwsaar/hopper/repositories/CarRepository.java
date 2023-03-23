package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Car;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Repository-Klasse für Car. Dient zum Abrufbarmachen über die Datenbank.
 * @author Ronny
 */
public class CarRepository {
    /**
     * Findet ein Car über seine ID.
     * @param carId ID des zu findenden Cars
     * @return Gefundenes Car; null, falls nicht gefunden
     */
    public static Car find(int carId) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.find(Car.class, carId);
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Geht alle gespeicherten Cars durch und gibt sie als Liste zurueck.
     * @return Alle Cars in der Datenbank; null, falls keine existieren.
     */
    public static List<Car> findAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT c FROM Car AS c");

        try {
            return (List<Car>) query.getResultList();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Sucht alle Cars, die noch verfügbar sind und gibt sie als Liste aus.
     * @return Die Car-Liste; null, falls keine verfügbaren Cars existieren
     */
    public static List<Car> findAvailable() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query queryForAvailableCars = entityManager.createQuery("SELECT c FROM Car AS c WHERE " +
                "NOT EXISTS (SELECT b FROM Booking AS b WHERE c.carId=b.carId AND b.realDropOffDate = null)");

        try {
            return (List<Car>) queryForAvailableCars.getResultList();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Sucht alle Cars, die nicht mehr verfuegbar sind und gibt sie als Liste aus.
     * @return Die Car-Liste; null, wenn keine nicht mehr verfügbaren Cars existieren.
     */
    public static List<Car> findUnavailable() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT c FROM Car AS c, Booking AS b WHERE " +
                "c.carId = b.carId AND b.realDropOffDate = null");

        try {
            return (List<Car>) query.getResultList();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Nimmt ein Car entgegen und loescht dieses aus der DB.
     * Wird dieses Car nicht in der DB gefunden, wird eine IllegalArgumentException geworfen.
     * Nach dem Löschen werden ggf. vorhandene orphaned records entfernt.
     * @param car Die uebergebene / zu loeschende Entitaet.
     * @throws IllegalArgumentException wenn Objekt nicht in DB
     */
    public static void delete(Car car) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.remove(entityManager.contains(car) ? car : entityManager.merge(car));

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        removeOrphan(car);
    }

    /**
     * Nimmt ein Car-Objekt entgegen und persistiert es in der Datenbank.
     * @param car Das uebergebene Objekt.
     */
    public static void persist(Car car) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.persist(entityManager.contains(car) ? car : entityManager.merge(car));

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Wird nach dem Löschen eines Cars automatisch aufgerufen und durchsucht alle vorhandenen Bookings.
     * Taucht das gelöschte Car in einem Booking auf, wird auch das korrespondierende Booking entfernt.
     * @param car Das gelöschte Car.
     */
    private static void removeOrphan(Car car) {
        List<Booking> bookings = BookingRepository.findAll();

        if (bookings != null) {
            for (Booking booking : bookings) {
                if (booking.getCarId() == car.getCarId()) {
                    BookingRepository.delete(booking);
                }
            }
        }
    }

    /**
     * Wird beim Ändern von eimen Auto automatisch aufgerufen.
     * @param id ist das Id von dem Auto, das geändert werden soll.
     * @param car ist das neue Auto
     */
    public  static void updateCar( int id , Car car){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default") ;
        EntityManager entityManager = entityManagerFactory.createEntityManager() ;
        Transaction transaction = (Transaction) entityManager.getTransaction();
        try {
            transaction.begin();
            Car oldcar = find(id);
            oldcar.setBrand(car.getBrand());
            oldcar.setType(car.getType());
            oldcar.setModel(car.getModel()) ;
            oldcar.setSeats(car.getSeats());
            oldcar.setCurrentPrice(car.getCurrentPrice()) ;
            oldcar.setBasePrice(car.getBasePrice());
            oldcar.setLicensePlate(car.getLicensePlate());
            oldcar.setCreationDate(car.getCreationDate());
            entityManager.merge(oldcar) ;
            transaction.commit();
        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
    }
}
