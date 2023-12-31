package de.htwsaar.hopper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Diese Klasse dient dem Erstellen der Testdatenbank.
 * Da Hibernate anscheinend keine Möglichkeiten bietet, Datenbanktests zu vereinfachen,
 * ist der einzige Zweck dieser Klasse Methoden bereitzustellen, um eine Testdatenbank
 * aufzusetzen und im Nachhinein auch wieder zurückzusetzen.
 */
public class TestDBUtils {

    /**
     * Macht die Datenbank bereit für Tests.
     * Die Methode kopiert dazu die aktuelle Datenbank und legt sie unter anderem Namen ab.
     * Diese Methode ist nur in einer Setup-Methode mit der Annotation @BeforeClass zu verwenden.
     * @throws IOException Falls es beim Einlesen der Datei zu einem Fehler kommt.
     * @throws IllegalStateException Falls bereits eine Kopie der Datenbank existiert.
     */
    public static void prepareTestDB() throws IOException {
        File originalDB = new File("src/main/resources/AutovermietungDB.sqlite");
        File copyDestination = new File("src/main/resources/AutovermietungDB_copy.sqlite");

        if(copyDestination.exists()) {
            throw new IllegalStateException("Es existiert bereits eine Kopie der Datenbank, die nicht zurückgesetzt wurde.");
        }

        FileUtils.copyFile(originalDB, copyDestination);
    }

    /**
     * Lädt die TestDB bzw. setzt sie auf den Ausgangszustand zurück.
     * Die Methode ist nur in einer Setup-Methode mit der Annotation @Before zu verwenden.
     * @throws IOException Falls es beim Einlesen der Dateien zu einem Fehler kommt.
     */
    public static void reloadTestDB() throws IOException {
        File originalDB = new File("src/main/resources/AutovermietungDB.sqlite");
        File testDB = new File("src/main/resources/TestDB.sqlite");

        FileUtils.copyFile(testDB, originalDB);
    }

    /**
     * Setzt die Datenbank auf ihren Ausgangszustand zurück.
     * Dazu wird die Testdatenbank gelöscht und die ursprüngliche Datenbank wieder hergestellt.
     * Diese Methode ist nur in einer Setup-Methode mit der Annotation @AfterClass zu verwenden.
     * @throws IOException Falls es beim Einlesen der Dateien zu einem Fehler kommt.
     * @throws IllegalStateException Falls die Backup-Datei nicht gefunden werden kann, um einen Datenverlust zu vermeiden.
     */
    public static void loadBackupDB() throws IOException {
        File testDB = new File("src/main/resources/AutovermietungDB.sqlite");
        File copyDB = new File("src/main/resources/AutovermietungDB_copy.sqlite");

        if(copyDB.exists()) {
            FileUtils.delete(testDB);
            FileUtils.copyFile(copyDB, testDB);
            FileUtils.delete(copyDB);
        } else {
            throw new IllegalStateException("BackupDB not found, unsafe operation aborted");
        }
    }
}
