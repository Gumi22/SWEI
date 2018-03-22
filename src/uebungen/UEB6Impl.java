package uebungen;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.UEB6;
import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.models.PictureModel;
import picdb.BusinessLayerImpl;
import picdb.models.PhotographerModelImpl;
import picdb.models.PictureModelImpl;

public class UEB6Impl implements UEB6 {

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
	public PictureModel getEmptyPictureModel() {
		return new PictureModelImpl();
	}

	@Override
	public PhotographerModel getEmptyPhotographerModel() {
		return new PhotographerModelImpl();
	}

	@Override
	public void testSetup(String picturePath) {
		// TODO Auto-generated method stub
		path = picturePath;
	}
}
