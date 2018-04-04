package picdb.DataAccessLayers;

import BIF.SWE2.interfaces.DataAccessLayer;
import BIF.SWE2.interfaces.ExposurePrograms;
import BIF.SWE2.interfaces.models.*;
import picdb.models.*;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.ConsoleHandler;

/**
 * Created by if16b014 on 05.03.18.
 */
public class DataAccessLayerMockImpl implements DataAccessLayer {

    private List<CameraModel> cameras;
    private List<PictureModel> pictures;
    private List<PhotographerModel> photographers;

    public DataAccessLayerMockImpl(){
        cameras = new ArrayList<>();
        pictures = new ArrayList<>();
        photographers = new ArrayList<>();

        cameras.add(new CameraModelImpl(1234, "producer", "Make", LocalDate.now(), "notes", 10.5f, 100f));
        cameras.add(new CameraModelImpl(1, "producer1", "Make1", LocalDate.now(), "notes1", 10.1f, 101f));

        photographers.add(new PhotographerModelImpl(1234, "ab", "cd", LocalDate.now(), "likes cheese"));
        photographers.add(new PhotographerModelImpl(1, "a", "men", LocalDate.now(), "likes cheese more"));

        //pictures.add(new PictureModelImpl("1234"));
        //pictures.add(new PictureModelImpl("1"));
    }

    @Override
    public Collection<PictureModel> getPictures(String namePart, PhotographerModel photographerModel, IPTCModel iptcModel, EXIFModel exifModel) throws Exception {
        if(namePart == null && photographerModel == null && iptcModel == null && exifModel == null){
            if(pictures.size() < 1){
                pictures.add(new PictureModelImpl("1234"));
                pictures.add(new PictureModelImpl("1"));
            }
            return pictures;
        }

        ArrayList<PictureModel> myPics = new ArrayList<>();
        for (PictureModel pic: pictures) {
            System.out.println(pic.getFileName());
            if(pic.getFileName().contains(namePart) || (pic.getIPTC() != null && pic.getIPTC().equals(iptcModel)) || (pic.getEXIF() != null && pic.getEXIF().equals(exifModel))){
                myPics.add(pic);
            }
        }
        return myPics;
    }

    //Lambda verwenden!

    @Override
    public PictureModel getPicture(int i) throws Exception {
        for (PictureModel pic: pictures) {
            if(pic.getID() == i){
                return pic;
            }
        }
        return new PictureModelImpl("1234");
    }

    @Override
    public void save(PictureModel pictureModel) throws Exception {
        pictures.add(pictureModel);
    }

    @Override
    public void deletePicture(int id) throws Exception {
        if(id < 0){
            pictures.clear();
        }else{
            for (int i = 0; i < pictures.size(); i++) {
                if(pictures.get(i).getID() == id){
                    pictures.remove(i);
                    //break;
                }
            }
        }

    }

    @Override
    public Collection<PhotographerModel> getPhotographers() throws Exception {
        return photographers;
    }

    @Override
    public PhotographerModel getPhotographer(int i) throws Exception {
        for (PhotographerModel phot: photographers) {
            if(phot.getID() == i){
                return phot;
            }
        }
        return null;
    }

    @Override
    public void save(PhotographerModel photographerModel) throws Exception {
        photographers.add(photographerModel);
    }

    @Override
    public void deletePhotographer(int id) throws Exception {
        for (int i = 0; i < photographers.size(); i++) {
            if(photographers.get(i).getID() == id){
                photographers.remove(i);
            }
        }
    }

    @Override
    public Collection<CameraModel> getCameras() {
        return cameras;
    }

    @Override
    public CameraModel getCamera(int i) {

        for (CameraModel cam: cameras) {
            if(cam.getID() == i){
                return cam;
            }
        }
        return new CameraModelImpl(1234, "producer", "Make", LocalDate.now(), "notes", 10.5f, 100f);
    }
}
