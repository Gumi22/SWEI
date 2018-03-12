package picdb.presentationmodels;

import BIF.SWE2.interfaces.presentationmodels.MainWindowPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PictureListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;
import BIF.SWE2.interfaces.presentationmodels.SearchPresentationModel;

/**
 * Created by if16b014 on 05.03.18.
 */
public class MainWindowPresentationModelImpl implements MainWindowPresentationModel {


    @Override
    public PicturePresentationModel getCurrentPicture() {
        return new PicturePresentationModelImpl();
    }

    @Override
    public PictureListPresentationModel getList() {
        return new PictureListPresentationModelImpl();
    }

    @Override
    public SearchPresentationModel getSearch() {
        return new SearchPresentationModelImpl();
    }
}
