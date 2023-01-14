package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.implementations.Customer;

import javax.persistence.PrePersist;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Stellt sicher, dass kein Customer mit unzulaessigen null-Werten in die DB gelangt
 * @author roblin
 */
public class PreventNullPersistForCustomer {

    /**
     * Wird automatisiert aufgerufen, wann immer eine Customer-Entitaet persistiert werden soll.
     * Prueft alle angegeben Attribute mithilfe der Utils.check()-Methode auf null,
     * indem es sie in einen Stream ueberfuehrt und diesen mit allMatch() untersucht.
     * Wird ein null-Wert erkannt, erfolgt eine IllegalArgumentException mit Message und
     * der Persist der Entitaet wird abgebrochen.
     * @param customer Das zu pruefende Customer-Objekt.
     */
    @PrePersist
    public void testAttributesOnNull(Customer customer) {
        Utils.check(Stream.of(customer.getFirstName(),
            customer.getLastName(),
            customer.getEmail(),
            customer.getStreet(),
            customer.getHouseNumber(),
            customer.getZipCode(),
            customer.getCity(),
            customer.getPhoneNumber(),
            customer.getIBAN(),
            customer.getDriverLicenseNumber(),
            customer.getDriverLicenseExpirationDate())
            .allMatch(Objects::nonNull),"Null-Wert erkannt -> unzulaessig");
    }
}
