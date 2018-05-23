package picdb.controllers;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.models.*;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import picdb.models.EXIFModelImpl;
import picdb.models.IPTCModelImpl;
import picdb.models.PhotographerModelImpl;
import picdb.models.PictureModelImpl;
import picdb.presentationmodels.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PictureInfoController extends AbstractController {

    @FXML
    public ComboBox IPTCCopyrighNotices;
    @FXML
    public TextArea IPTCKeywords;
    @FXML
    public ComboBox IPTCByLine;
    @FXML
    public TextField IPTCCaption;
    @FXML
    public TextField IPTCHeadline;
    @FXML
    public TabPane tabs;
    @FXML
    public ComboBox UsedCamera;
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

    private PicturePresentationModelImpl PPM = new PicturePresentationModelImpl();
    private BusinessLayer BL;
    private MainController MC;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        super.initialize(url, resources);
    }

    public void changePicture(PicturePresentationModel pm){
        try{
            if(IPTCKeywords != null){
                if(IPTCHeadline.textProperty().isBound()){
                    IPTCHeadline.textProperty().unbindBidirectional(PPM.getIPTCImpl().getHeadLineProperty());
                    IPTCKeywords.textProperty().unbindBidirectional(PPM.getIPTCImpl().getKeywordsProperty());
                    IPTCCaption.textProperty().unbindBidirectional(PPM.getIPTCImpl().getCaptionProperty());
                }
                IPTCHeadline.textProperty().bindBidirectional(((PicturePresentationModelImpl)pm).getIPTCImpl().getHeadLineProperty());
                IPTCKeywords.textProperty().bindBidirectional(((PicturePresentationModelImpl)pm).getIPTCImpl().getKeywordsProperty());
                IPTCCaption.textProperty().bindBidirectional(((PicturePresentationModelImpl)pm).getIPTCImpl().getCaptionProperty());
                setIPTCCopyrighNotices(pm.getIPTC().getCopyrightNotice());
                setByLine(((PicturePresentationModelImpl) pm).getPhotographerImpl(), (PicturePresentationModelImpl)pm);

                EXIFExposureTime.textProperty().bind(new SimpleStringProperty(Double.toString(((PicturePresentationModelImpl)pm).getEXIFImpl().getExposureTime())));
                EXIFFlash.selectedProperty().bind(new SimpleBooleanProperty(((PicturePresentationModelImpl)pm).getEXIFImpl().getFlash()));
                EXIFFNumber.textProperty().bind(new SimpleStringProperty(Double.toString(((PicturePresentationModelImpl)pm).getEXIFImpl().getFNumber())));
                EXIFIsoValue.textProperty().bind(new SimpleStringProperty(Double.toString(((PicturePresentationModelImpl)pm).getEXIFImpl().getISOValue())));
                EXIFISORating.textProperty().bind(new SimpleStringProperty(((PicturePresentationModelImpl)pm).getEXIFImpl().getISORating().name()));
                EXIFExposureProgram.textProperty().bind(new SimpleStringProperty(((PicturePresentationModelImpl)pm).getEXIFImpl().getExposureProgram()));

                setCamera((CameraPresentationModelImpl) pm.getCamera(), (PicturePresentationModelImpl)pm);
                //UsedCamera.textProperty().bind(new SimpleStringProperty(((PicturePresentationModelImpl)pm).getCameraImpl().getFullName()));

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

    private void setByLine(PhotographerPresentationModelImpl actual, PicturePresentationModelImpl pm){
        PhotographerListPresentationModelImpl plpm = new PhotographerListPresentationModelImpl(pm.getPictureModel());
        ObservableList obList = FXCollections.observableList((List)plpm.getList());
        obList.add("Not Set");
        IPTCByLine.getItems().clear();
        IPTCByLine.setItems(obList);
        if(actual != null && !actual.getName().trim().isEmpty()){
            IPTCByLine.getSelectionModel().select(actual);
        }else{
            IPTCByLine.getSelectionModel().selectLast();
        }
    }

    private void setCamera(CameraPresentationModelImpl actual, PicturePresentationModelImpl pm){
        CameraListPresentationModelImpl clpm = new CameraListPresentationModelImpl(pm.getPictureModel());
        ObservableList obList = FXCollections.observableList((List)clpm.getList());
        obList.add("Not Set");
        UsedCamera.getItems().clear();
        UsedCamera.setItems(obList);
        if(actual != null && !actual.getFullName().trim().isEmpty()){
            UsedCamera.getSelectionModel().select(actual);
        }else{
            UsedCamera.getSelectionModel().selectLast();
        }
    }

    public void setBL(BusinessLayer bl){
        BL = bl;
    }

    public void save(MouseEvent mouseEvent) {

        //Save changes into PictureModel:
        PictureModelImpl pmi = PPM.getPictureModel();

        IPTCModel iptc = pmi.getIPTC();

        iptc.setCopyrightNotice(IPTCCopyrighNotices.getSelectionModel().getSelectedItem().toString().equals("Not Set") ? "" : IPTCCopyrighNotices.getSelectionModel().getSelectedItem().toString());
        iptc.setByLine(IPTCByLine.getSelectionModel().getSelectedItem().toString().equals("Not Set") ? null : IPTCByLine.getSelectionModel().getSelectedItem().toString());
        iptc.setCaption(IPTCCaption.getText());
        iptc.setHeadline(IPTCHeadline.getText());
        iptc.setKeywords(IPTCKeywords.getText());

        if(IPTCByLine.getSelectionModel().getSelectedIndex() >=0){
            pmi.setPhotographer(IPTCByLine.getSelectionModel().getSelectedItem().toString().equals("Not Set") ? null :
                    ((PhotographerPresentationModelImpl)IPTCByLine.getItems().get(IPTCByLine.getSelectionModel().getSelectedIndex())).getPhotographer());
        }

        if(UsedCamera.getSelectionModel().getSelectedIndex() >= 0){
            pmi.setCamera(UsedCamera.getSelectionModel().getSelectedItem().toString().equals("Not Set") ? null :
                    ((CameraPresentationModelImpl)UsedCamera.getItems().get(UsedCamera.getSelectionModel().getSelectedIndex())).getCamera());
        }

        try {
            BL.save(pmi);
            MC.synchAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setMC(MainController MC) {
        this.MC = MC;
    }
}
