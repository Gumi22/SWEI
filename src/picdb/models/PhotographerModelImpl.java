package picdb.models;

import BIF.SWE2.interfaces.models.PhotographerModel;

import java.time.LocalDate;

/**
 * Created by if16b014 on 05.03.18.
 */
public class PhotographerModelImpl implements PhotographerModel {

    private int id = 0;
    private String firstName;
    private String lastName;
    private LocalDate birthDay;
    private String notes;

    public PhotographerModelImpl(){}
    public PhotographerModelImpl(int i, String name, String surname, LocalDate bDay, String note){
        this.id = i;
        this.firstName = name;
        this.lastName = surname;
        this.birthDay = bDay;
        this.notes = note;
    }


    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int i) {
        id = i;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String s) {
        firstName = s;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String s) {
        lastName = s;
    }

    @Override
    public LocalDate getBirthDay() {
        return birthDay;
    }

    @Override
    public void setBirthDay(LocalDate localDate) {
        birthDay = localDate;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public void setNotes(String s) {
        notes = s;
    }
}
