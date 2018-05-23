package picdb.presentationmodels;

import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import picdb.models.PhotographerModelImpl;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by if16b014 on 05.03.18.
 */
public class PhotographerPresentationModelImpl implements PhotographerPresentationModel {


    private PhotographerModel phot;

    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleObjectProperty<LocalDate> birthDay;
    private SimpleStringProperty notes;


    public PhotographerPresentationModelImpl(PhotographerModel photographerModel) {
        setUp(photographerModel);
    }

    private void setUp(PhotographerModel photographerModel){
        phot = photographerModel;
        if(photographerModel == null){
            phot = new PhotographerModelImpl();
        }
        this.firstName = new SimpleStringProperty(phot.getFirstName());
        this.lastName = new SimpleStringProperty(phot.getLastName());
        this.birthDay = new SimpleObjectProperty < LocalDate > (
                phot.getBirthDay());
        this.notes = new SimpleStringProperty(phot.getNotes());
    }

    @Override
    public int getID() {
        return phot.getID();
    }

    @Override
    public String getFirstName() {
        return firstName.get();
    }

    @Override
    public void setFirstName(final String s) {
        firstName.set(s);
    }

    @Override
    public String getLastName() {
        return lastName.get();
    }

    @Override
    public void setLastName(final String s) {
        lastName.set(s);
    }

    @Override
    public LocalDate getBirthDay() {
        return birthDay.get();
    }

    @Override
    public void setBirthDay(final LocalDate localDate) {
        birthDay.set(localDate);
    }

    @Override
    public String getNotes() {
        return notes.get();
    }

    @Override
    public void setNotes(final String s) {
        notes.set(s);
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
            validation += "Not a valid Birthdate.";
        }
        if(!isValidLastName()){
            validation += "Not a valid Last Name.";
        }
        return validation;
    }

    @Override
    public boolean isValidLastName() {
        return getLastName() != null && !getLastName().isEmpty();
    }

    @Override
    public boolean isValidBirthDay() {
        return getBirthDay() == null || (getBirthDay().isBefore(LocalDate.now()));
    }

    public PhotographerModelImpl getPhotographer(){
        PhotographerModelImpl ret = new PhotographerModelImpl();
        ret.setID(phot.getID());
        ret.setBirthDay(birthDay.get());
        ret.setFirstName(firstName.get());
        ret.setLastName(lastName.get());
        ret.setNotes(notes.get());
        setUp(ret);
        return ret;
    }

    public String getName(){
        return (getFirstName() == null ? "" : getFirstName()) + " " + (getLastName() == null ? "" : getLastName());
    }

    @Override
    public String toString() {
        return getName();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public SimpleObjectProperty<LocalDate> birthDayProperty() {
        return birthDay;
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public SimpleIntegerProperty idProperty() { return new SimpleIntegerProperty(phot.getID()); }
}
