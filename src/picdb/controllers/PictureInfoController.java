package picdb.controllers;

import BIF.SWE2.interfaces.models.PictureModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import picdb.models.IPTCModelImpl;
import picdb.presentationmodels.IPTCPresentationModelImpl;

import java.io.IOException;
import java.util.List;

public class PictureInfoController extends AbstractController {


    public ComboBox IPTCCopyrighNotices;
    public TextArea IPTCKeywords;
    public TextField IPTCByLine;
    public TextField IPTCCaption;
    public TextField IPTCHeadline;
    public TabPane tabs;
    public ChoiceBox UsedCamera;
    public TextField EXIFFNumber;
    public TextField EXIFExposureTime;
    public TextField EXIFIsoValue;
    public CheckBox EXIFFlash;
    public ChoiceBox EXIFExposureProgram;
    public Text EXIFISORating;

    public void changePicture(PictureModel pm){

    }

    @FXML
    private void onIPTCCopyrightListLoad(Event event) throws IOException {
        ObservableList obList = FXCollections.observableList((List)new IPTCPresentationModelImpl(new IPTCModelImpl()).getCopyrightNotices());
        obList.add("Not Set");
        IPTCCopyrighNotices.getItems().clear();
        IPTCCopyrighNotices.setItems(obList);
    }

}
