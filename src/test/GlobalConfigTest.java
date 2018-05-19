package test;

import static org.junit.Assert.*;

import org.junit.Test;
import picdb.GlobalConfig;

public class GlobalConfigTest {


    @Test
    public void globalConfigDefaultSet() throws Exception {
        assertNotNull(GlobalConfig.getInstance("E:\\FH\\4_Semester\\SWEI\\SWE2-Java\\src\\test\\config.txt").getPath());
        assertTrue(!GlobalConfig.getInstance("E:\\FH\\4_Semester\\SWEI\\SWE2-Java\\src\\test\\config.txt").isTestingMode());
    }

    @Test
    public void globalConfigPathSet() throws Exception {
        assertNotNull(GlobalConfig.getInstance("E:\\FH\\4_Semester\\SWEI\\SWE2-Java\\src\\test\\config.txt").getPath());
        assertEquals("Pictures", GlobalConfig.getInstance("E:\\FH\\4_Semester\\SWEI\\SWE2-Java\\src\\test\\config.txt").getPath());
    }

    @Test
    public void globalConfigTestingSetToFalse() throws Exception {
        assertTrue(!GlobalConfig.getInstance("E:\\FH\\4_Semester\\SWEI\\SWE2-Java\\src\\test\\config.txt").isTestingMode());
    }

}
