/**
 *	@author Ariana Fairbanks
 */

package view_menus;

import javax.swing.JPanel;
import javax.swing.Timer;

import control.AsteroidsControl;
import model_enum.StarType;
import model_enum.States;
import model_game.Star;
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

public class AsteroidsUserSelect extends JPanel implements Animation
{
	private static final long serialVersionUID = 3738683657239431767L;
	private AsteroidsControl base;
	private JButton newUser;
	private JButton returningUser;
	private ArrayList<Star> stars;
	private Timer repaintTimer;
	private ActionListener repainter;
	private double starRotation;
	
	public AsteroidsUserSelect(AsteroidsControl base)
	{
		this.base = base;
		newUser = new JButton("NEW USER");
		returningUser = new JButton("USER MODE");
		stars = base.createStars(new ArrayList<Star>(), 100, StarType.FAST);
		stars = base.createStars(stars, 50, StarType.STANDARD);
		stars = base.createStars(stars, 25, StarType.SLOW);
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
		
		newUser.setBorderPainted(false);
		newUser.setFocusPainted(false);
		newUser.setFont(new Font("Monospaced", Font.BOLD, 30));
		newUser.setForeground(Color.BLUE);
		newUser.setContentAreaFilled(false);
		GridBagConstraints gbc_newUser = new GridBagConstraints();
		gbc_newUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_newUser.insets = new Insets(0, 0, 5, 5);
		gbc_newUser.gridx = 1;
		gbc_newUser.gridy = 6;
		add(newUser, gbc_newUser);

		returningUser.setBorderPainted(false);
		returningUser.setFocusPainted(false);
		returningUser.setFont(new Font("Monospaced", Font.BOLD, 30));
		returningUser.setForeground(Color.BLUE);
		returningUser.setContentAreaFilled(false);
		GridBagConstraints gbc_returningUser = new GridBagConstraints();
		gbc_returningUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_returningUser.insets = new Insets(0, 0, 5, 5);
		gbc_returningUser.gridx = 1;
		gbc_returningUser.gridy = 8;
		add(returningUser, gbc_returningUser);
	}
	
	public void setUpListeners()
	{
		newUser.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		
		returningUser.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
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
