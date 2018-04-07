package picdb.controllers;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import picdb.models.IPTCModelImpl;
import picdb.presentationmodels.IPTCPresentationModelImpl;

public class MainController extends AbstractController {

    @FXML
    public MenuBar menuBarLeft;
    public TabPane tabs;
    public MenuBar menuBarRight;
    public ChoiceBox IPTCCopyrighNotices;

    @FXML
	private void onBtnAbout(ActionEvent event) throws IOException {
        showDialog("../fxml/About.fxml", "About");
	}

    public void initialize(java.net.URL arg0, ResourceBundle arg1) {

    }

    @FXML
    private void onIPTCCopyrightListLoad(ActionEvent event) throws IOException {
        ObservableList obList = FXCollections.observableList((List)new IPTCPresentationModelImpl(new IPTCModelImpl()).getCopyrightNotices());
        obList.add("Not Set");
        IPTCCopyrighNotices.getItems().clear();
        IPTCCopyrighNotices.setItems(obList);
    }

}
