/**
 *	@author Ariana Fairbanks
 */

package view;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import control.AsteroidsControl;
import view_games.AsteroidsArcade;
import view_menus.AsteroidsStartMenu;

public class AsteroidsFrame extends JFrame
{
	
	private static final long serialVersionUID = -2263772258599361367L;
	private AsteroidsControl base;
	private JPanel panel;
	
	public AsteroidsFrame(AsteroidsControl base)
	{
		this.base = base;
		this.panel = new AsteroidsStartMenu(base);
		setSize(AsteroidsControl.SCREEN_WIDTH, AsteroidsControl.SCREEN_HEIGHT);
		//setExtendedState(Frame.MAXIMIZED_BOTH);
		setVisible(true);
		setResizable(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
		setUpListeners();
	}
	
	private void setUpListeners()
	{
		this.addWindowListener(new java.awt.event.WindowAdapter() 
		{
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) 
		    {
		    	base.writeData();
		    	System.exit(0);
		    }
		});
	}
	
	public void changeViewState()
	{
		ViewPanel state = base.getState();
		switch (state)
		{
			case MENU:
				panel.removeAll();
				panel = new AsteroidsStartMenu(base);
				break;
			case ARCADE:
				panel.removeAll();
				panel = new AsteroidsArcade(base);
				break;
			default:
				System.out.println("Something went wrong when changing views.");
				break;
		}
		setContentPane(panel);
		panel.revalidate();
		panel.repaint();
	}

}
