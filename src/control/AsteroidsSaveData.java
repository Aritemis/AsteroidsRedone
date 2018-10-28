/**
 *	@author Ariana Fairbanks
 */

package control;

import java.io.Serializable;

import model.HighScore;
import model_enum.BulletType;
import model_enum.ShipType;

public class AsteroidsSaveData implements Serializable
{
	private static final long serialVersionUID = 1311671246780244461L;
	
	int credits;
	ShipType currentShip;
	BulletType currentBullet;
	Integer[] shipUpgrades;
	Integer[] bulletUpgrades;
	HighScore[] highScores;
	boolean fullscreen;
	int screenWidth;
	int screenHeight;
}
