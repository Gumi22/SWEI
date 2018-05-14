package picdb.presentationmodels;

import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;
import picdb.models.PhotographerModelImpl;

import java.time.LocalDate;

/**
 * Created by if16b014 on 05.03.18.
 */
public class PhotographerPresentationModelImpl implements PhotographerPresentationModel {


    private PhotographerModel phot;

    public PhotographerPresentationModelImpl(PhotographerModel photographerModel) {
        phot = photographerModel;
    }

    @Override
    public int getID() {
        return phot.getID();
    }

    @Override
    public String getFirstName() {
        return phot.getFirstName();
    }

    @Override
    public void setFirstName(String s) {
        phot.setFirstName(s);
    }

    @Override
    public String getLastName() {
        return phot.getLastName();
    }

    @Override
    public void setLastName(String s) {
        phot.setLastName(s);
    }

    @Override
    public LocalDate getBirthDay() {
        return phot.getBirthDay();
    }

    @Override
    public void setBirthDay(LocalDate localDate) {
        phot.setBirthDay(localDate);
    }

    @Override
    public String getNotes() {
        return phot.getNotes();
    }

    @Override
    public void setNotes(String s) {
        phot.setNotes(s);
    }

    @Override
    public int getNumberOfPictures() {
        return 0;
    }

    @Override
    public boolean isValid() {
        return (isValidLastName() && isValidBirthDay());
    }

    @Override
    public String getValidationSummary() {
        String validation = "";
        if(!isValidBirthDay()){
            validation += "Not a valid Birthdate";
        }
        if(!isValidLastName()){
            validation += "Not a valid Last Name";
        }
        return validation;
    }

    @Override
    public boolean isValidLastName() {
        return phot.getLastName() != null && !phot.getLastName().isEmpty();
    }

    @Override
    public boolean isValidBirthDay() {
        return phot.getBirthDay() == null || (phot.getBirthDay().isBefore(LocalDate.now()));
    }

    public PhotographerModelImpl getPhotographer(){
        return (PhotographerModelImpl)phot;
    }
}
