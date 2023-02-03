package de.htwsaar.hopper.logic.implementations;

import de.htwsaar.hopper.logic.interfaces.ChecklistInterface;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Checkliste, die bei jedem Wiedereingang eines Fahrzeugs ausgefüllt werden muss.
 * @author Philip
 */
@Entity
@Table(name = "Checklists")
public class Checklist implements ChecklistInterface {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "checklist_id")
    private int checklistId;

    @Basic
    @Column(name = "fueled_up")
    @NotNull
    private boolean fueledUp;

    @Basic
    @Column(name = "undamaged")
    @NotNull
    private boolean undamaged;

    @Basic
    @Column(name = "clean")
    @NotNull
    private boolean clean;

    @Basic
    @Column(name = "key_dropped_off")
    @NotNull
    private boolean keyDroppedOff;

    public Checklist() {

    }

    /**
     * Konstruktor zum Befüllen aller Attribute
     * @param fueledUp Auto vollgetankt
     * @param undamaged Auto unbeschädigt
     * @param clean Auto ist sauber
     * @param keyDroppedOff Schlüssel zum Auto sind vorhanden
     */
    public Checklist(boolean fueledUp, boolean undamaged, boolean clean, boolean keyDroppedOff) {
        this.fueledUp = fueledUp;
        this.undamaged = undamaged;
        this.clean = clean;
        this.keyDroppedOff = keyDroppedOff;
    }

    /**
     * Gibt die Anzahl der Probleme zurück.
     * Jedes Boolean, das false ist, wird dabei als Problem gewertet.
     * @return Anzahl der Probleme
     */
    @Override
    public int getProblemCount() {
        int count = 0;
        if (!fueledUp) count++;
        if (!undamaged) count++;
        if (!clean) count++;
        if (!keyDroppedOff) count++;
        return count;
    }

    /* GETTER */
    @Override
    public int getChecklistId() {
        return checklistId;
    }

    @Override
    public boolean isFueledUp() {
        return fueledUp;
    }

    @Override
    public boolean isUndamaged() {
        return undamaged;
    }

    @Override
    public boolean isClean() {
        return clean;
    }

    @Override
    public boolean isKeyDroppedOff() {
        return keyDroppedOff;
    }

    /* SETTER */
    @Override
    public void setFueledUp(boolean fueledUp) {
        this.fueledUp = fueledUp;
    }

    @Override
    public void setUndamaged(boolean undamaged) {
        this.undamaged = undamaged;
    }

    @Override
    public void setClean(boolean clean) {
        this.clean = clean;
    }

    @Override
    public void setKeyDroppedOff(boolean keyDroppedOff) {
        this.keyDroppedOff = keyDroppedOff;
    }

    @Override
    public String toString() {
        return String.format("Checklist (ID: %d):\n"
                + "hasFullTank: %b\n"
                + "isUndamaged: %b\n"
                + "isClean: %b\n"
                + "hasKey: %b",
                getChecklistId(), fueledUp, undamaged, clean, keyDroppedOff);
    }
}
