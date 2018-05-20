package test;

import BIF.SWE2.interfaces.ExposurePrograms;
import picdb.DataAccessLayers.DataAccessLayerImpl;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import picdb.models.EXIFModelImpl;
import picdb.models.IPTCModelImpl;
import picdb.models.PhotographerModelImpl;

public class DataAccessLayerImplTest {

    DataAccessLayerImpl DAL;

    @Before
    public void setUp() {
        DAL = new DataAccessLayerImpl();
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
        assertEquals(4, DAL.getPictures(null, null, null, EXIF).size());

        EXIF.setExposureTime(0.690946546174846);
        EXIF.setISOValue(7019.35525455328);
        EXIF.setFNumber(0.832433887917029);
        EXIF.setFlash(true);
        EXIF.setExposureProgram(ExposurePrograms.values()[1]);
        assertEquals(2, DAL.getPictures(null, null, null, EXIF).size());
    }

}
