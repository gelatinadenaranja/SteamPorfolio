package files;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JViewport;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.EventQueue;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.UncheckedIOException;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class MainForm {
	
	//GUI misc
	private JFrame frame;
	private static ItemTable table_manager;
	private static Additempopup additempopup_object;
	private JLabel lbl_totalcost;
	private JLabel lbl_totalvalue;
	private JLabel lbl_totalprofit;
	
	//Settings variables
	private static Settings setting;
	private static int profit_mode;
	private static int currency;
	
	//Data usage variables
	private static Dbconnector database_manager;
	private static Map<String, String> itemname_id_list; //Used for removing and updating items. Format is ("Item Name", "Item ID").
	
	
	/*Launch the application.*/
	public static void main(String[] args) {
		table_manager = new ItemTable();
		
		additempopup_object = new Additempopup();
		
		itemname_id_list = new TreeMap<String, String>();
		
		setting = new Settings();
		profit_mode = setting.get_profit_mode();
		currency = setting.get_currency();
		
		database_manager = new Dbconnector();
		database_manager.create_tables();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*Create the application.*/
	public MainForm() {
		initialize();
	}
	
	/*Initialize the contents of the frame*/
	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1055, 500);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		additempopup_object.set_mainform_frame(frame);
		table_manager.set_mainform_frame(frame);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial", Font.PLAIN, 12));
		frame.setJMenuBar(menuBar);
		
		JMenu mnItem = new JMenu("Item");
		mnItem.setForeground(Color.BLACK);
		menuBar.add(mnItem);
		
		JMenu mnSetting = new JMenu("Settings");
		mnSetting.setForeground(Color.BLACK);
		menuBar.add(mnSetting);
		
		JMenuItem mntmAddItem = new JMenuItem("Add item");
		mntmAddItem.setFont(new Font("Arial", Font.PLAIN, 12));
		mnItem.add(mntmAddItem);
		
		JMenuItem mntmRemoveItem = new JMenuItem("Remove item");
		mntmRemoveItem.setFont(new Font("Arial", Font.PLAIN, 12));
		mnItem.add(mntmRemoveItem);
		
		JMenuItem mntmChangeCurrency = new JMenuItem("Change currency");
		mntmChangeCurrency.setFont(new Font("Arial", Font.PLAIN, 12));
		mnSetting.add(mntmChangeCurrency);
		
		JButton btn_refresh = new JButton("Refresh");
		btn_refresh.setForeground(Color.BLACK);
		menuBar.add(btn_refresh);
		
		JPanel panelinfo = new JPanel();
		panelinfo.setPreferredSize(new Dimension(10, 40));
		panelinfo.setLayout(new GridLayout(1, 0, 0, 0));
		frame.getContentPane().add(panelinfo, BorderLayout.SOUTH);
		
		final JLabel lblCurrency = new JLabel("Currency: " + setting.get_currency_name());
		lblCurrency.setBorder(new LineBorder(Color.BLACK, 2));
		lblCurrency.setForeground(Color.BLACK);
		lblCurrency.setFont(new Font("Arial", Font.PLAIN, 12));
		panelinfo.add(lblCurrency);
		
		lbl_totalcost = new JLabel("Total cost: ");
		lbl_totalcost.setBorder(new LineBorder(Color.BLACK, 2));
		lbl_totalcost.setForeground(Color.BLACK);
		lbl_totalcost.setFont(new Font("Arial", Font.PLAIN, 12));
		panelinfo.add(lbl_totalcost);
		
		lbl_totalvalue = new JLabel("Total value: ");
		lbl_totalvalue.setBorder(new LineBorder(Color.BLACK, 2));
		lbl_totalvalue.setForeground(Color.BLACK);
		lbl_totalvalue.setFont(new Font("Arial", Font.PLAIN, 12));
		panelinfo.add(lbl_totalvalue);
		
		lbl_totalprofit = new JLabel("Profit: ");
		lbl_totalprofit.setBorder(new LineBorder(Color.BLACK, 2));
		lbl_totalprofit.setForeground(Color.BLACK);
		lbl_totalprofit.setFont(new Font("Arial", Font.PLAIN, 12));
		panelinfo.add(lbl_totalprofit);
		
		JScrollPane scroll_pane = new JScrollPane (table_manager.get_jtable());
		scroll_pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll_pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scroll_pane);
		scroll_pane.setColumnHeader(new JViewport() {
	        @Override public Dimension getPreferredSize() {
	          Dimension dim = new Dimension(0,40);
	          return dim;
	        }
	    });
		
		//After all the UI elements have been initialized, retrieve saved data from db and insert it in the JTable.
		ArrayList<ArrayList<String>> table_row_list = new ArrayList<ArrayList<String>>();
		table_row_list = get_database_data(); //Get data from db and store it into lists.
		
		for(ArrayList<String> element : table_row_list) { //Read the list and add the table rows with the obtained data.
			ImageIcon icon;
			try{
				if(!(element.get(0) == null)) {
					icon = new ImageIcon( ImageIO.read(new File(element.get(0))) ); //Get the item icon through the icon URL.
				} else {
					icon = get_default_icon();
				}
			} catch( IOException NullPointerException) {
				icon = get_default_icon();
			}
			
			//Add the new row.
			table_manager.add_new_row(icon,
					element.get(1),
					element.get(2),
					element.get(3),
					element.get(4),
					element.get(5),
					element.get(6),
					element.get(7),
					element.get(8));
			
			//Fill the itemname_id_list map
			itemname_id_list.put(element.get(1), element.get( element.size() - 1 ));
		}
		
		update_all_totals();
		
		//Events
		frame.addWindowListener(new WindowAdapter() { //App close
			
            public void windowClosing(WindowEvent e) {
            	setting.save_settings();
            }
        });
		
		frame.addComponentListener(new ComponentAdapter() { //Frame made visible
			
			   public void componentShown(ComponentEvent e) {
				   
				   if( additempopup_object.get_task_done() ) {
					   additempopup_object.set_task_completed();
					   add_new_item();
				   }
				   
				   if( table_manager.get_item_got_edited() ) {
					   table_manager.set_item_got_edited();
					   update_item();
				   }
			   }
		});
		
		mntmAddItem.addMouseListener(new MouseAdapter() { //Add item
			
			public void mousePressed(MouseEvent e) {
				additempopup_object.reset_fields();
				additempopup_object.set_pop_up_visible();
			}
		});
		
		mntmRemoveItem.addMouseListener(new MouseAdapter() { //Remove item
			
			public void mousePressed(MouseEvent e) {
				
				//The JOptionPane returns null if the user clicked "Cancel", closed the dialog or triggered this event while the JTable was empty.
				String selected_item = (String) JOptionPane.showInputDialog(
				                    frame,
				                    "Select which item you want to remove:",
				                    "Remove item",
				                    JOptionPane.PLAIN_MESSAGE,
				                    null,
				                    itemname_id_list.keySet().toArray(),
				                    null);
				
				if ((selected_item != null) && (selected_item.length() > 0)) {
					String id;
					boolean success;
					
					id = itemname_id_list.get(selected_item);
					
					success = database_manager.delete_table_row(id);
					
					if(success) {
						int row_index;
						
						for(row_index=0; row_index < table_manager.get_row_count(); row_index++) {
							if(table_manager.get_cell_value(row_index, 1).equals(selected_item)) {
								break;
							}
						}
						
						table_manager.remove_row(row_index);
						itemname_id_list.remove(selected_item);
						update_all_totals();
					}
				}
			}
		});
		
		mntmChangeCurrency.addMouseListener(new MouseAdapter() { //Change currency
			
			public void mousePressed(MouseEvent e) {
				String current_currency_name = setting.get_currency_name();
				String new_currency_name;
				
				setting.change_currency(frame);
				
				new_currency_name = setting.get_currency_name();
				
				if((new_currency_name != null) && (!new_currency_name.equals(current_currency_name))) {
					currency = setting.get_currency();
					lblCurrency.setText("Currency: " + setting.get_currency_name());
					JOptionPane.showMessageDialog(frame, "Click the Refresh button for change to make effect.");
				}
			}
		});
		
		btn_refresh.addMouseListener(new MouseAdapter() { //Refresh JTable item market data.
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				ArrayList<String[]> market_data = new ArrayList<String[]>(); //Usage: (hash_name, game_id, lowest_price, median_price, volume)
				
				String hash_name;
				String game_id;
				String item_id;
				String lowest_price = "";
				String median_price = "";
				String volume = "";
				double item_cost = 0;
				String item_name = "";
				int item_row;
				Boolean match;
				
				ResultSet data;
				
				data = database_manager.get_refresh_data();
				
				try {
					if(data.next()) {
						do {
							item_row = -1;
							
							hash_name = data.getString("market_hash_name");
							game_id = data.getString("game_id");
							item_id = data.getString("id");
							
							
							//Check if data has been gotten
							match = false;
							for(String[] element : market_data) {
								if(element[0].equals(hash_name) && element[1].equals(game_id)) {
									match = true;
									lowest_price = element[2];
									median_price = element[3];
									volume = element[4];
									break;
								}
							}
							
							//Get market data if it wasn't in the list
							if(match == false) {
								String[] item_market_data;
								
								item_market_data = get_item_market_data(game_id, hash_name);
								lowest_price = item_market_data[0];
								median_price = item_market_data[1];
								volume = item_market_data[2];
								
								//Store data
								market_data.add(new String[] {hash_name, game_id, lowest_price, median_price, volume});
							}
							
							//Find the item's name in the itemname_id_list map by id. (This is the name used in the JTable)
							for(Map.Entry<String, String> map : itemname_id_list.entrySet()) {
								if(item_id.equals(map.getValue())) {
									item_name = map.getKey();
									break;
								}
							}
							
							//Find the item's row
							item_row = table_manager.find_item_row(item_name);
							
							//Calculate new profit value
							try {
								item_cost = Double.parseDouble(table_manager.get_cell_value(item_row, 3).toString());
							} catch(NumberFormatException ex) {
								item_cost = 0;
							}
							String item_profit = profit_calc(item_cost, lowest_price);
							
							//Insert the new data into the cells
							if (item_row > -1) {
								table_manager.refresh_item_market_data(item_row, item_profit, lowest_price, median_price, volume);
							}
						} while(data.next());
					}
					data.close();
				} catch(SQLException ex) {
					JOptionPane.showMessageDialog(null, "Something happened while trying to read data from the database.\n" + ex.toString());
				}
				
				update_all_totals();
			}
		});
		
		
	}
	
	private void add_new_item() { //Add item to JTable and save data.
		int item_id;
		String item_name;
		int item_name_count;
		String item_market_link;
		String item_game_id;
		String item_market_hash_name;
		double item_cost;
		int item_quantity;
		double item_expected_value;
		double item_expected_value_tax;
		ImageIcon item_icon;
		String icon_URL;
		String[] item_market_data;
		String item_lowest_price = "";
		String item_median_price = "";
		String item_volume = "";
		String item_profit = "";
		
		//Get pop-up form data.
		item_name = additempopup_object.get_item_name();
		
		item_name_count = database_manager.get_new_name_count_val(item_name);
		
		item_market_link = additempopup_object.get_item_market_link();
		
		item_game_id = additempopup_object.get_item_game_id();
		
		item_market_hash_name = additempopup_object.get_item_market_hash_name();
		
		item_cost = additempopup_object.get_item_cost();
		
		item_quantity = additempopup_object.get_item_quantity();
		
		item_expected_value = truncate(additempopup_object.get_item_expected_value());
		
		item_expected_value_tax = add_tax(item_expected_value);
		
		icon_URL = System.getProperty("user.home") + "\\SteamPortfolio\\icons\\" + item_market_hash_name + ".png"; //While a file with this URL doesn't exist, the default item icon will be used
		
		if(additempopup_object.get_item_icon() == null) {
			item_icon = get_default_icon();
		} else {
			item_icon = new ImageIcon(additempopup_object.get_item_icon());
			
			try {
				ImageIO.write(additempopup_object.get_item_icon(), "png", new File(icon_URL));
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Couldn't save item icon.\n" + e1.toString());
			}
		}
		
		//Get the item's market data from the JSON.
		item_market_data = get_item_market_data(item_game_id, item_market_hash_name);
		
		item_lowest_price = item_market_data[0];
		
		item_median_price = item_market_data[1];
		
		item_volume = item_market_data[2];
		
		item_profit = profit_calc(item_cost, item_lowest_price);
		
		//Insert new data in the database and get the item's id if successful.
		item_id = database_manager.insert_new_item(item_name, item_name_count, item_market_link, item_game_id, item_market_hash_name, icon_URL, item_cost, item_quantity, item_expected_value, item_lowest_price, item_median_price, item_volume);
		
		//If returned value from insert_new_item() is -1 that means the new data couldn't be inserted into the db.
		//And thus data won't neither be saved nor displayed on the JTable.
		if(item_id != -1) {
			item_name = (item_name_count > 0) ? item_name + " (" + item_name_count + ")" : item_name;
			
			itemname_id_list.put(item_name, Integer.toString(item_id));
			
			table_manager.add_new_row(item_icon,
					item_name,
					Integer.toString(item_quantity),
					Double.toString(item_cost),
					item_profit,
					Double.toString(item_expected_value_tax),
					item_lowest_price,
					item_median_price,
					item_volume);
			
			update_all_totals();
		}
	}
	
	private void update_item() {
		int table_row_num = table_manager.apply_item_edits();
		
		String id = "";
		String item_name = table_manager.get_cell_value(table_row_num, 1).toString();
		String new_cost = table_manager.get_cell_value(table_row_num, 3).toString();
		String new_quantity = table_manager.get_cell_value(table_row_num, 2).toString();
		String new_expectedvalue = table_manager.get_cell_value(table_row_num, 5).toString();
		
		
		//Getting the item's id by its name in the map
		for(Map.Entry<String, String> map : itemname_id_list.entrySet()) {
			if( item_name.equals(map.getKey()) ) {
				id = map.getValue();
				break;
			}
		}
		
		//Updating database row
		database_manager.update_item_data( id, new_cost, new_quantity, new_expectedvalue );
		
		//Update labels
		update_all_totals();
	}
	//Total value and profit
	private void update_total_cost() {
		int quantity = 0;
		double cost = 0;
		double total_cost = 0;
		
		for(int index = 0; index < table_manager.get_row_count(); index++) {
			quantity = Integer.parseInt ( table_manager.get_cell_value(index, 2).toString() );
			cost = Double.parseDouble( table_manager.get_cell_value(index, 3).toString() );
			total_cost = total_cost + (quantity * cost);
		}
		
		lbl_totalcost.setText( "Total cost: " + truncate(total_cost) );
	}
	
	private void update_total_value() {
		int quantity = 0;
		double price = 0;
		double total_value = 0;
		
		for(int index = 0; index < table_manager.get_row_count(); index++) {
			quantity = Integer.parseInt ( table_manager.get_cell_value(index, 2).toString() );
			price = Double.parseDouble( table_manager.get_cell_value(index, 6).toString() );
			price = price * 0.8695;
			total_value = total_value + (quantity * price);
		}
		
		lbl_totalvalue.setText( "Total value: " + truncate(total_value) );
	}
	
	private void update_total_profit() {
		int quantity = 0;
		double cost = 0;
		double total_cost = 0;
		double price = 0;
		double total_value = 0;
		double total_profit = 0;
		
		for(int index = 0; index < table_manager.get_row_count(); index++) {
			quantity = Integer.parseInt ( table_manager.get_cell_value(index, 2).toString() );
			cost = Double.parseDouble( table_manager.get_cell_value(index, 3).toString() );
			price = Double.parseDouble( table_manager.get_cell_value(index, 6).toString() );
			price = price * 0.8695;
			
			total_value = total_value + (quantity * price);
			total_cost = total_cost + (quantity * cost);
		}
		
		total_profit = total_value - total_cost;
		
		total_profit = truncate(total_profit);
		
		if(total_profit >= 0) {
			lbl_totalprofit.setText( "Total profit: " + total_profit );
		} else {
			lbl_totalprofit.setText( "Total losses: " + total_profit );
		}
	}
	
	private void update_all_totals() {
		update_total_cost();
		update_total_value();
		update_total_profit();
	}
	
	private double truncate(double value) {
		//Leaves only two decimals without rounding anything
		return (Math.floor(value * 100) / 100);
	}
	
	private double add_tax(double val) {
		//Adds Valve's 5% and the game developer's 10% cut from the items sold on the Steam Communitty Market.
		double gametax = truncate(val * 0.1);
		double steamtax = truncate(val * 0.05);
		
		if(gametax < 0.01) {
			gametax = 0.01;
		}
		
		if(steamtax < 0.01) {
			steamtax = 0.01;
		}
		
		val = (gametax + steamtax + val);
		
		return truncate(val);
	}
	
	private ImageIcon get_default_icon() {
		return new ImageIcon(getClass().getResource("/images/Default_Item_Icon.png"));
	}
	
	private String profit_calc(double cost, String price_param) {
		if(cost == 0){
			return "Infinite";
		}
		
		double profit_value;
		double price;
		
		price = Double.parseDouble(price_param);
		
		cost = add_tax(cost);
		
		switch(profit_mode){
		case 0:
			profit_value =  ((price * 100) / cost) - 100;
			profit_value = truncate(profit_value);
			return String.valueOf(profit_value) + '%';
		default:
			JOptionPane.showMessageDialog(null, "An invalid profit display mode has been selected.\nSwitched to default.");
			profit_mode = 0;
			profit_value =  ((price * 100) / cost) - 100;
			profit_value = truncate(profit_value);
			return String.valueOf(profit_value) + '%';
		}
	}
	
	private static String get_clean_number(String value) {
		
		for(int index = 0; index < value.length(); index++) {
			if(value.charAt(index) == ',') {
				value = value.replace(".", "");
				value = value.replace(",", ".");
				break;
			}
		}
		value = value.replaceAll("[^\\d.]", "");
		
		return value;
	}
	
	private static String[] get_item_market_data(String game_id, String market_hash_name) {
		String link =("https://steamcommunity.com/market/priceoverview/?currency=" + currency + "&appid=" + game_id + "&market_hash_name=" + market_hash_name);
		JSONParser jsonparser = new JSONParser();
		JSONObject obj = null;
		String json;
		String[] values = {"0", "0", "0"};
		
		//Getting the JSON
		try {
			json = Jsoup.connect(link).ignoreContentType(true).execute().body();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't get the item's market data.\n" + e.toString());
			return values;
		} catch(UncheckedIOException e1) {
			JOptionPane.showMessageDialog(null, "Connection problem:\n" + e1.toString());
			return values;
		}
		
		//Parsing and using it
		try {
			obj = (JSONObject) jsonparser.parse(json);
		} catch (ParseException e) {
			e.printStackTrace();
			return values;
		}
		
		if(obj.get("success").toString().equals("false")) {
			JOptionPane.showMessageDialog(null, "Couldn't get the item's market data.");
			values[0] = "None";
			values[1] = "None";
			values[2] = "None";
			return values;
		}
		
		if(obj.get("lowest_price") != null){
			values[0] = obj.get("lowest_price").toString();
			values[0] = get_clean_number(values[0]);
		}
		
		if(obj.get("median_price") != null){
			values[1] = obj.get("median_price").toString();
			values[1] = get_clean_number(values[1]);
		}
		
		if(obj.get("volume") != null){
			values[2] = obj.get("volume").toString();
		}
		
		return values;
	}
	
	private ArrayList<ArrayList<String>> get_database_data() {
		//For now, the columns last_lowest_price, last_median_price and last_volume won't be used.
		//NEED FIELDS: iconURL, id, name, quantity, cost, expected_value, game_id and market_hash_name.
		
		ArrayList<ArrayList<String>> row_list = new ArrayList<ArrayList<String>>();
		ArrayList<String> row;
		
		ArrayList<String[]> stored_market_data = new ArrayList<String[]>(); //Usage: (market_hash_name, game_id, lowest_price, median_price, volume)
		
		int id;
		String name;
		int name_count;
		String iconURL;
		int quantity;
		double cost;
		String profit;
		double expected_value;
		String game_id;
		String market_hash_name;
		String[] market_data;
		String lowest_price = "";
		String median_price = "";
		String volume = "";
		
		ResultSet data = database_manager.query_data();
		
		try {
			if(data.next()) {
				do {
					//Read the contents of the db row.
					iconURL = data.getString("iconURL");
					name = data.getString("name");
					name_count = data.getInt("name_count");
					quantity = data.getInt("quantity");
					cost = data.getDouble("cost");
					expected_value = data.getDouble("expected_value");
					id = data.getInt("id");
					game_id = data.getString("game_id");
					market_hash_name = data.getString("market_hash_name");
					
					//Check if the current market_hash_name and game_id had already been used.
					//If so just loop through stored data until a match is found and grab the stored data.
					boolean found_match = false;
					for(String[] element : stored_market_data) {
						if(element[0].equals(market_hash_name) && element[1].equals(game_id)) {
							found_match = true;
							lowest_price = element[2];
							median_price = element[3];
							volume = element[4];
							break;
						}
					}
					
					//Get item market data if it hasn't already been gotten.
					if(found_match == false) {
						market_data = get_item_market_data(game_id, market_hash_name);
						lowest_price = market_data[0];
						median_price = market_data[1];
						volume = market_data[2];
						
						//Store the new data
						stored_market_data.add(new String[] {market_hash_name, game_id, lowest_price, median_price, volume});
					}
					
					profit = profit_calc(cost, lowest_price);
					expected_value = add_tax(expected_value);
					
					//Modify name according to name_count
					name = (name_count > 0) ? name + " (" + name_count + ")" : name;
					
					//Add everything to a list which will contain the values of a table row.
					row = new ArrayList<String>();
					row.add(iconURL);
					row.add(name);
					row.add(Integer.toString(quantity));
					row.add(Double.toString(cost));
					row.add(profit);
					row.add(Double.toString(expected_value));
					row.add(lowest_price);
					row.add(median_price);
					row.add(volume);
					row.add(Integer.toString(id));
					
					row_list.add(row);
				} while (data.next());
			}
			data.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Something happened while reading the data from the database.\n" + e.toString());
			e.printStackTrace();
		}
		
		return row_list;
	}
	
}
