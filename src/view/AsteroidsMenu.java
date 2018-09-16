/**
 *	@author Ariana Fairbanks
 */

package view;

import javax.swing.JPanel;
import javax.swing.Timer;

import control.AsteroidsControl;
import model.Star;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Graphics;

public class AsteroidsMenu extends JPanel implements Animation
{
	private static final long serialVersionUID = 3738683657239431767L;
	private AsteroidsControl base;
	private JButton newGameButton;
	private Star[] stars;
	private Timer repaintTimer;
	private ActionListener repainter;
	
	public AsteroidsMenu(AsteroidsControl base)
	{
		this.base = base;
		newGameButton = new JButton("Start New Game");
		stars = base.createStars(200, 5);
		
		setUpLayout();
		setUpListeners();
		setUpTimers();
		startTimers();
	}
	
	public void setUpLayout()
	{
		setBackground(Color.BLACK);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		GridBagConstraints gbc_newGameButton = new GridBagConstraints();
		gbc_newGameButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_newGameButton.insets = new Insets(0, 0, 5, 5);
		gbc_newGameButton.gridx = 1;
		gbc_newGameButton.gridy = 2;
		add(newGameButton, gbc_newGameButton);
	}
	
	public void setUpListeners()
	{
		newGameButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				base.changeState(States.GAME);
			}
		});
	}
	
	public void setUpTimers()
	{
		repainter = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				repaint();
			}
		};
		repaintTimer = new Timer(20, repainter);
		repaintTimer.setRepeats(true);
	}
	
	public void refreshStars(Graphics g)
	{
		for(Star star : stars)
		{
			star.paint(g, 45);
		}
	}

	@Override
	public void startTimers()
	{
		repaintTimer.start();
		
	}

	@Override
	public void stopTimers()
	{
		repaintTimer.stop();
		
	}
	
	@Override
	public void paint(Graphics g)
	{
		//g.drawImage(backgroundImg,0,0,this);

		super.paint(g);
		refreshStars(g);
	}
	
}
