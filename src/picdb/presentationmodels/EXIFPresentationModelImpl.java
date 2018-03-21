package picdb.presentationmodels;

import BIF.SWE2.interfaces.ExposurePrograms;
import BIF.SWE2.interfaces.ISORatings;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.EXIFPresentationModel;
import BIF.SWE2.interfaces.models.EXIFModel;

/**
 * Created by if16b014 on 05.03.18.
 */
public class EXIFPresentationModelImpl implements EXIFPresentationModel {

    EXIFModel exif = null;
    CameraPresentationModel cam = null;

    public EXIFPresentationModelImpl(EXIFModel i){
        this.exif = i;
    }

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
        return ExposurePrograms.LandscapeMode.name();
    }

    @Override
    public String getExposureProgramResource() {
        return "penis.jpg";
    }

    @Override
    public CameraPresentationModel getCamera() {
        return cam;
    }

    @Override
    public void setCamera(CameraPresentationModel cameraPresentationModel) {
        cam = cameraPresentationModel;
    }

    @Override
    public ISORatings getISORating() {
        if(exif == null || cam == null || exif.getISOValue() == 0){
            return ISORatings.NotDefined;
        }
        else{

            if(cam.getISOLimitGood() >= exif.getISOValue()){
                return ISORatings.Good;
            }else if(cam.getISOLimitAcceptable() >= exif.getISOValue()){
                return ISORatings.Acceptable;
            }
            return ISORatings.Noisey;
        }
    }

    @Override
    public String getISORatingResource() {
        return "x";
    }
}
