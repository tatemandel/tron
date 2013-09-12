import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Game implements Runnable {
  
	// true if the instructions page is in the frame
	private boolean instructOn = false;
	// true if the high scores are displayed in the frame
	private boolean scoresOn = false;
	
	public void run() {
	   
      // Top-level frame
      final JFrame frame = new JFrame("Tron");
      frame.setBackground(Color.BLACK);
      frame.setPreferredSize(new Dimension(500,560));
      frame.setLocation(400, 100);
      frame.setResizable(false);
      
      /**
       * 
       *  Main Menu
       * 
       */
      // main menu panel
      final JPanel mainMenu = new JPanel();
      mainMenu.setLayout(new BorderLayout());
      mainMenu.setBackground(Color.BLACK);
      
      // main menu screen image
  	 @SuppressWarnings("serial")
      final JComponent pict = new JComponent() {
    	  public void paintComponent(Graphics gc) {
    		  super.paintComponent(gc);
    		  Picture.draw(gc, "tron0_0.jpg", 0, 0);
    	  }
      };
      // panel for main menu buttons
      final JPanel topMenu = new JPanel();
      topMenu.setLayout(new GridLayout(1,3));
      topMenu.setBackground(Color.BLACK);
      
      // buttons for main menu
	  final JButton play = new JButton(new ImageIcon("play_before.png"));
	  topMenu.add(play);
	  final JButton instructions = new JButton(
			  new ImageIcon("instructions_before.png"));
	  topMenu.add(instructions);
	  final JButton quit = new JButton(
			  new ImageIcon("quit_before.png"));
	  topMenu.add(quit);
	  
	  // adds components to the main menu panel
	  mainMenu.add(pict, BorderLayout.CENTER);
	  mainMenu.add(topMenu, BorderLayout.SOUTH);
	  
	  // adds main menu panel to the frame
	  frame.add(mainMenu);
	  
	  // instructions image
	 @SuppressWarnings("serial")
	  final JComponent instrPict = new JComponent() {
		  public void paintComponent(Graphics gc) {
			  super.paintComponent(gc);
			  Picture.draw(gc, "instructions_page.png", 0, 0);
		  }
	  };
	  
	  /**
	   * 
	   *  Menu from 'Play'
	   * 
	   */
	  // play menu panel that replaces the main menu panel
	  final JPanel playMenu = new JPanel();
	  playMenu.setLayout(new BorderLayout());
	  playMenu.setBackground(Color.BLACK);
	  
	  // panel that displays an image or high scores and game type buttons
	  final JPanel playMenuUpper = new JPanel();
	  playMenuUpper.setLayout(new GridLayout(2, 1));
	  playMenuUpper.setBackground(Color.BLACK);
	 
	  // panel that holds the buttons for each game type
	  final JPanel modes = new JPanel();
	  modes.setLayout(new GridLayout(1,3));
	  modes.setBackground(Color.BLACK);
	  
	  // buttons for playMenuUpper
	  final JButton story = new JButton(new ImageIcon("story.png"));
	  modes.add(story);
	  final JButton survival = new JButton(new ImageIcon("survival.png"));
	  modes.add(survival);
	  final JButton twoPlayer = new JButton(new ImageIcon("two_player.png"));
	  modes.add(twoPlayer);
	  
	  // an image to be displayed by playMenuUpper
	 @SuppressWarnings("serial")
	  final JComponent menuPict = new JComponent() {
		 public void paintComponent(Graphics gc) {
			 super.paintComponent(gc);
			 Picture.draw(gc, "play_menu.jpg", 0, 0);
		 }
	  };
	  
	  // adds panels to playMenuUpper
	  playMenuUpper.add(modes);
	  playMenuUpper.add(menuPict);
	  
	  // panel that hold high score and main menu buttons
	  final JPanel bottomMenu = new JPanel();
	  bottomMenu.setBackground(Color.BLACK);
	  
	  // buttons for bottomMenu
	  final JButton highScores = new JButton(new ImageIcon("high_scores.png"));
	  bottomMenu.add(highScores);
	  final JButton back = new JButton(new ImageIcon("main_menu.png"));
	  bottomMenu.add(back);
	  
	  // adds bottomMenu and playMenuUpper to playMenu
	  playMenu.add(playMenuUpper, BorderLayout.CENTER);	  
	  playMenu.add(bottomMenu, BorderLayout.SOUTH);
	  
	  /**
	   * 
	   *  Survival Level Menu
	   * 
	   */
	  // panel that holds the buttons and label for the survival mode
	  final JPanel survMenu = new JPanel();
	  survMenu.setLayout(new GridLayout(1,3));
	  survMenu.setBackground(Color.BLACK);
	  
	  // label for the score and boost left
	  final JLabel score = new JLabel("   Score: 0" + "   Boost: 3");
	  score.setForeground(Color.WHITE);
	  score.setBackground(Color.BLACK);
	  survMenu.add(score);
	  
	  
	  // buttons for returning to the menu and reseting the game
	  final JButton reset = new JButton(new ImageIcon("restart.png"));
	  survMenu.add(reset);
	  final JButton exit = new JButton(new ImageIcon("play_before.png"));
	  survMenu.add(exit);
	  
	  // the survival level
      final TronMapSurvival levelSurv = new TronMapSurvival(score, 1);
      levelSurv.setBorder(BorderFactory.createLineBorder(Color.WHITE));
      
      /**
       * 
       *  Two-player Level Menu
       * 
       */
	  // panel that holds the buttons and labels for two-player mode
	  final JPanel twoMenu = new JPanel();
	  twoMenu.setLayout(new GridLayout(1,4));
	  twoMenu.setBackground(Color.BLACK);
	  
	  // panel that holds the scores and boost for each player
	  final JPanel scoresTwo = new JPanel();
	  scoresTwo.setLayout(new GridLayout(2,1));
	  scoresTwo.setBackground(Color.BLACK);
	  
	  // the score labels for each player
	  final JLabel scoreTwo1 = new JLabel("   Player 1: 0" + "    Boost: 3");
	  scoreTwo1.setForeground(Color.WHITE);
	  scoreTwo1.setBackground(Color.BLACK);
	  scoresTwo.add(scoreTwo1);
	  final JLabel scoreTwo2 = new JLabel("   Player 2: 0" + "    Boost: 3");
	  scoreTwo2.setForeground(Color.WHITE);
	  scoreTwo2.setBackground(Color.BLACK);
	  scoresTwo.add(scoreTwo2);
	  twoMenu.add(scoresTwo);
	  
	  // the reset and main menu buttons for two-player mode
	  final JButton resetTwo = new JButton(new ImageIcon("restart.png"));
	  twoMenu.add(resetTwo);
	  final JButton exitTwo = new JButton(new ImageIcon("play_before.png"));
	  twoMenu.add(exitTwo);
	  
	  // the two-player level
      final TronMapTwoPlayer levelTwoPlayer = 
    		  new TronMapTwoPlayer(scoreTwo1, scoreTwo2, 2);
      levelTwoPlayer.setBorder(BorderFactory.createLineBorder(Color.WHITE));
	 
      /**
       * 
       *  Story Level Menu
       * 
       */
      // panel that holds the buttons and labels for story mode
	  final JPanel storyMenu = new JPanel();
	  storyMenu.setLayout(new GridLayout(1,4));
	  storyMenu.setBackground(Color.BLACK);
	  
	  // panel that holds the score, level number, and boost
	  final JPanel scoresStory = new JPanel();
	  scoresStory.setLayout(new GridLayout(2,1));
	  scoresStory.setBackground(Color.BLACK);

	  // labels for score, level, and boost left
	  final JLabel scoreStory1 = 
			  new JLabel("     Score: = 0" + "    Level: 1");
	  scoreStory1.setForeground(Color.WHITE);
	  scoreStory1.setBackground(Color.BLACK);
	  scoresStory.add(scoreStory1);
	  final JLabel scoreStory2 = 
			  new JLabel("             Boost: 3");
	  scoreStory2.setForeground(Color.WHITE);
	  scoreStory2.setBackground(Color.BLACK);
	  scoresStory.add(scoreStory2);
	  storyMenu.add(scoresStory);
	  
	  // buttons for reseting the game and returning to the play menu
	  final JButton resetStory = new JButton(new ImageIcon("restart.png"));
	  storyMenu.add(resetStory);
	  final JButton exitStory = new JButton(new ImageIcon("play_before.png"));
	  storyMenu.add(exitStory);
	  
	  // the story level
      final TronMapStory levelStory = 
    		  new TronMapStory(scoreStory1, scoreStory2, 2);
      levelStory.setBorder(BorderFactory.createLineBorder(Color.WHITE));
      
      /**
       *						    										
       *  Adding action listeners 					
       *						  										    
       */
	  play.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  frame.remove(mainMenu);
			  frame.add(playMenu);
			  frame.update(frame.getGraphics());
			  playMenu.revalidate();
		  }
	  });
	  
	  instructions.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  if (instructOn) {
				  mainMenu.remove(instrPict);
				  mainMenu.add(pict);
				  instructions.setIcon(
						  new ImageIcon("instructions_before.png"));
			  } else if (!instructOn) {
				  mainMenu.remove(pict);
				  mainMenu.add(instrPict);
				  instructions.setIcon(new ImageIcon("main_menu.png"));
			  }
			  mainMenu.revalidate();
			  frame.repaint();
			  instructOn = !instructOn;
		  }
	  });
	  
	  quit.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  System.exit(1);
		  }
	  });
	  
	  highScores.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  if (scoresOn) {
				  playMenuUpper.removeAll();
				  playMenuUpper.add(modes);
				  playMenuUpper.add(menuPict);
			  } else if (!scoresOn) {
				  playMenuUpper.remove(menuPict);
				  playMenuUpper.add(levelSurv.getHighs());
			  }
			  scoresOn = !scoresOn;
			  playMenuUpper.revalidate();
			  frame.repaint();
		  }
	  });
	  
	  back.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  frame.remove(playMenu);
			  frame.add(mainMenu);
			  frame.update(frame.getGraphics());
		  }
	  });
	  
	  survival.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  frame.remove(playMenu);
			  frame.setLayout(new BorderLayout());
			  frame.add(levelSurv, BorderLayout.CENTER);
			  frame.add(survMenu, BorderLayout.SOUTH);
			  frame.update(frame.getGraphics());
			  levelSurv.requestFocusInWindow();
			  levelSurv.revalidate();
			  levelSurv.reset();
		  }
	  });

	 
	  reset.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  levelSurv.reset();
		  }
	  });
	  
	  exit.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  frame.remove(levelSurv);
			  frame.remove(survMenu);
			  frame.add(playMenu);
			  frame.update(frame.getGraphics());
			  playMenu.revalidate();
		  }
	  });
	  
	  twoPlayer.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  frame.remove(playMenu);
			  frame.setLayout(new BorderLayout());
			  frame.add(levelTwoPlayer, BorderLayout.CENTER);
			  frame.add(twoMenu, BorderLayout.SOUTH);
			  frame.update(frame.getGraphics());
			  levelTwoPlayer.requestFocusInWindow();
			  levelTwoPlayer.revalidate();
			  levelTwoPlayer.reset();
		  }
	  });
	  
	  resetTwo.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  levelTwoPlayer.reset();
		  }
	  });
	  
	  exitTwo.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  frame.remove(levelTwoPlayer);
			  frame.remove(twoMenu);
			  frame.add(playMenu);
			  frame.update(frame.getGraphics());
			  playMenu.revalidate();
			  levelTwoPlayer.restartGame();
		  }
	  });
	  
	  story.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  frame.remove(playMenu);
			  frame.setLayout(new BorderLayout());
			  frame.add(levelStory, BorderLayout.CENTER);
			  frame.add(storyMenu, BorderLayout.SOUTH);
			  frame.update(frame.getGraphics());
			  levelStory.requestFocusInWindow();
			  levelStory.revalidate();
			  levelStory.reset();
		  }
	  });
	  
	  resetStory.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  levelStory.reset();
		  }
	  });
	  
	  exitStory.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			 // frame.setLayout(null);
			  frame.remove(levelStory);
			  frame.remove(storyMenu);
			  frame.add(playMenu);
			  frame.update(frame.getGraphics());
			  playMenu.revalidate();
		  }
	  });
	  
      // put the frame on the screen
      frame.pack();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);

      // start the game running
      levelSurv.reset();
      levelTwoPlayer.reset();
      levelStory.reset();
   }
   
   /*
    * Get the game started!
    */
   public static void main(String[] args) {
       SwingUtilities.invokeLater(new Game());
   }
}
