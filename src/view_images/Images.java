/**
 *	@author Ariana Fairbanks
 */

package view_images;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Images
{	
	public static BufferedImage lose;
	public static BufferedImage win;
	public static BufferedImage any;
	public static BufferedImage pause;
	public static BufferedImage proceed;
	public static BufferedImage gameover;
	public static BufferedImage levelclear;
	
	public Images()
	{
		try 
		{
			lose = ImageIO.read(this.getClass().getResourceAsStream("lose.png"));
			win = ImageIO.read(this.getClass().getResourceAsStream("win.png"));
			any = ImageIO.read(this.getClass().getResourceAsStream("any.png"));
			pause = ImageIO.read(this.getClass().getResourceAsStream("pause.png"));
			proceed = ImageIO.read(this.getClass().getResourceAsStream("enter.png"));
			gameover = ImageIO.read(this.getClass().getResourceAsStream("gameover.png"));
			levelclear = ImageIO.read(this.getClass().getResourceAsStream("levelclear.png"));
		} 
		catch (Exception e) {}
	}
}
