package uebungen;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.DataAccessLayer;
import BIF.SWE2.interfaces.UEB3;
import BIF.SWE2.interfaces.presentationmodels.SearchPresentationModel;
import picdb.BusinessLayerImpl;
import picdb.DALFactory;
import picdb.presentationmodels.SearchPresentationModelImpl;

public class UEB3Impl implements UEB3 {


	@Override
	public void helloWorld() {
		// I'm fine		
	}

	@Override
	public BusinessLayer getBusinessLayer() {
		// TODO Auto-generated method stub
		return BusinessLayerImpl.getInstance();
	}

	@Override
	public DataAccessLayer getDataAccessLayer() {
		return DALFactory.getInstance().getDAL();
	}

	@Override
	public SearchPresentationModel getSearchPresentationModel() {
		return new SearchPresentationModelImpl();
	}

	@Override
	public void testSetup(String picturePath) {
		// TODO Auto-generated method stub
		
	}
}
