package picdb.DataAccessLayers;

import BIF.SWE2.interfaces.DataAccessLayer;
import BIF.SWE2.interfaces.ExposurePrograms;
import BIF.SWE2.interfaces.models.*;
import picdb.models.CameraModelImpl;
import picdb.models.EXIFModelImpl;
import picdb.models.IPTCModelImpl;
import picdb.models.PictureModelImpl;
import BIF.SWE2.interfaces.models.PictureModel;

import javax.swing.*;
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

        String selectSQL = "SELECT pic.id, pic.filename, pic.cameraid, pic.iptckeywords, " +
                "pic.iptccopyright, pic.iptcheadline, pic.iptccaption, pic.exifaperture, " +
                "pic.exifexposuretime, pic.exifiso, pic.exifflash, pic.exifexposureprog, " +
                "phot.id, phot.name, phot.surname, phot.birthdate, phot.notes, cam.model " +
                "FROM picture pic LEFT JOIN photographer phot ON pic.photographerid = phot.id " +
                "LEFT JOIN camera cam ON pic.cameraid = cam.id WHERE ";

        if(namePart != null && !namePart.isEmpty()){
            selectSQL += "concat_ws(' ', phot.name::text, phot.surname::text) LIKE ? "; //? = namePart
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
            selectSQL += "pic.iptckeywords = ? " + // ? = getKeywords -> ToDo: Sollte auch mit "LIKE" sein :D
                    "AND pic.iptccopyright = ? " + // ? = getCopyrightNotice
                    "AND pic.iptcheadline = ? " + // ? = getHeadline
                    "AND pic.iptccaption = ? " + // ? = getCaption
                    "AND concat_ws(' ', phot.name::text, phot.surname::text) LIKE ? "; // ? = getByLine
            iptcModelSet = true;
        }

        if(exifModel != null){
            if(namePartSet || photModelIdSet || iptcModelSet){
                selectSQL += "AND ";
            }
            selectSQL += "pic.exifaperture = ? " + // ? = getFNumber
                    "AND pic.exifexposuretime = ? " + // ? = getExposureTime
                    "AND pic.exifiso = ? " + // ? = getISOValue
                    "AND pic.exifflash = ? " + // ? = getFlash
                    "AND cam.model = ? " + // ? = getMake
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
                preparedStatement.setString(counter, "%" +  iptcModel.getByLine() + "%");
                counter ++;
            }
            if(exifModelSet){
                preparedStatement.setDouble(counter, exifModel.getFNumber());
                counter ++;
                preparedStatement.setDouble(counter, exifModel.getExposureTime());
                counter ++;
                preparedStatement.setDouble(counter, exifModel.getISOValue());
                counter ++;
                preparedStatement.setBoolean(counter, exifModel.getFlash());
                counter ++;
                preparedStatement.setString(counter, exifModel.getMake());
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

            PictureModelImpl pic = new PictureModelImpl();
            pic.setCamera(cam);
            pic.setEXIF(exif);
            pic.setIPTC(iptc);
            pic.setFileName(rs.getString("filename"));
            pic.setID(rs.getInt("id"));

            myPics.add(pic);
        }

        return myPics;
    }

    @Override
    public PictureModel getPicture(int i) throws Exception {

        String selectSQL = "SELECT id, filename, cameraid, iptckeywords, " +
                "iptccopyright, iptcheadline, iptccaption, exifaperture, " +
                "exifexposuretime, exifiso, exifflash, exifexposureprog FROM picture WHERE id = ?";
        PreparedStatement preparedStatement = openConnection().prepareStatement(selectSQL);
        preparedStatement.setInt(1, i);
        ResultSet rs = preparedStatement.executeQuery(selectSQL);

        rs.next();
        PictureModelImpl pic = new PictureModelImpl();
        //ToDo: set all values of models

        return pic;
    }

    @Override
    public void save(PictureModel pictureModel) throws Exception {
        if(pictureModel.getID() <= 0){ //database integer only has positive values from 1 to 2147483647, if <=0 its a new picture
            String insertSQL = "INSERT INTO picture (id, filename, cameraid, iptckeywords, iptccopyright, iptcheadline, iptccaption, " +
                    "exifaperture, exifexposuretime, exifiso, exifflash, exifexposureprog, photographerid)" +
                    " VALUES (DEFAULT, ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)";
            PreparedStatement preparedStatement = openConnection().prepareStatement(insertSQL);
            preparedStatement.setString(1, pictureModel.getFileName());
            // execute insert SQL statement
            preparedStatement.executeUpdate();
        }else{ //ToDo: This!!!!
            /*String updateSQL = "UPDATE picture SET name = ?, surname = ?, birthdate = ?, notes = ? WHERE id = ?";
            PreparedStatement preparedStatement = openConnection().prepareStatement(updateSQL);
            //preparedStatement.setString(1, photographerModel.getFirstName());
            //preparedStatement.setString(2, photographerModel.getLastName());
            //preparedStatement.setDate(3, Date.valueOf(photographerModel.getBirthDay()));
           // preparedStatement.setString(4, photographerModel.getNotes());
            //preparedStatement.setInt(4, photographerModel.getID());
            // execute update SQL statement
            preparedStatement.executeUpdate();*/
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
        return null;
    }

    @Override
    public PhotographerModel getPhotographer(int i) throws Exception {
        return null;
    }

    @Override
    public void save(PhotographerModel photographerModel) throws Exception {
        if(photographerModel.getID() <= 0){ //database integer only has positive values from 1 to 2147483647, if <=0 its a new photographer
            String insertSQL = "INSERT INTO photographer (id, name, surname, birthdate, notes)" +
                    " VALUES (DEFAULT, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = openConnection().prepareStatement(insertSQL);
            preparedStatement.setString(1, photographerModel.getFirstName());
            preparedStatement.setString(2, photographerModel.getLastName());
            preparedStatement.setDate(3, Date.valueOf(photographerModel.getBirthDay()));
            preparedStatement.setString(4, photographerModel.getNotes());
            // execute insert SQL stetement
            preparedStatement.executeUpdate();
        }else{
            String updateSQL = "UPDATE photographer SET name = ?, surname = ?, birthdate = ?, notes = ? WHERE id = ?";
            PreparedStatement preparedStatement = openConnection().prepareStatement(updateSQL);
            preparedStatement.setString(1, photographerModel.getFirstName());
            preparedStatement.setString(2, photographerModel.getLastName());
            preparedStatement.setDate(3, Date.valueOf(photographerModel.getBirthDay()));
            preparedStatement.setString(4, photographerModel.getNotes());
            preparedStatement.setInt(4, photographerModel.getID());
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
        return null;
    }

    @Override
    public CameraModel getCamera(int i) {

        String selectSQL = "SELECT id, producer, model, purchasedate, " +
                "notes, isolimitgood, isolimitacceptable FROM camera WHERE id = ?";

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
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
