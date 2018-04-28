package picdb.controllers;

import BIF.SWE2.interfaces.models.PictureModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import picdb.models.IPTCModelImpl;
import picdb.presentationmodels.IPTCPresentationModelImpl;
import picdb.presentationmodels.PicturePresentationModelImpl;

import java.io.IOException;
import java.util.List;

public class PictureInfoController extends AbstractController {

    @FXML
    public ComboBox IPTCCopyrighNotices;
    @FXML
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

    PicturePresentationModelImpl PPM = new PicturePresentationModelImpl();

    public void changePicture(PicturePresentationModel pm){
        PPM = (PicturePresentationModelImpl) pm;
        try{
            if(IPTCKeywords != null){
                IPTCKeywords.textProperty().bindBidirectional(PPM.getIPTCImpl().getKeywordsProperty());
            }
        }catch (Exception e){
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

    public void save(MouseEvent mouseEvent) {
        //ToDo: Save properties into Model and send to Businesslayer
        System.out.println("Keywords: " + PPM.getIPTCImpl().getKeywords());
    }
}
