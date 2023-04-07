package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Customer;
import de.htwsaar.hopper.repositories.CustomerRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller für die Auswahl eines Kunden für eine Buchung
 */
public final class BookingCustomerChooseController implements Initializable {

    private Customer chosenCustomer;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Customer, String> customerCityColumn;

    @FXML
    private TableColumn<Customer, String> customerEmailColumn;

    @FXML
    private TableColumn<Customer, String> customerFirstNameColumn;

    @FXML
    private TableColumn<Customer, String> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerLastNameColumn;

    @FXML
    private TableColumn<Customer, String> customerZipCodeColumn;

    @FXML
    private MenuButton menuButtonCriteria;

    @FXML
    private MenuItem menuItemUncheck;

    @FXML
    private CheckMenuItem searchCritEmail;

    @FXML
    private CheckMenuItem searchCritFirstName;

    @FXML
    private CheckMenuItem searchCritLastName;

    @FXML
    private TableView<Customer> tableView;

    @FXML
    private TextField textFieldSearch;

    /**
     * Bricht die Auswahl ab und schließt das Fenster
     */
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Speichert den ausgewählten Kunden und schließt das Fenster
     */
    @FXML
    private void saveChosenCustomer(ActionEvent event) {
        chosenCustomer = tableView.getSelectionModel().getSelectedItem();
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    /**
     * Sucht nach ausgewählten Autos anhand der Filterkriterien
     *
     * @param event Event
     */
    @FXML
    private void searchForCustomer(ActionEvent event) {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
            String searchCriteria = textFieldSearch.getText();

            if (searchCriteria.trim().isEmpty()) {
                throw new IllegalArgumentException(bundle.getString("NO_CRITERIA_ENTERED"));
            }

            ObservableList<CheckMenuItem> checkMenuItems;
            checkMenuItems = getAllSelectedCriteria();

            if (checkMenuItems.isEmpty())
                throw new IllegalArgumentException(bundle.getString("NO_CRITERIA_SELECTED"));


            tableView.getItems().clear();
            for (Customer customer : CustomerRepository.findAll()) {
                for (CheckMenuItem item : checkMenuItems) {
                    boolean allowedToInsert = false;
                    if (item.equals(searchCritFirstName)) {
                        if (customer.getFirstName().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    } else if (item.equals(searchCritLastName)) {
                        if (customer.getLastName().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    } else if (item.equals(searchCritEmail)) {
                        if (customer.getEmail().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    }
                    if (!IsCustomerAlreadyInTable(customer)) {
                        if (allowedToInsert)
                            tableView.getItems().add(customer);
                    }

                }
            }
            if (tableView.getItems().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("CUSTOMERS_NOT_FOUND"));
                alert.setHeaderText(bundle.getString("CUSTOMERS_NOT_FOUND"));
                alert.setContentText(bundle.getString("CUSTOMERS_NOT_FOUND_BY_CRITERIA"));
                alert.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("MENU_ERROR"));
            alert.setHeaderText(bundle.getString("MENU_ERROR_SEARCH"));
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Sucht nach Autos, wenn die Enter-Taste gedrückt wird
     *
     * @param event Event
     */
    @FXML
    private void searchForCustomerViaEnter(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            searchForCustomer(new ActionEvent());
        }
    }

    /**
     * Speichert den ausgewählten Kunden durch Drücken der Enter-Taste
     *
     * @param event KeyEvent, das die Enter-Taste repräsentiert
     */
    @FXML
    private void saveCustomerViaEnter(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            saveChosenCustomer(new ActionEvent());
        }
    }

    /**
     * Entfernt die Auswahl aller Filterkriterien
     *
     * @param event Event
     */
    @FXML
    private void uncheckCriteria(ActionEvent event) {
        searchCritFirstName.setSelected(false);
        searchCritLastName.setSelected(false);
        searchCritEmail.setSelected(false);
    }

    /**
     * Setzt die Suche zurück, sodass keine Filterkriterien mehr aktiviert sind,
     * die Tabelle wieder auf original zurückgesetzt wird und das Suchfeld geleert wird
     *
     * @param event Event
     */
    @FXML
    private void resetSearch(ActionEvent event) {
        uncheckCriteria(new ActionEvent());
        reloadTable();
        textFieldSearch.clear();
    }

    /**
     * Lädt die Tabelle mit allen verfügbaren Autos
     */
    private void reloadTable() {
        tableView.getItems().clear();

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerZipCodeColumn.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        customerCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        ObservableList<Customer> observableList = FXCollections.observableArrayList();
        observableList.addAll(CustomerRepository.findAll());

        tableView.getItems().addAll(observableList);
        tableView.getSelectionModel().selectFirst();
        if (tableView.getSelectionModel().isEmpty()) {
            btnSave.setDisable(true);
            btnSearch.setDisable(true);
            btnCancel.setDisable(true);
        }
    }

    /**
     * Initialisiert die Controller-Klasse. Diese Methode wird automatisch aufgerufen
     *
     * @param url            The location used to resolve relative paths for the root object, or
     *                       {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if
     *                       the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadTable();
    }


    /**
     * Gibt den ausgewählten Kunden zurück
     *
     * @return ausgewählter Kunde
     */
    public Customer getChosenCustomer() {
        return chosenCustomer;
    }

    /**
     * Gibt eine Liste aus mit allen Suchkriterien, die ausgewählt sind
     *
     * @return Liste mit allen ausgewählten Suchkriterien
     */
    private ObservableList<CheckMenuItem> getAllSelectedCriteria() {
        ObservableList<CheckMenuItem> checkMenuItems = FXCollections.observableArrayList();

        if (searchCritFirstName.isSelected())
            checkMenuItems.add(searchCritFirstName);
        if (searchCritLastName.isSelected())
            checkMenuItems.add(searchCritLastName);
        if (searchCritEmail.isSelected())
            checkMenuItems.add(searchCritEmail);

        return checkMenuItems;
    }

    /**
     * Prüft, ob das Auto bereits in der Liste ist
     *
     * @param customer Auto was zu prüfen ist
     * @return true, wenn Auto bereits in der Liste ist, sonst false
     */
    private boolean IsCustomerAlreadyInTable(Customer customer) {
        return tableView.getItems().contains(customer);
    }


}
