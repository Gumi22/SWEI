package picdb.models;

import BIF.SWE2.interfaces.models.*;

/**
 * Created by if16b014 on 05.03.18.
 */
public class PictureModelImpl implements PictureModel {

    private int id = 0;
    private String fileName;
    private IPTCModel iptc;
    private EXIFModel exif;
    private CameraModel cam;
    private PhotographerModel phot;

    public PictureModelImpl(){
    }

    public PictureModelImpl(String fileName){
        this.fileName = fileName;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int i) {
        id = i;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void setFileName(String s) {
        fileName = s;
    }

    @Override
    public IPTCModel getIPTC() {
        return iptc;
    }

    @Override
    public void setIPTC(IPTCModel iptcModel) {
        iptc = iptcModel;
    }

    @Override
    public EXIFModel getEXIF() {
        return exif;
    }

    @Override
    public void setEXIF(EXIFModel exifModel) {
        exif = exifModel;
    }

    @Override
    public CameraModel getCamera() {
        return cam;
    }

    @Override
    public void setCamera(CameraModel cameraModel) {
        cam = cameraModel;
    }

    public PhotographerModel getPhotographer() {
        return phot;
    }

    public void setPhotographer(PhotographerModel phot) {
        this.phot = phot;
    }
}
