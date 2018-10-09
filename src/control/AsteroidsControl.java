/**
 *	@author Ariana Fairbanks
 */

package control;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import model.HighScore;
import model_enum.BulletType;
import model_enum.ShipType;
import view.AsteroidsFrame;
import view.ColorScheme;
import view.ViewPanel;
import view_images.Images;

public class AsteroidsControl
{
	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	
	public static final int refreshTime = 10;
	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 600;

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
	}
	
	public void resetLocalData()
	{
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
		return JOptionPane.showConfirmDialog(messagePanel, message, header, JOptionPane.OK_CANCEL_OPTION);
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
