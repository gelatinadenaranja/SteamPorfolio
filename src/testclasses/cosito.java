package testclasses;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class cosito {
	public static void main(String[] args){
        
	    // Creating an empty HashMap
	    Map<Integer, String> hash_map = new TreeMap<Integer, String>();
	 
	    // Mapping string values to int keys
	    hash_map.put(10, "Geeks");
	    hash_map.put(15, "4");
	    hash_map.put(20, "Geeks");
	    hash_map.put(25, "Welcomes");
	    hash_map.put(30, "You");
	 
	    // Displaying the HashMap
	    System.out.println("Initial Mappings are: " + hash_map);
	 
	    // Removing the existing key mapping
	    String returned_value = (String)hash_map.remove(20);
	 
	    // Verifying the returned value
	    System.out.println("Returned value is: "+ returned_value);
	 
	    // Displaying the new map
	    System.out.println("New map is: "+ hash_map);
		
		/*//List of arrays test:
		ArrayList <int[]> coso = new ArrayList<int[]>();
		
		int[] nose = {1, 2};
		
		coso.add(nose);
		
		coso.add(new int[] {3, 4});
		
		System.out.println(coso.get(1)[0]);*/
		
		ArrayList<ArrayList<String>> listoflist = new ArrayList<ArrayList<String>>();
		
		ArrayList <String> colores = new ArrayList<String>();
		colores.add("rosa");
		
		listoflist.add(colores);
		
		colores = new ArrayList<String>();
		colores.add("perro");
		colores.add("chanchito");
		
		System.out.println(colores.get(colores.size() - 1));
		
		listoflist.add(colores);
		
		for(ArrayList<String> element : listoflist) {
			System.out.println(element.toString());
		}
		
		/*java.lang.System.setProperty("https.protocols", "TLSv1.2");
		java.lang.System.setProperty("Dhttps.cipherSuites", "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384");
		
		Document steam_market_item_site;
		
		Elements item_html_elem;
		
		Elements item_image_html_elem;
		
		String item_name = "";
		
		String item_image_source = "";
		
		Image item_icon;
		
		int i;
		
		//coso de la foto
		BufferedImage bImage = null;
		File archivo = new File("C:\\Users\\Termostato\\Desktop\\pingo.jpg");
		try {
			steam_market_item_site = Jsoup.connect("https://steamcommunity.com/market/listings/730/Sticker%20%7C%20Natus%20Vincere%20%7C%202020%20RMR").get();
			//Item's name
			item_html_elem = steam_market_item_site.getElementsByClass("market_listing_nav");
			item_name = item_html_elem.text();
			System.out.println(item_name);
			
			//Item's image
			item_image_html_elem = steam_market_item_site.getElementsByClass("market_listing_largeimage");
			item_image_source = item_image_html_elem.select("img").attr("src").toString(); //Item's image source URL
			
			//Item's image size change
			item_image_source = item_image_source.replace("360fx360f", "30fx30f");
			
			URLConnection connection = new URL(item_image_source).openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.connect();
			item_icon = ImageIO.read(connection.getInputStream());
			
			
			ImageIO.write((RenderedImage) item_icon, "jpg", new File("C:\\Users\\Termostato\\Desktop\\pingo.jpg"));
			
			
			
			//URL url = new URL(null, item_image_source, new sun.net.www.protocol.https.Handler());
			//HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			//con.connect();
			//System.out.println(con.getCipherSuite());
			//TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384
			
		} catch (IOException e) {
			System.out.println(e);
			item_icon = null;
		}*/
		
	}
}