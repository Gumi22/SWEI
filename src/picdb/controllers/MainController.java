package picdb.controllers;

import java.awt.*;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import picdb.models.IPTCModelImpl;
import picdb.presentationmodels.IPTCPresentationModelImpl;

public class MainController extends AbstractController {

    @FXML
    public MenuBar menuBarLeft;
    public TabPane tabs;
    public MenuBar menuBarRight;
    public ChoiceBox IPTCCopyrighNotices;
    public SplitPane ImageInfoSplit = new SplitPane();
    final DoubleProperty zoomProperty = new SimpleDoubleProperty(100);
    public ImageView SelectedPicture = new ImageView();
    public ScrollPane scrollPane = new ScrollPane();


    @FXML
	private void onBtnAbout(ActionEvent event) throws IOException {
        showDialog("../fxml/About.fxml", "About");
	}

	@Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        super.initialize(arg0,arg1);
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
        });

        SelectedPicture.setImage(new Image("https://cdn1.iconfinder.com/data/icons/hawcons/32/698956-icon-111-search-128.png"));
        SelectedPicture.preserveRatioProperty().set(true);
        scrollPane.setContent(SelectedPicture);

        ImageInfoSplit.getItems().add(0, scrollPane);
    }

    @FXML
    private void onIPTCCopyrightListLoad(ActionEvent event) throws IOException {
        ObservableList obList = FXCollections.observableList((List)new IPTCPresentationModelImpl(new IPTCModelImpl()).getCopyrightNotices());
        obList.add("Not Set");
        IPTCCopyrighNotices.getItems().clear();
        IPTCCopyrighNotices.setItems(obList);
    }

}
