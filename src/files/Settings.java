package files;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JOptionPane;

public class Settings {
	private File file;
	private FileInputStream file_input;
	private Properties setting;
	
	//Settings
	private int first_run; //Ever going to use this?
	private int profit_mode;
	private int currency;
	private String currency_name;
	
	public Settings() {
		setting = new Properties();
		
		file = new File(System.getProperty("user.home") + "\\SteamPortfolio\\Settings.donottouch");
		
		create_directories();
		create_currencies_file();
		
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
	
	private void load_setting_values() { //Read the settings file and load values
		try{
			first_run = Integer.parseInt(setting.getProperty("first_run"));
			profit_mode = Integer.parseInt(setting.getProperty("profit_mode"));
			currency = Integer.parseInt(setting.getProperty("currency"));
			currency_name = setting.getProperty("currency_name");
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Why did you touch the settings?");
			write_defaults();
			
			//Reload input stream after a default settings file is created
			try{
				file_input = new FileInputStream(file.getAbsoluteFile());
				setting.load(file_input);
			} catch(IOException e1) {
				JOptionPane.showMessageDialog(null, "Error:" + e1.toString());
			}
			
			//Try loading setting values again
			load_setting_values();
		}
	}
	
	private void create_directories() {
		File icon_folder = new File(System.getProperty("user.home") + "\\SteamPortfolio\\icons");
		try{
			icon_folder.mkdirs();
		} catch(SecurityException e) {
			JOptionPane.showMessageDialog(null, "Couldn't create the icon folder:\n" + e.toString());
		}
	}
	
	private void write_defaults() {
		FileWriter writer;
		try{
			writer = new FileWriter(file.getAbsoluteFile(), false);
			
			writer.write("first_run=0" + System.lineSeparator() + 
					     "profit_mode=0" + System.lineSeparator() + 
					     "currency=1" + System.lineSeparator() + 
					     "currency_name=US dollar");
			
			writer.close();
		}catch(IOException e){
			JOptionPane.showMessageDialog(null, "Couldn't write default settings.\n" + e.toString());
		}
	}
	
	public void save_settings() {
		File file = new File((System.getProperty("user.home") + "\\SteamPortfolio\\Settings.donottouch"));
		FileWriter writer;
		
		try {
			writer = new FileWriter(file.getAbsoluteFile(), false);
			
			writer.write("first_run=" + first_run + System.lineSeparator() + 
					     "profit_mode=" + profit_mode + System.lineSeparator() + 
					     "currency=" + currency + System.lineSeparator() + 
					     "currency_name=" + currency_name);
			
			writer.close();
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't save settings.\n" + e.toString());
		}
	}
	
	public void change_currency(Component parent_comp) {
		TreeMap<String, String> currencies = new TreeMap<String, String>();
		Scanner scan = null;
		
		try {
			scan = new Scanner(new File(System.getProperty("user.home") + "\\SteamPortfolio\\Currencies.txt"));
			scan.useDelimiter("=|\\n");
		} catch (FileNotFoundException e) {
			create_currencies_file();
			change_currency(parent_comp);
		}
		
		while(scan.hasNextLine()) {
			String currency_id = scan.next();
			String currency_name = scan.next();
			currencies.put(currency_name, currency_id);
		}
		
		String selected_currency = (String) JOptionPane.showInputDialog(
                parent_comp,
                "Pick a currency from the list:",
                "Change currency",
                JOptionPane.PLAIN_MESSAGE,
                null,
                currencies.keySet().toArray(),
                null);
		
		if ((selected_currency != null) && (selected_currency.length() > 0)) {
			for (Map.Entry<String, String> map : currencies.entrySet()) {
				if(selected_currency.equals(map.getKey())) {
					currency = Integer.parseInt(map.getValue());
					currency_name = selected_currency;
					break;
				}
			}
		}
	}
	
	private void create_currencies_file() {
		File file = new File(System.getProperty("user.home") + "\\SteamPortfolio\\Currencies.txt");
		FileWriter writer;
		try {
			if(file.createNewFile()) {
				writer = new FileWriter(file.getAbsoluteFile(), false);
				
				writer.write("1=US dollar" + System.lineSeparator() + 
						     "2=British pound" + System.lineSeparator() + 
						     "3=Euro" + System.lineSeparator() + 
						     "4=Swiss franc" + System.lineSeparator() + 
						     "5=Russian rubble" + System.lineSeparator() + 
						     "6=Polish Zloty" + System.lineSeparator() + 
						     "7=Brazilian real" + System.lineSeparator() + 
						     "8=Japanese Yen" + System.lineSeparator() + 
						     "9=Norwegian krone" + System.lineSeparator() + 
						     "10=Indonesian rupiah" + System.lineSeparator() + 
						     "11=Malaysian Ringgit" + System.lineSeparator() + 
						     "12=Philippine peso" + System.lineSeparator() + 
						     "13=Singapore dollar" + System.lineSeparator() + 
						     "14=Thai baht" + System.lineSeparator() + 
						     "15=Vietnamese dong" + System.lineSeparator() + 
						     "16=South korean won" + System.lineSeparator() + 
						     "17=Turkish lira" + System.lineSeparator() + 
						     "18=Ukrainian Hryvnia" + System.lineSeparator() + 
						     "19=Mexican peso" + System.lineSeparator() + 
						     "20=Canadian Dollar" + System.lineSeparator() + 
						     "21=Australian dollar" + System.lineSeparator() + 
						     "22=New zealand dollar" + System.lineSeparator() + 
						     "23=Chinese yuan" + System.lineSeparator() + 
						     "24=Indian rupee" + System.lineSeparator() + 
						     "25=Chilean peso" + System.lineSeparator() + 
						     "26=Peruvian nuevo sol" + System.lineSeparator() + 
						     "27=Colombian peso" + System.lineSeparator() + 
						     "28=South african rand" + System.lineSeparator() + 
						     "29=Hong kong dollar" + System.lineSeparator() + 
						     "30=Taiwan dollar" + System.lineSeparator() + 
						     "31=Saudi riyal" + System.lineSeparator() + 
						     "32=Arab emirates dirham" + System.lineSeparator() + 
						     "34=Argentine peso" + System.lineSeparator() + 
						     "35=Israeli new shekel" + System.lineSeparator() + 
						     "37=Kazakhstan tenge" + System.lineSeparator() + 
						     "38=Kuwaiti dinar" + System.lineSeparator() + 
						     "39=Qatari rial" + System.lineSeparator() + 
						     "40=Costa rican Colon" + System.lineSeparator() + 
						     "41=Uruguayan peso");
				
				writer.close();
			}
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't create currency list file.\n" + e.toString());
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
	
	public String get_currency_name() {
		return currency_name;
	}
	
	//Setters
	public void set_first_run(int val) {
		first_run = val;
	}
	
	public void set_profit_mode(int val) {
		profit_mode = val;
	}
}
