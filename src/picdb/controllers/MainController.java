package picdb.controllers;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import BIF.SWE2.interfaces.BusinessLayer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import picdb.BusinessLayerImpl;
import picdb.models.IPTCModelImpl;
import picdb.presentationmodels.IPTCPresentationModelImpl;
import picdb.presentationmodels.PictureListPresentationModelImpl;

public class MainController extends AbstractController {

    @FXML
    public MenuBar menuBarLeft;
    public TabPane tabs;
    public MenuBar menuBarRight;
    public ComboBox IPTCCopyrighNotices;
    public SplitPane ImageInfoSplit = new SplitPane();
    final DoubleProperty zoomProperty = new SimpleDoubleProperty(100);
    public ImageView SelectedPicture = new ImageView();
    public ScrollPane scrollPane = new ScrollPane();
    public ListView pictureScroller = new ListView();
    private BusinessLayer BL = BusinessLayerImpl.getInstance("Pictures", false); //E:/Bilder/Desktophintergrund/FHungergames


    @FXML
	private void onBtnAbout(ActionEvent event) throws IOException {
        showDialog("../fxml/About.fxml", "About");
	}

	@Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        super.initialize(arg0,arg1);

        sync();

        zoomProperty.addListener(arg01 -> {
            SelectedPicture.setFitWidth(zoomProperty.get() * 4);
            SelectedPicture.setFitHeight(zoomProperty.get() * 3);
        });

        scrollPane.addEventFilter(ScrollEvent.ANY, event -> {
            if (event.getDeltaY() > 0) {
                zoomProperty.set(zoomProperty.get() * 1.1);
            } else if (event.getDeltaY() < 0) {
                zoomProperty.set(zoomProperty.get() / 1.1);
            }
            scrollPane.setHvalue(scrollPane.getHvalue() + event.getDeltaY() / SelectedPicture.getBoundsInParent().getMaxX() * 2);
            scrollPane.setVvalue(scrollPane.getVvalue() + event.getDeltaY() / SelectedPicture.getBoundsInParent().getMaxY() * 2);
        });

        SelectedPicture.setImage(new Image("https://cdn1.iconfinder.com/data/icons/hawcons/32/698956-icon-111-search-128.png"));
        SelectedPicture.preserveRatioProperty().set(true);
        scrollPane.setContent(SelectedPicture);
        scrollPane.setPannable(true);
        ImageInfoSplit.getItems().add(0, scrollPane);

        try {
            pictureScroller.setItems(FXCollections.observableList((List)new PictureListPresentationModelImpl(BL.getPictures(null, null, null, null), pictureScroller).getImages()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onIPTCCopyrightListLoad(Event event) throws IOException {
        ObservableList obList = FXCollections.observableList((List)new IPTCPresentationModelImpl(new IPTCModelImpl()).getCopyrightNotices());
        obList.add("Not Set");
        IPTCCopyrighNotices.getItems().clear();
        IPTCCopyrighNotices.setItems(obList);
    }

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

}
