package testclasses;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

import javax.swing.Box;
import java.awt.BorderLayout;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.SwingConstants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFormBackup {

	private JFrame frame;
	/**
	 * Launch the application.
	 */
	/*ACA EMPIEZA EL MAIN*/
	/*ACA EMPIEZA EL MAIN*/
	/*ACA EMPIEZA EL MAIN*/
	/*ACA EMPIEZA EL MAIN*/
	/*ACA EMPIEZA EL MAIN*/
	/*ACA EMPIEZA EL MAIN*/
	/*ACA EMPIEZA EL MAIN*/
	public static void main(String[] args) {
		java.lang.System.setProperty("https.protocols", "TLSv1.2");
		java.lang.System.setProperty("Dhttps.cipherSuites", "TLS_RSA_WITH_AES_256_CBC_SHA256");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFormBackup window = new MainFormBackup();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFormBackup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(643, 0));
		frame.getContentPane().setMinimumSize(new Dimension(643, 0));
		frame.getContentPane().setBackground(SystemColor.control);
		frame.setBounds(100, 100, 900, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setMinimumSize(new Dimension(643, 2));
		menuBar.setFont(new Font("Arial", Font.PLAIN, 12));
		frame.setJMenuBar(menuBar);
		
		JMenu mnItem = new JMenu("Item");
		mnItem.setForeground(Color.BLACK);
		menuBar.add(mnItem);
		
		JMenuItem mntmAddItem = new JMenuItem("Add item");
		mntmAddItem.setFont(new Font("Arial", Font.PLAIN, 12));
		mntmAddItem.setIcon(null);
		mnItem.add(mntmAddItem);
		
		JMenuItem mntmRemoveItem = new JMenuItem("Remove item");
		mntmRemoveItem.setFont(new Font("Arial", Font.PLAIN, 12));
		mnItem.add(mntmRemoveItem);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelinfo = new JPanel();
		panelinfo.setMinimumSize(new Dimension(643, 10));
		panelinfo.setPreferredSize(new Dimension(10, 30));
		panelinfo.setBackground(SystemColor.controlShadow);
		frame.getContentPane().add(panelinfo, BorderLayout.SOUTH);
		panelinfo.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblCurrency = new JLabel("Currency: ");
		lblCurrency.setBorder(new LineBorder(Color.BLACK, 2));
		lblCurrency.setForeground(Color.BLACK);
		lblCurrency.setFont(new Font("Arial", Font.PLAIN, 12));
		panelinfo.add(lblCurrency);
		
		JLabel lblKeyprice = new JLabel("Key price: ");
		lblKeyprice.setBorder(new LineBorder(Color.BLACK, 2));
		lblKeyprice.setBackground(SystemColor.menu);
		lblKeyprice.setFont(new Font("Arial", Font.PLAIN, 12));
		lblKeyprice.setForeground(Color.BLACK);
		panelinfo.add(lblKeyprice);
		
		JLabel lblTotalcost = new JLabel("Total cost: ");
		lblTotalcost.setBorder(new LineBorder(Color.BLACK, 2));
		lblTotalcost.setForeground(Color.BLACK);
		lblTotalcost.setFont(new Font("Arial", Font.PLAIN, 12));
		panelinfo.add(lblTotalcost);
		
		JLabel lblTotalvalue = new JLabel("Total value: ");
		lblTotalvalue.setBorder(new LineBorder(Color.BLACK, 2));
		lblTotalvalue.setForeground(Color.BLACK);
		lblTotalvalue.setFont(new Font("Arial", Font.PLAIN, 12));
		panelinfo.add(lblTotalvalue);
		
		JLabel lblTotalprofit = new JLabel("Profit: ");
		lblTotalprofit.setBorder(new LineBorder(Color.BLACK, 2));
		lblTotalprofit.setForeground(Color.BLACK);
		lblTotalprofit.setFont(new Font("Arial", Font.PLAIN, 12));
		panelinfo.add(lblTotalprofit);
		
		JPanel paneltabs = new JPanel();
		paneltabs.setPreferredSize(new Dimension(884, 43));
		paneltabs.setMaximumSize(new Dimension(32767, 43));
		paneltabs.setMinimumSize(new Dimension(884, 43));
		paneltabs.setBackground(SystemColor.controlShadow);
		frame.getContentPane().add(paneltabs, BorderLayout.NORTH);
		paneltabs.setLayout(new BoxLayout(paneltabs, BoxLayout.X_AXIS));
		
		final JLabel lblColItem = new JLabel("Item");
		paneltabs.add(lblColItem);
		lblColItem.setPreferredSize(new Dimension(243,43));
		lblColItem.setMinimumSize(new Dimension(243,43));
		lblColItem.setMaximumSize(new Dimension(243, 43));
		lblColItem.setBorder(new LineBorder(Color.BLACK));
		lblColItem.setForeground(Color.BLACK);
		lblColItem.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblColitemquantity = new JLabel("Quantity");
		paneltabs.add(lblColitemquantity);
		lblColitemquantity.setPreferredSize(new Dimension(60,43));
		lblColitemquantity.setMinimumSize(new Dimension(60,43));
		lblColitemquantity.setMaximumSize(new Dimension(9999,43));
		lblColitemquantity.setBorder(new LineBorder(Color.BLACK));
		lblColitemquantity.setForeground(Color.BLACK);
		lblColitemquantity.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblColitemcost = new JLabel("Cost");
		lblColitemcost.setBackground(Color.CYAN);
		lblColitemcost.setOpaque(true);
		paneltabs.add(lblColitemcost);
		lblColitemcost.setPreferredSize(new Dimension(85,43));
		lblColitemcost.setMinimumSize(new Dimension(85,43));
		lblColitemcost.setMaximumSize(new Dimension(9999,43));
		lblColitemcost.setBorder(new LineBorder(Color.BLACK));
		lblColitemcost.setForeground(Color.BLACK);
		lblColitemcost.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblColExpectedValueNoTax = new JLabel("<html>Expected value<br>(No tax)");
		paneltabs.add(lblColExpectedValueNoTax);
		lblColExpectedValueNoTax.setPreferredSize(new Dimension(100,43));
		lblColExpectedValueNoTax.setMinimumSize(new Dimension(100,43));
		lblColExpectedValueNoTax.setMaximumSize(new Dimension(9999,43));
		lblColExpectedValueNoTax.setBorder(new LineBorder(Color.BLACK));
		lblColExpectedValueNoTax.setForeground(Color.BLACK);
		lblColExpectedValueNoTax.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblColExpectedValueTax = new JLabel("<html>Expected value<br>(Tax)");
		paneltabs.add(lblColExpectedValueTax);
		lblColExpectedValueTax.setPreferredSize(new Dimension(100,43));
		lblColExpectedValueTax.setMinimumSize(new Dimension(100,43));
		lblColExpectedValueTax.setMaximumSize(new Dimension(9999,43));
		lblColExpectedValueTax.setBorder(new LineBorder(Color.BLACK));
		lblColExpectedValueTax.setForeground(Color.BLACK);
		lblColExpectedValueTax.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblColLowestPrice = new JLabel("Lowest price");
		paneltabs.add(lblColLowestPrice);
		lblColLowestPrice.setPreferredSize(new Dimension(85,43));
		lblColLowestPrice.setMinimumSize(new Dimension(85,43));
		lblColLowestPrice.setMaximumSize(new Dimension(9999,43));
		lblColLowestPrice.setBorder(new LineBorder(Color.BLACK));
		lblColLowestPrice.setForeground(Color.BLACK);
		lblColLowestPrice.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblColMedPrice = new JLabel("Median price");
		paneltabs.add(lblColMedPrice);
		lblColMedPrice.setPreferredSize(new Dimension(85,43));
		lblColMedPrice.setMinimumSize(new Dimension(85,43));
		lblColMedPrice.setMaximumSize(new Dimension(9999,43));
		lblColMedPrice.setBorder(new LineBorder(Color.BLACK));
		lblColMedPrice.setForeground(Color.BLACK);
		lblColMedPrice.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblColVolume = new JLabel("Volume");
		paneltabs.add(lblColVolume);
		lblColVolume.setPreferredSize(new Dimension(108, 43));
		lblColVolume.setMinimumSize(new Dimension(108, 43));
		lblColVolume.setMaximumSize(new Dimension(9999,43));
		lblColVolume.setForeground(Color.BLACK);
		lblColVolume.setBorder(new LineBorder(Color.BLACK));
		lblColVolume.setFont(new Font("Arial", Font.PLAIN, 12));
		
		final JPanel panelitemcontainer = new JPanel();
		final JScrollPane itemscroll = new JScrollPane(panelitemcontainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelitemcontainer.setPreferredSize(new Dimension(606, 643));
		panelitemcontainer.setBackground(SystemColor.scrollbar);
		panelitemcontainer.setBounds(201, 43, 606, 643);
		frame.getContentPane().add(itemscroll);
		panelitemcontainer.setLayout(new BoxLayout(panelitemcontainer, BoxLayout.Y_AXIS));
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setAlignmentY(Component.CENTER_ALIGNMENT);
		horizontalBox.setToolTipText("");
		horizontalBox.setMinimumSize(new Dimension(0, 999));
		horizontalBox.setPreferredSize(new Dimension(999, 43));
		horizontalBox.setMaximumSize(new Dimension(9999, 43));
		panelitemcontainer.add(horizontalBox);
		
		final JButton btnNewButton = new JButton("Stattrak SSG 008 | Blood In The Water");
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 10));
		btnNewButton.setHorizontalAlignment(SwingConstants.LEADING);
		btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnNewButton.setMinimumSize(new Dimension(700, 23));
		btnNewButton.setMaximumSize(new Dimension(700, 23));
		btnNewButton.setPreferredSize(new Dimension(700, 23));
		horizontalBox.add(btnNewButton);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		horizontalBox_1.setToolTipText("");
		horizontalBox_1.setPreferredSize(new Dimension(999, 43));
		horizontalBox_1.setMinimumSize(new Dimension(0, 999));
		horizontalBox_1.setMaximumSize(new Dimension(9999, 43));
		horizontalBox_1.setAlignmentY(0.5f);
		panelitemcontainer.add(horizontalBox_1);
		
		final JButton button = new JButton("CLICK ME");
		button.setFont(new Font("Arial", Font.PLAIN, 14));
		button.setPreferredSize(new Dimension(243, 23));
		button.setMinimumSize(new Dimension(243, 23));
		button.setMaximumSize(new Dimension(243, 23));
		button.setHorizontalAlignment(SwingConstants.LEADING);
		button.setBorder(new LineBorder(new Color(112, 128, 144), 2));
		horizontalBox_1.add(button);
		
		JButton button_1 = new JButton("300000");
		button_1.setPreferredSize(new Dimension(60, 23));
		button_1.setMinimumSize(new Dimension(60, 23));
		button_1.setMaximumSize(new Dimension(999, 23));
		button_1.setHorizontalAlignment(SwingConstants.LEADING);
		horizontalBox_1.add(button_1);
		
		final JButton button_2 = new JButton("1233");
		button_2.setPreferredSize(new Dimension(85, 23));
		button_2.setMinimumSize(new Dimension(85, 23));
		button_2.setMaximumSize(new Dimension(999, 23));
		button_2.setHorizontalAlignment(SwingConstants.LEADING);
		horizontalBox_1.add(button_2);
		
		final JButton btnLeDbTest = new JButton("Le db test");
		btnLeDbTest.setPreferredSize(new Dimension(100, 23));
		btnLeDbTest.setMinimumSize(new Dimension(100, 23));
		btnLeDbTest.setMaximumSize(new Dimension(999, 23));
		btnLeDbTest.setHorizontalAlignment(SwingConstants.LEADING);
		horizontalBox_1.add(btnLeDbTest);
		
		JButton button_4 = new JButton("New button");
		button_4.setPreferredSize(new Dimension(100, 23));
		button_4.setMinimumSize(new Dimension(100, 23));
		button_4.setMaximumSize(new Dimension(999, 23));
		button_4.setHorizontalAlignment(SwingConstants.LEADING);
		horizontalBox_1.add(button_4);
		
		JButton button_5 = new JButton("New button");
		button_5.setPreferredSize(new Dimension(85, 23));
		button_5.setMinimumSize(new Dimension(85, 23));
		button_5.setMaximumSize(new Dimension(999, 23));
		button_5.setHorizontalAlignment(SwingConstants.LEADING);
		horizontalBox_1.add(button_5);
		
		JButton button_6 = new JButton("New button");
		button_6.setPreferredSize(new Dimension(85, 23));
		button_6.setMinimumSize(new Dimension(85, 23));
		button_6.setMaximumSize(new Dimension(999, 23));
		button_6.setHorizontalAlignment(SwingConstants.LEADING);
		horizontalBox_1.add(button_6);
		
		JButton pedobtn = new JButton("PEDO");
		pedobtn.setPreferredSize(new Dimension(108, 23));
		pedobtn.setMinimumSize(new Dimension(108, 23));
		pedobtn.setMaximumSize(new Dimension(999, 23));
		pedobtn.setHorizontalAlignment(SwingConstants.LEADING);
		horizontalBox_1.add(pedobtn);
		
		JLabel foto;
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Document steam_market_item_site;
				Elements html_elem;
				String nose = "null";
				BufferedImage nosev2;
				int i;
				
				try {
					steam_market_item_site = Jsoup.connect("https://steamcommunity.com/market/listings/730/Sticker%20%7C%20TYLOO%20%7C%202020%20RMR?l=english").get();
					html_elem = steam_market_item_site.getElementsByClass("market_listing_largeimage");
					//---------------------------
					//Conseguir la foto
					nose = html_elem.select("img").attr("src");
					
					URLConnection connection = new URL(nose).openConnection();
					connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
					
					connection.connect();
					nosev2 = ImageIO.read(connection.getInputStream());
					
					ImageIO.write(nosev2, "png", new File("C:\\Users\\Termostato\\Desktop\\pingo.png"));
					
					//nosev2 = ImageIO.read(new URL(nose).openStream());				
					
					JLabel foto = new JLabel(new ImageIcon(nosev2));
					panelitemcontainer.add(foto);
					
					//C:\\Users\\Termostato\\Desktop\\test.db
					
					
					
					
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
				}
			}
		});
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int n = JOptionPane.showConfirmDialog(
					    frame,
					    "Would you like green eggs and ham?",
					    "An Inane Question",
					    JOptionPane.YES_NO_OPTION);
				
				if(n == 0){
					btnNewButton.setText("iiiiiiiiiiiiiiiiiii");
				}else if(n == 1){
					btnNewButton.setText("eeeehhhh");
				}else{
					btnNewButton.setText("??????????????");
				}
			}
		});
		
		btnLeDbTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pruebasqlite conn = new pruebasqlite();
				ResultSet result;
				
				conn.setQuery("CREATE TABLE IF NOT EXISTS coso(val INT, nom TEXT);");
				
				/*conn.setQuery("INSERT INTO coso(val, nom) VALUES(4, 'pepe')");
				conn.setQuery("INSERT INTO coso(val, nom) VALUES(2, 'jorge')");
				conn.setQuery("INSERT INTO coso(val, nom) VALUES(3, 'jorge')");*/
				
				//result = conn.getQuery("SELECT nom FROM coso;");
				result = conn.getQuery("SELECT count(nom) FROM coso WHERE(nom = 'jorge')");
				try {
					while(result.next()) {
						System.out.println(result.getInt("count(nom)"));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		pedobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] possibilities = {"ham", "spam", "yam"};
				
				//The JOptionPane returns null if you clicked Cancel
				String s = (String)JOptionPane.showInputDialog(
				                    frame,
				                    "Complete the sentence:\n"
				                    + "\"Green eggs and...\"",
				                    "Customized Dialog",
				                    JOptionPane.PLAIN_MESSAGE,
				                    null,
				                    possibilities,
				                    "ham");

				//If a string was returned, say so.
				if ((s != null) && (s.length() > 0)) {
				    System.out.println("Green eggs and... " + s + "!");
				    return;
				}

				//If you're here, the return value was null/empty.
				System.out.println("Come on, finish the sentence!");
			}
		});
		
	}
}
