package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Car;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Repository-Klasse für Car.
 *
 * @author Ronny
 */
public class CarRepository {
    /**
     * Findet ein Car über seine ID.
     *
     * @param carId ID des zu findenden Cars
     * @return Gefundenes Car; null, falls nicht gefunden
     */
    public static Car find(int carId) {
        return (Car) DBObjectRepository.find(Car.class, carId);
    }

    /**
     * Geht alle gespeicherten Cars durch und gibt sie als Liste zurück.
     *
     * @return Alle Cars in der Datenbank; null, falls keine existieren.
     */
    public static List<Car> findAll() {
        return (List<Car>) DBObjectRepository.findAll("Car");
    }

    /**
     * Sucht alle Cars, die noch verfügbar sind und gibt sie als Liste aus.
     *
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
     * Sucht alle Cars, die nicht mehr verfügbar sind und gibt sie als Liste aus.
     *
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
     * Nimmt ein Car-Objekt entgegen und löscht dieses aus der DB.
     * Wird das Car-Objekt nicht in der DB gefunden, wird eine IllegalArgumentException geworfen.
     * Nach dem Löschen werden ggf. vorhandene orphaned records entfernt.
     *
     * @param car Die übergebene / zu löschende Entität.
     * @throws IllegalArgumentException wenn Objekt nicht in DB
     */
    public static void delete(Car car) {
        DBObjectRepository.delete(car);

        removeOrphan(car);
    }

    /**
     * Nimmt ein Car-Objekt entgegen und persistiert es in der Datenbank.
     *
     * @param car Das übergebene Objekt.
     */
    public static void persist(Car car) {
        DBObjectRepository.persist(car);
    }

    /**
     * Wird nach dem Löschen eines Cars automatisch aufgerufen und durchsucht alle vorhandenen Bookings.
     * Taucht das gelöschte Car-Objekt in einem Booking auf, wird auch das korrespondierende Booking entfernt.
     *
     * @param car Das gelöschte Car-Objekt.
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
}
