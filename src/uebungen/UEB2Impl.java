package uebungen;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.UEB2;
import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.PictureModel;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.MainWindowPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;
import picdb.BusinessLayerImpl;
import picdb.DALFactory;
import picdb.DataAccessLayers.DataAccessLayerImpl;
import picdb.models.CameraModelImpl;
import picdb.models.PictureModelImpl;
import picdb.presentationmodels.MainWindowPresentationModelImpl;
import picdb.presentationmodels.CameraPresentationModelImpl;
import picdb.presentationmodels.PicturePresentationModelImpl;

public class UEB2Impl implements UEB2 {



	@Override
	public void helloWorld() {
		// I'm fine		
	}

	@Override
	public void testSetup(String picturePath) {
		DALFactory.getInstance();
		DALFactory.setDatabaseAccessible(false);
	}

	@Override
	public MainWindowPresentationModel GetMainWindowPresentationModel() {
		return new MainWindowPresentationModelImpl();
	}

	@Override
	public CameraModel getCameraModel(String s, String s1) {
		CameraModelImpl MyCamMod = new CameraModelImpl();
		MyCamMod.setProducer(s);
		MyCamMod.setMake(s1);
		return MyCamMod;
	}

	@Override
	public CameraPresentationModel getCameraPresentationModel(CameraModel cameraModel) {
		CameraPresentationModelImpl myCamPresMod = new CameraPresentationModelImpl(cameraModel);
		myCamPresMod.setMake(cameraModel.getMake());
		myCamPresMod.setProducer(cameraModel.getProducer());
		return myCamPresMod;
	}

	@Override
	public BusinessLayer getBusinessLayer() {
		BusinessLayerImpl mybus = BusinessLayerImpl.getInstance();
		BusinessLayerImpl.setTestingMode(true);
		BusinessLayerImpl.setPath("./Pictures");
		return mybus;
	}

	@Override
	public PictureModel getPictureModel(String arg0) {
		PictureModel myPic = new PictureModelImpl("arg0");
		myPic.setFileName(arg0);
		return myPic;
	}

	@Override
	public PicturePresentationModel getPicturePresentationModel(
			PictureModel mdl) {
		PicturePresentationModel mypic = new PicturePresentationModelImpl(mdl, null, null, null, null);
		return mypic;
	}


}
