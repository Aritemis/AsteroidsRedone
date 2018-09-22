/**
 *	@author Ariana Fairbanks
 */

package view_images;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public enum Images
{
	LOSE("lose.png"), WIN("win.png"), ANY("any.png"), PAUSE("pause.png"), PROCEED("enter.png");
	
	public BufferedImage image;
	
	private Images(String imageString)
	{
		try 
		{
			image = ImageIO.read(this.getClass().getResourceAsStream("lose.png"));
		} 
		catch (Exception e) 
		{
			System.out.println(imageString + " failed to load.");
		}
	}
	
}
