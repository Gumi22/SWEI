package picdb.models;

import BIF.SWE2.interfaces.ExposurePrograms;
import BIF.SWE2.interfaces.models.EXIFModel;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by if16b014 on 05.03.18.
 */
public class EXIFModelImpl implements EXIFModel {

    private String make;
    private double fNumber;
    private double exposureTime;
    private double iso;
    private boolean flash;
    private ExposurePrograms exposureProgram;

    public EXIFModelImpl(){
        this.make = "Test";
        this.fNumber = 2.3f;
        this.exposureTime = 10f;
        this.iso = 800f;
        this.flash = true;
        this.exposureProgram = ExposurePrograms.LandscapeMode;
    }


    public EXIFModelImpl(String make, double f, double e, double i, boolean fl, ExposurePrograms ex){
        this.make = make;
        this.fNumber = f;
        this.exposureTime = e;
        this.iso = i;
        this.flash = fl;
        this.exposureProgram = ex;
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
    public double getFNumber() {
        return fNumber;
    }

    @Override
    public void setFNumber(double v) {
        fNumber = v;
    }

    @Override
    public double getExposureTime() {
        return exposureTime;
    }

    @Override
    public void setExposureTime(double v) {
        exposureTime = v;
    }

    @Override
    public double getISOValue() {
        return iso;
    }

    @Override
    public void setISOValue(double v) {
        iso = v;
    }

    @Override
    public boolean getFlash() {
        return flash;
    }

    @Override
    public void setFlash(boolean b) {
        flash = b;
    }

    @Override
    public ExposurePrograms getExposureProgram() {
        return exposureProgram;
    }

    @Override
    public void setExposureProgram(ExposurePrograms exposurePrograms) {
        exposureProgram = exposurePrograms;
    }
}
