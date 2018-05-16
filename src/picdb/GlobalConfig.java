package picdb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GlobalConfig {

    private static GlobalConfig instance;

    private Map<String, String> configs;

    private GlobalConfig(){
        configs = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("config.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\\s+"," "); //remove unnecessary whitespaces
                String[] splittedline = line.split("(: |:)");
                if(splittedline.length == 2){
                    configs.put(splittedline[0].toLowerCase(), splittedline[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //path = "Pictures";
        //testingMode = false;
    }

    public static GlobalConfig getInstance() {
        if (GlobalConfig.instance == null) {
            GlobalConfig.instance = new GlobalConfig();
        }
        return GlobalConfig.instance;
    }

    public String getPath(){
        return configs.get("path");
    }

    public boolean isTestingMode() {
        return Boolean.parseBoolean(configs.get("testing"));
    }
}
