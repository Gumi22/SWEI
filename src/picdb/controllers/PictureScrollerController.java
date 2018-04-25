package picdb.controllers;

import BIF.SWE2.interfaces.BusinessLayer;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import picdb.presentationmodels.PictureListPresentationModelImpl;

import java.util.List;
import java.util.ResourceBundle;

public class PictureScrollerController extends AbstractController {

    @FXML
    private ListView pictureScroller;

    private static MainController MC;
    private static BusinessLayer BL;

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        super.initialize(arg0, arg1);
    }

    public void setMC(MainController MC) {
        PictureScrollerController.MC = MC;
    }

    public void setBL(BusinessLayer BL) {
        PictureScrollerController.BL = BL;
        loadPictures();
    }

    public void loadPictures(){
        try {
            pictureScroller.setItems(FXCollections.observableList((List) new PictureListPresentationModelImpl(BL.getPictures(null, null, null, null), pictureScroller).getImages()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        pictureScroller.getSelectionModel().getSelectedItems()
                .addListener((ListChangeListener<String>) arg01 -> MC.changeSelectedPicture((ImageView)pictureScroller.getSelectionModel().getSelectedItems().iterator().next()));
    }
}
