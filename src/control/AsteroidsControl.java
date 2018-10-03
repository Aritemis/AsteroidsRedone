/**
 *	@author Ariana Fairbanks
 */

package control;

import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import model.HighScore;
import model_abstracts.Point;
import model_enum.AsteroidType;
import model_enum.BulletType;
import model_enum.ShipType;
import model_enum.StarType;
import model_enum.States;
import model_objects.Asteroid;
import model_objects.Star;
import view.AsteroidsFrame;
import view.ColorPreset;
import view_images.Images;

public class AsteroidsControl
{
	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	public static final int refreshTime = 10;

	public static boolean paused;
	public static boolean limbo; //continue?
	public static boolean reset; //set up next level
	public static boolean menu;
	public static boolean move;
	
	public static JPanel messagePanel;
	public Images images;
	
	
	public static ColorPreset colors = ColorPreset.OBLUE; 
	
	private static byte campaignLevel;
	private static int credits;
	private static ShipType currentShip;
	private static BulletType currentBullet;
	private static Integer[] shipUpgrades;
	private static Integer[] bulletUpgrades;
	private static HighScore[] highScores;
	
	
	
	private AsteroidsFrame frame;
	private States state;
	
	private File dataFile;
	private JsonObject data;
	private JsonReader parser;
	private FileReader fileReader;
	private FileWriter fileWriter;
	
	public void start()
	{
		dataFile = new File(System.getProperty("user.home") + "/.AsteroidsData.json");
		resetLocalData();
		loadData();
		resetGameVariables();
		messagePanel = new JPanel();
		images = new Images();
		frame = new AsteroidsFrame(this);
	}
	
	public void resetLocalData()
	{
		campaignLevel = 0;
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
	
	public void loadData()
	{
		if(dataFile.exists())
		{
			System.out.println("yes");
			readData();
		}
		else
		{
			writeData();
		}
	}
	
	public void readData()
	{
		try
		{
			fileReader = new FileReader(dataFile);
			parser = Json.createReader(fileReader);
			data = parser.readObject();
			parser.close();
			fileReader.close();
			printData();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void writeData()
	{
		try
		{
			updateJsonData();
			if(dataFile.exists())
			{ dataFile.delete(); }
			dataFile.createNewFile();
			fileWriter = new FileWriter(dataFile);
			fileWriter.write(data.toString());
            fileWriter.close();
		} 
		catch (IOException e)
		{
			System.out.println("Data not written.");
			e.printStackTrace();
		}
	}
	
	public void printData()
	{
		System.out.println(data.toString());
	}
	
	public void updateJsonData()
	{
		JsonArray sUpgrades = Json.createArrayBuilder().build();
		if(shipUpgrades.length > 0)
		{
			sUpgrades = Json.createArrayBuilder(Arrays.asList((Object[])shipUpgrades)).build();
		}
		JsonArray bUpgrades = Json.createArrayBuilder().build();
		if(bulletUpgrades.length > 0)
		{
			bUpgrades = Json.createArrayBuilder(Arrays.asList(bulletUpgrades)).build();
		}
		//JsonArray hScores = Json.createArrayBuilder(Arrays.asList(bulletUpgrades)).build();
		data = Json.createObjectBuilder()
				.add("campaignLevel", campaignLevel)
				.add("credits", credits)
				.add("currentShip", currentShip.ordinal())
				.add("currentBullet", currentBullet.ordinal())
				.add("shipUpgrades", sUpgrades)
				.add("bulletUpgrades", bUpgrades)
				//.add("highScores", hScores)
				.build();
	}
	
	public void updateLocalData()
	{
		campaignLevel = (byte) data.getInt("campaignLevel");
		credits = data.getInt("credits");
		currentShip = ShipType.values()[data.getInt("currentShip")];
		currentBullet = BulletType.values()[data.getInt("currentBullet")];
		JsonArray sUpgrades = data.getJsonArray("shipUpgrades");
		for(int i = 0; i < sUpgrades.size(); i ++) 
		{
			shipUpgrades[i] = sUpgrades.getInt(i);
		}
		JsonArray bUpgrades = data.getJsonArray("bulletUpgrades");
		for(int i = 0; i < bUpgrades.size(); i ++) 
		{
			bulletUpgrades[i] = bUpgrades.getInt(i);
		}
	}
	
	public void changeState(States state)
	{
		this.state = state;
		frame.changeViewState();
	}
	
	public ArrayList<Star> createStars(ArrayList<Star> stars, int numberOfStars, StarType type) 
	{
		for(int i = 0; i < numberOfStars; ++i) 
		{
			Point center = new Point(ThreadLocalRandom.current().nextDouble() * SCREEN_WIDTH, ThreadLocalRandom.current().nextDouble() * SCREEN_HEIGHT);
			int radius = ThreadLocalRandom.current().nextInt(type.minRadius, type.maxRadius + 1);
			stars.add(new Star(center, radius, type.speed));
		}
		return stars;
	 }
	
	public ArrayList<Asteroid> generateArcadeAsteroids(ArrayList<Asteroid> asteroids, int level, int baseScore)
	{
		int totalAsteroids = 5 + (level / 5);
		int speedModifier = 1 + (level / 10);
		int healthModifier = 1 + (level / 15);
		asteroids = createAsteroids(asteroids, totalAsteroids, baseScore, speedModifier, healthModifier, AsteroidType.STANDARD);
		return asteroids;
	}
	
	public ArrayList<Asteroid> createAsteroids(ArrayList<Asteroid> asteroids, int number, int baseScore, double speedModifier, double healthModifier, AsteroidType type) 
	{
		for(int i = 0; i < number; ++i) 
		{
			// Create random asteroids by sampling points on a circle
			// Find the radius first.
			int radius = ThreadLocalRandom.current().nextInt(type.minWidth, type.maxWidth + 1);

			// Find the circles angle
			double angle = (ThreadLocalRandom.current().nextDouble() * Math.PI * 1.0/2.0);
			if(angle < Math.PI * 1.0/5.0)
			{
				angle += Math.PI * 1.0/5.0;
			}
			// Sample and store points around that circle
			ArrayList<Point> asteroidSides = new ArrayList<Point>();
			double originalAngle = angle;
			while(angle < 2*Math.PI) 
			{
				double x = Math.cos(angle) * radius;
				double y = Math.sin(angle) * radius;
				asteroidSides.add(new Point(x, y));
				angle += originalAngle;
			}
			Point[] inSides = asteroidSides.toArray(new Point[asteroidSides.size()]);
			double x = ThreadLocalRandom.current().nextDouble() * SCREEN_WIDTH;
			double y = ThreadLocalRandom.current().nextDouble() * SCREEN_HEIGHT;
			if(ThreadLocalRandom.current().nextDouble() > .5) { x = 100; }
			else { y = 100; }
			Point inPosition = new Point(x, y);
			double inRotation = ThreadLocalRandom.current().nextDouble() * 360;
			double speed = type.baseSpeed * speedModifier;
			int health = (int) (type.baseHealth * healthModifier);
			int score = (int) (speed * health * 10);
			asteroids.add(new Asteroid(inSides, inPosition, inRotation, type, type.maxWidth, speed, health, score));
		}
		return asteroids;
	}
	
	public static int confirmationMessage(String message, String header)
	{
		return JOptionPane.showConfirmDialog(messagePanel, message, header, JOptionPane.OK_CANCEL_OPTION);
	}
	
	public States getState()
	{
		return state;
	}
	
	public AsteroidsFrame getFrame()
	{
		return frame;
	}
	
}
