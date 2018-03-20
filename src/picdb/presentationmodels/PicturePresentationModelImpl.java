package picdb.presentationmodels;

import BIF.SWE2.interfaces.models.PictureModel;
import BIF.SWE2.interfaces.presentationmodels.*;
import javafx.beans.property.*;
import picdb.models.EXIFModelImpl;
import picdb.models.IPTCModelImpl;
import picdb.models.PictureModelImpl;

/**
 * Created by if16b014 on 05.03.18.
 */
public class PicturePresentationModelImpl implements PicturePresentationModel {

    private StringProperty filePath = new SimpleStringProperty();
    private PictureModel pic = null;
    private IPTCPresentationModel iptc = null;
    private EXIFPresentationModel exif = null;
    private CameraPresentationModel cam = null;
    private PhotographerPresentationModel phot = null;

    public PicturePresentationModelImpl(){
        pic = new PictureModelImpl("lel.jpg");
    }

    public PicturePresentationModelImpl(PictureModel pi, IPTCPresentationModel i, EXIFPresentationModel e,
                                        CameraPresentationModel c, PhotographerPresentationModel ph){
        pic = (pi != null)? pi : new PictureModelImpl("lel.jpg");
        iptc = (i != null)? i : new IPTCPresentationModelImpl(new IPTCModelImpl());
        exif = (e != null)? e : new EXIFPresentationModelImpl(new EXIFModelImpl());
        cam = (c != null)? c : new CameraPresentationModelImpl();
        phot = (ph != null)? ph : new PhotographerPresentationModelImpl();
    }


    @Override
    public int getID() {
        return pic.getID();
    }

    @Override
    public String getFileName() {
        return pic.getFileName();
    }

    @Override
    public String getFilePath() {
        return filePath.get();
    }

    @Override
    public String getDisplayName() {
        return pic.getFileName().split("\\.")[0] + " (by " + phot.getFirstName() + " "  + phot.getLastName() + ")";
    }

    @Override
    public IPTCPresentationModel getIPTC() {
        return iptc;
    }

    @Override
    public EXIFPresentationModel getEXIF() {
        return exif;
    }

    @Override
    public PhotographerPresentationModel getPhotographer() {
        return phot;
    }

    @Override
    public CameraPresentationModel getCamera() {
        return cam;
    }
}
