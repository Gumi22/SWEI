package picdb.controllers;

import BIF.SWE2.interfaces.BusinessLayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import picdb.BusinessLayerImpl;
import picdb.GlobalConfig;
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

    private Stage stage;

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

        BusinessLayer BL = BusinessLayerImpl.getInstance(GlobalConfig.getInstance().getPath(), GlobalConfig.getInstance().isTestingMode());

        table.setItems(cameras);

        try {
            BL.getCameras().forEach(c -> cameras.add(new CameraPresentationModelImpl(c)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializeCellFactories();
    }

    private void initializeCellFactories() {
        // allows the individual cells to be selected
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        producerColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        modelColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        purchaseDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

        notesColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        isoLimitAcceptableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        isoLimitGoodColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

    }

}