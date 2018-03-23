package picdb.presentationmodels;

import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;
import picdb.models.PhotographerModelImpl;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by if16b014 on 05.03.18.
 */
public class PhotographerListPresentationModelImpl implements PhotographerListPresentationModel {

    private Collection<PhotographerPresentationModel> list = new ArrayList<>();

    @Override
    public Collection<PhotographerPresentationModel> getList() {
        return list;
    }

    @Override
    public PhotographerPresentationModel getCurrentPhotographer() {
        return new PhotographerPresentationModelImpl(new PhotographerModelImpl());//ToDo: Do this correctly
    }
}
