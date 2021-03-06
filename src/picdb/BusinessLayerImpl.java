package picdb;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.DataAccessLayer;
import BIF.SWE2.interfaces.ExposurePrograms;
import BIF.SWE2.interfaces.models.*;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.UnitValue;
import javafx.util.Pair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.CosNaming.NamingContextPackage.NotFoundReason;
import picdb.DataAccessLayers.DataAccessLayerImpl;
import picdb.models.EXIFModelImpl;
import picdb.models.IPTCModelImpl;

import picdb.models.PictureModelImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Objects;

import com.itextpdf.layout.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by if16b014 on 05.03.18.
 */
public class BusinessLayerImpl implements BusinessLayer {

    private static BusinessLayerImpl instance;
    private static DataAccessLayer myDAL;
    private static boolean testingMode = true;
    private static String path;

    private static final Logger logger = LogManager.getRootLogger();

    private BusinessLayerImpl() {
    }

    public static BusinessLayerImpl getInstance(String path, boolean Testing) {
        if (BusinessLayerImpl.instance == null) {
            BusinessLayerImpl.instance = new BusinessLayerImpl();
        }

        testingMode = Testing;
        BusinessLayerImpl.path = path;

        myDAL = DALFactory.getInstance(!testingMode).getDAL();

        return BusinessLayerImpl.instance;
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
        Collection<PictureModel> pics = myDAL.getPictures(s, photographerModel, iptcModel, exifModel);
        if (testingMode && Objects.equals(s, "blume")) {
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
        myDAL.save(pictureModel);
    }

    @Override
    public void deletePicture(int i) throws Exception {
        myDAL.deletePicture(i);
    }

    @Override
    public void sync() throws Exception {
        Collection<PictureModel> DBPics = myDAL.getPictures(null, null, null, null);

        File folder = new File(path);
        //System.out.println(folder.getAbsolutePath());
        File[] listOfFiles = folder.listFiles();

        //Add new Pictures
        for (File listOfFile : listOfFiles != null ? listOfFiles : new File[0]) {
            boolean alreadySaved = false;
            if (listOfFile.isFile()) {
                for (PictureModel pm : DBPics) {
                    if (pm.getFileName().equals(listOfFile.getName())) {
                        alreadySaved = true;
                    }
                }
                if (!alreadySaved) {
                    save(new PictureModelImpl(listOfFile.getName()));
                }
            }
        }

        //Delete old Pictures in DB
        DBPics = myDAL.getPictures(null, null, null, null);
        for (PictureModel pm : DBPics) {
            boolean onDisk = false;
            for (File listOfFile : listOfFiles != null ? listOfFiles : new File[0]) {
                if (pm.getFileName().equals(listOfFile.getName())) {
                    onDisk = true;
                }
            }
            if (!onDisk) {
                myDAL.deletePicture(pm.getID());
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
        myDAL.save(photographerModel);
    }

    @Override
    public void deletePhotographer(int i) throws Exception {
        myDAL.deletePhotographer(i);
    }

    @Override
    public IPTCModel extractIPTC(String s) throws Exception {
        if (testingMode) {
            IPTCModelImpl iptc = new IPTCModelImpl();
            iptc.setCaption("lel");
            iptc.setHeadline("test");
            iptc.setCopyrightNotice("copyright: gnu licence");
            iptc.setByLine("Me");
            iptc.setKeywords("lol, Me, test, witzig");
            Collection<PictureModel> pics = myDAL.getPictures(s, null, null, null);
            if (pics.size() == 1) {
                return iptc;
            } else {
                throw new NotFound("No such Picture: " + s, NotFoundReason.not_object, new NameComponent[1]);
            }
            //return iptc;
        } else return null;
    }

    @Override
    public EXIFModel extractEXIF(String s) throws Exception {
        if (testingMode) {
            EXIFModelImpl exif = new EXIFModelImpl("123", 1f, 1f, 1f, true, ExposurePrograms.LandscapeMode);
            Collection<PictureModel> pics = myDAL.getPictures(s, null, null, null);
            if (pics.size() >= 1) {
                return exif;
            } else {
                throw new NotFound("No such Picture: " + s, NotFoundReason.not_object, new NameComponent[1]);
            }
        } else return null;
    }

    @Override
    public void writeIPTC(String s, IPTCModel iptcModel) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public Collection<CameraModel> getCameras() {
        return myDAL.getCameras();
    }

    @Override
    public CameraModel getCamera(int i) {
        return myDAL.getCamera(i);
    }

    public void save(CameraModel cameraModel) throws Exception {
        ((DataAccessLayerImpl)myDAL).save(cameraModel);
    }

    public void deleteCamera(int i) throws Exception{
        ((DataAccessLayerImpl)myDAL).deleteCamera(i);
    }

    public void writeTagsPDF(String path) throws FileNotFoundException {
        Collection<Pair<String, Integer>> tags = ((DataAccessLayerImpl) myDAL).getTags();
        Document doc = getPDFDocument(path + "/Tags.pdf");

        doc.add(new Paragraph().add(new Text("Tags:")));

        Table tagTable = new Table(new float[]{4, 1});
        tagTable.setWidth(UnitValue.createPercentValue(100));

        tagTable.addHeaderCell("Tag");
        tagTable.addHeaderCell("Count");

        for (Pair<String, Integer> p : tags) {
            tagTable.addCell(p.getKey());
            tagTable.addCell(p.getValue().toString());
        }

        doc.add(tagTable);
        doc.close();

    }

    private Document getPDFDocument(String fullFileName) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(fullFileName);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);
        doc.setMargins(20, 20, 20, 20);
        return doc;
    }

    public void writePicturePDF(String path, PictureModel pic) throws FileNotFoundException {
        Document doc = getPDFDocument(path + "/" + pic.getFileName().split("\\.")[0] + ".pdf");

        DottedLine  customLine= new DottedLine();
        customLine.setGap(7);
        customLine.setLineWidth(2);

        try {
            doc.add(new Paragraph().add(new Text("Bild: " + pic.getFileName() + " [" + pic.getID() + "]").setBold()));

            doc.add(new LineSeparator(customLine));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            doc.add(new Paragraph().add(new Image(ImageDataFactory.create(GlobalConfig.getInstance().getPath() + "/" + pic.getFileName())).setAutoScale(true)));

            doc.add(new LineSeparator(customLine));
        } catch (Exception e) {
            doc.add(new Paragraph("Error loading Image"));
            doc.add(new LineSeparator(customLine));
            e.printStackTrace();
        }
        try {
            Paragraph iptc = new Paragraph().add(new Text("IPTC Infos:")).add("\n");
            iptc.add(new Text("Headline: " + pic.getIPTC().getHeadline())).add("\n");
            iptc.add(new Text("Caption: " + pic.getIPTC().getCaption())).add("\n");
            iptc.add(new Text("Keywords: " + pic.getIPTC().getKeywords())).add("\n");
            iptc.add(new Text("By: " + pic.getIPTC().getByLine())).add("\n");
            iptc.add(new Text("Copyright: " + pic.getIPTC().getCopyrightNotice())).add("\n");
            doc.add(iptc);

            doc.add(new LineSeparator(customLine));
        } catch (Exception e) {
            doc.add(new Paragraph("Error Parsing IPTC Informations"));
            doc.add(new LineSeparator(customLine));
            e.printStackTrace();
        }
        try {
            Paragraph exif = new Paragraph().add(new Text("EXIF Infos:")).add("\n");
            exif.add(new Text("Make: " + ((pic.getEXIF().getMake() == null)? "not set" : pic.getEXIF().getMake()))).add("\n");
            exif.add(new Text("ISO: " + pic.getEXIF().getISOValue())).add("\n");
            exif.add(new Text("Exposure Time: " + pic.getEXIF().getExposureTime())).add("\n");
            exif.add(new Text("F-Number: " + pic.getEXIF().getFNumber())).add("\n");
            exif.add(new Text("Flash: " + pic.getEXIF().getFlash())).add("\n");
            exif.add(new Text("Exposure Program: " + pic.getEXIF().getExposureProgram())).add("\n");
            doc.add(exif);

            doc.add(new LineSeparator(customLine));
        } catch (Exception e) {
            doc.add(new Paragraph("Error Parsing EXIF Informations"));
            doc.add(new LineSeparator(customLine));
            e.printStackTrace();
        }
        try {
            doc.add(new Paragraph().add(new Text("Camera: " + pic.getCamera().getProducer().trim() + " " + pic.getCamera().getMake().trim() + " [" + pic.getCamera().getID() + "]")));
        } catch (Exception e) {
            doc.add(new Paragraph().add(new Text("Camera not set")));
            e.printStackTrace();
        }
        try {
            doc.add(new Paragraph().add(new Text("Photographer: " + ((PictureModelImpl)pic).getPhotographer().getFirstName() + " " + ((PictureModelImpl)pic).getPhotographer().getLastName() + " [" + ((PictureModelImpl)pic).getPhotographer().getID() + "]")));
        } catch (Exception e) {
            doc.add(new Paragraph().add(new Text("Photographer not set")));
            e.printStackTrace();
        }

        doc.close();
    }
}
