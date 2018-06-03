package picdb.controllers;

import BIF.SWE2.interfaces.BusinessLayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import picdb.BusinessLayerImpl;
import picdb.GlobalConfig;
import picdb.models.CameraModelImpl;
import picdb.presentationmodels.CameraPresentationModelImpl;
import picdb.presentationmodels.PhotographerPresentationModelImpl;

import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditCamerasController extends AbstractController {

    @FXML
    public TableView<CameraPresentationModelImpl> table;
    @FXML
    public TableColumn<CameraPresentationModelImpl, Integer> idColumn;
    @FXML
    public TableColumn<CameraPresentationModelImpl, LocalDate> purchaseDateColumn;
    @FXML
    public TableColumn<CameraPresentationModelImpl, String> producerColumn;
    @FXML
    public TableColumn<CameraPresentationModelImpl, String> modelColumn;
    @FXML
    public TableColumn<CameraPresentationModelImpl, String> notesColumn;
    @FXML
    public TableColumn<CameraPresentationModelImpl, Double> isoLimitGoodColumn;
    @FXML
    public TableColumn<CameraPresentationModelImpl, Double> isoLimitAcceptableColumn;
    @FXML
    public Text errorMsg;
    @FXML
    public Text errorMsgNew;
    @FXML
    public TextField producer;
    @FXML
    public TextField make;
    @FXML
    public TextField boughtOn;
    @FXML
    public TextField notes;
    @FXML
    public TextField isoLimitGood;
    @FXML
    public TextField isoLimitAcceptable;

    private Stage stage;
    private BusinessLayer BL;

    private ObservableList<CameraPresentationModelImpl> cameras = FXCollections.observableArrayList();

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

        table.setItems(cameras);

        try {
            BL.getCameras().forEach(c -> cameras.add(new CameraPresentationModelImpl(c)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializeCellFactories();
    }

    private void initializeCellFactories() {
        table.setEditable(true);
        // allows the individual cells to be selected
        table.getSelectionModel().cellSelectionEnabledProperty().set(true);

        idColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idColumn.editableProperty().set(false);

        producerColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        producerColumn.setOnEditCommit(event -> {
                    (event.getTableView().getItems().get(
                            event.getTablePosition().getRow())
                    ).setProducer(event.getNewValue());
                    save(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                }
        );

        modelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modelColumn.setOnEditCommit(event -> {
            (event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setMake(event.getNewValue());
            save(event.getTableView().getItems().get(event.getTablePosition().getRow()));
        });

        purchaseDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        purchaseDateColumn.setOnEditCommit(event -> {
            (event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setBoughtOn(event.getNewValue());
            save(event.getTableView().getItems().get(event.getTablePosition().getRow()));
        });

        notesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        notesColumn.setOnEditCommit(event -> {
            (event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setNotes(event.getNewValue());
            save(event.getTableView().getItems().get(event.getTablePosition().getRow()));
        });

        isoLimitAcceptableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        isoLimitAcceptableColumn.setOnEditCommit(event -> {
            (event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setISOLimitAcceptable(event.getNewValue());
            save(event.getTableView().getItems().get(event.getTablePosition().getRow()));
        });

        isoLimitGoodColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        isoLimitGoodColumn.setOnEditCommit(event -> {
            (event.getTableView().getItems()
                    .get(event.getTablePosition().getRow()))
                    .setISOLimitGood(event.getNewValue());
            save(event.getTableView().getItems().get(event.getTablePosition().getRow()));
        });

    }

    public void submit(ActionEvent actionEvent) {
        CameraModelImpl cam = new CameraModelImpl();
        cam.setProducer(producer.getText());
        cam.setMake(make.getText());
        try {
            cam.setBoughtOn(LocalDate.parse(boughtOn.getText()));
        } catch (Exception e) {
        }
        cam.setNotes(notes.getText());
        try {
            cam.setISOLimitGood(Double.valueOf(isoLimitGood.getText()));
        } catch (Exception e) {
        }
        try {
            cam.setISOLimitAcceptable(Double.valueOf(isoLimitAcceptable.getText()));
        } catch (Exception e) {
        }

        CameraPresentationModelImpl camModel = new CameraPresentationModelImpl(cam);
        if (save(camModel)) {
            cameras.add(camModel);
        }
        errorMsgNew.setText(camModel.getValidationSummary());
    }

    private boolean save(CameraPresentationModelImpl Cam) {
        try {
            if (Cam.isValid()) {
                ((BusinessLayerImpl) BL).save(Cam.getCamera());
                errorMsg.setText(Cam.getValidationSummary());
                return true;
            }
            errorMsg.setText(Cam.getValidationSummary());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}