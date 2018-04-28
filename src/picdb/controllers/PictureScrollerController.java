package picdb.controllers;

import BIF.SWE2.interfaces.BusinessLayer;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import picdb.presentationmodels.PictureListPresentationModelImpl;

import java.util.ResourceBundle;

public class PictureScrollerController extends AbstractController {

    @FXML
    private ListView pictureScroller;

    private static MainController MC;
    private static BusinessLayer BL;

    private PictureListPresentationModelImpl PL;

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        super.initialize(arg0, arg1);
        PL = new PictureListPresentationModelImpl();
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
            PL = new PictureListPresentationModelImpl(BL.getPictures(null, null, null, null), pictureScroller);
            pictureScroller.setItems((ObservableList) PL.getImages());
        } catch (Exception e) {
            e.printStackTrace();
        }

        pictureScroller.getSelectionModel().getSelectedItems()
                .addListener(new ListChangeListener() {
                    @Override
                    public void onChanged(Change c) {
                        PL.setSelected(pictureScroller.getSelectionModel().getSelectedIndex());
                        MC.changeSelectedPicture(PL.getCurrentPicture());
                    }
                });

        pictureScroller.getSelectionModel().selectFirst(); //when loading all the pictures Select the first one as default
    }
}
