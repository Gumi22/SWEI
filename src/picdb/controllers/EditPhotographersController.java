package picdb.controllers;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.ExposurePrograms;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import picdb.BusinessLayerImpl;
import picdb.GlobalConfig;
import picdb.models.PhotographerModelImpl;
import picdb.presentationmodels.PhotographerPresentationModelImpl;

import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditPhotographersController extends AbstractController {

    @FXML
    public TableView<PhotographerPresentationModelImpl> table;
    @FXML
    public TableColumn<PhotographerPresentationModelImpl, Integer> idColumn;
    @FXML
    public TableColumn<PhotographerPresentationModelImpl, LocalDate> dateOfBirthColumn;
    @FXML
    public TableColumn<PhotographerPresentationModelImpl, String> firstNameColumn;
    @FXML
    public TableColumn<PhotographerPresentationModelImpl, String> lastNameColumn;
    @FXML
    public TableColumn<PhotographerPresentationModelImpl, String> notesColumn;
    @FXML
    public Text errorMsg;
    @FXML
    public TextField firstName;
    @FXML
    public TextField lastName;
    @FXML
    public TextField birthDate;
    @FXML
    public TextField notes;
    @FXML
    public Text errorMsgNew;

    private Stage stage;
    private BusinessLayer BL;

    private ObservableList<PhotographerPresentationModelImpl> photographers = FXCollections.observableArrayList();

    public void setStage(Stage temp) {
        stage = temp;
    }

    @FXML
    private void onClose(ActionEvent event) {
        stage.close();
    }

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        super.initialize(arg0, arg1);

        BL = BusinessLayerImpl.getInstance(GlobalConfig.getInstance().getPath(), GlobalConfig.getInstance().isTestingMode());

        table.setItems(photographers);

        try {
            BL.getPhotographers().forEach(p -> photographers.add(new PhotographerPresentationModelImpl(p)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTableEditable();
    }

    private void setTableEditable() {
        table.setEditable(true);
        // allows the individual cells to be selected
        table.getSelectionModel().cellSelectionEnabledProperty().set(true);

        idColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idColumn.editableProperty().set(false);
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setOnEditCommit(event -> {
                    (event.getTableView().getItems().get(
                            event.getTablePosition().getRow())
                    ).setFirstName(event.getNewValue());
                    save(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                }
        );
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setOnEditCommit(event -> {
            ((PhotographerPresentationModelImpl) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setLastName(event.getNewValue());
            save(event.getTableView().getItems().get(event.getTablePosition().getRow()));
        });
        dateOfBirthColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        dateOfBirthColumn.setOnEditCommit(event -> {
            ((PhotographerPresentationModelImpl) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setBirthDay(event.getNewValue());
            save(event.getTableView().getItems().get(event.getTablePosition().getRow()));
        });
        notesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        notesColumn.setOnEditCommit(event -> {
                    (event.getTableView().getItems().get(
                            event.getTablePosition().getRow())
                    ).setNotes(event.getNewValue());
                    save(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                }
        );
    }

    private boolean save(PhotographerPresentationModelImpl Phot) {
        try {
            if (Phot.isValid()) {
                BL.save(Phot.getPhotographer());
                errorMsg.setText(Phot.getValidationSummary());
                return true;
            }
            errorMsg.setText(Phot.getValidationSummary());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private void submit(final ActionEvent event) {
        PhotographerModelImpl phot = new PhotographerModelImpl();
        phot.setFirstName(firstName.getText());
        phot.setLastName(lastName.getText());
        try {
            phot.setBirthDay(LocalDate.parse(birthDate.getText()));
        }catch (Exception e){      }
        phot.setNotes(notes.getText());
        PhotographerPresentationModelImpl photModel = new PhotographerPresentationModelImpl(phot);
        if(save(photModel)){
            photographers.add(photModel);
        }
        errorMsgNew.setText(photModel.getValidationSummary());
    }

}