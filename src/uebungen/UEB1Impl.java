package uebungen;

import BIF.SWE2.interfaces.models.*;
import BIF.SWE2.interfaces.presentationmodels.*;
import picdb.BusinessLayerImpl;
import picdb.DataAccessLayerImpl;
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
	public DataAccessLayer getAnyDataAccessLayer() {
		// TODO Auto-generated method stub
		return new DataAccessLayerImpl();
	}

	@Override
	public BusinessLayer getBusinessLayer() {
		// TODO Auto-generated method stub
		return new BusinessLayerImpl();
	}

	@Override
	public EXIFModel getEmptyEXIFModel() {
		// TODO Auto-generated method stub
		return new EXIFModelImpl();
	}

	@Override
	public EXIFPresentationModel getEmptyEXIFPresentationModel() {
		// TODO Auto-generated method stub
		return new EXIFPresentationModelImpl();
	}

	@Override
	public IPTCModel getEmptyIPTCModel() {
		// TODO Auto-generated method stub
		return new IPTCModelImpl();
	}

	@Override
	public IPTCPresentationModel getEmptyIPTCPresentationModel() {
		// TODO Auto-generated method stub
		return new IPTCPresentationModelImpl();
	}

	@Override
	public MainWindowPresentationModel getEmptyMainWindowPresentationModel() {
		// TODO Auto-generated method stub
		return new MainWindowPresentationModelImpl();
	}

	@Override
	public PhotographerListPresentationModel getEmptyPhotographerListPresentationModel() {
		// TODO Auto-generated method stub
		return new PhotographerListPresentationModelImpl();
	}

	@Override
	public PhotographerModel getEmptyPhotographerModel() {
		// TODO Auto-generated method stub
		return new PhotographerModelImpl();
	}

	@Override
	public CameraModel getEmptyCameraModel() {
		// TODO Auto-generated method stub
		return new CameraModelImpl();
	}

	@Override
	public PhotographerPresentationModel getEmptyPhotographerPresentationModel() {
		// TODO Auto-generated method stub
		return new PhotographerPresentationModelImpl();
	}

	@Override
	public CameraListPresentationModel getEmptyCameraListPresentationModel() {
		// TODO Auto-generated method stub
		return new CameraListPresentationModelImpl();
	}

	@Override
	public CameraPresentationModel getEmptyCameraPresentationModel() {
		// TODO Auto-generated method stub
		return new CameraPresentationModelImpl();
	}

	@Override
	public PictureListPresentationModel getEmptyPictureListPresentationModel() {
		// TODO Auto-generated method stub
		return new PictureListPresentationModelImpl();
	}

	@Override
	public PictureModel getEmptyPictureModel() {
		// TODO Auto-generated method stub
		return new PictureModelImpl();
	}

	@Override
	public PicturePresentationModel getEmptyPicturePresentationModel() {
		// TODO Auto-generated method stub
		return new PicturePresentationModelImpl();
	}

	@Override
	public SearchPresentationModel getEmptySearchPresentationModel() {
		// TODO Auto-generated method stub
		return new SearchPresentationModelImpl();
	}

	@Override
	public void testSetup(String picturePath) {
		// TODO Auto-generated method stub

		// Nothing to do here, just returning new instances
	}
}
