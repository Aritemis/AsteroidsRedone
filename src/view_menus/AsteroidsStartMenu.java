/**
 *	@author Ariana Fairbanks
 */

package view_menus;

import javax.swing.JPanel;
import javax.swing.Timer;

import control.AsteroidsControl;
import model_enum.StarType;
import model_objects.Star;
import view.Animation;
import view.ViewPanel;

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
	private AsteroidsControl base;
	private JButton arcadeModeButton;
	private JButton campaignModeButton;
	private JButton optionsButton;
	private ArrayList<Star> stars;
	private Timer repaintTimer;
	private ActionListener repainter;
	private double starRotation;
	
	public AsteroidsStartMenu(AsteroidsControl base)
	{
		this.base = base;
		arcadeModeButton = new JButton("ARCADE");
		campaignModeButton = new JButton("CAMPAIGN");
		optionsButton = new JButton("OPTIONS");
		stars = Star.createDefaultStars();
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
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 20, 0, 20, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
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
		GridBagConstraints gbc_arcadeModeButton = new GridBagConstraints();
		gbc_arcadeModeButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_arcadeModeButton.insets = new Insets(0, 0, 5, 5);
		gbc_arcadeModeButton.gridx = 1;
		gbc_arcadeModeButton.gridy = 6;
		add(arcadeModeButton, gbc_arcadeModeButton);
		
		campaignModeButton.setBorderPainted(false);
		campaignModeButton.setFocusPainted(false);
		campaignModeButton.setFont(new Font("Monospaced", Font.BOLD, 30));
		campaignModeButton.setForeground(Color.BLUE);
		campaignModeButton.setContentAreaFilled(false);
		GridBagConstraints gbc_campaignModeButton = new GridBagConstraints();
		gbc_campaignModeButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_campaignModeButton.insets = new Insets(0, 0, 5, 5);
		gbc_campaignModeButton.gridx = 1;
		gbc_campaignModeButton.gridy = 8;
		add(campaignModeButton, gbc_campaignModeButton);
		
		optionsButton.setBorderPainted(false);
		optionsButton.setFocusPainted(false);
		optionsButton.setFont(new Font("Monospaced", Font.BOLD, 30));
		optionsButton.setForeground(Color.BLUE);
		optionsButton.setContentAreaFilled(false);
		GridBagConstraints gbc_optionsButton = new GridBagConstraints();
		gbc_optionsButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_optionsButton.insets = new Insets(0, 0, 5, 5);
		gbc_optionsButton.gridx = 1;
		gbc_optionsButton.gridy = 10;
		add(optionsButton, gbc_optionsButton);
	}
	
	public void setUpListeners()
	{
		arcadeModeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				base.changeState(ViewPanel.ARCADE);
			}
		});
		
		campaignModeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//base.changeState();
			}
		});
		
		optionsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				base.changeState(ViewPanel.SETTINGS);
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
