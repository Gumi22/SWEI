package picdb.models;

import BIF.SWE2.interfaces.models.CameraModel;

import java.time.LocalDate;

/**
 * Created by if16b014 on 05.03.18.
 */
public class CameraModelImpl implements CameraModel {
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setID(int i) {

    }

    @Override
    public String getProducer() {
        return null;
    }

    @Override
    public void setProducer(String s) {

    }

    @Override
    public String getMake() {
        return null;
    }

    @Override
    public void setMake(String s) {

    }

    @Override
    public LocalDate getBoughtOn() {
        return null;
    }

    @Override
    public void setBoughtOn(LocalDate localDate) {

    }

    @Override
    public String getNotes() {
        return null;
    }

    @Override
    public void setNotes(String s) {

    }

    @Override
    public double getISOLimitGood() {
        return 0;
    }

    @Override
    public void setISOLimitGood(double v) {

    }

    @Override
    public double getISOLimitAcceptable() {
        return 0;
    }

    @Override
    public void setISOLimitAcceptable(double v) {

    }
}
