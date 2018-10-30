/**
 * @author Ariana Fairbanks
 */

package view;

import java.awt.Color;

import control.AsteroidsControl;

public class ColorScheme
{
	public static Color shipNLine1;
	public static Color shipNLine2;
	public static Color shipNFill1;
	public static Color shipNFill2;
	
	public static Color shipDLine1;
	public static Color shipDLine2;
	public static Color shipDFill1;
	public static Color shipDFill2;
	
	public static Color bulletN1;
	public static Color bulletN2;
	
	public static Color bulletD1;
	public static Color bulletD2;
	
	public static Color shipLine;
	public static Color shipFill;
	public static Color bullet;
	
	private static int colorPosition;
	private static int colorPosition2;
	
	
	public static void setColorDefaults()
	{
		shipNLine1 = Color.cyan;
		shipNLine2 = Color.blue;
		shipNFill1 = Color.black;
		shipNFill2 = Color.black;
		
		shipDLine1 = Color.red;
		shipDLine2 = Color.white;
		shipDFill1 = Color.black;
		shipDFill2 = Color.black;
		
		bulletN1 = shipNLine1;
		bulletN2 = shipNLine2;
		
		bulletD1 = shipDLine1;
		bulletD2 = shipDLine2;
	}
	
	public static void normalColor()
	{
		shipLine = shipNLine1;
		shipFill = shipNFill1;
		bullet = bulletN1;
		if(AsteroidsControl.flashingColors)
		{
			if(colorPosition > 4)
			{
				shipLine = shipNLine2;
				shipFill = shipNFill2;
				bullet = bulletN2;
				if(colorPosition > 7)
				{
					colorPosition = -1;
				}
			}
			colorPosition++;
		}
	}
	
	public static void damagedColor()
	{
		shipLine = shipDLine1;
		shipFill = shipDFill1;
		bullet = bulletD1;
		if(AsteroidsControl.flashingColors)
		{
			if(colorPosition2 > 4)
			{
				shipLine = shipDLine2;
				shipFill = shipDFill2;
				bullet = bulletD2;
				if(colorPosition2 > 7)
				{
					colorPosition2 = -1;
				}
			}
			colorPosition2++;
		}
	}
	
}
