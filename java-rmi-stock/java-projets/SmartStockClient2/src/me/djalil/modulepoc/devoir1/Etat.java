package me.djalil.modulepoc.devoir1;

import java.io.Serializable;
import java.time.OffsetDateTime;


public class Etat implements Serializable {
    private static final long serialVersionUID = 1L;

    private int Q;
    private OffsetDateTime date;

    public Etat(int quantity, OffsetDateTime date) {
        this.Q = quantity;
        this.date = date;
    }

    // There's no setters, because "Etat" is read-only.

    public int getQ() {
        return Q;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("Etat(Q = %d, date modifie = %s)", getQ(), getDate().toString());
    }
}
