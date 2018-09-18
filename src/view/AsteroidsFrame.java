/**
 *	@author Ariana Fairbanks
 */

package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import control.AsteroidsControl;
import model_enum.States;
import view_games.AsteroidsArcade;
import view_menus.AsteroidsMenu;


public class AsteroidsFrame extends JFrame
{
	
	private static final long serialVersionUID = -2263772258599361367L;
	private AsteroidsControl base;
	private JPanel panel;
	
	public AsteroidsFrame(AsteroidsControl base)
	{
		this.base = base;
		this.panel = new AsteroidsMenu(base);
		setSize(AsteroidsControl.SCREEN_WIDTH, AsteroidsControl.SCREEN_HEIGHT);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
	}
	
	public void changeViewState()
	{
		States state = base.getState();
		switch (state)
		{
			case MENU:
				panel.removeAll();
				panel = new AsteroidsMenu(base);
				break;
			case GAME:
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
