package uebungen;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.DataAccessLayer;
import BIF.SWE2.interfaces.UEB3;
import BIF.SWE2.interfaces.presentationmodels.SearchPresentationModel;
import picdb.BusinessLayerImpl;
import picdb.DALFactory;
import picdb.presentationmodels.SearchPresentationModelImpl;

public class UEB3Impl implements UEB3 {

	String path;

	@Override
	public void helloWorld() {
		// I'm fine		
	}

	@Override
	public BusinessLayer getBusinessLayer() {
		return BusinessLayerImpl.getInstance(path,true);
	}

	@Override
	public DataAccessLayer getDataAccessLayer() {
		return DALFactory.getInstance(false).getDAL();
	}

	@Override
	public SearchPresentationModel getSearchPresentationModel() {
		return new SearchPresentationModelImpl();
	}

	@Override
	public void testSetup(String picturePath) {
		path = picturePath;
	}
}
