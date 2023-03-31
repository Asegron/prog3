package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.repositories.CarRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public final class CarManagementController implements Initializable {
    private static Car selectedCar;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnRead;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnResetSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Car, String> carBasePriceColumn;

    @FXML
    private TableColumn<Car, String> carBrandColumn;

    @FXML
    private TableColumn<Car, String> carCurrentPriceColumn;

    @FXML
    private TableColumn<Car, String> carFuelTypeColumn;

    @FXML
    private TableColumn<Car, String> carHorsepowerColumn;

    @FXML
    private TableColumn<Car, String> carIDColumn;

    @FXML
    private TableColumn<Car, String> carLicensePlateColumn;

    @FXML
    private TableColumn<Car, String> carMileageColumn;

    @FXML
    private TableColumn<Car, String> carModelColumn;

    @FXML
    private TableColumn<Car, String> carSatNavColumn;

    @FXML
    private TableColumn<Car, String> carTransmissionColumn;

    @FXML
    private TableColumn<Car, String> carTypeColumn;

    @FXML
    private CheckMenuItem filterBrand;

    @FXML
    private CheckMenuItem filterFuelType;

    @FXML
    private CheckMenuItem filterModel;

    @FXML
    private CheckMenuItem filterLicensePlate;

    @FXML
    private CheckMenuItem filterTransmission;

    @FXML
    private CheckMenuItem filterType;

    @FXML
    private MenuButton menuButtonFilter;

    @FXML
    private MenuItem menuItemUncheck;

    @FXML
    private BorderPane root;

    @FXML
    private TableView<Car> tableView;

    @FXML
    private TextField textFieldSearch;

    @FXML
    void switchToSceneAddCar(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        URL url = getClass().getResource("fxml/Car-creation-view.fxml");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(url, bundle);
            Parent root1 = (Parent) fxmlLoader.load();
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

    void disableWindow() {
        btnCreate.setDisable(true);
        btnRead.setDisable(true);
        btnRemove.setDisable(true);
        btnUpdate.setDisable(true);
        btnGoBack.setDisable(true);

        Stage primaryStage = (Stage) btnCreate.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            e.consume();
        });
    }

    void enableWindow() {
        btnCreate.setDisable(false);
        btnRead.setDisable(false);
        btnRemove.setDisable(false);
        btnUpdate.setDisable(false);
        btnGoBack.setDisable(false);

        // Roten Kreuz Button wieder aktivieren
        Stage primaryStage = (Stage) btnCreate.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            primaryStage.close();
        });
    }

    /**
     * Wechselt bei Aufruf auf das Fenster Car-read-view.
     *
     * @param event button click
     */
    @FXML
    void switchToSceneReadCar(ActionEvent event) throws IOException {
        setSelectedCar(tableView.getSelectionModel().getSelectedItem());
        App.setRoot("fxml/Car-read-view.fxml");
    }

    @FXML
    void switchToSceneUpdateCar(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        URL url = getClass().getResource("fxml/Car-edit-view.fxml");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
            selectedCar = tableView.getSelectionModel().getSelectedItem();
            setSelectedCar(selectedCar);
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

    public static Car getSelectedCar() {
        return selectedCar;
    }

    /**
     * Wechselt bei Aufruf auf die Startseite zurück.
     *
     * @param event button click
     */
    @FXML
    void switchToFirstView(ActionEvent event) throws IOException {
        App.setRoot("fxml/first-view.fxml");
    }

    /**
     * Wird beim Aufruf der View ausgeführt und bereitet die View entsprechend vor.
     *
     * @param url            Der Ort, an dem relative Pfade für das Root-Objekt aufgelöst werden, oder
     *                       {@code null}, wenn der Speicherort nicht bekannt ist.
     * @param resourceBundle Die Ressourcen, die zum Lokalisieren des Root-Objekts verwendet werden, oder {@code null}
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadTable();
    }

    /**
     * Quasi wie Initialisierung,
     */
    public void reloadTable() {
        tableView.getItems().clear();

        carIDColumn.setCellValueFactory(new PropertyValueFactory<>("carId"));
        carTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        carBrandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        carModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        carTransmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmissionType"));
        carFuelTypeColumn.setCellValueFactory(new PropertyValueFactory<>("fuelType"));
        carHorsepowerColumn.setCellValueFactory(new PropertyValueFactory<>("horsepower"));
        carSatNavColumn.setCellValueFactory(new PropertyValueFactory<>("satNavShowField"));
        carMileageColumn.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        carLicensePlateColumn.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        carBasePriceColumn.setCellValueFactory(new PropertyValueFactory<>("basePrice"));
        carCurrentPriceColumn.setCellValueFactory(new PropertyValueFactory<>("currentPrice"));

        ObservableList<Car> observableList = FXCollections.observableArrayList();
        observableList.addAll(CarRepository.findAll());
        tableView.getItems().addAll(observableList);
        tableView.getSelectionModel().selectFirst();
        if (tableView.getSelectionModel().isEmpty()) {
            btnRead.setDisable(true);
            btnRemove.setDisable(true);
            btnUpdate.setDisable(true);
        }
    }


    @FXML
    public void deleteCar() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");

        setSelectedCar(tableView.getSelectionModel().getSelectedItem());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bundle.getString("CAR_CONFIRM_DELETE"));
        alert.setHeaderText(bundle.getString("CAR_HEADER_CONFIRM_DELETE"));
        alert.setContentText(bundle.getString("CAR_CONTENT_TEXT")+ " " +selectedCar.getCarId()+" "+selectedCar.getBrand()+" "+selectedCar.getType());
        alert.showAndWait();
        if(alert.getResult().getText().equals("OK"))
        {
            CarRepository.delete(selectedCar);
            reloadTable();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, bundle.getString("CAR_DELETED"));
            alert2.show();
        } else {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, bundle.getString("CAR_NOT_DELETED"));
            alert2.show();
            alert.close();
        }

    }

    @FXML
    void resetSearch(ActionEvent event) {
        uncheckFilters(new ActionEvent());
        textFieldSearch.clear();
        reloadTable();
    }

    @FXML
    void searchCars(ActionEvent event) {
        try{
            String searchCriteria = textFieldSearch.getText();

            if(searchCriteria.trim().isEmpty()){
                throw new IllegalArgumentException("Kein Suchkriterium eingegeben");
            }

            ObservableList<CheckMenuItem> checkMenuItems = FXCollections.observableArrayList();
            checkMenuItems = getAllSelectedCriteria();

            if (checkMenuItems.isEmpty())
                throw new IllegalArgumentException("Kein Kriterium ausgewählt");


            tableView.getItems().clear();
            for (Car car : CarRepository.findAvailable()) {
                for (CheckMenuItem item : checkMenuItems) {
                    boolean allowedToInsert = false;
                    if (item.equals(filterBrand)) {
                        if (car.getBrand().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    } else if (item.equals(filterModel)) {
                        if (car.getModel().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    } else if (item.equals(filterType)) {
                        if (car.getType().getLabel().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    } else if (item.equals(filterFuelType)) {
                        if (car.getFuelType().getLabel().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    } else if (item.equals(filterTransmission)) {
                        if (car.getTransmissionType().getLabel().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    } else if (item.equals(filterLicensePlate)) {
                        if (car.getLicensePlate().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    }
                    if (!IsCarAlreadyInTable(car)){
                        if (allowedToInsert)
                            tableView.getItems().add(car);
                    }

                }
            }
            if (tableView.getItems().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Keine Autos gefunden");
                alert.setHeaderText("Keine Autos gefunden");
                alert.setContentText("Es wurden keine Autos gefunden, die den Suchkriterien entsprechen");
                alert.showAndWait();
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler bei der Suche");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void searchCarsViaEnter(KeyEvent event) {

    }


    /**
     * Gibt eine Liste aus mit allen Suchkriterien, die ausgewählt sind
     * @return Liste mit allen ausgewählten Suchkriterien
     */
    private ObservableList<CheckMenuItem> getAllSelectedCriteria(){
        ObservableList<CheckMenuItem> checkMenuItems = FXCollections.observableArrayList();

        if (filterBrand.isSelected())
            checkMenuItems.add(filterBrand);
        if (filterModel.isSelected())
            checkMenuItems.add(filterModel);
        if (filterType.isSelected())
            checkMenuItems.add(filterType);
        if (filterTransmission.isSelected())
            checkMenuItems.add(filterTransmission);
        if (filterFuelType.isSelected())
            checkMenuItems.add(filterFuelType);
        if (filterLicensePlate.isSelected())
            checkMenuItems.add(filterLicensePlate);

        return checkMenuItems;
    }

    @FXML
    void uncheckFilters(ActionEvent event) {
        filterBrand.setSelected(false);
        filterModel.setSelected(false);
        filterType.setSelected(false);
        filterFuelType.setSelected(false);
        filterTransmission.setSelected(false);
        filterLicensePlate.setSelected(false);
    }

    /**
     * Prüft ob das Auto bereits in der Liste ist
     * @param car Auto was zu prüfen ist
     * @return true wenn Auto bereits in der Liste ist, sonst false
     */
    private boolean IsCarAlreadyInTable(Car car){
        return tableView.getItems().contains(car);
    }

    /**
     * Setzt die Variable selectedCar auf ein angegebenes Car-Objekt.
     * @param car Zu setzendes Car-Objekt
     */
    public static void setSelectedCar(Car car) {
        selectedCar = car;
    }
}



