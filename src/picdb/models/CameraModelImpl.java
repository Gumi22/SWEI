package picdb.models;

import BIF.SWE2.interfaces.models.CameraModel;

import java.time.LocalDate;

/**
 * Created by if16b014 on 05.03.18.
 */
public class CameraModelImpl implements CameraModel {

    private int id = 0;
    private String producer;
    private String make;
    private LocalDate boughtOn;
    private String notes;
    private double isoAcceptable;
    private double isoGood;

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int i) {
        id = i;
    }

    @Override
    public String getProducer() {
        return producer;
    }

    @Override
    public void setProducer(String s) {
        producer = s;
    }

    @Override
    public String getMake() {
        return make;
    }

    @Override
    public void setMake(String s) {
        make = s;
    }

    @Override
    public LocalDate getBoughtOn() {
        return boughtOn;
    }

    @Override
    public void setBoughtOn(LocalDate localDate) {
        boughtOn = localDate;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public void setNotes(String s) {
        notes = s;
    }

    @Override
    public double getISOLimitGood() {
        return isoGood;
    }

    @Override
    public void setISOLimitGood(double v) {
        isoGood = v;
    }

    @Override
    public double getISOLimitAcceptable() {
        return isoAcceptable;
    }

    @Override
    public void setISOLimitAcceptable(double v) {
        isoAcceptable = v;
    }
}
