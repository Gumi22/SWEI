package picdb.controllers;

import BIF.SWE2.interfaces.models.PictureModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    @FXML
    public TextField IPTCByLine;
    @FXML
    public TextField IPTCCaption;
    @FXML
    public TextField IPTCHeadline;
    @FXML
    public TabPane tabs;
    @FXML
    public ChoiceBox UsedCamera;
    @FXML
    public TextField EXIFFNumber;
    @FXML
    public TextField EXIFExposureTime;
    @FXML
    public TextField EXIFIsoValue;
    @FXML
    public CheckBox EXIFFlash;
    @FXML
    public ChoiceBox EXIFExposureProgram;
    @FXML
    public Text EXIFISORating;

    PicturePresentationModelImpl PPM = new PicturePresentationModelImpl();

    public void changePicture(PicturePresentationModel pm){
        try{
            if(IPTCKeywords != null){
                if(IPTCHeadline.textProperty().isBound()){
                    IPTCHeadline.textProperty().unbindBidirectional(PPM.getIPTCImpl().getHeadLineProperty());
                    IPTCByLine.textProperty().unbindBidirectional(PPM.getIPTCImpl().getByLineProperty());
                    IPTCKeywords.textProperty().unbindBidirectional(PPM.getIPTCImpl().getKeywordsProperty());

                }
                IPTCHeadline.textProperty().bindBidirectional(((PicturePresentationModelImpl)pm).getIPTCImpl().getHeadLineProperty());
                IPTCByLine.textProperty().bindBidirectional(((PicturePresentationModelImpl)pm).getIPTCImpl().getByLineProperty());
                IPTCKeywords.textProperty().bindBidirectional(((PicturePresentationModelImpl)pm).getIPTCImpl().getKeywordsProperty());
                setIPTCCopyrighNotices(((PicturePresentationModelImpl)pm).getIPTCImpl().getCopyrightProperty());
                IPTCCaption.textProperty().bindBidirectional(((PicturePresentationModelImpl)pm).getIPTCImpl().getCaptionProperty());

                EXIFExposureTime.textProperty().bind(new SimpleStringProperty(Double.toString(((PicturePresentationModelImpl)pm).getEXIFImpl().getExposureTime())));
                EXIFFlash.selectedProperty().bind(new SimpleBooleanProperty(((PicturePresentationModelImpl)pm).getEXIFImpl().getFlash()));
                EXIFFNumber.textProperty().bind(new SimpleStringProperty(Double.toString(((PicturePresentationModelImpl)pm).getEXIFImpl().getFNumber())));
                EXIFIsoValue.textProperty().bind(new SimpleStringProperty(Double.toString(((PicturePresentationModelImpl)pm).getEXIFImpl().getISOValue())));
                EXIFISORating.textProperty().bind(new SimpleStringProperty(((PicturePresentationModelImpl)pm).getEXIFImpl().getISORating().name()));

                PPM = (PicturePresentationModelImpl) pm;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setIPTCCopyrighNotices(SimpleStringProperty actual){
        ObservableList obList = FXCollections.observableList((List)new IPTCPresentationModelImpl(new IPTCModelImpl()).getCopyrightNotices());
        obList.add("Not Set");
        if(!actual.getName().isEmpty()){
            obList.add(actual.getName());
        }
        IPTCCopyrighNotices.getItems().clear();
        IPTCCopyrighNotices.setItems(obList);
        IPTCCopyrighNotices.getSelectionModel().selectLast();
    }

    public void save(MouseEvent mouseEvent) {
        //ToDo: Save properties into Model and send to Businesslayer
        System.out.println("Keywords: " + PPM.getIPTCImpl().getKeywords());
    }
}
