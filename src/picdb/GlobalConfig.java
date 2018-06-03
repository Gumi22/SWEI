package picdb;

import org.apache.log4j.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GlobalConfig {

    private static GlobalConfig instance;

    private Map<String, String> configs;

    private static final Logger logger = LogManager.getLogger(GlobalConfig.class);

    private GlobalConfig(String path){
        configs = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\\s+"," "); //remove unnecessary whitespaces
                String[] splittedline = line.split("(: |:)");
                if(splittedline.length >= 2){
                    StringBuilder value = new StringBuilder(splittedline[1]);
                    for(int i = 2; i < splittedline.length; i++){
                        value.append(":").append(splittedline[i]);
                    }
                    configs.put(splittedline[0].toLowerCase(), value.toString());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GlobalConfig getInstance() {
        return getInstance("config.txt");
    }

    public static GlobalConfig getInstance(String path) {
        if (GlobalConfig.instance == null) {
            GlobalConfig.instance = new GlobalConfig(path);
            logger.info("Started Application with config: " + path);
        }
        return GlobalConfig.instance;
    }

    public String getPath(){
        return configs.get("path");
    }

    public boolean isTestingMode() {
        return Boolean.parseBoolean(configs.get("testing"));
    }

    public void setPath(String path){
        if(path != null && !path.trim().isEmpty()){
            configs.remove("path");
            configs.put("path", path);
        }
    }

    public String getValue(String key){
        return configs.get(key);
    }
}
