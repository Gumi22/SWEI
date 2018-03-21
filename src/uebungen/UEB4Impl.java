package uebungen;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.UEB4;
import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.EXIFModel;
import BIF.SWE2.interfaces.models.IPTCModel;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.EXIFPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.IPTCPresentationModel;
import picdb.BusinessLayerImpl;
import picdb.models.CameraModelImpl;
import picdb.models.EXIFModelImpl;
import picdb.models.IPTCModelImpl;
import picdb.presentationmodels.CameraPresentationModelImpl;
import picdb.presentationmodels.EXIFPresentationModelImpl;
import picdb.presentationmodels.IPTCPresentationModelImpl;

public class UEB4Impl implements UEB4 {


	@Override
	public void helloWorld() {
		// I'm fine		
	}

	@Override
	public BusinessLayer getBusinessLayer() {
		return BusinessLayerImpl.getInstance();
	}

	@Override
	public EXIFModel getEmptyEXIFModel() {
		return new EXIFModelImpl();
	}

	@Override
	public EXIFPresentationModel getEXIFPresentationModel(EXIFModel exifModel) {
		return new EXIFPresentationModelImpl(exifModel);
	}

	@Override
	public IPTCModel getEmptyIPTCModel() {
		IPTCModel i = new IPTCModelImpl();
		i.setCopyrightNotice("lel");
		return i;
	}

	@Override
	public IPTCPresentationModel getIPTCPresentationModel(IPTCModel iptcModel) {
		return new IPTCPresentationModelImpl(iptcModel);
	}

	@Override
	public CameraModel getCameraModel(String s, String s1) {
		return new CameraModelImpl();
	}

	@Override
	public CameraPresentationModel getCameraPresentationModel(CameraModel cameraModel) {
		return new CameraPresentationModelImpl(cameraModel);
	}

	@Override
	public void testSetup(String picturePath) {
		
	}
}
