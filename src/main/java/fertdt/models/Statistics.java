package fertdt.models;

import java.sql.Date;
import java.util.Objects;

public class Statistics {
    private Date date;
    private int numberForgot;
    private int numberRepeat;
    private int numberNew;
    private int numberTrained;

    public Statistics(Date date, int numberForgot, int numberRepeat, int numberNew, int numberTrained) {
        this.date = date;
        this.numberForgot = numberForgot;
        this.numberRepeat = numberRepeat;
        this.numberNew = numberNew;
        this.numberTrained = numberTrained;
    }

    public Date getDate() {
        return date;
    }

    public int getNumberForgot() {
        return numberForgot;
    }

    public int getNumberRepeat() {
        return numberRepeat;
    }

    public int getNumberNew() {
        return numberNew;
    }

    public int getNumberTrained() {
        return numberTrained;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistics)) return false;
        Statistics that = (Statistics) o;
        return numberForgot == that.numberForgot && numberRepeat == that.numberRepeat && numberNew == that.numberNew && numberTrained == that.numberTrained && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, numberForgot, numberRepeat, numberNew, numberTrained);
    }
}
