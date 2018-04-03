package picdb;

import BIF.SWE2.interfaces.DataAccessLayer;
import picdb.DataAccessLayers.DataAccessLayerImpl;
import picdb.DataAccessLayers.DataAccessLayerMockImpl;

public class DALFactory {

    private static DALFactory instance;
    private static boolean databaseAccessible;
    private static DataAccessLayer activeDAL;
    private static DataAccessLayer activeMockDAL;

    public static DALFactory getInstance (boolean DBAccessible) {
        if (DALFactory.instance == null) {
            DALFactory.instance = new DALFactory();
        }
        databaseAccessible = DBAccessible;
        return DALFactory.instance;
    }

    public DataAccessLayer getDAL(){
        if(!DALFactory.databaseAccessible){
            if(activeMockDAL == null){
                activeMockDAL = new DataAccessLayerMockImpl();
            }
            return activeMockDAL;
        }
        else{
            if(activeDAL == null){
                activeDAL = new DataAccessLayerImpl();
            }
            return activeDAL;
        }
    }
}
