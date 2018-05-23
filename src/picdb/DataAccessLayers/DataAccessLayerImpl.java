package picdb.DataAccessLayers;

import BIF.SWE2.interfaces.DataAccessLayer;
import BIF.SWE2.interfaces.ExposurePrograms;
import BIF.SWE2.interfaces.models.*;
import picdb.models.*;
import BIF.SWE2.interfaces.models.PictureModel;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by if16b014 on 05.03.18.
 */
public class DataAccessLayerImpl implements DataAccessLayer {

    private Connection con;
    private int conCounter = 0;

    public DataAccessLayerImpl(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
        }
    }

    @Override
    public Collection<PictureModel> getPictures(String namePart, PhotographerModel photographerModel, IPTCModel iptcModel, EXIFModel exifModel) throws Exception {
        ArrayList<PictureModel> myPics = new ArrayList<PictureModel>();

        boolean namePartSet = false;
        boolean photModelIdSet = false;
        boolean iptcModelSet = false;
        boolean exifModelSet = false;
        int counter = 1;

        String selectSQL = "SELECT pic.id as picid, pic.filename, pic.cameraid, pic.iptckeywords, " +
                "pic.iptccopyright, pic.iptcheadline, pic.iptccaption, pic.exifaperture, " +
                "pic.exifexposuretime, pic.exifiso, pic.exifflash, pic.exifexposureprog, " +
                "phot.id as photid, phot.name, phot.surname, phot.birthdate, phot.notes, cam.model " +
                "FROM picture pic LEFT JOIN photographer phot ON pic.photographerid = phot.id " +
                "LEFT JOIN camera cam ON pic.cameraid = cam.id WHERE ";

        if(namePart != null && !namePart.isEmpty()){
            selectSQL += "pic.filename LIKE ? "; //? = namePart
            namePartSet = true;
        }

        if(photographerModel != null){
            if(namePartSet){
                selectSQL += "AND ";
            }
            selectSQL += "phot.id = ?"; //? = photographerModel.getid();
            photModelIdSet = true;
        }

        if(iptcModel != null){
            if(namePartSet || photModelIdSet){
                selectSQL += "AND ";
            }
            selectSQL += "pic.iptckeywords LIKE ? " + // ? = getKeywords
                    "AND pic.iptccopyright LIKE ? " + // ? = getCopyrightNotice
                    "AND pic.iptcheadline LIKE ? " + // ? = getHeadline
                    "AND pic.iptccaption LIKE ? ";// ? = getCaption
            iptcModelSet = true;
        }

        if(exifModel != null){
            if(namePartSet || photModelIdSet || iptcModelSet){
                selectSQL += "AND ";
            }
            selectSQL += "round(pic.exifaperture::numeric,2) = ? " + // ? = getFNumber
                    "AND round(pic.exifexposuretime::numeric,2) = ? " + // ? = getExposureTime
                    "AND round(pic.exifiso::numeric,2) = ? " + // ? = getISOValue
                    "AND pic.exifflash = ? " + // ? = getFlash
                    "AND pic.exifexposureprog = ? "; // ? = getExposureProgram
            exifModelSet = true;
        }

        ResultSet rs;

        if(!namePartSet && !photModelIdSet && !iptcModelSet && !exifModelSet){ //if nothing was set return whole list
            selectSQL += " 1 = ? ";
            PreparedStatement preparedStatement = openConnection().prepareStatement(selectSQL);
            preparedStatement.setInt(1, 1);
            rs = preparedStatement.executeQuery();

        }else{
            PreparedStatement preparedStatement = openConnection().prepareStatement(selectSQL);
            if(namePartSet){
                preparedStatement.setString(counter, "%" + namePart + "%" );
                counter ++;
            }
            if(photModelIdSet){
                preparedStatement.setInt(counter, photographerModel.getID());
                counter ++;
            }
            if(iptcModelSet){
                preparedStatement.setString(counter, iptcModel.getKeywords());
                counter ++;
                preparedStatement.setString(counter, iptcModel.getCopyrightNotice());
                counter ++;
                preparedStatement.setString(counter, iptcModel.getHeadline());
                counter ++;
                preparedStatement.setString(counter, iptcModel.getCaption());
                counter ++;
            }
            if(exifModelSet){
                preparedStatement.setDouble(counter, new BigDecimal(Double.toString(exifModel.getFNumber())).setScale(2, RoundingMode.HALF_UP).doubleValue());
                counter ++;
                preparedStatement.setDouble(counter, new BigDecimal(Double.toString(exifModel.getExposureTime())).setScale(2, RoundingMode.HALF_UP).doubleValue());
                counter ++;
                preparedStatement.setDouble(counter, new BigDecimal(Double.toString(exifModel.getISOValue())).setScale(2, RoundingMode.HALF_UP).doubleValue());
                counter ++;
                preparedStatement.setBoolean(counter, exifModel.getFlash());
                counter ++;
                preparedStatement.setInt(counter, exifModel.getExposureProgram().getValue());
            }

            rs = preparedStatement.executeQuery();
        }

        while(rs.next()){
            EXIFModelImpl exif = new EXIFModelImpl();
            exif.setExposureProgram(ExposurePrograms.values()[rs.getInt("exifexposureprog")]);
            exif.setExposureTime(rs.getDouble("exifexposuretime"));
            exif.setFlash(rs.getBoolean("exifflash"));
            exif.setFNumber(rs.getDouble("exifaperture"));
            exif.setISOValue(rs.getDouble("exifiso"));
            exif.setMake(rs.getString("model"));

            IPTCModelImpl iptc = new IPTCModelImpl();
            iptc.setByLine(rs.getString("name") + " " + rs.getString("surname"));
            iptc.setCaption(rs.getString("iptccaption"));
            iptc.setCopyrightNotice(rs.getString("iptccopyright"));
            iptc.setHeadline(rs.getString("iptcheadline"));
            iptc.setKeywords(rs.getString("iptckeywords"));

            CameraModelImpl cam = (CameraModelImpl) getCamera(rs.getInt("cameraid"));

            PhotographerModelImpl phot = (PhotographerModelImpl) getPhotographer(rs.getInt("photid"));

            PictureModelImpl pic = new PictureModelImpl();
            pic.setEXIF(exif);
            pic.setIPTC(iptc);
            pic.setPhotographer(phot);
            pic.setFileName(rs.getString("filename"));
            pic.setID(rs.getInt("picid"));
            pic.setCamera(cam);

            myPics.add(pic);
        }

        return myPics;
    }

    @Override
    public PictureModel getPicture(int i) throws Exception {

        String selectSQL = "SELECT id, filename, cameraid, iptckeywords, " +
                "iptccopyright, iptcheadline, iptccaption, exifaperture, " +
                "exifexposuretime, exifiso, exifflash, exifexposureprog, " +
                "photographerid FROM picture WHERE id = ?";
        PreparedStatement preparedStatement = openConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, i);
        ResultSet rs = preparedStatement.executeQuery();

        PictureModelImpl pic = null;
        if(rs.next()) {
            pic = new PictureModelImpl();
            pic.setID(i);
            pic.setFileName(rs.getString("filename"));
            pic.setCamera(getCamera(rs.getInt("cameraid")));
            //ToDo: set all values of models
            pic.setIPTC(new IPTCModelImpl());
            pic.setEXIF(new EXIFModelImpl());
            pic.setPhotographer(getPhotographer(rs.getInt("photographerid")));
        }

        return pic;
    }

    @Override
    public void save(PictureModel pictureModel1) throws Exception {
        PictureModelImpl pictureModel = (PictureModelImpl) pictureModel1;
        if(pictureModel.getID() <= 0){ //database integer only has positive values from 1 to 2147483647, if <=0 its a new picture
            String insertSQL = "INSERT INTO picture (id, filename, cameraid, iptckeywords, iptccopyright, iptcheadline, iptccaption, " +
                    "exifaperture, exifexposuretime, exifiso, exifflash, exifexposureprog, photographerid)" +
                    " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = openConnection().prepareStatement(insertSQL, new String[]{ "id" });
            preparedStatement.setString(1, pictureModel.getFileName());
            //using setobject from here on out because we can set null values without exception
            preparedStatement.setObject(2, pictureModel.getCamera() == null ? null : pictureModel.getCamera().getID());
            preparedStatement.setObject(3, pictureModel.getIPTC() == null ? null : pictureModel.getIPTC().getKeywords());
            preparedStatement.setObject(4, pictureModel.getIPTC() == null ? null : pictureModel.getIPTC().getCopyrightNotice());
            preparedStatement.setObject(5, pictureModel.getIPTC() == null ? null : pictureModel.getIPTC().getHeadline());
            preparedStatement.setObject(6, pictureModel.getIPTC() == null ? null : pictureModel.getIPTC().getCaption());
            preparedStatement.setObject(7, pictureModel.getEXIF() == null ? null : pictureModel.getEXIF().getFNumber());
            preparedStatement.setObject(8, pictureModel.getEXIF() == null ? null : pictureModel.getEXIF().getExposureTime());
            preparedStatement.setObject(9, pictureModel.getEXIF() == null ? null : pictureModel.getEXIF().getISOValue());
            preparedStatement.setObject(10, pictureModel.getEXIF() == null ? null : pictureModel.getEXIF().getFlash());
            preparedStatement.setObject(11, pictureModel.getEXIF() == null ? null : pictureModel.getEXIF().getExposureProgram().getValue());
            preparedStatement.setObject(12, pictureModel.getPhotographer() == null ? null : pictureModel.getPhotographer().getID());
            // execute insert SQL statement
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pictureModel.setID(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }else{

            String updateSQL = "UPDATE picture SET filename = ?, cameraid = ?, iptckeywords = ?, iptccopyright = ?, iptcheadline = ?, iptccaption = ?, " +
                    "exifaperture = ?, exifexposuretime = ?, exifiso = ?, exifflash = ?, exifexposureprog = ?, photographerid = ? WHERE id = ?";
            PreparedStatement preparedStatement = openConnection().prepareStatement(updateSQL);
            preparedStatement.setString(1, pictureModel.getFileName());
            //using setobject from here on out because we can set null values without exception
            preparedStatement.setObject(2, pictureModel.getCamera() == null ? null : pictureModel.getCamera().getID());
            preparedStatement.setObject(3, pictureModel.getIPTC() == null ? null : pictureModel.getIPTC().getKeywords());
            preparedStatement.setObject(4, pictureModel.getIPTC() == null ? null : pictureModel.getIPTC().getCopyrightNotice());
            preparedStatement.setObject(5, pictureModel.getIPTC() == null ? null : pictureModel.getIPTC().getHeadline());
            preparedStatement.setObject(6, pictureModel.getIPTC() == null ? null : pictureModel.getIPTC().getCaption());
            preparedStatement.setObject(7, pictureModel.getEXIF() == null ? null : pictureModel.getEXIF().getFNumber());
            preparedStatement.setObject(8, pictureModel.getEXIF() == null ? null : pictureModel.getEXIF().getExposureTime());
            preparedStatement.setObject(9, pictureModel.getEXIF() == null ? null : pictureModel.getEXIF().getISOValue());
            preparedStatement.setObject(10, pictureModel.getEXIF() == null ? null : pictureModel.getEXIF().getFlash());
            preparedStatement.setObject(11, pictureModel.getEXIF() == null ? null : pictureModel.getEXIF().getExposureProgram().getValue());
            preparedStatement.setObject(12, pictureModel.getPhotographer() == null ? null : pictureModel.getPhotographer().getID());
            //set id:
            preparedStatement.setInt(13, pictureModel.getID());
            // execute update SQL statement
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deletePicture(int i) throws Exception {
        String deleteSQL = "DELETE FROM picture WHERE id = ?";
        PreparedStatement preparedStatement = openConnection().prepareStatement(deleteSQL);
        preparedStatement.setInt(1, i);
        // execute delete SQL statement
        preparedStatement.executeUpdate();
    }

    @Override
    public Collection<PhotographerModel> getPhotographers() throws Exception {
        String selectSQL = "SELECT id, name, surname, birthdate, notes FROM photographer";
        PreparedStatement preparedStatement = openConnection().prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();

        Collection<PhotographerModel> phots = new ArrayList<>();

        while(rs.next()) {

            PhotographerModel phot = new PhotographerModelImpl();
            phot.setID(rs.getInt("id"));
            phot.setNotes(rs.getString("notes"));
            phot.setBirthDay(rs.getDate("birthdate") == null ? null :  rs.getDate("birthdate").toLocalDate());
            phot.setLastName(rs.getString("surname"));
            phot.setFirstName(rs.getString("name"));

            phots.add(phot);
        }

        return phots;
    }

    @Override
    public PhotographerModel getPhotographer(int i) throws Exception {
        String selectSQL = "SELECT id, name, surname, birthdate, notes FROM photographer WHERE id = ?";
        PreparedStatement preparedStatement = openConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, i);
        ResultSet rs = preparedStatement.executeQuery();

        PhotographerModel phot = null;
        if(rs.next()) {
            phot = new PhotographerModelImpl();
            phot.setID(i);
            phot.setNotes(rs.getString("notes"));
            phot.setBirthDay(rs.getDate("birthdate").toLocalDate());
            phot.setLastName(rs.getString("surname"));
            phot.setFirstName(rs.getString("name"));
        }

        return phot;
    }

    @Override
    public void save(PhotographerModel photographerModel) throws Exception {
        if(photographerModel.getID() <= 0){ //database integer only has positive values from 1 to 2147483647, if <=0 its a new photographer
            String insertSQL = "INSERT INTO photographer (id, name, surname, birthdate, notes)" +
                    " VALUES (DEFAULT, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = openConnection().prepareStatement(insertSQL, new String[]{ "id" });
            preparedStatement.setObject(1, photographerModel.getFirstName());
            preparedStatement.setString(2, photographerModel.getLastName());
            preparedStatement.setObject(3, photographerModel.getBirthDay() == null ? null : Date.valueOf(photographerModel.getBirthDay()));
            preparedStatement.setObject(4, photographerModel.getNotes());
            // execute insert SQL stetement

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    photographerModel.setID(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }else{
            String updateSQL = "UPDATE photographer SET name = ?, surname = ?, birthdate = ?, notes = ? WHERE id = ?";
            PreparedStatement preparedStatement = openConnection().prepareStatement(updateSQL);
            preparedStatement.setObject(1, photographerModel.getFirstName());
            preparedStatement.setString(2, photographerModel.getLastName());
            preparedStatement.setObject(3, photographerModel.getBirthDay() == null ? null : Date.valueOf(photographerModel.getBirthDay()));
            preparedStatement.setObject(4, photographerModel.getNotes());
            preparedStatement.setInt(5, photographerModel.getID());
            // execute update SQL stetement
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public void deletePhotographer(int i) throws Exception {
        String deleteSQL = "DELETE photographer WHERE id = ?";
        PreparedStatement preparedStatement = openConnection().prepareStatement(deleteSQL);
        preparedStatement.setInt(1, i);
        // execute delete SQL stetement
        preparedStatement.executeUpdate();
    }

    @Override
    public Collection<CameraModel> getCameras() {
        String selectSQL = "SELECT id, producer, model, purchasedate, " +
                "notes, isolimitgood, isolimitacceptable FROM camera";

        PreparedStatement preparedStatement;
        ResultSet rs;

        Collection<CameraModel> cams = new ArrayList<>();

        try {
            preparedStatement = openConnection().prepareStatement(selectSQL);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                CameraModelImpl cam = new CameraModelImpl();

                cam.setMake(rs.getString("model"));
                cam.setProducer(rs.getString("producer"));
                cam.setBoughtOn(rs.getDate("purchasedate").toLocalDate());
                cam.setID(rs.getInt("id"));
                cam.setISOLimitAcceptable(rs.getDouble("isolimitacceptable"));
                cam.setISOLimitGood(rs.getDouble("isolimitgood"));
                cam.setNotes(rs.getString("notes"));

                cams.add(cam);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cams;
    }

    @Override
    public CameraModel getCamera(int i) {

        String selectSQL = "SELECT id, producer, model, purchasedate, " +
                "notes, isolimitgood, isolimitacceptable FROM camera WHERE id = ?";

        PreparedStatement preparedStatement;
        ResultSet rs;
        CameraModelImpl cam = new CameraModelImpl();


        try {
            preparedStatement = openConnection().prepareStatement(selectSQL);
            preparedStatement.setInt(1, i);
            rs = preparedStatement.executeQuery();

            if(rs.next()){
                cam.setMake(rs.getString("model"));
                cam.setProducer(rs.getString("producer"));
                cam.setBoughtOn(rs.getDate("purchasedate").toLocalDate());
                cam.setID(rs.getInt("id"));
                cam.setISOLimitAcceptable(rs.getDouble("isolimitacceptable"));
                cam.setISOLimitGood(rs.getDouble("isolimitgood"));
                cam.setNotes(rs.getString("notes"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cam;
    }

    private Connection openConnection() throws Exception{ //ToDo: get a more efficient way of getting a connection that refreshes with every new command but nor every new statement
        try {
            if(con == null || conCounter >= 100){ // reset connection only every 100th try, this takes too long ^^
                con = DriverManager.getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/imgDB", "postgres",
                        "postgres");
                conCounter = 0;
            }
            conCounter ++;
            return con;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Couldn't get a connection to Database");
    }
}
