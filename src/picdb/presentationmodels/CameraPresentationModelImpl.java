package picdb.presentationmodels;

import BIF.SWE2.interfaces.ISORatings;
import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;
import picdb.models.CameraModelImpl;

import java.time.LocalDate;

public class CameraPresentationModelImpl implements CameraPresentationModel {

	private CameraModel cam;

	public CameraPresentationModelImpl(CameraModel c){
		cam = c;
	}

	@Override
	public int getID() {
		return cam.getID();
	}

	@Override
	public String getProducer() {
		return cam.getProducer();
	}

	@Override
	public void setProducer(String s) {
		cam.setProducer(s);
	}

	@Override
	public String getMake() {
		return cam.getMake();
	}

	@Override
	public void setMake(String s) {
		cam.setMake(s);
	}

	@Override
	public LocalDate getBoughtOn() {
		return cam.getBoughtOn();
	}

	@Override
	public void setBoughtOn(LocalDate localDate) {
		cam.setBoughtOn(localDate);
	}

	@Override
	public String getNotes() {
		return cam.getNotes();
	}

	@Override
	public void setNotes(String s) {
		cam.setNotes(s);
	}

	@Override
	public int getNumberOfPictures() {
		return 0;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public String getValidationSummary() {
		return null;
	}

	@Override
	public boolean isValidProducer() {
		return false;
	}

	@Override
	public boolean isValidMake() {
		return false;
	}

	@Override
	public boolean isValidBoughtOn() {
		return false;
	}

	@Override
	public double getISOLimitGood() {
		return cam.getISOLimitGood();
	}

	@Override
	public void setISOLimitGood(double v) {
		cam.setISOLimitGood(v);
	}

	@Override
	public double getISOLimitAcceptable() {
		return cam.getISOLimitAcceptable();
	}

	@Override
	public void setISOLimitAcceptable(double v) {
		cam.setISOLimitAcceptable(v);
	}

	@Override
	public ISORatings translateISORating(double v) {
		return null;
	}
}
