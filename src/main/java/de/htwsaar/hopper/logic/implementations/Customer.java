package de.htwsaar.hopper.logic.implementations;

import de.htwsaar.hopper.logic.interfaces.CustomerInterface;
import de.htwsaar.hopper.logic.validations.CustomerValidation;
import de.htwsaar.hopper.logic.validations.PreventNullPersistForCustomer;

import javax.persistence.*;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * Implementierung des Customer-Interface.
 * Annotiert für die Verwendung mit der Datenbank.
 *
 * @author gitroba
 */
@EntityListeners(PreventNullPersistForCustomer.class)
@Entity
@Table(name = "Customers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "CustomerId"),
                @UniqueConstraint(columnNames = "Email"),
                @UniqueConstraint(columnNames = "PhoneNumber"),
                @UniqueConstraint(columnNames = "IBAN"),
                @UniqueConstraint(columnNames = "DriverLicenseNumber")
        })
public final class Customer implements CustomerInterface {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CustomerId")
    int customerId;

    @Basic
    @Column(name = "FirstName")
    String firstName;

    @Basic
    @Column(name = "LastName")
    String lastName;

    @Basic
    @Column(name = "Email")
    String email;

    @Basic
    @Column(name = "Street")
    String street;

    @Basic
    @Column(name = "HouseNumber")
    String houseNumber;

    @Basic
    @Column(name = "ZipCode")
    String zipCode;

    @Basic
    @Column(name = "City")
    String city;

    @Basic
    @Column(name = "PhoneNumber")
    String phoneNumber;

    @Basic
    @Column(name = "IBAN")
    String iban;

    @Basic
    @Column(name = "DriverLicenseNumber")
    String driverLicenseNumber;

    @Basic
    @Column(name = "DriverLicenseExpirationDate")
    @Temporal(TemporalType.DATE)
    Calendar driverLicenseExpirationDate;

    /**
     * Leerer Konstruktor, der von Hibernate benötigt wird.
     * Nicht verwenden, da sonst die anderen Felder möglicherweise nicht gesetzt werden können.
     */
    public Customer() {
    }

    /**
     * Konstruktor für alle Parameter
     *
     * @param firstName                   Vorname des Kunden
     * @param lastName                    Nachname des Kunden
     * @param email                       E-Mail des Kunden
     * @param street                      Straße des Kunden
     * @param houseNumber                 Hausnummer des Kunden
     * @param zipCode                     Postleitzahl des Kunden
     * @param city                        Stadt des Kunden
     * @param phoneNumber                 Telefonnummer des Kunden
     * @param iban                        IBAN des Kunden
     * @param driverLicenseNumber         Führerscheinnummer des Kunden
     * @param driverLicenseExpirationDate Ablaufdatum des Führerscheins des Kunden
     */
    public Customer(String firstName, String lastName, String email, String street,
                    String houseNumber, String zipCode, String city, String phoneNumber,
                    String iban, String driverLicenseNumber, Calendar driverLicenseExpirationDate) {
        this.firstName = CustomerValidation.validateString(firstName, "Der Vorname darf nicht leer sein.");
        this.lastName = CustomerValidation.validateString(lastName, "Der Nachname darf nicht leer sein.");
        this.email = CustomerValidation.validateEmail(email);
        this.street = CustomerValidation.validateString(street, "Die Strasse darf nicht leer sein.");
        this.houseNumber = CustomerValidation.validateHouseNumber(houseNumber);
        this.zipCode = CustomerValidation.validateZipCode(zipCode);
        this.city = CustomerValidation.validateString(city, "Die Stadt darf nicht leer sein.");
        this.phoneNumber = CustomerValidation.validatePhoneNumber(phoneNumber);
        this.iban = CustomerValidation.validateIBAN(iban);
        this.driverLicenseNumber = CustomerValidation.validateDriverLicenseNumber(driverLicenseNumber);
        this.driverLicenseExpirationDate = CustomerValidation.validateExpirationDate(driverLicenseExpirationDate);
    }

    /**
     * Gibt zurück, ob der in der Datenbank gesetzte Führerschein noch gültig ist.
     *
     * @return true, falls das Datum noch nicht abgelaufen ist; sonst false
     */
    public boolean isDriverLicenseValid() {
        Calendar currentDate = Calendar.getInstance();
        return !driverLicenseExpirationDate.before(currentDate);
    }

    /* GETTER */
    @Override
    public int getCustomerId() {
        return customerId;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getHouseNumber() {
        return houseNumber;
    }

    @Override
    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getIBAN() {
        return iban;
    }

    @Override
    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }

    @Override
    public Calendar getDriverLicenseExpirationDate() {
        return driverLicenseExpirationDate;
    }

    /* SETTER */
    @Override
    public void setFirstName(String firstName) {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        this.firstName = CustomerValidation.validateString(firstName, bundle.getString("FIRSTNAME_NOT_VALID"));
    }

    @Override
    public void setLastName(String lastName) {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        this.lastName = CustomerValidation.validateString(lastName, bundle.getString("LASTNAME_NOT_VALID"));
    }

    @Override
    public void setEmail(String email) {
        this.email = CustomerValidation.validateEmail(email);
    }

    @Override
    public void setStreet(String street) {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        this.street = CustomerValidation.validateString(street, bundle.getString("STREET_NOT_VALID"));
    }

    @Override
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = CustomerValidation.validateHouseNumber(houseNumber);
    }

    @Override
    public void setZipCode(String zipCode) {
        this.zipCode = CustomerValidation.validateZipCode(zipCode);
    }

    @Override
    public void setCity(String city) {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        this.city = CustomerValidation.validateString(city, bundle.getString("CITY_NOT_VALID"));
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = CustomerValidation.validatePhoneNumber(phoneNumber);
    }

    @Override
    public void setIBAN(String iban) {
        this.iban = CustomerValidation.validateIBAN(iban);
    }

    @Override
    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = CustomerValidation.validateDriverLicenseNumber(driverLicenseNumber);
    }

    @Override
    public void setDriverLicenseExpirationDate(Calendar driverLicenseExpirationDate) {
        this.driverLicenseExpirationDate = CustomerValidation.validateExpirationDate(driverLicenseExpirationDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!getFirstName().equals(customer.getFirstName())) return false;
        if (!getLastName().equals(customer.getLastName())) return false;
        if (!getEmail().equals(customer.getEmail())) return false;
        if (!getStreet().equals(customer.getStreet())) return false;
        if (!getHouseNumber().equals(customer.getHouseNumber())) return false;
        if (!getZipCode().equals(customer.getZipCode())) return false;
        if (!getCity().equals(customer.getCity())) return false;
        if (!getPhoneNumber().equals(customer.getPhoneNumber())) return false;
        if (!iban.equals(customer.iban)) return false;
        if (!getDriverLicenseNumber().equals(customer.getDriverLicenseNumber())) return false;
        return getDriverLicenseExpirationDate().equals(customer.getDriverLicenseExpirationDate());
    }

    @Override
    public int hashCode() {
        int result = getCustomerId();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getStreet().hashCode();
        result = 31 * result + getHouseNumber().hashCode();
        result = 31 * result + getZipCode().hashCode();
        result = 31 * result + getCity().hashCode();
        result = 31 * result + getPhoneNumber().hashCode();
        result = 31 * result + iban.hashCode();
        result = 31 * result + getDriverLicenseNumber().hashCode();
        result = 31 * result + getDriverLicenseExpirationDate().hashCode();
        return result;
    }
}
