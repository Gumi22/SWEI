package picdb.controllers;

import BIF.SWE2.interfaces.BusinessLayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import picdb.BusinessLayerImpl;
import picdb.GlobalConfig;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

public class MenuBarController extends AbstractController {

    public MenuBar menuBarLeft;
    public MenuBar menuBarRight;

    private static MainController MC;
    private static BusinessLayer BL;

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        super.initialize(arg0, arg1);
    }

    @FXML
    public void onBtnSynch(ActionEvent actionEvent) {
        MC.synchAll();
    }

    @FXML
    private void onBtnAbout(ActionEvent event) throws IOException {
        showDialog("../fxml/About.fxml", "About");
    }

    public void setMC(MainController MC) {
        MenuBarController.MC = MC;
    }

    public void setBL(BusinessLayer BL) {
        MenuBarController.BL = BL;
    }

    public void onBtnExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void onBtnEditPhotographers(ActionEvent actionEvent) throws IOException {
        showDialog("../fxml/EditPhotographers.fxml", "Edit Photographers");
    }

    public void onBtnEditCameras(ActionEvent actionEvent) throws IOException {
        showDialog("../fxml/EditCameras.fxml", "Edit Cameras");
    }

    public void onBtnTagsPDF(ActionEvent actionEvent) {
        //ToDo: get path and filename from Fileopendialogue;

        try {
            ((BusinessLayerImpl)BL).writeTagsPDF(GlobalConfig.getInstance().getPath() + "/Tags.pdf");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
