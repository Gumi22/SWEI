package uebungen;

import BIF.SWE2.interfaces.models.*;
import BIF.SWE2.interfaces.presentationmodels.*;
import picdb.BusinessLayerImpl;
import picdb.DataAccessLayers.DataAccessLayerImpl;
import picdb.Main;
import BIF.SWE2.interfaces.Application;
import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.DataAccessLayer;
import BIF.SWE2.interfaces.UEB1;
import picdb.models.*;
import picdb.presentationmodels.*;

public class UEB1Impl implements UEB1 {

	@Override
	public Application getApplication() {
		return new Main();
	}

	@Override
	public void helloWorld() {
		// I'm fine		
	}

	@Override
	public DataAccessLayer getAnyDataAccessLayer() { return DataAccessLayerImpl.getInstance(); }

	@Override
	public BusinessLayer getBusinessLayer() {
		return BusinessLayerImpl.getInstance();
	}

	@Override
	public EXIFModel getEmptyEXIFModel() {
		return new EXIFModelImpl();
	}

	@Override
	public EXIFPresentationModel getEmptyEXIFPresentationModel() {
		return new EXIFPresentationModelImpl(new EXIFModelImpl());
	}

	@Override
	public IPTCModel getEmptyIPTCModel() {
		return new IPTCModelImpl();
	}

	@Override
	public IPTCPresentationModel getEmptyIPTCPresentationModel() {
		return new IPTCPresentationModelImpl(new IPTCModelImpl());
	}

	@Override
	public MainWindowPresentationModel getEmptyMainWindowPresentationModel() {
		return new MainWindowPresentationModelImpl();
	}

	@Override
	public PhotographerListPresentationModel getEmptyPhotographerListPresentationModel() {
		return new PhotographerListPresentationModelImpl();
	}

	@Override
	public PhotographerModel getEmptyPhotographerModel() {
		return new PhotographerModelImpl();
	}

	@Override
	public CameraModel getEmptyCameraModel() {
		return new CameraModelImpl();
	}

	@Override
	public PhotographerPresentationModel getEmptyPhotographerPresentationModel() {
		return new PhotographerPresentationModelImpl();
	}

	@Override
	public CameraListPresentationModel getEmptyCameraListPresentationModel() {
		return new CameraListPresentationModelImpl();
	}

	@Override
	public CameraPresentationModel getEmptyCameraPresentationModel() {
		return new CameraPresentationModelImpl();
	}

	@Override
	public PictureListPresentationModel getEmptyPictureListPresentationModel() {
		return new PictureListPresentationModelImpl();
	}

	@Override
	public PictureModel getEmptyPictureModel() {
		return new PictureModelImpl("arg0");
	}

	@Override
	public PicturePresentationModel getEmptyPicturePresentationModel() {
		return new PicturePresentationModelImpl();
	}

	@Override
	public SearchPresentationModel getEmptySearchPresentationModel() {
		return new SearchPresentationModelImpl();
	}

	@Override
	public void testSetup(String picturePath) {
		// Nothing to do here, just returning new instances
		BusinessLayerImpl.setTestingMode(true);
	}
}
