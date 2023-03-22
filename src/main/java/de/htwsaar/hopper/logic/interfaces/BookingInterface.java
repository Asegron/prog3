package de.htwsaar.hopper.logic.interfaces;

import java.util.Calendar;

/**
 * Interface für die Klasse Booking.
 * @author Philip
 */
public interface BookingInterface extends DBObjectInterface{

    /* UI-METHODEN */
    String getCustomerShowField();
    String getCarShowField();

    /* PREISBERECHNUNG */
    double calculatePrice(int carId);
    double calculateFinalPrice(int carId);

    /* GETTER */
    int getBookingId();
    int getCarId();
    int getCustomerId();
    Calendar getPickUpDate();
    Calendar getDropOffDate();
    Calendar getRealDropOffDate();
    int getChecklistId();

    /* SETTER */
    void setCarId(int carId);
    void setCustomerId(int customerId);
    void setPickUpDate(Calendar startDate);
    void setDropOffDate(Calendar returnDate);
    void setRealDropOffDate(Calendar realReturnDate);
    void setChecklistId(int checklistId);

    /* toString */
    String toString();
}
