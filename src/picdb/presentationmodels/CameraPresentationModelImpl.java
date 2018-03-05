package picdb.presentationmodels;

import BIF.SWE2.interfaces.ISORatings;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;

import java.time.LocalDate;

public class CameraPresentationModelImpl implements CameraPresentationModel {

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public String getProducer() {
		return null;
	}

	@Override
	public void setProducer(String s) {

	}

	@Override
	public String getMake() {
		return null;
	}

	@Override
	public void setMake(String s) {

	}

	@Override
	public LocalDate getBoughtOn() {
		return null;
	}

	@Override
	public void setBoughtOn(LocalDate localDate) {

	}

	@Override
	public String getNotes() {
		return null;
	}

	@Override
	public void setNotes(String s) {

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
		return 0;
	}

	@Override
	public void setISOLimitGood(double v) {

	}

	@Override
	public double getISOLimitAcceptable() {
		return 0;
	}

	@Override
	public void setISOLimitAcceptable(double v) {

	}

	@Override
	public ISORatings translateISORating(double v) {
		return null;
	}
}
