package picdb.controllers;

import BIF.SWE2.interfaces.BusinessLayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

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
        sync();
    }

    private void sync(){
        try{
            BL.sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}