package picdb.controllers;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.EXIFModel;
import BIF.SWE2.interfaces.models.IPTCModel;
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
import picdb.models.EXIFModelImpl;
import picdb.models.IPTCModelImpl;
import picdb.models.PictureModelImpl;
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
    public TextField UsedCamera;
    @FXML
    public TextField EXIFFNumber;
    @FXML
    public TextField EXIFExposureTime;
    @FXML
    public TextField EXIFIsoValue;
    @FXML
    public CheckBox EXIFFlash;
    @FXML
    public TextField EXIFExposureProgram;
    @FXML
    public Text EXIFISORating;

    PicturePresentationModelImpl PPM = new PicturePresentationModelImpl();
    BusinessLayer BL;

    public void changePicture(PicturePresentationModel pm){
        try{
            if(IPTCKeywords != null){
                if(IPTCHeadline.textProperty().isBound()){
                    IPTCHeadline.textProperty().unbindBidirectional(PPM.getIPTCImpl().getHeadLineProperty());
                    IPTCByLine.textProperty().unbindBidirectional(PPM.getIPTCImpl().getByLineProperty());
                    IPTCKeywords.textProperty().unbindBidirectional(PPM.getIPTCImpl().getKeywordsProperty());
                    IPTCCaption.textProperty().unbindBidirectional(PPM.getIPTCImpl().getCaptionProperty());

                }
                IPTCHeadline.textProperty().bindBidirectional(((PicturePresentationModelImpl)pm).getIPTCImpl().getHeadLineProperty());
                IPTCByLine.textProperty().bindBidirectional(((PicturePresentationModelImpl)pm).getIPTCImpl().getByLineProperty());
                IPTCKeywords.textProperty().bindBidirectional(((PicturePresentationModelImpl)pm).getIPTCImpl().getKeywordsProperty());
                IPTCCaption.textProperty().bindBidirectional(((PicturePresentationModelImpl)pm).getIPTCImpl().getCaptionProperty());
                setIPTCCopyrighNotices(pm.getIPTC().getCopyrightNotice());

                EXIFExposureTime.textProperty().bind(new SimpleStringProperty(Double.toString(((PicturePresentationModelImpl)pm).getEXIFImpl().getExposureTime())));
                EXIFFlash.selectedProperty().bind(new SimpleBooleanProperty(((PicturePresentationModelImpl)pm).getEXIFImpl().getFlash()));
                EXIFFNumber.textProperty().bind(new SimpleStringProperty(Double.toString(((PicturePresentationModelImpl)pm).getEXIFImpl().getFNumber())));
                EXIFIsoValue.textProperty().bind(new SimpleStringProperty(Double.toString(((PicturePresentationModelImpl)pm).getEXIFImpl().getISOValue())));
                EXIFISORating.textProperty().bind(new SimpleStringProperty(((PicturePresentationModelImpl)pm).getEXIFImpl().getISORating().name()));
                EXIFExposureProgram.textProperty().bind(new SimpleStringProperty(((PicturePresentationModelImpl)pm).getEXIFImpl().getExposureProgram()));

                UsedCamera.textProperty().bind(new SimpleStringProperty(((PicturePresentationModelImpl)pm).getCameraImpl().getFullName()));

                PPM = (PicturePresentationModelImpl) pm;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setIPTCCopyrighNotices(String actual){
        ObservableList obList = FXCollections.observableList((List)new IPTCPresentationModelImpl(new IPTCModelImpl()).getCopyrightNotices());
        obList.add("Not Set");
        if(actual != null && !actual.isEmpty()){
            obList.add(actual);
        }
        IPTCCopyrighNotices.getItems().clear();
        IPTCCopyrighNotices.setItems(obList);
        if(actual != null && !actual.isEmpty()){
            IPTCCopyrighNotices.getSelectionModel().select(actual);
        }else{
            IPTCCopyrighNotices.getSelectionModel().selectLast();
        }
    }

    public void setBL(BusinessLayer bl){
        BL = bl;
    }

    public void save(MouseEvent mouseEvent) {
        //ToDo: Save properties into Model and send to Businesslayer

        //Save changes into PictureModel:
        PictureModelImpl pmi = PPM.getPictureModel();

        IPTCModel iptc = pmi.getIPTC();

        iptc.setCopyrightNotice(IPTCCopyrighNotices.getSelectionModel().getSelectedItem().toString().equals("Not Set") ? "" : IPTCCopyrighNotices.getSelectionModel().getSelectedItem().toString());
        iptc.setByLine(IPTCByLine.getText());
        iptc.setCaption(IPTCCaption.getText());
        iptc.setHeadline(IPTCHeadline.getText());
        iptc.setKeywords(IPTCKeywords.getText());

        try {
            BL.save(pmi);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
