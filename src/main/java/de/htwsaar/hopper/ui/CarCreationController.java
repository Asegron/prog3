package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.enums.FuelTypeEnum;
import de.htwsaar.hopper.logic.enums.SatNavEnum;
import de.htwsaar.hopper.logic.enums.TransmissionTypeEnum;
import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.repositories.CarRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class CarCreationController implements Initializable{

    private String carTypeStr;
    private String transmissionStr;
    private String fuelStr;
    private String satNavStr;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private Label labelBasePrice;

    @FXML
    private Label labelBrand;

    @FXML
    private Label labelCreationDate;

    @FXML
    private Label labelCurrentPrice;

    @FXML
    private Label labelLicensePlate;

    @FXML
    private Label labelModel;

    @FXML
    private Label labelSeats;

    @FXML
    private Label labelType;

    @FXML
    private Label labelHorsePower;

    @FXML
    private Label labelTransmissionType;

    @FXML
    private Label labelFuelType;

    @FXML
    private Label labelSatNav;

    @FXML
    private Label labelMileage;

    @FXML
    private MenuButton menuType;

    @FXML
    private MenuButton menuTransmission;

    @FXML
    private MenuButton menuFuel;

    @FXML
    private MenuButton menuSatNav;

    @FXML
    private TextField textFieldBasePrice;

    @FXML
    private TextField textFieldBrand;

    @FXML
    private DatePicker datePickCreationDate;

    @FXML
    private TextField textFieldCurrentPrice;

    @FXML
    private TextField textFieldLicensePlate;

    @FXML
    private TextField textFieldModel;

    @FXML
    private TextField textFieldSeats;

    @FXML
    private TextField textFieldHorsePower;

    @FXML
    private TextField textFieldMileage;

    @FXML
    void cancelCreation(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void createCar(ActionEvent event) {
        try {

            validateTextField(textFieldBrand, labelBrand.getText() + " leer");
            validateTextField(textFieldModel, labelModel.getText() + " leer");
            if (datePickCreationDate.getValue() == null){
                throw new IllegalArgumentException(labelCreationDate.getText() + " leer");
            }
            validateTextField(textFieldSeats, labelSeats.getText() + " leer");
            validateTextField(textFieldLicensePlate, labelLicensePlate.getText() + " leer");
            validateTextField(textFieldBasePrice, labelBasePrice.getText() + " leer");
            validateTextField(textFieldCurrentPrice, labelCurrentPrice.getText() + " leer");

            validateTextField(textFieldHorsePower, labelHorsePower.getText() + "leer");
            validateTextField(textFieldMileage, labelMileage.getText() + "leer");



            String brand = textFieldBrand.getText();
            int seats = Integer.parseInt(textFieldSeats.getText());
            LocalDate creationDateLocal = datePickCreationDate.getValue();
            String model = textFieldModel.getText();
            double curPrice = Double.parseDouble(textFieldCurrentPrice.getText());
            double basePrice = Double.parseDouble(textFieldBasePrice.getText());
            String licensePlate = textFieldLicensePlate.getText();
            int horsePower = Integer.parseInt(textFieldHorsePower.getText());
            int mileage = Integer.parseInt(textFieldMileage.getText());

            // Enum bekommen vom Menü
            CarTypeEnum concreteType = CarTypeEnum.ANDERE;
            TransmissionTypeEnum concreteTransmission = TransmissionTypeEnum.MANUELL;
            FuelTypeEnum concreteFuel = FuelTypeEnum.BENZIN;
            SatNavEnum concreteSatNav= SatNavEnum.JA;

            for (CarTypeEnum type : CarTypeEnum.values()){
                if (type.getLabel().equals(carTypeStr)){
                    concreteType = type;
                }
            }

            for (TransmissionTypeEnum transmission : TransmissionTypeEnum.values()){
                if (transmission.getLabel().equals(transmissionStr)){
                    concreteTransmission = transmission;
                }
            }

            for (FuelTypeEnum type : FuelTypeEnum.values()){
                if (type.getLabel().equals(fuelStr)){
                    concreteFuel = type;
                }
            }

            for (SatNavEnum type : SatNavEnum.values()){
                if (type.getLabel().equals(satNavStr)){
                    concreteSatNav = type;
                }
            }


            // LocalDate vom DatePicker zu Calender-Format
            Date creationDate = Date.from(creationDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Calendar creationDateCal = Calendar.getInstance();
            creationDateCal.setTime(creationDate);
            
            //Car car = new Car(concreteType,brand,creationDateCal,seats,basePrice,curPrice,licensePlate,model);

           // CarRepository.persist(car);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Auto erfolgreich erstellt");
            alert.showAndWait();
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Erstellen des Autos");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setChosenCarType();
        setChosenTransmission();
        setChosenFuel();
        setChosenSatNav();
    }

    /**
     * Setzt gewollten Autotypen, hierbei wird bei jedem
     * Klick auf ein Menü-Item der Text im Menü-Button ersetzt und
     * die lokale Variable carType mit dem neuen Typen ersetzt
     */
    private void setChosenCarType(){
        // basistyp, falls kein Typ ausgewählt
        carTypeStr = CarTypeEnum.ANDERE.getLabel();
        menuType.setText(carTypeStr);

        // Iteration über alle Enums und für jedes 1 Menü-Item erstellen
        for (CarTypeEnum type : CarTypeEnum.values()){
            MenuItem item = new MenuItem(type.getLabel());
            menuType.getItems().add(item);
            // wird Action auf Item bemerkt -> carType aktualisieren und Text
            item.setOnAction(e ->{
                menuType.setText(item.getText());
                carTypeStr = item.getText();
            });
        }
    }

    /**
     * Setzt gewollte Getriebeart, hierbei wird bei jedem
     * Klick auf ein Menü-Item der Text im Menü-Button ersetzt und
     * die lokale Variable transmission mit dem neuen Typen ersetzt
     */
    private void setChosenTransmission(){
        // basistyp, falls kein Typ ausgewählt
        transmissionStr = TransmissionTypeEnum.MANUELL.getLabel();
        menuTransmission.setText(transmissionStr);

        // Iteration über alle Enums und für jedes 1 Menü-Item erstellen
        for (TransmissionTypeEnum transmission : TransmissionTypeEnum.values()){
            MenuItem item = new MenuItem(transmission.getLabel());
            menuTransmission.getItems().add(item);
            // wird Action auf Item bemerkt -> carType aktualisieren und Text
            item.setOnAction(e ->{
                menuTransmission.setText(item.getText());
                transmissionStr = item.getText();
            });
        }
    }

    /**
     * Setzt gewollten Kraftstoff, hierbei wird bei jedem
     * Klick auf ein Menü-Item der Text im Menü-Button ersetzt und
     * die lokale Variable fuel mit dem neuen Typen ersetzt
     */
    private void setChosenFuel(){
        // basistyp, falls kein Typ ausgewählt
        fuelStr = FuelTypeEnum.BENZIN.getLabel();
        menuFuel.setText(fuelStr);

        // Iteration über alle Enums und für jedes 1 Menü-Item erstellen
        for (FuelTypeEnum fuel : FuelTypeEnum.values()){
            MenuItem item = new MenuItem(fuel.getLabel());
            menuFuel.getItems().add(item);
            // wird Action auf Item bemerkt -> carType aktualisieren und Text
            item.setOnAction(e ->{
                menuFuel.setText(item.getText());
                fuelStr = item.getText();
            });
        }
    }

    /**
     * Setzt gewollten Navi-Verfügbarkeit, hierbei wird bei jedem
     * Klick auf ein Menü-Item der Text im Menü-Button ersetzt und
     * die lokale Variable satNav mit dem neuen Typen ersetzt
     */
    private void setChosenSatNav(){
        // basistyp, falls kein Typ ausgewählt
        satNavStr = SatNavEnum.JA.getLabel();
        menuSatNav.setText(satNavStr);

        // Iteration über alle Enums und für jedes 1 Menü-Item erstellen
        for (SatNavEnum satNav : SatNavEnum.values()){
            MenuItem item = new MenuItem(satNav.getLabel());
            menuSatNav.getItems().add(item);
            // wird Action auf Item bemerkt -> carType aktualisieren und Text
            item.setOnAction(e ->{
                menuSatNav.setText(item.getText());
                satNavStr = item.getText();
            });
        }
    }

    /**
     * Überprüft Textfelder auf Gültigkeit
     * @param textField betreffendes Textfeld
     * @param errorMessage Fehlermeldung
     */
    private void validateTextField(TextField textField, String errorMessage){
        if(textField.getText() == null || textField.getText().isEmpty()){
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
