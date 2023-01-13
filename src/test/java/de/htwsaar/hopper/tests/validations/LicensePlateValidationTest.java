package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.logic.validations.CarValidation;
import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

public class LicensePlateValidationTest {

    @Test
    public void licensePlateSmallCharsWorking(){
        String smallCharLicensePlate = "sb-ll-123";
        CarValidation.validateLicensePlate(smallCharLicensePlate);
    }

    @Test
    public void licensePlate2NumbersAtEndWorking(){
        String bigCharLicensePlate = "SB-LL-12";
        CarValidation.validateLicensePlate(bigCharLicensePlate);
    }

    @Test
    public void licensePlate1CharInMiddleWorking(){
        String bigCharLicensePlate = "SB-L-123";
        CarValidation.validateLicensePlate(bigCharLicensePlate);
    }

    @Test
    public void licensePlate1CharsAtBeggingWorking(){
        String bigCharLicensePlate = "S-LL-123";
        CarValidation.validateLicensePlate(bigCharLicensePlate);
    }

    @Test
    public void licensePlate3CharsAtBeggingWorking(){
        String bigCharLicensePlate = "SBE-LL-12";
        CarValidation.validateLicensePlate(bigCharLicensePlate);
    }

    @Test
    public void licensePlateWorking(){
        String license = "SB-XX-69";
        CarValidation.validateLicensePlate(license);
    }

    @Test
    public void licensePlateWorking2(){
        String license = "SB-KV-42";
        CarValidation.validateLicensePlate(license);
    }

    @Test (expected = IllegalArgumentException.class)
    public void licensePlateCompletelyEmpty(){
        String license = "";
        CarValidation.validateLicensePlate(license);
    }
    @Test (expected = IllegalArgumentException.class)
    public void licensePlate1NumberAtEndShouldThrowException(){
        String oneCharEndLicensePlate = "SB-LL-1";
        CarValidation.validateLicensePlate(oneCharEndLicensePlate);
    }
    @Test (expected = IllegalArgumentException.class)
    public void licensePlateOnlyNumbers(){
        String license = "69-96-69";
        CarValidation.validateLicensePlate(license);
    }

    @Test (expected = IllegalArgumentException.class)
    public void licensePlateOnlyLetters(){
        String license = "XD-ke-kW";
        CarValidation.validateLicensePlate(license);
    }

    @Test (expected = IllegalArgumentException.class)
    public void licensePlateLetterAndNumberAtBeginning(){
        String license = "X1-23-45";
        CarValidation.validateLicensePlate(license);
    }
    @Test (expected = IllegalArgumentException.class)
    public void licensePlateTooLong(){
        String license = "SB-KV-42-69";
        CarValidation.validateLicensePlate(license);
    }

    @Test (expected = IllegalArgumentException.class)
    public void licensePlateTooShort(){
        String license = "SB-42";
        CarValidation.validateLicensePlate(license);
    }

    @Test (expected = IllegalArgumentException.class)
    public void licensePlateWithoutDashes(){
        String license = "SB4269";
        CarValidation.validateLicensePlate(license);
    }

    @Test (expected = IllegalArgumentException.class)
    public void licensePlateWithDashesAtBeginning(){
        String license = "-SB-42-69";
        CarValidation.validateLicensePlate(license);
    }

    @Test (expected = IllegalArgumentException.class)
    public void licensePlateWithDashesAtEnd(){
        String license = "SB-42-69-";
        CarValidation.validateLicensePlate(license);
    }

    @Test (expected = IllegalArgumentException.class)
    public void licensePlateWithDashesInBetween(){
        String license = "SB-42--69";
        CarValidation.validateLicensePlate(license);
    }

    @Test (expected = IllegalArgumentException.class)
    public void licensePlateNull(){
        CarValidation.validateLicensePlate(null);
    }

}
