package picdb.presentationmodels;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.models.PictureModel;
import BIF.SWE2.interfaces.presentationmodels.PictureListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import picdb.BusinessLayerImpl;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by if16b014 on 05.03.18.
 */
public class PictureListPresentationModelImpl implements PictureListPresentationModel {

    private Collection<PicturePresentationModel> l = new ArrayList<>();
    private Collection<ImageView> list = new ArrayList<>();
    String path = BusinessLayerImpl.getPath();

    public PictureListPresentationModelImpl(){}

    public PictureListPresentationModelImpl(Collection<PictureModel> l){
        for (PictureModel pm: l) {
            ImageView imgV = new ImageView(new Image("file:" + path + "/" + pm.getFileName()));
            imgV.setPreserveRatio(true);
            imgV.setFocusTraversable(true);
            imgV.setFitHeight(128);
            list.add(imgV);
        }
    }

    @Override
    public PicturePresentationModel getCurrentPicture() {
        return null;
    }

    @Override
    public Collection<PicturePresentationModel> getList() {
        return l;
    }

    public Collection<ImageView> getImages(){return list;}

    @Override
    public Collection<PicturePresentationModel> getPrevPictures() {
        return null;
    }

    @Override
    public Collection<PicturePresentationModel> getNextPictures() {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public int getCurrentIndex() {
        return 0;
    }

    @Override
    public String getCurrentPictureAsString() {
        return null;
    }
}
