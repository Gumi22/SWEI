package picdb.presentationmodels;

import BIF.SWE2.interfaces.DataAccessLayer;
import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.PictureModel;
import BIF.SWE2.interfaces.presentationmodels.CameraListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;
import picdb.DALFactory;
import picdb.GlobalConfig;
import picdb.models.CameraModelImpl;
import picdb.models.PictureModelImpl;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by if16b014 on 05.03.18.
 */
public class CameraListPresentationModelImpl implements CameraListPresentationModel {

    private Collection<CameraPresentationModel> list = new ArrayList<>();
    private CameraPresentationModel CPM;


    public CameraListPresentationModelImpl(PictureModel picm){
        DataAccessLayer DAL = DALFactory.getInstance(!GlobalConfig.getInstance().isTestingMode()).getDAL();
        try {
            Collection<CameraModel> l = DAL.getCameras();
            for (CameraModel cm: l) {
                list.add(new CameraPresentationModelImpl(cm));
            }
            CPM = new CameraPresentationModelImpl(picm.getCamera());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<CameraPresentationModel> getList() {
        return list;
    }

    @Override
    public CameraPresentationModel getCurrentCamera() {
        return CPM;
    }
}
