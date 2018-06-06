package test;

import BIF.SWE2.interfaces.ExposurePrograms;
import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.models.PhotographerModel;
import org.junit.After;
import picdb.DataAccessLayers.DataAccessLayerImpl;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import picdb.GlobalConfig;
import picdb.models.CameraModelImpl;
import picdb.models.EXIFModelImpl;
import picdb.models.IPTCModelImpl;
import picdb.models.PhotographerModelImpl;

import java.time.LocalDate;

public class DataAccessLayerImplTest {

    DataAccessLayerImpl DAL;
    GlobalConfig GC;

    @Before
    public void setUp() {
        DAL = new DataAccessLayerImpl();
        GC = GlobalConfig.getInstance("D:\\FH\\4_Semester\\SWEI\\SWE2-Java\\src\\picdb\\config.txt");
    }

    @Test
    public void searchFiltersCorrectlyByFileName() throws Exception {
        assertEquals(6, DAL.getPictures("I", null, null, null).size());
        assertEquals(5, DAL.getPictures("Im", null, null, null).size());
    }

    @Test
    public void searchFiltersCorrectlyByPhotographer() throws Exception {
        PhotographerModelImpl PM = new PhotographerModelImpl();
        PM.setID(1);
        assertEquals(6, DAL.getPictures(null, null, null, null).size());
        assertEquals(2, DAL.getPictures(null, PM, null, null).size());
        PM.setID(2);
        assertEquals(1, DAL.getPictures(null, PM, null, null).size());
    }

    @Test
    public void searchFiltersCorrectlyByIPTC() throws Exception {
        IPTCModelImpl IPTC = new IPTCModelImpl();
        assertEquals(6, DAL.getPictures(null, null, null, null).size());
        assertEquals(5, DAL.getPictures(null, null, IPTC, null).size());
        IPTC.setCopyrightNotice("CC by lol");
        assertEquals(1, DAL.getPictures(null, null, IPTC, null).size());
    }

    @Test
    public void searchFiltersCorrectlyByEXIF() throws Exception {
        EXIFModelImpl EXIF = new EXIFModelImpl();
        assertEquals(6, DAL.getPictures(null, null, null, null).size());

        EXIF.setExposureTime(10);
        EXIF.setISOValue(800);
        EXIF.setFNumber(2.29999995231628);
        EXIF.setFlash(true);
        EXIF.setExposureProgram(ExposurePrograms.values()[8]);
        assertEquals(5, DAL.getPictures(null, null, null, EXIF).size());

        EXIF.setExposureTime(0.614177867512815);
        EXIF.setISOValue(7183.84819149721);
        EXIF.setFNumber(0.0657194419868822);
        EXIF.setFlash(true);
        EXIF.setExposureProgram(ExposurePrograms.values()[4]);
        assertEquals(1, DAL.getPictures(null, null, null, EXIF).size());
    }

    @Test
    public void saveCamera() throws Exception{
        CameraModel cam = getCamera();

        int sizeBefore = DAL.getCameras().size();

        DAL.save(cam);

        int sizeAfter = DAL.getCameras().size();

        assertTrue("Size of Camera Collection must increase by one after insertion",sizeAfter == (sizeBefore+1));

        DAL.deleteCamera(cam.getID());
    }

    @Test
    public void deleteCamera() throws Exception{
        CameraModel cam = getCamera();

        int sizeBefore = DAL.getCameras().size();
        DAL.save(cam);
        DAL.deleteCamera(cam.getID());
        int sizeAfter = DAL.getCameras().size();

        assertTrue("Size of Camera Collection must be back to before",sizeAfter == sizeBefore);
    }

    @Test
    public void everySavedCameraGetsDifferentID() throws Exception{
        CameraModel cam = getCamera();
        CameraModel cam2 = getCamera();
        CameraModel cam3 = getCamera();
        int sizeBefore = DAL.getCameras().size();
        DAL.save(cam);
        DAL.save(cam2);
        DAL.save(cam3);
        int sizeAfter = DAL.getCameras().size();
        assertTrue("Size of Camera Collection must increase by one after insertion",sizeAfter == (sizeBefore+3));

        assertTrue("Cameras ids aren't 0 after insertion", (cam.getID() != 0 && cam2.getID() != 0 && cam3.getID() != 0));
        assertTrue("Cameras ids must differ", (cam.getID() != cam2.getID()) && (cam.getID() != cam3.getID()) && (cam2.getID() != cam3.getID()));

        DAL.deleteCamera(cam.getID());
        DAL.deleteCamera(cam2.getID());
        DAL.deleteCamera(cam3.getID());
    }

    @Test
    public void savePhotographer() throws Exception{
        PhotographerModel cam = getPhotographer();

        int sizeBefore = DAL.getPhotographers().size();

        DAL.save(cam);

        int sizeAfter = DAL.getPhotographers().size();

        assertTrue("Size of Photographer Collection must increase by one after insertion",sizeAfter == (sizeBefore+1));

        DAL.deletePhotographer(cam.getID());
    }

    @Test
    public void deletePhotographer() throws Exception{
        PhotographerModel cam = getPhotographer();

        int sizeBefore = DAL.getPhotographers().size();
        DAL.save(cam);
        DAL.deletePhotographer(cam.getID());
        int sizeAfter = DAL.getPhotographers().size();

        assertTrue("Size of Photographer Collection must be back to before",sizeAfter == (sizeBefore));
    }

    @Test
    public void everySavedPhotographerGetsDifferentID() throws Exception{
        PhotographerModel cam = getPhotographer();
        PhotographerModel cam2 = getPhotographer();
        PhotographerModel cam3 = getPhotographer();
        int sizeBefore = DAL.getPhotographers().size();
        DAL.save(cam);
        DAL.save(cam2);
        DAL.save(cam3);
        int sizeAfter = DAL.getPhotographers().size();
        assertTrue("Size of Photographer Collection must increase by one after insertion",sizeAfter == (sizeBefore+3));

        assertTrue("Photographers ids aren't 0 after insertion", (cam.getID() != 0 && cam2.getID() != 0 && cam3.getID() != 0));
        assertTrue("Photographers ids must differ", (cam.getID() != cam2.getID()) && (cam.getID() != cam3.getID()) && (cam2.getID() != cam3.getID()));

        DAL.deletePhotographer(cam.getID());
        DAL.deletePhotographer(cam2.getID());
        DAL.deletePhotographer(cam3.getID());
    }

    private CameraModel getCamera(){
        CameraModel cam = new CameraModelImpl();
        cam.setISOLimitAcceptable(450.55);
        cam.setISOLimitGood(243);
        cam.setBoughtOn(LocalDate.now());
        cam.setMake("Test");
        cam.setProducer("Test");
        cam.setNotes("losdlsoadlsaoda");

        return cam;
    }

    private PhotographerModel getPhotographer(){
        PhotographerModel phot = new PhotographerModelImpl();
        phot.setFirstName("Test");
        phot.setLastName("Test");
        phot.setBirthDay(LocalDate.parse("2005-10-10"));
        phot.setNotes("fdfsdfdsfsd");

        return phot;
    }

}
