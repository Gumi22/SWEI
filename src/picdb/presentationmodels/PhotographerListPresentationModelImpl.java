package picdb.presentationmodels;

import BIF.SWE2.interfaces.DataAccessLayer;
import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.models.PictureModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;
import picdb.DALFactory;
import picdb.GlobalConfig;
import picdb.models.PhotographerModelImpl;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by if16b014 on 05.03.18.
 */
public class PhotographerListPresentationModelImpl implements PhotographerListPresentationModel {

    private Collection<PhotographerPresentationModel> list = new ArrayList<>();
    private PhotographerPresentationModel PPM;

    public PhotographerListPresentationModelImpl(PictureModel picm){
        DataAccessLayer DAL = DALFactory.getInstance(!GlobalConfig.getInstance().isTestingMode()).getDAL();
        try {
            Collection<PhotographerModel> l = DAL.getPhotographers();
            for (PhotographerModel pm: l) {
                list.add(new PhotographerPresentationModelImpl(pm));
            }
            PPM = new PhotographerPresentationModelImpl(DAL.getPhotographer(picm.getID()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<PhotographerPresentationModel> getList() {
        return list;
    }

    @Override
    public PhotographerPresentationModel getCurrentPhotographer() {
        return PPM;
    }
}
