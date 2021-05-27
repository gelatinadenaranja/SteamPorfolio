package files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Properties;

import javax.swing.JOptionPane;

public class Settings {
	private File file;
	private FileInputStream file_input;
	private Properties setting;
	
	private int first_run;
	private int profit_mode;
	private int currency;
	
	public Settings() {
		setting = new Properties();
		
		file = new File(System.getProperty("user.home") + "\\SteamPortfolio\\Settings.donottouch");
		file.getParentFile().mkdir();
		
		try{
			if(file.createNewFile()) {
				write_defaults();
			}
			
			file_input = new FileInputStream(file.getAbsoluteFile());
			
			setting.load(file_input);
			
			load_setting_values();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Something happened with the settings file.\n" + e.toString());
		}
	}
	
	private void write_defaults() {
		FileWriter writer;
		try{
			writer = new FileWriter(file.getAbsoluteFile(), false);
			
			writer.write("first_run=0" + System.lineSeparator() +
					     "profit_mode=0" + System.lineSeparator() +
					     "currency=1");
			
			writer.close();
		}catch(IOException e){
			JOptionPane.showMessageDialog(null, "Error when writing default settings.\n" + e.toString());
		}
	}
	
	private void load_setting_values() {//Make one method to save settings too
		try{
			first_run = Integer.parseInt(setting.getProperty("first_run"));
			profit_mode = Integer.parseInt(setting.getProperty("profit_mode"));
			currency = Integer.parseInt(setting.getProperty("currency"));
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Why did you touch the settings?");
			write_defaults();
			
			//Reload input stream
			try{
				file_input = new FileInputStream(file.getAbsoluteFile());
				setting.load(file_input);
			} catch(IOException e1) {
				JOptionPane.showMessageDialog(null, "Error:" + e1.toString());
			}
			
			load_setting_values();
		}
	}
	
	public void create_directories() {
		File icon_folder = new File(System.getProperty("user.home") + "\\SteamPortfolio\\icons");
		try{
			icon_folder.mkdirs();
		} catch(SecurityException e) {
			JOptionPane.showMessageDialog(null, "Couldn't create the icon folder:\n" + e.toString());
		}
	}
	
	//Getters
	public int get_first_run() {
		return first_run;
	}
	
	public int get_profit_mode() {
		return profit_mode;
	}
	
	public int get_currency() {
		return currency;
	}
	
	//Setters
	public void set_first_run(int val) {
		first_run = val;
	}
	
	public void set_profit_mode(int val) {
		profit_mode = val;
	}
	
	public void set_currency(int val) {
		currency = val;
	}
}
