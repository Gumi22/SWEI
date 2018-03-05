package picdb.models;

import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.EXIFModel;
import BIF.SWE2.interfaces.models.IPTCModel;
import BIF.SWE2.interfaces.models.PictureModel;

/**
 * Created by if16b014 on 05.03.18.
 */
public class PictureModelImpl implements PictureModel {
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setID(int i) {

    }

    @Override
    public String getFileName() {
        return null;
    }

    @Override
    public void setFileName(String s) {

    }

    @Override
    public IPTCModel getIPTC() {
        return null;
    }

    @Override
    public void setIPTC(IPTCModel iptcModel) {

    }

    @Override
    public EXIFModel getEXIF() {
        return null;
    }

    @Override
    public void setEXIF(EXIFModel exifModel) {

    }

    @Override
    public CameraModel getCamera() {
        return null;
    }

    @Override
    public void setCamera(CameraModel cameraModel) {

    }
}
