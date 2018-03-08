package uebungen;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.DataAccessLayer;
import BIF.SWE2.interfaces.UEB2;
import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.PictureModel;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.MainWindowPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;
import picdb.BusinessLayerImpl;
import picdb.models.CameraModelImpl;
import picdb.presentationmodels.MainWindowPresentationModelImpl;
import picdb.presentationmodels.CameraPresentationModelImpl;

public class UEB2Impl implements UEB2 {

	MainWindowPresentationModel MyMainWindowPresentationModel;
	BusinessLayer MyBusinessLayer;

	@Override
	public void helloWorld() {
		// I'm fine		
	}

	@Override
	public MainWindowPresentationModel GetMainWindowPresentationModel() {
		return MyMainWindowPresentationModel;
	}

	@Override
	public CameraModel getCameraModel(String s, String s1) {
		CameraModelImpl MyCamMod = new CameraModelImpl();
		MyCamMod.setProducer(s);
		MyCamMod.setMake(s1);
		return new CameraModelImpl();
	}

	@Override
	public CameraPresentationModel getCameraPresentationModel(CameraModel cameraModel) {
		CameraPresentationModelImpl myCamPresMod = new CameraPresentationModelImpl();
		myCamPresMod.setMake(cameraModel.getMake());
		myCamPresMod.setProducer(cameraModel.getProducer());
		return myCamPresMod;
	}

	@Override
	public BusinessLayer getBusinessLayer() {
		return MyBusinessLayer;
	}

	@Override
	public PictureModel getPictureModel(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PicturePresentationModel getPicturePresentationModel(
			PictureModel mdl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void testSetup(String picturePath) {
		// TODO Auto-generated method stub
		MyMainWindowPresentationModel = new MainWindowPresentationModelImpl();
		MyBusinessLayer = BusinessLayerImpl.getInstance();
	}
}
