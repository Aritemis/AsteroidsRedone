/**
 *	@author Ariana Fairbanks
 */

package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import control.AsteroidsControl;

public class AsteroidsPanel extends JPanel
{
	
	protected void paintSides(Graphics brush)
	{
		if(AsteroidsControl.screenBoundaryLeft > 0)
		{
			brush.setColor(Color.black);
			brush.fillRect(0, 0, AsteroidsControl.screenBoundaryLeft, AsteroidsControl.screenHeight);
			brush.fillRect(AsteroidsControl.screenBoundaryRight, 0, AsteroidsControl.screenBoundaryLeft, AsteroidsControl.screenHeight);
			brush.setColor(Color.white);
			brush.drawRect(AsteroidsControl.screenBoundaryLeft, 0, AsteroidsControl.screenHeight, AsteroidsControl.screenHeight - 30);
		}
	}
	
}
