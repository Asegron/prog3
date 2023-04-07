package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.repositories.CarRepository;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

/**
 * Controller für die Anzeige eines Autos
 */
public final class CarReadController implements Initializable {

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label labelCarBasePrice;

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
    private Label labelFuelType;

    @FXML
    private Label labelTransType;

    @FXML
    private Label labelModel;

    @FXML
    private Label labelSatNav;

    @FXML
    private Label labelMileage;

    @FXML
    private Label labelHorsePower;

    @FXML
    private Label labelcarLicensePlate;

    /**
     * Öffnet das Fenster zum Löschen des Autos
     *
     * @param event Event
     * @throws IOException IOException
     */
    @FXML
    private void removeCar(ActionEvent event) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        Car selectedCar = CarManagementController.getSelectedCar();
        CarManagementController.setSelectedCar(selectedCar);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bundle.getString("CAR_CONFIRM_DELETE"));
        alert.setHeaderText(bundle.getString("CAR_HEADER_CONFIRM_DELETE"));
        alert.setContentText(bundle.getString("CAR_CONTENT_TEXT") + selectedCar.getCarId() + " " + selectedCar.getBrand() + " " + selectedCar.getType());
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            CarRepository.delete(selectedCar);
            reloadTable();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, bundle.getString("CAR_DELETED"));
            alert2.show();
            App.setRoot("fxml/Car-view.fxml");
        } else {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, bundle.getString("CAR_NOT_DELETED"));
            alert2.show();
            alert.close();
        }
    }

    /**
     * zeigt das gewaehlte Auto beim Aufruf.
     *
     * @param event Event
     * @throws IOException IOException
     */
    @FXML
    private void switchToCarView(ActionEvent event) throws IOException {
        App.setRoot("fxml/Car-view.fxml");

    }

    /**
     * Öffnet das Fenster zum Bearbeiten des Autos
     *
     * @param event Event
     */
    @FXML
    private void updateCar(ActionEvent event) {
        Stage stage;
        URL url = getClass().getResource("fxml/Car-edit-view.fxml");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
            Car selectedCar = CarManagementController.getSelectedCar();
            CarManagementController.setSelectedCar(selectedCar);
            FXMLLoader fxmlLoader = new FXMLLoader(url, bundle);
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setScene(new Scene(root1));
            disableWindow();
            stage.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        enableWindow();
        reloadTable();
    }

    /**
     * Deaktiviert das Fenster
     */
    private void disableWindow() {
        btnRemove.setDisable(true);
        btnUpdate.setDisable(true);
        btnGoBack.setDisable(true);

        Stage primaryStage = (Stage) btnUpdate.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(Event::consume);
    }

    /**
     * Aktiviert das Fenster
     */
    private void enableWindow() {
        btnRemove.setDisable(false);
        btnUpdate.setDisable(false);
        btnGoBack.setDisable(false);

        // Roten Kreuz Button wieder aktivieren
        Stage primaryStage = (Stage) btnUpdate.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> primaryStage.close());
    }


    /**
     * @param url            Der Ort, an dem relative Pfade für das Root-Objekt aufgelöst werden
     * @param resourceBundle Diese Methode initialisiert die Informationen über das ausgewählte Auto
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadTable();
    }

    /**
     * Aktualisiert die Tabelle
     */
    private void reloadTable() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        Car car = CarManagementController.getSelectedCar();
        labelCarBasePrice.setText(String.valueOf(car.getBasePrice()));
        labelCarBrand.setText(String.valueOf(car.getBrand()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(car.getCreationDate().getTime());
        labelCarCreationDate.setText((date));
        labelCarCurrentPrise.setText(String.valueOf(car.getCurrentPrice()));
        labelCarSeats.setText(String.valueOf(car.getSeats()));
        labelCarType.setText(bundle.getString(car.getType().name()));
        labelcarLicensePlate.setText(car.getLicensePlate());
        labelFuelType.setText(bundle.getString(car.getFuelType().name()));
        labelTransType.setText(bundle.getString(car.getTransmissionType().name()));
        labelModel.setText(car.getModel());
        if (car.getSatNav()) {
            labelSatNav.setText(bundle.getString("JA"));
        } else {
            labelSatNav.setText(bundle.getString("NEIN"));
        }
        labelMileage.setText(String.valueOf(car.getMileage()));
        labelHorsePower.setText(String.valueOf(car.getHorsepower()));
    }
}