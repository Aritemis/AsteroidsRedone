/**
 *	@author Ariana Fairbanks
 */

package view_menus;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import control.AsteroidsControl;
import view.AsteroidsPanel;

public class AsteroidsSettings extends AsteroidsPanel
{
	private AsteroidsControl base;
	
	public AsteroidsSettings(AsteroidsControl base)
	{
		this.base = base;
		
		setUpLayout();
	}
	
	public void setUpLayout()
	{
		setBackground(Color.BLACK);
	}
	
	@Override 
	public void paint(Graphics brush)
	{
		super.paint(brush);
		paintSides(brush);
	}
}
