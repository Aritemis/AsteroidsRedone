/**
 *	@author Ariana Fairbanks
 */

package control;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.format.DateTimeFormatter;
import model.HighScore;
import model.ShipShapes;
import model_enum.BulletType;
import model_enum.ShipType;
import view.AsteroidsFrame;
import view.ColorScheme;
import view.ViewPanel;
import view_images.Images;

public class AsteroidsControl
{
	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	
	public static final int updateTime = 15;
	public static int repaintTime = 8;
	public static int screenWidth;
	public static int screenHeight;
	public static boolean fullscreen;

	public static boolean paused;
	public static boolean limbo; //continue?
	public static boolean reset; //set up next level
	public static boolean menu;
	public static boolean move;
	
	public static JPanel messagePanel;
	public Images images;
	
	private int credits;
	private ShipType currentShip;
	private BulletType currentBullet;
	private Integer[] shipUpgrades;
	private Integer[] bulletUpgrades;
	private HighScore[] highScores;
	
	
	
	private AsteroidsFrame frame;
	private ViewPanel state;
	
	private File dataFile;
	private FileInputStream fileReader;
	private ObjectInputStream objectReader;
	private FileOutputStream fileWriter;
	private ObjectOutputStream objectWriter;
	
	public void start()
	{
		dataFile = new File(System.getProperty("user.home") + "/.AsteroidsData");
		resetLocalData();
		loadData();
		resetGameVariables();
		messagePanel = new JPanel();
		images = new Images();
		frame = new AsteroidsFrame(this);
		ColorScheme.setColorDefaults();
		setUI();
	}
	
	private void setUI()
	{
		try
		{
			UIManager.setLookAndFeel("view.AsteroidsLookAndFeel");
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		UIManager.put("Panel.background", Color.black);
		UIManager.put("OptionPane.messageBackground", Color.black);
		UIManager.put("OptionPane.background", Color.black);
	
		//UIManager.put("OptionPane.messageForeground", Color.white);
		UIManager.put("Panel.foreground", Color.white);
		UIManager.put("Button.background", Color.black);
		UIManager.put("Button.foreground", Color.white);
	}
	
	public void resetLocalData()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		fullscreen = true;
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
		credits = 0;
		currentShip = ShipType.STANDARD;
		currentBullet = BulletType.STANDARD;
		shipUpgrades = new Integer[ShipType.values().length];
		for(int i = 0; i < shipUpgrades.length; i++)
		{
			shipUpgrades[i] = Integer.valueOf(0);
		}
		bulletUpgrades = new Integer[BulletType.values().length];
		for(int i = 0; i < bulletUpgrades.length; i++)
		{
			bulletUpgrades[i] = Integer.valueOf(0);
		}
		highScores = new HighScore[5];
	}
	
	public void resetGameVariables()
	{
		limbo = false;
		reset = false;
		paused = false;
		menu = false;
		move = true;
	}
	
	private void loadData()
	{
		if(dataFile.exists())
		{
			readData();
		}
		else
		{
			writeData();
		}
	}
	
	private void readData()
	{
		try
		{
			fileReader = new FileInputStream(dataFile);
			objectReader = new ObjectInputStream(fileReader);
			AsteroidsSaveData saveData = (AsteroidsSaveData) objectReader.readObject();
			objectReader.close();
			fileReader.close();
			updateLocalData(saveData);
			System.out.println("Data Loaded");
		} 
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public void writeData()
	{
		try
		{
		    fileWriter = new FileOutputStream(dataFile);
		    objectWriter = new ObjectOutputStream(fileWriter);
		    objectWriter.writeObject(createSaveData());
		    objectWriter.close();
		    fileWriter.close();
		} 
		catch (IOException e)
		{
			System.out.println("Data not written.");
			e.printStackTrace();
		}
	}
	
	private AsteroidsSaveData createSaveData()
	{
		AsteroidsSaveData saveData = new AsteroidsSaveData();
		saveData.fullscreen = fullscreen;
		saveData.screenWidth = screenWidth;
		saveData.screenHeight = screenHeight;
		saveData.credits = credits;
		saveData.currentShip = currentShip;
		saveData.currentBullet = currentBullet;
		saveData.shipUpgrades = shipUpgrades;
		saveData.bulletUpgrades = bulletUpgrades;
		saveData.highScores = highScores;
		return saveData;
	}
	
	private void updateLocalData(AsteroidsSaveData saveData)
	{
		fullscreen = saveData.fullscreen;
		screenWidth = saveData.screenWidth;
		screenHeight = saveData.screenHeight;
		credits = saveData.credits;
		currentShip = saveData.currentShip;
		currentBullet = saveData.currentBullet;
		shipUpgrades = saveData.shipUpgrades;
		bulletUpgrades = saveData.bulletUpgrades;
		highScores = saveData.highScores;
	}
	
	public void changeState(ViewPanel state)
	{
		this.state = state;
		frame.changeViewState();
	}
	
	public static int confirmationMessage(String message, String header)
	{
		int result = JOptionPane.showConfirmDialog(messagePanel, message, header, JOptionPane.OK_CANCEL_OPTION);
		
		return result;
	}
	
	public ViewPanel getState()
	{
		return state;
	}
	
	public AsteroidsFrame getFrame()
	{
		return frame;
	}
	
}
