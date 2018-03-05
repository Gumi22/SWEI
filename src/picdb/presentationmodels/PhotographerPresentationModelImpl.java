package picdb.presentationmodels;

import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;

import java.time.LocalDate;

/**
 * Created by if16b014 on 05.03.18.
 */
public class PhotographerPresentationModelImpl implements PhotographerPresentationModel {
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public void setFirstName(String s) {

    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public void setLastName(String s) {

    }

    @Override
    public LocalDate getBirthDay() {
        return null;
    }

    @Override
    public void setBirthDay(LocalDate localDate) {

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
    public boolean isValidLastName() {
        return false;
    }

    @Override
    public boolean isValidBirthDay() {
        return false;
    }
}
