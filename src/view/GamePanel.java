/**
 *	@author Ariana Fairbanks
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import control.AsteroidsControl;


public class GamePanel extends JPanel implements Animation
{
	private static final long serialVersionUID = 4338632920192192267L;
	private AsteroidsControl base;
	private byte pause;
	private SpringLayout theLayout;
	private JLabel timerLabel;
	private JLabel scoreLabel;
	private JButton menu;
	private JButton help;
	private Timer fishTimer;
	private Timer displayTime;
	private ActionListener fishMover;
	private ActionListener timeDisplayer;
	
	private int fishSpeed;
	
	private boolean playing;
	private boolean reset;
	private boolean miss;

	public GamePanel(AsteroidsControl base)
	{
		this.base = base;
		
		theLayout = new SpringLayout();
		timerLabel = new JLabel("Time");
		scoreLabel = new JLabel("Score: 0");
		menu = new JButton(" Exit ");
		help = new JButton(" Help ");
		
		fishSpeed = 40;
		pause = 0;
		
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setUpLayout();
		playGame();
		setUpListeners();
		setUpTimers();

	}
	
	private void setUpLayout()
	{
		setLayout(theLayout);

		timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timerLabel.setForeground(new Color(70, 130, 180));
		timerLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		theLayout.putConstraint(SpringLayout.NORTH, timerLabel, 10, SpringLayout.NORTH, this);
		theLayout.putConstraint(SpringLayout.WEST, timerLabel, 10, SpringLayout.WEST, this);

		scoreLabel.setFont(new Font("Arial", Font.PLAIN, 35));
		scoreLabel.setForeground(new Color(70, 130, 180));
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		theLayout.putConstraint(SpringLayout.NORTH, scoreLabel, 0, SpringLayout.NORTH, menu);
		theLayout.putConstraint(SpringLayout.EAST, scoreLabel, -20, SpringLayout.WEST, help);

		menu.setFont(new Font("Arial", Font.PLAIN, 30));
		menu.setForeground(new Color(70, 130, 180));
		menu.setBackground(new Color(70, 130, 180));
		menu.setFocusPainted(false);
		menu.setContentAreaFilled(false);
		menu.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		theLayout.putConstraint(SpringLayout.NORTH, menu, 10, SpringLayout.NORTH, this);
		theLayout.putConstraint(SpringLayout.EAST, menu, -10, SpringLayout.EAST, this);
		
		help.setFont(new Font("Arial", Font.PLAIN, 30));
		help.setForeground(new Color(70, 130, 180));
		help.setBackground(new Color(70, 130, 180));
		help.setFocusPainted(false);
		help.setContentAreaFilled(false);
		help.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		theLayout.putConstraint(SpringLayout.NORTH, help, 0, SpringLayout.NORTH, menu);
		theLayout.putConstraint(SpringLayout.EAST, help, -20, SpringLayout.WEST, menu);
		
		add(timerLabel);
		add(scoreLabel);
		add(menu);
		add(help);
	}

	private void playGame()
	{
/* asteroids 
		ArrayList<Integer> answerOptions = new ArrayList<Integer>();
		for (int i = 0; i < maxFishVertical; i++)
		{
			int fishAnswer = Controller.rng.nextInt(questionBase);
			while (fishAnswer == answer || answerOptions.contains(fishAnswer))
			{
				fishAnswer = Controller.rng.nextInt(questionBase);
			}
			if (i == randomPlacement)
			{
				fishAnswer = answer;
			}
			answerOptions.add(fishAnswer);
			currentFish.add(new FishObject(fishAnswer, i, this, fishIcon));
		}
		remove(background);
		addFish();
		add(background);
		playing = true;
		reset = false;
		*/
	}
	
	private void setUpListeners()
	{
		menu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				//stopTimers();
				//playing = false;
				/*
				int dialogResult = base.confirmationMessage("Your score is " +  " . Would you like to exit the game?", "Exit game?");
				if(dialogResult == JOptionPane.OK_OPTION)
				{

				}
				else
				{
					startTimers();
					playing = true;
				}
				*/
			}
		});

	}

	private void setUpTimers()
	{
		/*
		currentTime = gamePeriod - 1;
		timeDisplayer = new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (playing)
				{
					currentTime--;
				}
				if ((currentTime % 60) < 10)
				{
					timerLabel.setText("Time: " + (int) (currentTime / 60) + ":0" + (int) (currentTime % 60));
				}
				else
				{
					timerLabel.setText("Time: " + (int) (currentTime / 60) + ":" + (int) (currentTime % 60));
				}
				if (!playing && reset)
				{
					playing = true;
					clearCurrentFish();
					//remove(background);
					playGame();
				}
				else if (!playing)
				{
					reset = true;
				}
				if (currentTime == 0)
				{
					playing = false;
					timerLabel.setText("Time: 0:00");
					stopTimers();
					if(questionsAnswered > 0)
					{
						base.addGameRecord(1, questionsAnswered, questionsCorrect, guesses, gamePeriod, score);
					}
					System.out.println("Time's up!");
					base.informationMessage("Your score was " + score + ".", "Time's up!");
					clearCurrentFish();
					base.returnToMenu();
				}
			}
		};
		displayTime = new Timer(1000, timeDisplayer);
		displayTime.setRepeats(true);

		fishMover = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if(playing)
				{
					moveFish();
					if(miss)
					{
						if (pause >= 15)
						{
							miss = false;
							pause = 0;
							moveFish();
							questionLabel.setText(question);
						}
						pause++;
					}
				}
			}
		};
		fishTimer = new Timer(fishSpeed, fishMover);
		fishTimer.setRepeats(true);

		startTimers();
		*/
	}
	

	private void updateScore(boolean correct)
	{/*
		if (correct)
		{
			questionsCorrect++;
			score += 50;
			scoreLabel.setText("Score: " + Integer.toString(score));
			questionLabel.setText(question.substring(0, question.indexOf("?")) + " " + answer + "  Correct!");
			feedbackLabel.setIcon(catchIcon);
			repaint();
			playing = false;
		}
		else
		{
			guesses++;
			if (score > 0)
			{
				score -= 5;
				scoreLabel.setText("Score: " + Integer.toString(score));
			}
			questionLabel.setText(question + " Try again!");
			miss = true;
		}
		*/
	}

	private void removeFish()
	{
		//currentFish.remove(fish);
		//this.remove(fish);
	}

	private void clearCurrentFish()
	{
		/*
		for (FishObject fish : currentFish)
		{
			this.remove(fish);
		}
		currentFish = new ArrayList<FishObject>();
		repaint();
		questionsAnswered++;
		*/
	}

	private void moveFish()
	{
		/*
		for (FishObject fish : currentFish)
		{
			fish.updateFishLocation();
			repaint(fish.getBounds());
		}
		*/
	}

	private void addFish()
	{
		/*
		for (FishObject fish : currentFish)
		{
			fish.setFocusPainted(false);
			fish.setFont(new Font("Ariel", Font.PLAIN, 20));
			fish.setForeground(Color.WHITE);
			add(fish);
		}
		*/
	}

	private void refreshFishLocation()
	{
		/*
		for (FishObject fish : currentFish)
		{
			fish.setLocation(fish.getXValue(), fish.getYValue());
		}
		*/
	}

	public void startTimers()
	{
		displayTime.start();
		fishTimer.start();
	}

	public void stopTimers()
	{
		displayTime.stop();
		fishTimer.stop();
	}

	@Override
	public void paint(Graphics g)
	{
		//g.drawImage(backgroundImg,0,0,this);
		refreshFishLocation();
		super.paint(g);
	}
}
