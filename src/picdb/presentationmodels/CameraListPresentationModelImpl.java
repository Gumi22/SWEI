package picdb.presentationmodels;

import BIF.SWE2.interfaces.presentationmodels.CameraListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;
import picdb.models.CameraModelImpl;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by if16b014 on 05.03.18.
 */
public class CameraListPresentationModelImpl implements CameraListPresentationModel {

    private Collection<CameraPresentationModel> list = new ArrayList<>();


    @Override
    public Collection<CameraPresentationModel> getList() {
        return list;
    }

    @Override
    public CameraPresentationModel getCurrentCamera() {
        return new CameraPresentationModelImpl(new CameraModelImpl()); //is this correct? xD
    }
}
