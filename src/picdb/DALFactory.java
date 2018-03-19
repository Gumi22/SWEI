package picdb;

import BIF.SWE2.interfaces.DataAccessLayer;
import picdb.DataAccessLayers.DataAccessLayerImpl;
import picdb.DataAccessLayers.DataAccessLayerMockImpl;

public class DALFactory {

    private static DALFactory instance;
    private static boolean databaseAccessible;

    public static DALFactory getInstance () {
        if (DALFactory.instance == null) {
            DALFactory.instance = new DALFactory();
        }
        return DALFactory.instance;
    }

    public static void setDatabaseAccessible(boolean databaseAccessible) {
        DALFactory.databaseAccessible = databaseAccessible;
    }

    public DataAccessLayer getDAL(){
        //if(!DALFactory.databaseAccessible){
            return DataAccessLayerMockImpl.getInstance();
        //}
        //else{
        //    return DataAccessLayerImpl.getInstance();
        //}
    }
}
