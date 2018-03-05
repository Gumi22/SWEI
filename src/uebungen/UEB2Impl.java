package uebungen;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.UEB2;
import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.PictureModel;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.MainWindowPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;
import picdb.models.CameraModelImpl;
import picdb.presentationmodels.MainWindowPresentationModelImpl;

public class UEB2Impl implements UEB2 {


	@Override
	public void helloWorld() {
		// I'm fine		
	}

	@Override
	public MainWindowPresentationModel GetMainWindowPresentationModel() {
		// TODO Auto-generated method stub
		return new MainWindowPresentationModelImpl();
	}

	@Override
	public CameraModel getCameraModel(String s, String s1) {
	}

	@Override
	public CameraPresentationModel getCameraPresentationModel(CameraModel cameraModel) {
		return null;
	}

	@Override
	public BusinessLayer getBusinessLayer() {
		// TODO Auto-generated method stub
		return null;
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
		
	}
}
