package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigBuilder {
	public void makeConfigFile(String name, String adress) {
		try {
			FileWriter fileConfigWriter = new FileWriter("config.txt");
			fileConfigWriter.write(name + "/" + adress);
			fileConfigWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String nameFromConfig() {
		String name = null;
		File configFile = new File("config.txt");
		FileReader fileReader;
		try {
			fileReader = new FileReader(configFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
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
	
	public String adressFromConfig() {
		String adress = null;
		File configFile = new File("config.txt");
		FileReader fileReader;
		try {
			fileReader = new FileReader(configFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while((line = bufferedReader.readLine()) != null) {
				int beginIndex = line.indexOf("/");
				adress = line.substring(beginIndex + 1);
			}
			bufferedReader.close();			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adress;
	}
}
