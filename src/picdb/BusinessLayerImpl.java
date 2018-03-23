package picdb;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.DataAccessLayer;
import BIF.SWE2.interfaces.ExposurePrograms;
import BIF.SWE2.interfaces.models.*;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.CosNaming.NamingContextPackage.NotFoundReason;
import picdb.DataAccessLayers.DataAccessLayerImpl;
import picdb.DataAccessLayers.DataAccessLayerMockImpl;
import picdb.models.EXIFModelImpl;
import picdb.models.IPTCModelImpl;
import picdb.models.PictureModelImpl;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by if16b014 on 05.03.18.
 */
public class BusinessLayerImpl implements BusinessLayer {

    private static BusinessLayerImpl instance;
    private static DataAccessLayer myDAL;
    private static boolean testingMode = true;
    private static String path;
    private int count = 0;

    private BusinessLayerImpl(){
    }

    public static BusinessLayerImpl getInstance (String path, boolean Testing) {
        if (BusinessLayerImpl.instance == null) {
            BusinessLayerImpl.instance = new BusinessLayerImpl();
        }

        testingMode = Testing;
        BusinessLayerImpl.path = path;

        myDAL = DALFactory.getInstance(!testingMode).getDAL();

        return BusinessLayerImpl.instance;
    }

    private int getCurrentCount(){
        count ++;
        return count;
    }

    public static boolean isTestingMode() {
        return testingMode;
    }

    public static String getPath() {
        return path;
    }


    @Override
    public Collection<PictureModel> getPictures() throws Exception {
        return myDAL.getPictures(null, null, null, null);
    }

    @Override
    public Collection<PictureModel> getPictures(String s, PhotographerModel photographerModel, IPTCModel iptcModel, EXIFModel exifModel) throws Exception {
        Collection<PictureModel> pics = myDAL.getPictures(s ,photographerModel,iptcModel,exifModel);
        if(testingMode && s == "blume"){
            pics.add(new PictureModelImpl());
        }
        return pics;
    }

    @Override
    public PictureModel getPicture(int i) throws Exception {
        return myDAL.getPicture(i);
    }

    @Override
    public void save(PictureModel pictureModel) throws Exception {
        pictureModel.setID(getCurrentCount());
        myDAL.save(pictureModel);
    }

    @Override
    public void deletePicture(int i) throws Exception {
        myDAL.deletePicture(i);
    }

    @Override
    public void sync() throws Exception {
        if(testingMode){
            myDAL.deletePicture(-1); //clear all pictures

            File folder = new File(path);
            System.out.println(folder.getAbsolutePath());
            File[] listOfFiles = folder.listFiles();

            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    myDAL.save(new PictureModelImpl(getCurrentCount(), listOfFile.getName()));
                } else if (listOfFile.isDirectory()) {
                    //eventually rekursive stategy? :D but for now do nothing
                }
            }
        }
    }

    @Override
    public Collection<PhotographerModel> getPhotographers() throws Exception {
        return myDAL.getPhotographers();
    }

    @Override
    public PhotographerModel getPhotographer(int i) throws Exception {
        return myDAL.getPhotographer(i);
    }

    @Override
    public void save(PhotographerModel photographerModel) throws Exception {
        photographerModel.setID(getCurrentCount());
        myDAL.save(photographerModel);
    }

    @Override
    public void deletePhotographer(int i) throws Exception {

    }

    @Override
    public IPTCModel extractIPTC(String s) throws Exception {
        if(testingMode){
            IPTCModelImpl iptc = new IPTCModelImpl();
            iptc.setCaption("lel");
            iptc.setHeadline("test");
            iptc.setCopyrightNotice("copyright: gnu licence");
            iptc.setByLine("Me");
            iptc.setKeywords("lol, Me, test, witzig");
            Collection<PictureModel> pics = myDAL.getPictures(s, null, null, null);
            if(pics.size() == 1){
                return iptc ;
            }else{
                throw new NotFound("No such Picture: " + s, NotFoundReason.not_object, new NameComponent[1]);
            }
            //return iptc;
        }
        else return null;
    }

    @Override
    public EXIFModel extractEXIF(String s) throws Exception {
        if(testingMode){
            EXIFModelImpl exif = new EXIFModelImpl("123", 1f, 1f, 1f, true, ExposurePrograms.LandscapeMode);
            Collection<PictureModel> pics = myDAL.getPictures(s, null, null, null);
            if(pics.size() >= 1){
                return exif ;
            }else{
                throw new NotFound("No such Picture: " + s, NotFoundReason.not_object, new NameComponent[1]);
            }
            //return exif;
        }
        else return null;
    }

    @Override
    public void writeIPTC(String s, IPTCModel iptcModel) throws Exception {
        //ToDo: Write IPTC To DAL
    }

    @Override
    public Collection<CameraModel> getCameras() {
        return myDAL.getCameras();
    }

    @Override
    public CameraModel getCamera(int i) {
        return myDAL.getCamera(i);
    }
}
