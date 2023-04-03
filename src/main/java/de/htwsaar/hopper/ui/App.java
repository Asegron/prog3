package de.htwsaar.hopper.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Die Klasse App ist die Hauptklasse der JavaFX Anwendung.
 * Sie startet das UI und ermöglicht den Wechsel zwischen den einzelnen Scenes.
 */
public final class App extends Application {
    private static Parent root;
    private static Scene scene;

    /**
     * Startet die JavaFX Anwendung.
     * @param primaryStage Hauptfenster der Anwendung
     * @throws Exception Fehler beim Laden der FXML-Datei
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = App.class.getResource("fxml/first-view.fxml");
        if (url != null) {
            root = FXMLLoader.load(url, ResourceBundle.getBundle("bundles.i18n"));
            scene = new Scene(root, 1366,768);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hopper Autovermietung");
            primaryStage.setMinHeight(600);
            primaryStage.setMinWidth(850);
            URL iconURL = App.class.getResource("icons/car-icon.png");
            primaryStage.getIcons().add(new javafx.scene.image.Image(iconURL.toString()));
            primaryStage.show();
        } else {
            throw new IOException("Could not load FXML file.");
        }
    }

    /**
     * Die Methode erlaubt den Wechsel zwischen Scenes.
     *
     * @param fxmlfile Pfad zur FXML-Datei, die geladen werden soll
     * @throws IOException Fehler beim Laden der FXML-Datei
     */
    public static void setRoot(String fxmlfile) throws IOException {
        URL url = App.class.getResource(fxmlfile);
        if (url != null) {
            root = FXMLLoader.load(url, ResourceBundle.getBundle("bundles.i18n"));
            scene.setRoot(root);
        }
    }

    /**
     * Die Methode startet die Applikation.
     * @param args Kommandozeilenargumente
     */
    public static void main(String[] args) {
        launch(args);
    }

}
