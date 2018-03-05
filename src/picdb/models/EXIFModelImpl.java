package picdb.models;

import BIF.SWE2.interfaces.ExposurePrograms;
import BIF.SWE2.interfaces.models.EXIFModel;

/**
 * Created by if16b014 on 05.03.18.
 */
public class EXIFModelImpl implements EXIFModel {
    @Override
    public String getMake() {
        return null;
    }

    @Override
    public void setMake(String s) {

    }

    @Override
    public double getFNumber() {
        return 0;
    }

    @Override
    public void setFNumber(double v) {

    }

    @Override
    public double getExposureTime() {
        return 0;
    }

    @Override
    public void setExposureTime(double v) {

    }

    @Override
    public double getISOValue() {
        return 0;
    }

    @Override
    public void setISOValue(double v) {

    }

    @Override
    public boolean getFlash() {
        return false;
    }

    @Override
    public void setFlash(boolean b) {

    }

    @Override
    public ExposurePrograms getExposureProgram() {
        return null;
    }

    @Override
    public void setExposureProgram(ExposurePrograms exposurePrograms) {

    }
}
