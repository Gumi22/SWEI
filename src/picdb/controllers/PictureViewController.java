package picdb.controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import picdb.presentationmodels.PictureListPresentationModelImpl;
import picdb.presentationmodels.PicturePresentationModelImpl;

import java.util.List;
import java.util.ResourceBundle;

public class PictureViewController extends AbstractController {

    final DoubleProperty zoomProperty = new SimpleDoubleProperty(100);

    @FXML
    public ScrollPane scrollPane;
    @FXML
    public ImageView SelectedPicture;


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
            scrollPane.setHvalue(scrollPane.getHvalue() + event.getDeltaY() / SelectedPicture.getBoundsInParent().getMaxX() * 2);
            scrollPane.setVvalue(scrollPane.getVvalue() + event.getDeltaY() / SelectedPicture.getBoundsInParent().getMaxY() * 2);
        });

        SelectedPicture.setImage(new Image("https://cdn1.iconfinder.com/data/icons/hawcons/32/698956-icon-111-search-128.png"));
        SelectedPicture.preserveRatioProperty().set(true);
        scrollPane.setContent(SelectedPicture);
        scrollPane.setPannable(true);

    }


}
