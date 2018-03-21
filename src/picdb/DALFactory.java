package picdb;

import BIF.SWE2.interfaces.DataAccessLayer;
import picdb.DataAccessLayers.DataAccessLayerImpl;
import picdb.DataAccessLayers.DataAccessLayerMockImpl;

public class DALFactory {

    private static DALFactory instance;
    private static boolean databaseAccessible;

    public static DALFactory getInstance (boolean DBAccessible) {
        if (DALFactory.instance == null) {
            DALFactory.instance = new DALFactory();
        }
        databaseAccessible = DBAccessible;
        return DALFactory.instance;
    }

    public DataAccessLayer getDAL(){
        if(!DALFactory.databaseAccessible){
            return DataAccessLayerMockImpl.getInstance();
        }
        else{
            return DataAccessLayerImpl.getInstance();
        }
    }
}
