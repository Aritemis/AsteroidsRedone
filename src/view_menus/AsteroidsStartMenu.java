/**
 *	@author Ariana Fairbanks
 */

package view_menus;

import javax.swing.JPanel;
import javax.swing.Timer;

import control.AsteroidsControl;
import model_enum.StarType;
import model_enum.States;
import model_objects.Star;
import view.Animation;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import javax.swing.JLabel;

public class AsteroidsStartMenu extends JPanel implements Animation
{
	private static final long serialVersionUID = 3738683657239431767L;
	private AsteroidsControl base;
	private JButton arcadeModeButton;
	private JButton userModeButton;
	private ArrayList<Star> stars;
	private Timer repaintTimer;
	private ActionListener repainter;
	private double starRotation;
	
	public AsteroidsStartMenu(AsteroidsControl base)
	{
		this.base = base;
		arcadeModeButton = new JButton("ARCADE");
		userModeButton = new JButton("USER MODE");
		stars = Star.createStars(new ArrayList<Star>(), 100, StarType.FAST);
		stars = Star.createStars(stars, 50, StarType.STANDARD);
		stars = Star.createStars(stars, 25, StarType.SLOW);
		starRotation = 0;
		
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
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblTitle = new JLabel("ASTEROIDS");
		lblTitle.setForeground(Color.CYAN);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 80));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 3;
		add(lblTitle, gbc_lblTitle);
		
		arcadeModeButton.setBorderPainted(false);
		arcadeModeButton.setFocusPainted(false);
		arcadeModeButton.setFont(new Font("Monospaced", Font.BOLD, 30));
		arcadeModeButton.setForeground(Color.BLUE);
		arcadeModeButton.setContentAreaFilled(false);
		GridBagConstraints gbc_arcadeGameButton = new GridBagConstraints();
		gbc_arcadeGameButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_arcadeGameButton.insets = new Insets(0, 0, 5, 5);
		gbc_arcadeGameButton.gridx = 1;
		gbc_arcadeGameButton.gridy = 6;
		add(arcadeModeButton, gbc_arcadeGameButton);
		
		userModeButton.setBorderPainted(false);
		userModeButton.setFocusPainted(false);
		userModeButton.setFont(new Font("Monospaced", Font.BOLD, 30));
		userModeButton.setForeground(Color.BLUE);
		userModeButton.setContentAreaFilled(false);
		GridBagConstraints gbc_userModeButton = new GridBagConstraints();
		gbc_userModeButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_userModeButton.insets = new Insets(0, 0, 5, 5);
		gbc_userModeButton.gridx = 1;
		gbc_userModeButton.gridy = 8;
		add(userModeButton, gbc_userModeButton);
	}
	
	public void setUpListeners()
	{
		arcadeModeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				base.changeState(States.ARCADE);
			}
		});
		
		userModeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//base.changeState();
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
			star.move(starRotation);
			star.paint(g);
			starRotation += .005;
			starRotation %= 360;
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
		super.paint(g);
		refreshStars(g);
	}
	
}
