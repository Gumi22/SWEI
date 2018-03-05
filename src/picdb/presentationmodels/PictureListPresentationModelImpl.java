package picdb.presentationmodels;

import BIF.SWE2.interfaces.presentationmodels.PictureListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;

import java.util.Collection;

/**
 * Created by if16b014 on 05.03.18.
 */
public class PictureListPresentationModelImpl implements PictureListPresentationModel {
    @Override
    public PicturePresentationModel getCurrentPicture() {
        return null;
    }

    @Override
    public Collection<PicturePresentationModel> getList() {
        return null;
    }

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
