package picdb.presentationmodels;

import BIF.SWE2.interfaces.ISORatings;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.EXIFPresentationModel;
import picdb.models.EXIFModelImpl;

/**
 * Created by if16b014 on 05.03.18.
 */
public class EXIFPresentationModelImpl implements EXIFPresentationModel {

    EXIFModelImpl exif = new EXIFModelImpl();

    @Override
    public String getMake() {
        return exif.getMake();
    }

    @Override
    public double getFNumber() {
        return exif.getFNumber();
    }

    @Override
    public double getExposureTime() {
        return exif.getExposureTime();
    }

    @Override
    public double getISOValue() {
        return exif.getISOValue();
    }

    @Override
    public boolean getFlash() {
        return exif.getFlash();
    }

    @Override
    public String getExposureProgram() {
        return null;
    }

    @Override
    public String getExposureProgramResource() {
        return null;
    }

    @Override
    public CameraPresentationModel getCamera() {
        return null;
    }

    @Override
    public void setCamera(CameraPresentationModel cameraPresentationModel) {

    }

    @Override
    public ISORatings getISORating() {
        return null;
    }

    @Override
    public String getISORatingResource() {
        return null;
    }
}
