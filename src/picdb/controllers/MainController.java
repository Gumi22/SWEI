package picdb.controllers;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.models.PictureModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import picdb.BusinessLayerImpl;
import picdb.GlobalConfig;
import picdb.models.IPTCModelImpl;
import picdb.models.PictureModelImpl;
import picdb.presentationmodels.IPTCPresentationModelImpl;
import picdb.presentationmodels.PictureListPresentationModelImpl;
import picdb.presentationmodels.PicturePresentationModelImpl;

public class MainController extends AbstractController {

    @FXML
    public SplitPane ImageInfoSplit;
    public BorderPane mainBorderPane;
    public SplitPane TopBottomSplit;
    public VBox topBars;

    private BusinessLayer BL; //E:/Bilder/Desktophintergrund/FHungergames

    private PictureViewController PVC;
    private PictureInfoController PIC;
    private PictureScrollerController PSC;
    private SearchController SC;
    private MenuBarController MBC;


    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        super.initialize(arg0, arg1);

        BL = BusinessLayerImpl.getInstance(GlobalConfig.getInstance().getPath(), GlobalConfig.getInstance().isTestingMode());
        //synch Businesslayer at startup :D
        try {
            BL.sync();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //load other controllers
        try {
            loadIncludesAndControllers();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    private void loadIncludesAndControllers() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        ScrollPane sp = fxmlLoader.load(getClass().getResource("../fxml/PictureView.fxml").openStream());
        PVC = (PictureViewController) fxmlLoader.getController();
        ImageInfoSplit.getItems().add(sp);

        fxmlLoader = new FXMLLoader();
        TabPane tp = fxmlLoader.load(getClass().getResource("../fxml/PictureInfo.fxml").openStream());
        PIC = (PictureInfoController) fxmlLoader.getController();
        PIC.setBL(BL);
        ImageInfoSplit.getItems().add(tp);

        fxmlLoader = new FXMLLoader();
        ListView lv = fxmlLoader.load(getClass().getResource("../fxml/PictureScroller.fxml").openStream());
        PSC = (PictureScrollerController) fxmlLoader.getController();
        PSC.setMC(this);
        PSC.setBL(BL);
        TopBottomSplit.getItems().add(lv);

        fxmlLoader = new FXMLLoader();
        HBox hb = fxmlLoader.load(getClass().getResource("../fxml/MenuBar.fxml").openStream());
        MBC = (MenuBarController) fxmlLoader.getController();
        MBC.setBL(BL);
        MBC.setMC(this);
        topBars.getChildren().add(hb);

        fxmlLoader = new FXMLLoader();
        HBox h = fxmlLoader.load(getClass().getResource("../fxml/Search.fxml").openStream());
        SC = (SearchController) fxmlLoader.getController();
        topBars.getChildren().add(h);

    }

    public void changeSelectedPicture(ImageView next) {
        try {
            PVC.changePicture(next.getImage().impl_getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeSelectedPicture(PicturePresentationModel pic) {
        try {
            PVC.changePicture("file:" + BusinessLayerImpl.getPath() + "/" + pic.getFileName());
            PIC.changePicture(pic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void synchAll(){
        try {
            BL.sync();
            PSC.loadPictures();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
