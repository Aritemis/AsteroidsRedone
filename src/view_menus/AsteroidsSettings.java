/**
 *	@author Ariana Fairbanks
 */

package view_menus;

import java.awt.Color;
import javax.swing.JPanel;
import control.AsteroidsControl;

public class AsteroidsSettings extends JPanel
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
}
