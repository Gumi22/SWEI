package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import picdb.GlobalConfig;

public class GlobalConfigTest {

    GlobalConfig GC;

    @Before
    public void setUp(){
        GC = GlobalConfig.getInstance("E:\\FH\\4_Semester\\SWEI\\SWE2-Java\\src\\test\\config.txt");
    }


    @Test
    public void globalConfigDefaultSet() throws Exception {
        assertNotNull(GC.getPath());
        assertTrue(!GC.isTestingMode());
    }

    @Test
    public void globalConfigPathSet() throws Exception {
        assertNotNull(GC.getPath());
        assertEquals("Pictures", GC.getPath());
    }

    @Test
    public void globalConfigTestingSetToFalse() throws Exception {
        assertTrue(!GC.isTestingMode());
    }

    @Test
    public void globalConfigDatabaseCredentialsSet() throws Exception {
        assertNotNull(GC.getValue("dbuser"));
        assertNotNull(GC.getValue("dbpassword"));
        assertEquals("Pictures", GC.getPath());
    }

    @Test
    public void globalConfigDatabaseUrlSet() throws Exception {
        assertNotNull(GC.getValue("dburl"));
        assertEquals("jdbc:postgresql://127.0.0.1:5432/imgDB", GC.getValue("dburl"));
    }

}
