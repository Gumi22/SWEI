package uebungen;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.UEB5;
import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;
import picdb.BusinessLayerImpl;
import picdb.models.CameraModelImpl;
import picdb.models.PhotographerModelImpl;
import picdb.presentationmodels.CameraPresentationModelImpl;
import picdb.presentationmodels.PhotographerPresentationModelImpl;

public class UEB5Impl implements UEB5 {


	static String path;

	@Override
	public void helloWorld() {
		// I'm fine		
	}

	@Override
	public BusinessLayer getBusinessLayer() {
		// TODO Auto-generated method stub
		return BusinessLayerImpl.getInstance(path, true);
	}

	@Override
	public PhotographerModel getEmptyPhotographerModel() {
		return new PhotographerModelImpl();
	}

	@Override
	public PhotographerPresentationModel getPhotographerPresentationModel(PhotographerModel photographerModel) {
		return new PhotographerPresentationModelImpl();
	}

	@Override
	public CameraModel getEmptyCameraModel() {
		return new CameraModelImpl();
	}

	@Override
	public CameraPresentationModel getCameraPresentationModel(CameraModel cameraModel) {
		return new CameraPresentationModelImpl(cameraModel);
	}

	@Override
	public void testSetup(String picturePath) {
		// TODO Auto-generated method stub
		path = picturePath;
	}
}
