package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CarShowController implements Initializable {

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label labelCarBasePrise;

    @FXML
    private Label labelCarBrand;
    @FXML
    private Label labelCarCreationDate;

    @FXML
    private Label labelCarCurrentPrise;

    @FXML
    private Label labelCarSeats;

    @FXML
    private Label labelCarType;

    @FXML
    void removeCar(ActionEvent event) {

    }

    @FXML
    void switchToCarView(ActionEvent event) throws IOException {
        App.setRoot("Car-view.fxml");

    }

    @FXML
    void updateCar(ActionEvent event) {

    }


    /**
     * @param url Der Ort, an dem relative Pfade für das Root-Objekt aufgelöst werden
     * @param resourceBundle Diese Methode initialisiert die Informationen über das ausgewählte Auto
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Car car = CarManagementController.getSelectedCar() ;
        labelCarBasePrise.setText(String.valueOf(car.getBasePrice()));
        labelCarBrand.setText(String.valueOf(car.getBrand()));
        labelCarCreationDate.setText(String.valueOf(car.getCreationDate()));
        labelCarCurrentPrise.setText(String.valueOf(car.getCurrentPrice()));
        labelCarSeats.setText(String.valueOf(car.getSeats()));
        labelCarType.setText(car.getType().getLabel());
    }
}
