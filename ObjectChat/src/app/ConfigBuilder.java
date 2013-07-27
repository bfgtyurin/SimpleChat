package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigBuilder {
    private static final String CONFIG_PATH = "config.txt";
	public void makeConfigFile(String name, String address) {
		try {
			FileWriter fileConfigWriter = new FileWriter(CONFIG_PATH);
			fileConfigWriter.write(name + "/" + address);
			fileConfigWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String nameFromConfig() {
		String name = null;
		File configFile = new File(CONFIG_PATH);
		FileReader fileReader;
		try {
			fileReader = new FileReader(configFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while((line = bufferedReader.readLine()) != null) {
				int endIndex = line.indexOf("/");
				name = line.substring(0, endIndex);
			}
			bufferedReader.close();			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return name;
	}
	
	public String addressFromConfig() {
		String address = null;
		File configFile = new File(CONFIG_PATH);
		FileReader fileReader;
		try {
			fileReader = new FileReader(configFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while((line = bufferedReader.readLine()) != null) {
				int beginIndex = line.indexOf("/");
				address = line.substring(beginIndex + 1);
			}
			bufferedReader.close();			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
	}
}
