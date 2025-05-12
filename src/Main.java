// Name: Jessica Ying
// Date: June 18, 2023
// Description: This is the main (driver) class for a program that is a rendition of
// Super Smash Bros Ultimate

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/* LEGEND for states
 * 0: title screen
 * 1: main screen (with options)
 * 2: select characters and map
 * 3: game
 * 4: stats
 * 5: instructions
 * 6: about
 * 7: settings
 * 8: winner

*/

@SuppressWarnings("serial")
public class Main extends JPanel implements ActionListener, KeyListener, Runnable {
	// Image path constants
	public static final String assetsFolder = "./assets/";
	public static final String imagesFolder = assetsFolder + "images/";
	public static final String ingameFolder = imagesFolder + "ingame/";
	public static final String menusFolder = imagesFolder + "menus/";
	public static final String instructionsFolder = menusFolder + "instructions/";
	public static final String mainmenuFolder = menusFolder + "mainmenu/";
	public static final String selectionsFolder = menusFolder + "selections/";
	public static final String settingsFolder = menusFolder + "settings/";
	public static final String keyboardFolder = settingsFolder + "keyboard/";
	public static final String statsFolder = menusFolder + "stats/";

	// Image variables
	public static BufferedImage titleScreen;
	public static BufferedImage[] mainImages = new BufferedImage[5];
	public static BufferedImage[][] selectImages = new BufferedImage[2][4];
	public static BufferedImage[] characterSelectImages = new BufferedImage[4];
	public static BufferedImage[] mapSelectImages = new BufferedImage[2];
	public static BufferedImage[] pauseSelectImages = new BufferedImage[2];
	public static BufferedImage respawnPlatform;
	public static BufferedImage pausedCover;
	public static BufferedImage gameEnd;
	public static BufferedImage[] livesLeftCounterImages = new BufferedImage[2];

	public static BufferedImage[] instructionsImages = new BufferedImage[2];
	public static BufferedImage aboutImage;
	public static BufferedImage[] settingsSelectImages = new BufferedImage[3];
	public static BufferedImage[] settingsTimeImages = new BufferedImage[4];
	public static BufferedImage[] settingsKeyChangeImages = new BufferedImage[2];
	public static BufferedImage[] settingsLifeCountImages = new BufferedImage[5];

	public static BufferedImage generalStatsImage;
	public static BufferedImage[] specificStatsImages = new BufferedImage[4];
	public static BufferedImage[] highestCharIcons = new BufferedImage[4];

	public static BufferedImage[][] inGameIcons = new BufferedImage[2][4];

	public static BufferedImage[][] winnerScreenImages = new BufferedImage[2][4];
	public static BufferedImage[] loserImages = new BufferedImage[4];
	public static BufferedImage tieImage;

	// Selection variables
	public static int gameState = 0;
	public static int p1SelectedCharPic;
	public static int p2SelectedCharPic;
	public static int optionSelected = 0;
	public static boolean p1Selected;
	public static boolean p2Selected;

	public static int p1CharacterNum;
	public static int p2CharacterNum;

	// Game Paused
	public static boolean paused = false;

	// The characters and map that will be chosen
	public static Character p1;
	public static Character p2;

	// P1 Actions (moving, jumping, attacking, special)
	public static int p1X = 410;
	public static int p1Y = 10;
	public static double p1XSpeed;
	public static double p1YSpeed;
	public static double p1WalkSpeed;
	public static double p1JumpSpeed;
	public static boolean p1Jump = false;
	public static boolean p1Jump2 = false;
	public static boolean p1DoubleJumped = false;
	public static boolean p1Airborne = true;
	public static boolean p1Left = false;
	public static boolean p1Right = false;
	public static boolean p1Down = false;
	public static int p1CurFloor;
	public static Rectangle p1CurLedge;
	public static boolean p1Attacking = false;
	public static boolean p1OnLedge = true;
	public static boolean p1FacingLeft = false;
	public static boolean p1Special = false;

	// P2 Actions (moving, jumping, attacking, special)
	public static int p2X = 480;
	public static int p2Y = 10;
	public static double p2XSpeed;
	public static double p2YSpeed;
	public static double p2WalkSpeed;
	public static double p2JumpSpeed;
	public static boolean p2Jump = false;
	public static boolean p2Jump2 = false;
	public static boolean p2DoubleJumped = false;
	public static boolean p2Airborne = true;
	public static boolean p2Left = false;
	public static boolean p2Right = false;
	public static boolean p2Down = false;
	public static int p2CurFloor;
	public static Rectangle p2CurLedge;
	public static boolean p2Attacking = false;
	public static boolean p2FacingLeft = false;
	public static boolean p2Special = false;

	// Gravity for falling
	public static double gravity = 0.8;

	// Thread
	public static Thread thread;

	// Loading the stages' ledges
	public static Rectangle groundBox = new Rectangle(115, 398, 712, 162); // Floor: 115, 398 --> 827, 398 (size)
	public static Rectangle[] stage1 = { new Rectangle(182, 288, 185, 30), new Rectangle(587, 288, 185, 30),
			new Rectangle(384, 184, 185, 30) };
	public static Rectangle[] stage2 = { new Rectangle(422, 288, 185, 30), new Rectangle(121, 184, 185, 30),
			new Rectangle(306, 184, 185, 30) };

	// Stage that will be played on
	public static Stage selectedStage;

	// Animation Counters:
	// Walking
	public static int p1WalkCounter = 0;
	public static int p1WalkImage = 0;

	public static int p2WalkCounter = 0;
	public static int p2WalkImage = 0;

	// Attacking
	public static int p1AttackCounter = 0;
	public static int p1AttackImage = 0;

	public static int p2AttackCounter = 0;
	public static int p2AttackImage = 0;

	// Special
	public static int p1SpecialCounter = 0;
	public static int p1SpecialImage = 0;

	public static int p2SpecialCounter = 0;
	public static int p2SpecialImage = 0;

	// Death-Respawn Variables
	public static int p1RespawnCounter = 0;
	public static boolean p1RespawnStarted;
	public static int p2RespawnCounter = 0;
	public static boolean p2RespawnStarted;
	public static boolean p1KO = false;
	public static boolean p2KO = false;
	public static boolean p1FallenOff;
	public static boolean p2FallenOff;

	// Invulnerability Variables (you are invulnerable for a little bit after
	// respawning)
	public static int p1InvulnerableCounter;
	public static boolean p1Invulnerable;
	public static int p2InvulnerableCounter;
	public static boolean p2Invulnerable;

	// Winner (1 = p1, 2 = p2)
	public static int winner = 0;

	// Settings variables
	public static boolean changingTimeLimit = false;
	public static boolean changingKeyMap = false;
	public static boolean changingLifeCount = false;

	// Timer + Time limit
	public static int timeLimit;
	public static int timerCount = -10;
	public static int timeLeft;
	public static boolean hasTimeLimit;

	// Life count
	public static int livesPerCharacter = 3;

	// Key Mapping for controls
	public static Map<Integer, BufferedImage> keyImageMap;
	public static Map<String, Integer> p1KeyMap;
	public static Map<String, Integer> p2KeyMap;
	public static int p1UpKey, p1DownKey, p1LeftKey, p1RightKey, p1AttackKey, p1SpecialKey;
	public static int p2UpKey, p2DownKey, p2LeftKey, p2RightKey, p2AttackKey, p2SpecialKey;
	public static boolean changingUpKey, changingDownKey, changingLeftKey, changingRightKey, changingAttackKey,
			changingSpecialKey;

	// Stats Variables
	public static ArrayList<Character> charStatsList;
	public static ArrayList<Character> mostPlayed;
	public static ArrayList<Character> mostWins;
	public static int selectedNumPlayed;
	public static int selectedNumWins;

	public static boolean specificStats;

	// Music Variables
	public static Clip gameSong, menuSong;

	// RUN CODE

	// Constructor for graphics
	public Main() {
		// Size and background
		setPreferredSize(new Dimension(960, 540));
		setBackground(new Color(255, 255, 255));

		try {
			// Adding images
			titleScreen = ImageIO.read(new File(menusFolder + "SuperSmashBros.jpeg"));
			for (int i = 0; i < 5; i++) {
				mainImages[i] = ImageIO.read(new File(mainmenuFolder + "SmashMain" + (i + 1) + ".png"));
				settingsLifeCountImages[i] = ImageIO
						.read(new File(settingsFolder + "SettingsLives" + (i + 1) + ".png"));
			}
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 4; j++) {
					selectImages[i][j] = ImageIO
							.read(new File(selectionsFolder + "P" + (i + 1) + "Select" + (j + 1) + ".png"));
				}
			}

			for (int i = 0; i < 4; i++) {
				characterSelectImages[i] = ImageIO.read(new File(selectionsFolder + "COption" + (i + 1) + ".png"));
				inGameIcons[0][i] = ImageIO.read(new File(ingameFolder + "P1InGameIcon" + (i + 1) + ".png"));
				inGameIcons[1][i] = ImageIO.read(new File(ingameFolder + "P2InGameIcon" + (i + 1) + ".png"));
				specificStatsImages[i] = ImageIO.read(new File(statsFolder + "StatsDetails" + (i + 1) + ".png"));
				highestCharIcons[i] = ImageIO.read(new File(statsFolder + "StatsWin" + (i + 1) + ".png"));
				settingsTimeImages[i] = ImageIO.read(new File(settingsFolder + "SettingsTime" + (i + 1) + ".png"));
				winnerScreenImages[0][i] = ImageIO.read(new File(ingameFolder + "P1Victory" + (i + 1) + ".png"));
				winnerScreenImages[1][i] = ImageIO.read(new File(ingameFolder + "P2Victory" + (i + 1) + ".png"));
				loserImages[i] = ImageIO.read(new File(ingameFolder + "Loser" + (i + 1) + ".png"));
			}

			generalStatsImage = ImageIO.read(new File(statsFolder + "StatsOverview.png"));

			instructionsImages[0] = ImageIO.read(new File(instructionsFolder + "Instructions1.png"));
			instructionsImages[1] = ImageIO.read(new File(instructionsFolder + "Instructions2.png"));

			aboutImage = ImageIO.read(new File(menusFolder + "About.png"));

			for (int i = 0; i < 3; i++) {
				settingsSelectImages[i] = ImageIO.read(new File(settingsFolder + "SettingsMain" + (i + 1) + ".png"));
			}
			settingsKeyChangeImages[0] = ImageIO.read(new File(settingsFolder + "SettingsP1KeyChange.png"));
			settingsKeyChangeImages[1] = ImageIO.read(new File(settingsFolder + "SettingsP2KeyChange.png"));

			mapSelectImages[0] = ImageIO.read(new File(selectionsFolder + "MapSelect1.png"));
			mapSelectImages[1] = ImageIO.read(new File(selectionsFolder + "MapSelect2.png"));

			pauseSelectImages[0] = ImageIO.read(new File(ingameFolder + "Pause1.png"));
			pauseSelectImages[1] = ImageIO.read(new File(ingameFolder + "Pause2.png"));

			respawnPlatform = ImageIO.read(new File(ingameFolder + "RespawnPlatform.png"));
			gameEnd = ImageIO.read(new File(ingameFolder + "GameEnd.png"));
			pausedCover = ImageIO.read(new File(ingameFolder + "PausedCover.png"));

			livesLeftCounterImages[0] = ImageIO.read(new File(ingameFolder + "LifeCounterL.png"));
			livesLeftCounterImages[1] = ImageIO.read(new File(ingameFolder + "LifeCounterR.png"));

			tieImage = ImageIO.read(new File(ingameFolder + "NoVictory.png"));

			// Adding music
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File(assetsFolder + "ThemeSong.wav"));
			gameSong = AudioSystem.getClip();
			gameSong.open(sound);

			sound = AudioSystem.getAudioInputStream(new File(assetsFolder + "MainSong.wav"));
			menuSong = AudioSystem.getClip();
			menuSong.open(sound);

		} catch (Exception e) {
			System.out.println("Image not found");
		}

		// Starting a thread
		thread = new Thread(this);
		thread.start();

	}

	// Main method
	public static void main(String[] args) {
		// Creating JFrame and JPanel
		JFrame frame = new JFrame("Super Smash Bros Penultimate");
		Main panel = new Main();

		// Loading in key control presets
		keyImageMap = new HashMap<>();
		loadKeyImages();

		p1KeyMap = new HashMap<>();
		p2KeyMap = new HashMap<>();

		p1KeyMap.put("up", 38);
		p1KeyMap.put("down", 40);
		p1KeyMap.put("left", 37);
		p1KeyMap.put("right", 39);
		p1KeyMap.put("attack", 46);
		p1KeyMap.put("special", 44);

		p2KeyMap.put("up", 87);
		p2KeyMap.put("down", 83);
		p2KeyMap.put("left", 65);
		p2KeyMap.put("right", 68);
		p2KeyMap.put("attack", 69);
		p2KeyMap.put("special", 82);

		setKeyMap();

		// Getting current stats from the text file
		readInStats();

		// Starting the menu song
		menuSong.setFramePosition(0);
		menuSong.loop(Clip.LOOP_CONTINUOUSLY);

		// Adding everything to the frame
		frame.add(panel);
		frame.addKeyListener(panel);
		frame.pack();
		frame.setVisible(true);
	}

	// Description: This handles all the graphics for the program
	// Parameters: Graphics object
	// Return: Void
	public void paintComponent(Graphics g) {
		// For drawing the hitboxes (for debugging and reference)
		// Graphics2D g2 = (Graphics2D) g;

		// 0. Title screen
		if (gameState == 0) {
			g.drawImage(titleScreen, 0, 0, 960, 540, this);
		}
		// 1. Main screen with the different options
		else if (gameState == 1) {
			g.drawImage(mainImages[optionSelected], 0, 0, 960, 540, this);
		}
		// 2. Select characters and stage screen
		else if (gameState == 2) {
			// P1 choosing character
			if (!p1Selected) {
				g.drawImage(selectImages[0][optionSelected], 0, 0, 960, 540, this);

				// For each of the four options
				for (int i = 0; i < 4; i++) {
					g.drawImage(characterSelectImages[i], 220 * i + 80, 85, 150, 160, this);
				}
			}
			// P2 choosing character
			else if (!p2Selected) {
				g.drawImage(selectImages[1][optionSelected], 0, 0, 960, 540, this);

				// For each of the 4 options
				for (int i = 0; i < 4; i++) {
					g.drawImage(characterSelectImages[i], 220 * i + 80, 85, 150, 160, this);
				}

				// Showing the character that P1 chose
				g.drawImage(characterSelectImages[p1SelectedCharPic], 75, 375, 150, 160, this);
			}
			// choosing stage
			else {
				// Map Images
				g.drawImage(mapSelectImages[optionSelected], 0, 0, 960, 540, this);

				// Showing both characters that P1 and P2 chose
				g.drawImage(characterSelectImages[p1SelectedCharPic], 75, 375, 150, 160, this);
				g.drawImage(characterSelectImages[p2SelectedCharPic], 520, 375, 150, 160, this);
			}
		}
		// 3. The game part
		else if (gameState == 3) {
			// Drawing the stage background
			g.drawImage(selectedStage.getImage(), 0, 0, 960, 540, this);

			// Drawing the icons
			g.drawImage(inGameIcons[0][p1CharacterNum], 0, 0, 960, 540, this);
			g.drawImage(inGameIcons[1][p2CharacterNum], 0, 0, 960, 540, this);

			// Drawing strings with the damage each character has taken
			g.setColor(new Color(255, 255, 255));
			g.setFont(new Font("Arial", Font.BOLD, 44));
			String p1DamPercent = String.format("%.1f%%", p1.getDamageTaken() / 150 * 100);
			String p2DamPercent = String.format("%.1f%%", p2.getDamageTaken() / 150 * 100);
			g.drawString(p1DamPercent, 298, 482);
			g.drawString(p2DamPercent, 648, 482);

			// If there is a time limit, draw the timer (time remaining)
			if (hasTimeLimit) {
				g.drawString(timeLeft + "", 30, 70);
			}

			// Using small pale yellow circles underneath each character icon to show number
			// of
			// lives remaining
			for (int i = 0; i < p1.getLivesLeft(); i++) {
				g.drawImage(livesLeftCounterImages[0], i * 20, 0, 960, 540, this);
			}
			for (int i = 0; i < p2.getLivesLeft(); i++) {
				g.drawImage(livesLeftCounterImages[1], i * 20, 0, 960, 540, this);
			}

			// Drawing hitboxes that can be used for referece
			// g2.draw(p1.getHitBox());
			// g2.draw(p2.getHitBox());

			// PLAYER 1
			// When P1 is walking or idle
			if (!p1Attacking && !p1Special) {
				// Walking to the right
				if (p1Right) {
					g.drawImage(p1.getSprites("walkR").get(p1WalkImage), p1X, p1Y, p1.getWidth(), p1.getHeight(), this);
				}
				// Walking to the left
				else if (p1Left) {
					g.drawImage(p1.getSprites("walkL").get(p1WalkImage), p1X, p1Y, p1.getWidth(), p1.getHeight(), this);
				}
				// Idle
				else {
					// Idle facing left
					if (p1FacingLeft) {
						g.drawImage(p1.getSprites("").get(1), p1X, p1Y, p1.getWidth(), p1.getHeight(), this);
					}
					// Idle facing right
					else {
						g.drawImage(p1.getSprites("").get(0), p1X, p1Y, p1.getWidth(), p1.getHeight(), this);
					}
				}

			}
			// When P1 is attacking
			else if (!p1Special) {
				// Attack hitbox for reference
				// g2.draw(p1.getAttackBox());

				// Facing left
				if (p1FacingLeft) {
					g.drawImage(p1.getSprites("attackL").get(p1AttackImage), p1X - (int) p1.getAttackBox().getWidth(),
							p1Y - (p1.getAttackingHeight() - p1.getHeight()), p1.getAttackingWidth(),
							p1.getAttackingHeight(), this);
				}
				// Facing right
				else {
					g.drawImage(p1.getSprites("attackR").get(p1AttackImage), p1X,
							p1Y - (p1.getAttackingHeight() - p1.getHeight()), p1.getAttackingWidth(),
							p1.getAttackingHeight(), this);
				}

			}
			// When P1 is performing the special attack
			else {
				// Attack hitbox for reference
				// g2.draw(p1.getSpecial().getSpecialBox());

				int x = p1X - (int) p1.getSpecial().getSpecialBox().getWidth();
				int y = p1Y - ((int) p1.getSpecial().getAttackingSize().getHeight() - p1.getHeight());
				int width = (int) p1.getSpecial().getAttackingSize().getWidth();
				int height = (int) p1.getSpecial().getAttackingSize().getHeight();

				// Facing left
				if (p1FacingLeft) {
					// If the special is left-right (Kirby, Link, Luigi)
					if (p1.getSpecial().getIsHorizontal()) {
						g.drawImage(p1.getSprites("specialL").get(p1SpecialImage), x, y, width, height, this);
					}
					// If the special does damage in a column (Meta Knight)
					else {
						g.drawImage(p1.getSprites("specialL").get(p1SpecialImage), p1X, y, width, height, this);
					}
				}
				// Facing right
				else {
					g.drawImage(p1.getSprites("specialR").get(p1SpecialImage), p1X, y, width, height, this);
				}
			}

			// PLAYER 2
			// When P2 is walking or idle
			if (!p2Attacking && !p2Special) {
				// Walking to the right
				if (p2Right) {
					g.drawImage(p2.getSprites("walkR").get(p2WalkImage), p2X, p2Y, p2.getWidth(), p2.getHeight(), this);
				}
				// Walking to the left
				else if (p2Left) {
					g.drawImage(p2.getSprites("walkL").get(p2WalkImage), p2X, p2Y, p2.getWidth(), p2.getHeight(), this);
				}
				// Idle
				else {
					// Idle facing left
					if (p2FacingLeft) {
						g.drawImage(p2.getSprites("").get(1), p2X, p2Y, p2.getWidth(), p2.getHeight(), this);
					}
					// Idle facing right
					else {
						g.drawImage(p2.getSprites("").get(0), p2X, p2Y, p2.getWidth(), p2.getHeight(), this);
					}

				}

			}
			// When P2 is attacking
			else if (!p2Special) {
				// Attack hitbox for reference
				// g2.draw(p2.getAttackBox());

				// Facing left
				if (p2FacingLeft) {
					g.drawImage(p2.getSprites("attackL").get(p2AttackImage), p2X - (int) p2.getAttackBox().getWidth(),
							p2Y - (p2.getAttackingHeight() - p2.getHeight()), p2.getAttackingWidth(),
							p2.getAttackingHeight(), this);
				}
				// Facing right
				else {
					g.drawImage(p2.getSprites("attackR").get(p2AttackImage), p2X,
							p2Y - (p2.getAttackingHeight() - p2.getHeight()), p2.getAttackingWidth(),
							p2.getAttackingHeight(), this);
				}

			} else {
				// Attack hitbox for reference
				// g2.draw(p2.getSpecial().getSpecialBox());

				int x = p2X - (int) p2.getSpecial().getSpecialBox().getWidth();
				int y = p2Y - ((int) p2.getSpecial().getAttackingSize().getHeight() - p2.getHeight());
				int width = (int) p2.getSpecial().getAttackingSize().getWidth();
				int height = (int) p2.getSpecial().getAttackingSize().getHeight();

				// Facing left
				if (p2FacingLeft) {
					// If the special is left-right damage (Kirby, Link, Luigi)
					if (p2.getSpecial().getIsHorizontal()) {
						g.drawImage(p2.getSprites("specialL").get(p2SpecialImage), x, y, width, height, this);
					}
					// If the special does the damage in a vertical column (Meta Knight)
					else {
						g.drawImage(p2.getSprites("specialL").get(p2SpecialImage), p2X, y, width, height, this);
					}
				}
				// Facing right
				else {
					g.drawImage(p2.getSprites("specialR").get(p2SpecialImage), p2X, y, width, height, this);
				}
			}

			// Drawing the pause menu
			if (paused) {
				g.drawImage(pausedCover, 0, 0, 960, 540, this);
				g.drawImage(pauseSelectImages[optionSelected], 0, 0, 960, 540, this);
			}

			// Drawing the respawn platform once a character is KO'ed
			if (p1KO) {
				g.drawImage(respawnPlatform, 0, 0, 960, 540, this);
			}
			if (p2KO) {
				g.drawImage(respawnPlatform, 0, 0, 960, 540, this);
			}

			// Drawing a respawn platform and making the screen darker when a winner is
			// decided
			if (winner != 0) {
				g.drawImage(respawnPlatform, 0, 0, 960, 540, this);
				g.drawImage(gameEnd, 0, 0, 960, 540, this);
			}

		}
		// 4. Stats
		else if (gameState == 4) {
			// Looking at just general stats (most played character, highest win character)
			if (!specificStats) {
				g.drawImage(generalStatsImage, 0, 0, 960, 540, this);

				// Can draw multiple characters if they are tied
				// Getting and displaying the proper character icon
				for (int i = 0; i < mostPlayed.size(); i++) {
					if (mostPlayed.get(i).equals(new Kirby(true))) {
						g.drawImage(highestCharIcons[0], 120 * i, 0, 960, 540, this);
					} else if (mostPlayed.get(i).equals(new Link(true))) {
						g.drawImage(highestCharIcons[1], 120 * i, 0, 960, 540, this);
					} else if (mostPlayed.get(i).equals(new MetaKnight(true))) {
						g.drawImage(highestCharIcons[2], 120 * i, 0, 960, 540, this);
					} else if (mostPlayed.get(i).equals(new Luigi(true))) {
						g.drawImage(highestCharIcons[3], 120 * i, 0, 960, 540, this);
					}
				}

				// Can draw multiple characters if they are tied
				// Getting and displaying the proper character icon
				for (int i = 0; i < mostWins.size(); i++) {
					if (mostWins.get(i).equals(new Kirby(true))) {
						g.drawImage(highestCharIcons[0], 120 * i, 140, 960, 540, this);
					} else if (mostWins.get(i).equals(new Link(true))) {
						g.drawImage(highestCharIcons[1], 120 * i, 140, 960, 540, this);
					} else if (mostWins.get(i).equals(new MetaKnight(true))) {
						g.drawImage(highestCharIcons[2], 120 * i, 140, 960, 540, this);
					} else if (mostWins.get(i).equals(new Luigi(true))) {
						g.drawImage(highestCharIcons[3], 120 * i, 140, 960, 540, this);
					}
				}

			} else {
				// Drawing the image for the character's specific stats
				g.drawImage(specificStatsImages[optionSelected], 0, 0, 960, 540, this);

				// Writing out the stats
				g.setColor(new Color(255, 255, 255));
				g.setFont(new Font("Arial", Font.BOLD, 40));
				g.drawString(selectedNumPlayed + "", 750, 310);
				g.drawString(selectedNumWins + "", 750, 235);
			}
		}
		// 5. Instructions
		else if (gameState == 5) {
			g.drawImage(instructionsImages[optionSelected], 0, 0, 960, 540, this);
		}
		// 6. About
		else if (gameState == 6) {
			g.drawImage(aboutImage, 0, 0, 960, 540, this);
		}
		// 7. Settings
		else if (gameState == 7) {
			// Changing the time limit
			if (changingTimeLimit) {
				g.drawImage(settingsTimeImages[optionSelected], 0, 0, 960, 540, this);
			}
			// Changing the key controls
			else if (changingKeyMap) {
				g.drawImage(settingsKeyChangeImages[optionSelected], 0, 0, 960, 540, this);

				// Draws the current key controls for P1
				if (optionSelected == 0) {
					g.drawImage(keyImageMap.get(p1KeyMap.get("up")), 170, 178, 50, 50, this);
					g.drawImage(keyImageMap.get(p1KeyMap.get("down")), 170, 260, 50, 50, this);
					g.drawImage(keyImageMap.get(p1KeyMap.get("left")), 85, 260, 50, 50, this);
					g.drawImage(keyImageMap.get(p1KeyMap.get("right")), 255, 260, 50, 50, this);
					g.drawImage(keyImageMap.get(p1KeyMap.get("attack")), 215, 360, 50, 50, this);
					g.drawImage(keyImageMap.get(p1KeyMap.get("special")), 215, 445, 50, 50, this);
				}
				// Draws the current key controls for P2
				else {
					g.drawImage(keyImageMap.get(p2KeyMap.get("up")), 170, 178, 50, 50, this);
					g.drawImage(keyImageMap.get(p2KeyMap.get("down")), 170, 260, 50, 50, this);
					g.drawImage(keyImageMap.get(p2KeyMap.get("left")), 85, 260, 50, 50, this);
					g.drawImage(keyImageMap.get(p2KeyMap.get("right")), 255, 260, 50, 50, this);
					g.drawImage(keyImageMap.get(p2KeyMap.get("attack")), 215, 360, 50, 50, this);
					g.drawImage(keyImageMap.get(p2KeyMap.get("special")), 215, 445, 50, 50, this);
				}
			}
			// Changing the number of lives per game
			else if (changingLifeCount) {
				g.drawImage(settingsLifeCountImages[optionSelected], 0, 0, 960, 540, this);
			}
			// Still in the main settings screen
			else {
				g.drawImage(settingsSelectImages[optionSelected], 0, 0, 960, 540, this);
			}
		}
		// 8. Winner screen
		else if (gameState == 8) {
			// P1 Wins
			if (winner == 1) {
				// Drawing the player's character in the 1st place section
				if (p1.getName().equals("Kirby")) {
					g.drawImage(winnerScreenImages[0][0], 0, 0, 960, 540, this);
				} else if (p1.getName().equals("Link")) {
					g.drawImage(winnerScreenImages[0][1], 0, 0, 960, 540, this);
				} else if (p1.getName().equals("MetaKnight")) {
					g.drawImage(winnerScreenImages[0][2], 0, 0, 960, 540, this);
				} else if (p1.getName().equals("Luigi")) {
					g.drawImage(winnerScreenImages[0][3], 0, 0, 960, 540, this);
				}

				// Drawing the player's character in the 2nd place section
				if (p2.getName().equals("Kirby")) {
					g.drawImage(loserImages[0], 0, 0, 960, 540, this);
				} else if (p2.getName().equals("Link")) {
					g.drawImage(loserImages[1], 0, 0, 960, 540, this);
				} else if (p2.getName().equals("MetaKnight")) {
					g.drawImage(loserImages[2], 0, 0, 960, 540, this);
				} else if (p2.getName().equals("Luigi")) {
					g.drawImage(loserImages[3], 0, 0, 960, 540, this);
				}
			}
			// P2 Wins
			else if (winner == 2) {
				// Drawing the player's character in the 1st place section
				if (p2.getName().equals("Kirby")) {
					g.drawImage(winnerScreenImages[1][0], 0, 0, 960, 540, this);
				} else if (p2.getName().equals("Link")) {
					g.drawImage(winnerScreenImages[1][1], 0, 0, 960, 540, this);
				} else if (p2.getName().equals("MetaKnight")) {
					g.drawImage(winnerScreenImages[1][2], 0, 0, 960, 540, this);
				} else if (p2.getName().equals("Luigi")) {
					g.drawImage(winnerScreenImages[1][3], 0, 0, 960, 540, this);
				}

				// Drawing the player's character in the 2nd place section
				if (p1.getName().equals("Kirby")) {
					g.drawImage(loserImages[0], 0, 0, 960, 540, this);
				} else if (p1.getName().equals("Link")) {
					g.drawImage(loserImages[1], 0, 0, 960, 540, this);
				} else if (p1.getName().equals("MetaKnight")) {
					g.drawImage(loserImages[2], 0, 0, 960, 540, this);
				} else if (p1.getName().equals("Luigi")) {
					g.drawImage(loserImages[3], 0, 0, 960, 540, this);
				}
			}
			// In the case of a tie
			else if (winner == 3) {
				g.drawImage(tieImage, 0, 0, 960, 540, this);
			}
		}
	}

	// Description: This is a run method responsible all actions that have to be
	// constantly updated
	// Parameters: None
	// Return: void
	public void run() {
		// Keeps looping
		while (true) {
			// Moving
			move();
			// Staying in bounds
			keepInBound();

			// If there is no winner yet
			if (winner == 0 && gameState == 3) {
				// PLAYER 1
				// Attacking
				if (p1Attacking) {
					// Animating the attack
					attackAnimation("p1", p1.getNumAttackSprites());

					// P2 takes damage if attacked by P1
					if (p2.isAttacked(p1) && !p2KO && !p2Invulnerable) {
						// Defensive moves cause the enemy to take 1/3 of their own damage, and
						// the user is dealt 1/2 of the incoming damage
						// This works on both sides (a shield is just THAT powerful!)
						if (p2.getSpecial().getType().equals("defense") && p2Special) {
							p1.incDamageTaken(p1.getBaseDmg() / 3);
							p2.incDamageTaken(p1.getBaseDmg() / 2);
						}
						// Otherwise, just increase damage taken
						else {
							p2.incDamageTaken(p1.getBaseDmg());
						}

						p2.incDamageTaken(p1.getBaseDmg());

						// Getting pushed back a little bit after getting hit by an attack
						if (p1X < p2X) {
							p2X += 5;
						} else {
							p2X -= 5;
						}
						p2Y -= 3;
						p2Airborne = true;

					}

				}
				// Special
				else if (p1Special) {
					specialAnimation("p1", p1.getSpecial().getNumSprites());

					if (p2.isSpecialAttacked(p1) && !p2KO && !p2Invulnerable) {
						// Defensive moves cause the enemy to take 1/3 of their own damage, and
						// the user is dealt 1/2 of the incoming damage
						// This works on both sides (a shield is just THAT powerful!)
						if (p2.getSpecial().getType().equals("defense") && p2Special) {
							p1.incDamageTaken(p1.getSpecial().getDamage() / 3);
							p2.incDamageTaken(p1.getSpecial().getDamage() / 2);
						}
						// Otherwise, just increase damage taken
						else {
							p2.incDamageTaken(p1.getSpecial().getDamage());
						}

						// Getting pushed back a little bit after getting hit by an attack
						// Horizontal push back
						if (p1X < p2X) {
							p2X += 5;
						} else {
							p2X -= 5;
						}
						// Vertically jumps up a tiny bit
						p2Y -= 3;
						p2Airborne = true;
					}

				}

				// PLAYER 2
				// Attacking
				if (p2Attacking) {
					// Animating the attack
					attackAnimation("p2", p2.getNumAttackSprites());

					// P1 takes damage if attacked by P2
					if (p1.isAttacked(p2) && !p1KO && !p1Invulnerable) {
						// Defensive moves cause the enemy to take 1/3 of their own damage, and
						// the user is dealt 1/2 of the incoming damage
						// This works on both sides (a shield is just THAT powerful!)
						if (p1.getSpecial().getType().equals("defense") && p1Special) {
							p2.incDamageTaken(p2.getBaseDmg() / 3);
							p1.incDamageTaken(p2.getBaseDmg() / 2);
						}
						// Otherwise, just increase damage taken
						else {
							p1.incDamageTaken(p2.getBaseDmg());
						}

						// Getting pushed back a little bit after getting hit by an attack
						// Horizontal push back
						if (p2X < p1X) {
							p1X += 5;
						} else {
							p1X -= 5;
						}
						// Vertical jumps a little bit
						p1Y -= 3;
						p1Airborne = true;
					}

				} else if (p2Special) {
					specialAnimation("p2", p2.getSpecial().getNumSprites());

					// P2 takes damage if specialed by P1
					if (p1.isSpecialAttacked(p2) && !p1KO && !p1Invulnerable) {
						// Defensive moves cause the enemy to take 1/3 of their own damage, and
						// the user is dealt 1/2 of the incoming damage
						// This works on both sides (a shield is just THAT powerful!)
						if (p1.getSpecial().getType().equals("defense") && p1Special) {
							p2.incDamageTaken(p2.getSpecial().getDamage() / 3);
							p1.incDamageTaken(p2.getSpecial().getDamage() / 2);
						}
						// Otherwise, just increase damage taken
						else {
							p1.incDamageTaken(p2.getSpecial().getDamage());
						}

						// Getting pushed back a little bit after getting hit by an attack
						// Horizontal push back
						if (p2X < p1X) {
							p1X += 5;
						} else {
							p1X -= 5;
						}
						// Vertical jumps a little bit
						p1Y -= 3;
						p1Airborne = true;
					}

				}

				// Seeing if P1 has taken fatal damage
				if (p1.getDamageTaken() > 150) {
					// If so, it has been KO'ed
					p1KO = true;
					respawn();
				}

				// Seeing if P2 has taken fatal damage
				if (p2.getDamageTaken() > 150) {
					// If so, it has been KO'ed
					p2KO = true;
					respawn();
				}

				// Characters get around 0.5 s of invulnerability after respawning
				// Helps reduce spawn camping
				makeInvulnerable();

			}

			if (!paused && winner == 0) {
				// Updating the timer
				updateTimer();
			}

			repaint();

			// For fps
			try {
				Thread.sleep(20);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	// Description: This method is called upon when a key is pressed
	// Paramters: KeyEvent object
	// Return: Void
	public void keyPressed(KeyEvent e) {
		// Getting the key that was pressed
		int key = e.getKeyCode();

		// Escape key
		if (key == KeyEvent.VK_ESCAPE) {
			// 0. Title screen --> close the program
			if (gameState == 0) {
				System.exit(0);
			}
			// 1. Main screen --> go to title screen
			else if (gameState == 1) {
				gameState = 0;
			}
			// 2. Character select screen
			else if (gameState == 2) {
				// Undo select P2's character
				if (p1Selected && p2Selected) {
					p2Selected = false;
				}
				// Undo select P1's character
				else if (p1Selected) {
					p1Selected = false;
				}
				// Go back to main screen
				else {
					gameState = 1;
					p1Selected = false;
					p2Selected = false;
					optionSelected = 0;
				}
			}
			// 3. Game part
			else if (gameState == 3) {
				// Pause
				if (paused) {
					paused = false;
				} else {
					paused = true;
				}
			}
			// 4. Stats --> go to main screen
			else if (gameState == 4) {
				// Specific stats --> main stats
				if (specificStats) {
					specificStats = false;
				}
				// Main stats --> main menu
				else {
					gameState = 1;
					optionSelected = 0;
				}
			}
			// 5. Instructions --> go to main screen
			else if (gameState == 5) {
				if (optionSelected == 1) {
					optionSelected = 0;
				} else {
					gameState = 1;
				}
			}
			// 6. About --> go to main screen
			else if (gameState == 6) {
				gameState = 1;
			}
			// 7. Settings
			else if (gameState == 7) {
				// Setting variables for settings options
				// Time limit
				if (changingTimeLimit) {
					if (optionSelected == 0) {
						timeLimit = 0;
						hasTimeLimit = false;
					} else if (optionSelected == 1) {
						timeLimit = 30;
						hasTimeLimit = true;
					} else if (optionSelected == 2) {
						timeLimit = 60;
						hasTimeLimit = true;
					} else {
						timeLimit = 90;
						hasTimeLimit = true;
					}

					changingTimeLimit = false;
				}
				// Key controls
				else if (changingKeyMap) {
					setKeyMap();
					changingKeyMap = false;
				}
				// Number of lives
				else if (changingLifeCount) {
					changingLifeCount = false;
				}
				// Going back to main menu
				else {
					gameState = 1;
				}

				optionSelected = 0;
			}

			repaint();
		}
		// Any key takes you from title to main screen
		else if (gameState == 0) {
			gameState = 1;
			optionSelected = 0;
			repaint();
		}
		// 1. Main screen
		else if (gameState == 1) {
			// Right arrow key
			if (key == 39) {
				// To go forward through the options
				optionSelected++;
				if (optionSelected == 5) {
					optionSelected = 0;
				}
				repaint();
			}
			// Left arrow key
			else if (key == 37) {
				// To go backward through the options
				optionSelected--;
				if (optionSelected == -1) {
					optionSelected = 4;
				}
				repaint();
			}
			// Enter key
			else if (key == 10) {
				// Goes to character+stage selection screen (2)
				if (optionSelected == 0) {
					gameState = 2;
				}
				// Goes to stats screen (4)
				else if (optionSelected == 1) {
					optionSelected = 0;
					findBestStats();
					gameState = 4;
				}
				// Goes to instructions screen (5)
				else if (optionSelected == 2) {
					optionSelected = 0;
					gameState = 5;
				}
				// Goes to about screen (6)
				else if (optionSelected == 3) {
					gameState = 6;
				}
				// Goes to settings screen (7)
				else if (optionSelected == 4) {
					optionSelected = 0;
					gameState = 7;
				}

				repaint();
			}
		}
		// 2. When in character + stage select
		else if (gameState == 2) {
			// Right arrow key
			if (key == 39) {
				// To go forward through the characters/stages
				optionSelected++;

				// Character selection has 4 options
				if (!p1Selected || !p2Selected) {
					if (optionSelected == 4) {
						optionSelected = 0;
					}
				}
				// Stage selection has 2 options
				else {
					if (optionSelected == 2) {
						optionSelected = 0;
					}
				}
				repaint();
			}
			// Left arrow key
			else if (key == 37) {
				// To go backward through the characters/stages
				optionSelected--;

				if (optionSelected == -1) {
					// Character selection has 4 options
					if (!p1Selected || !p2Selected) {
						optionSelected = 3;
					}
					// Stage selection has 2 options
					else {
						optionSelected = 1;
					}
				}
				repaint();
			}
			// Enter key
			else if (key == 10) {
				// If P1 is selecting character
				if (!p1Selected) {
					// First option - Kirby
					if (optionSelected == 0) {
						p1 = new Kirby();
						p1CharacterNum = 0;
					}
					// Second option - Link
					else if (optionSelected == 1) {
						p1 = new Link();
						p1CharacterNum = 1;
					}
					// Third option - Meta Knight
					else if (optionSelected == 2) {
						p1 = new MetaKnight();
						p1CharacterNum = 2;
					}
					// Fourth option - Luigi
					else if (optionSelected == 3) {
						p1 = new Luigi();
						p1CharacterNum = 3;
					}

					// Setting up variables
					p1WalkSpeed = p1.getSpeed();
					p1JumpSpeed = p1.getJumpSpeed();

					// Picture to be shown as selected character during stage selection
					p1SelectedCharPic = optionSelected;

					optionSelected = 0;
					p1Selected = true;
					repaint();
				}
				// If P2 is selecting
				else if (!p2Selected) {
					// First option - Kirby
					if (optionSelected == 0) {
						p2 = new Kirby();
						p2CharacterNum = 0;
					}
					// Second option - Link
					else if (optionSelected == 1) {
						p2 = new Link();
						p2CharacterNum = 1;
					}
					// Third option - Meta Knight
					else if (optionSelected == 2) {
						p2 = new MetaKnight();
						p2CharacterNum = 2;
					}
					// Fourth option - Luigi
					else if (optionSelected == 3) {
						p2 = new Luigi();
						p2CharacterNum = 3;
					}

					// Setting up variables
					p2WalkSpeed = p2.getSpeed();
					p2JumpSpeed = p2.getJumpSpeed();

					// Picture to be shown as selected character during stage selection
					p2SelectedCharPic = optionSelected;

					optionSelected = 0;
					p2Selected = true;

					repaint();
				}
				// Selecting stage
				else {
					// First stage option
					if (optionSelected == 0) {
						selectedStage = new Stage(ingameFolder + "Stage1.png", groundBox, stage1[0], stage1[1],
								stage1[2]);
					}
					// Second stage option
					else {
						selectedStage = new Stage(ingameFolder + "Stage2.png", groundBox, stage2[0], stage2[1],
								stage2[2]);
					}

					// Setting up variables for stage
					p1CurLedge = groundBox;
					p1CurFloor = (int) groundBox.getMinX();
					p2CurLedge = groundBox;
					p2CurFloor = (int) groundBox.getMinX();

					// Setting up time limit
					timeLeft = timeLimit;

					// Setting up number of lives
					p1.setLivesLeft(livesPerCharacter);
					p2.setLivesLeft(livesPerCharacter);

					gameState = 3;

					// Stopping menu music
					menuSong.stop();

					// Starting the stage music
					gameSong.setFramePosition(0);
					gameSong.loop(Clip.LOOP_CONTINUOUSLY);

					repaint();
				}
			}
			// Player 2 choosing
			else if (key == KeyEvent.VK_D) {
				// To go forward through the characters/stages
				optionSelected++;

				// Character selection has 4 options
				if (!p1Selected || !p2Selected) {
					if (optionSelected == 4) {
						optionSelected = 0;
					}
				}
				// Stage selection has 2 options
				else {
					if (optionSelected == 2) {
						optionSelected = 0;
					}
				}
				repaint();
			} else if (key == KeyEvent.VK_A) {
				// To go backward through the characters/stages
				optionSelected--;

				if (optionSelected == -1) {
					// Character selection has 4 options
					if (!p1Selected || !p2Selected) {
						optionSelected = 3;
					}
					// Stage selection has 2 options
					else {
						optionSelected = 1;
					}
				}
				repaint();
			}
		}
		// GAME PART
		else if (gameState == 3) {
			// If the game is paused
			if (paused) {
				// Top option - resume
				if (key == KeyEvent.VK_UP) {
					optionSelected = 0;
				}
				// Bottom option - exit
				else if (key == KeyEvent.VK_DOWN) {
					optionSelected = 1;
				}
				// Choosing the option that was selected
				else if (key == KeyEvent.VK_ENTER) {
					// Unpause
					if (optionSelected == 0) {
						paused = false;
					}
					// Exit
					else {
						gameState = 1;
						paused = false;

						// Stage music stoped
						gameSong.stop();

						// Menu music begins again
						menuSong.setFramePosition(0);
						menuSong.loop(Clip.LOOP_CONTINUOUSLY);

						resetGame();
					}
				}
				repaint();
			}

			// When there is a winner, pressing enter takes you to the winner screen
			if (key == KeyEvent.VK_ENTER && winner != 0) {
				gameState = 8;
				updateStats();
				writeInStats();

				repaint();
			}

			// PLAYER 1
			// Right key
			if (key == p1RightKey) {
				p1Right = true;
				p1Left = false;
				p1FacingLeft = false;
			}
			// Left key
			else if (key == p1LeftKey) {
				p1Left = true;
				p1Right = false;
				p1FacingLeft = true;
			}
			// Up key
			else if (key == p1UpKey) {
				if (!p1Airborne) {
					p1Jump = true;
				} else if (!p1DoubleJumped) {
					p1Jump2 = true;
				}
			}
			// Down key
			else if (key == p1DownKey) {
				p1Down = true;
				p1Airborne = true;

				if (p1KO) {
					p1KO = false;
				}
			}

			// Attack key
			if (key == p1AttackKey) {
				p1Attacking = true;
			}
			// Special attack key
			else if (key == p1SpecialKey) {
				p1Special = true;
			}

			// PLAYER 2
			// Right key
			if (key == p2RightKey) {
				p2Right = true;
				p2Left = false;
				p2FacingLeft = false;
			}
			// Left key
			else if (key == p2LeftKey) {
				p2Left = true;
				p2Right = false;
				p2FacingLeft = true;
			}
			// Up key
			else if (key == p2UpKey) {
				// Jumping
				if (!p2Airborne) {
					p2Jump = true;
				}
				// Double jumping
				else if (!p2DoubleJumped) {
					p2Jump2 = true;
				}
			}
			// Down key
			else if (key == p2DownKey) {
				p2Down = true;
				p2Airborne = true;

			}

			// Attack key
			if (key == p2AttackKey) {
				p2Attacking = true;
			}
			// Special attack key
			else if (key == p2SpecialKey) {
				p2Special = true;
			}
		}
		// Statistics screen
		else if (gameState == 4) {
			// Looking at specific stats
			if (key == KeyEvent.VK_ENTER) {
				if (!specificStats) {
					optionSelected = 0;
					specificStats = true;
					findSpecificStats(new Kirby(true));
				}
			}
			// Traversing through specific stats screen
			else if (specificStats) {
				// Going to stats of character above this character
				if (key == KeyEvent.VK_UP) {
					optionSelected--;

					if (optionSelected == -1) {
						optionSelected = 3;
					}
				}
				// Going to stats of character below this character
				else if (key == KeyEvent.VK_DOWN) {
					optionSelected++;

					if (optionSelected == 4) {
						optionSelected = 0;
					}
				}
				// Setting the right winning variables
				if (optionSelected == 0) {
					findSpecificStats(new Kirby(true));
				} else if (optionSelected == 1) {
					findSpecificStats(new Link(true));
				} else if (optionSelected == 2) {
					findSpecificStats(new MetaKnight(true));
				} else if (optionSelected == 3) {
					findSpecificStats(new Luigi(true));
				}
			}

			repaint();
		}
		// 5. Instructions
		else if (gameState == 5) {
			// Going through the pages forwards
			if (key == KeyEvent.VK_RIGHT) {
				optionSelected++;

				if (optionSelected == 2) {
					optionSelected = 0;
				}
			}
			// Going through the pages backwards
			else if (key == KeyEvent.VK_LEFT) {
				optionSelected--;

				if (optionSelected == -1) {
					optionSelected = 1;
				}
			}

			repaint();
		}
		// 7. Settings
		else if (gameState == 7) {
			// If you are changing the key controls
			if (changingKeyMap) {
				if (key == KeyEvent.VK_1) {
					changingUpKey = false;
					changingDownKey = false;
					changingLeftKey = false;
					changingRightKey = false;

					optionSelected = 0;
				}
				if (key == KeyEvent.VK_2) {
					changingUpKey = false;
					changingDownKey = false;
					changingLeftKey = false;
					changingRightKey = false;

					optionSelected = 1;
				}

				// Setting the new values
				if (optionSelected == 0) {
					// Keys can't match values within and outside of their player #, so that is
					// checked as well
					if (changingUpKey) {
						if (!p1KeyMap.containsValue(key) && !p2KeyMap.containsValue(key)) {
							p1KeyMap.put("up", key);
						}
						changingUpKey = false;
					} else if (changingDownKey) {
						if (!p1KeyMap.containsValue(key) && !p2KeyMap.containsValue(key)) {
							p1KeyMap.put("down", key);
						}
						changingDownKey = false;
					} else if (changingLeftKey) {
						if (!p1KeyMap.containsValue(key) && !p2KeyMap.containsValue(key)) {
							p1KeyMap.put("left", key);
						}
						changingLeftKey = false;
					} else if (changingRightKey) {
						if (!p1KeyMap.containsValue(key) && !p2KeyMap.containsValue(key)) {
							p1KeyMap.put("right", key);
						}
						changingRightKey = false;
					} else if (changingAttackKey) {
						if (!p1KeyMap.containsValue(key) && !p2KeyMap.containsValue(key)) {
							p1KeyMap.put("attack", key);
						}
						changingAttackKey = false;
					} else if (changingSpecialKey) {
						if (!p1KeyMap.containsValue(key) && !p2KeyMap.containsValue(key)) {
							p1KeyMap.put("special", key);
						}
						changingSpecialKey = false;
					}
					// Keys can be changed by first pressing the key of the current one
					else if (key == p1KeyMap.get("up")) {
						changingUpKey = true;
					} else if (key == p1KeyMap.get("down")) {
						changingDownKey = true;
					} else if (key == p1KeyMap.get("left")) {
						changingLeftKey = true;
					} else if (key == p1KeyMap.get("right")) {
						changingRightKey = true;
					} else if (key == p1KeyMap.get("attack")) {
						changingAttackKey = true;
					} else if (key == p1KeyMap.get("special")) {
						changingSpecialKey = true;
					}
				} else {
					// Keys can't match values within and outside of their player #, so that is
					// checked as well
					if (changingUpKey) {
						if (!p2KeyMap.containsValue(key) && !p1KeyMap.containsValue(key)) {
							p2KeyMap.put("up", key);
						}
						changingUpKey = false;
					} else if (changingDownKey) {
						if (!p2KeyMap.containsValue(key) && !p1KeyMap.containsValue(key)) {
							p2KeyMap.put("down", key);
						}
						changingDownKey = false;
					} else if (changingLeftKey) {
						if (!p2KeyMap.containsValue(key) && !p1KeyMap.containsValue(key)) {
							p2KeyMap.put("left", key);
						}
						changingLeftKey = false;
					} else if (changingRightKey) {
						if (!p2KeyMap.containsValue(key) && !p1KeyMap.containsValue(key)) {
							p2KeyMap.put("right", key);
						}
						changingRightKey = false;
					} else if (changingAttackKey) {
						if (!p2KeyMap.containsValue(key) && !p1KeyMap.containsValue(key)) {
							p2KeyMap.put("attack", key);
						}
						changingAttackKey = false;
					} else if (changingSpecialKey) {
						if (!p2KeyMap.containsValue(key) && !p1KeyMap.containsValue(key)) {
							p2KeyMap.put("special", key);
						}
						changingSpecialKey = false;
					}
					// Keys can be changed by first pressing the key of the current one
					else if (key == p2KeyMap.get("up")) {
						changingUpKey = true;
					} else if (key == p2KeyMap.get("down")) {
						changingDownKey = true;
					} else if (key == p2KeyMap.get("left")) {
						changingLeftKey = true;
					} else if (key == p2KeyMap.get("right")) {
						changingRightKey = true;
					} else if (key == p2KeyMap.get("attack")) {
						changingAttackKey = true;
					} else if (key == p2KeyMap.get("special")) {
						changingSpecialKey = true;
					}
				}

				// Setting the keys as controls
				setKeyMap();
			} else {
				if (key == KeyEvent.VK_RIGHT) {
					optionSelected++;

					// Going through the settings main options
					if (!changingTimeLimit && !changingKeyMap && !changingLifeCount) {
						if (optionSelected == 3) {
							optionSelected = 0;
						}
					}
					// Going through the settings time limit options
					else if (changingTimeLimit) {
						if (optionSelected == 4) {
							optionSelected = 0;
						}
					}
					// Going through the life count options
					else if (changingLifeCount) {
						if (optionSelected == 5) {
							optionSelected = 0;
						}
					}
				} else if (key == KeyEvent.VK_LEFT) {
					optionSelected--;

					if (optionSelected == -1) {
						// Going through the settings main options backward
						if (!changingTimeLimit && !changingKeyMap && !changingLifeCount) {
							optionSelected = 2;
						}
						// Going through the changing time limit options
						else if (changingTimeLimit) {
							optionSelected = 3;
						}
						// Going through the starting lives options
						else if (changingLifeCount) {
							optionSelected = 4;
						}
					}
				}
				// Choosing selected options
				else if (key == KeyEvent.VK_ENTER) {
					if (!changingTimeLimit && !changingKeyMap && !changingLifeCount) {
						// First is time limit
						if (optionSelected == 0) {
							changingTimeLimit = true;
							optionSelected = 0;
						}
						// Second is key controls
						else if (optionSelected == 1) {
							changingKeyMap = true;
							optionSelected = 0;
						}
						// Third is changing number of lives
						else if (optionSelected == 2) {
							changingLifeCount = true;
							optionSelected = 2;
						}
					}
					// Time Left
					else if (changingTimeLimit) {
						// Different times based on the option selected
						if (optionSelected == 0) {
							timeLimit = 0;
							hasTimeLimit = false;
						} else if (optionSelected == 1) {
							timeLimit = 30;
							hasTimeLimit = true;
						} else if (optionSelected == 2) {
							timeLimit = 60;
							hasTimeLimit = true;
						} else if (optionSelected == 3) {
							timeLimit = 90;
							hasTimeLimit = true;
						}

						optionSelected = 0;
						changingTimeLimit = false;
					}
					// Lives that each character starts with
					else if (changingLifeCount) {
						livesPerCharacter = optionSelected + 1;
						optionSelected = 0;
						changingLifeCount = false;

					}
				}
			}

			repaint();
		}
		// Pressing any key takes you back to the main screen
		else if (gameState == 8) {
			gameState = 1;

			gameSong.stop();
			menuSong.setFramePosition(0);
			menuSong.loop(Clip.LOOP_CONTINUOUSLY);

			resetGame();
		}
	}

	// Description: This handles all actions that occur when a key is released
	// Parameters: KeyEvent object
	// Return: None
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		// If in the game
		if (gameState == 3) {
			// PLAYER 1
			// Walking ceases if right or left key is let go
			if (key == p1RightKey) {
				p1Right = false;
				p1WalkImage = 0;
			} else if (key == p1LeftKey) {
				p1Left = false;
				p1WalkImage = 0;
			}
			// Stops falling through ledges if down key is let go
			else if (key == p1DownKey) {
				p1Down = false;
			}

			// PLAYER 2
			// Walking ceases if D or A is let go
			if (key == p2RightKey) {
				p2Right = false;
				p2WalkImage = 0;
			} else if (key == p2LeftKey) {
				p2Left = false;
				p2WalkImage = 0;
			}
			// Stops falling through ledges if S is let go
			else if (key == p2DownKey) {
				p2Down = false;
			}
		}
	}

	// Description: This facilitates the increase/decrease of a character's x speed,
	// x value, etc.
	// It allows the characters to move around
	// Paramters: None
	// Return: Void
	public static void move() {
		if (!paused && winner == 0) {
			if (gameState == 3) {
				if (!p1KO) {
					// PLAYER 1
					// Walking left
					if (p1Left) {
						p1XSpeed = -p1WalkSpeed;

					}
					// Walking right
					else if (p1Right) {
						p1XSpeed = p1WalkSpeed;
					}
					// Idle
					else {
						p1XSpeed = 0;
					}

					// If you are in the air
					if ((p1Airborne || p1DoubleJumped) && !p1Jump2) {
						p1YSpeed -= gravity;
					} else {
						// First jump
						if (p1Jump && !p1Jump2) {
							p1Airborne = true;
							p1YSpeed = p1JumpSpeed;
						}
						// Second jump
						else if (p1Jump2) {
							p1DoubleJumped = true;
							p1Jump2 = false;
							p1YSpeed = p1JumpSpeed;
						}
					}

					// If you are on a ledge and you fall off
					if (p1.getHitBox().getMaxX() < p1CurLedge.getMinX()
							|| p1.getHitBox().getMinX() < p1CurLedge.getMaxX()) {
						if (!p1KO) {
							p1Airborne = true;
						}
					}

					// Falling off the ground floor
					if (!p1KO && (p1.getHitBox().getMaxX() < 115 || p1.getHitBox().getMinX() > 827)) {
						p1Airborne = true;
						p1CurFloor = 560 + p1.getHeight();
					}

					// Updating coordinates
					p1X += p1XSpeed;
					p1Y -= p1YSpeed;

					// Updating the hit box and attack box with the new coordinates
					p1.updateBoxes(p1X, p1Y, p1FacingLeft);
				}

				if (!p2KO) {
					// PLAYER 2
					// Walking left
					if (p2Left) {
						p2XSpeed = -p2WalkSpeed;
					}
					// Walking right
					else if (p2Right) {
						p2XSpeed = p2WalkSpeed;
					}
					// Idle
					else {
						p2XSpeed = 0;
					}

					// If you are in the air
					if ((p2Airborne || p2DoubleJumped) && !p2Jump2) {
						p2YSpeed -= gravity;
					} else {
						// First jump
						if (p2Jump && !p2Jump2) {
							p2Airborne = true;
							p2YSpeed = p2JumpSpeed;
						}
						// Second jump
						else if (p2Jump2) {
							p2DoubleJumped = true;
							p2Jump2 = false;
							p2YSpeed = p2JumpSpeed;
						}
					}

					// If you are on a ledge and you fall off
					if (p2.getHitBox().getMaxX() < p2CurLedge.getMinX()
							|| p2.getHitBox().getMinX() < p2CurLedge.getMaxX()) {
						if (!p2KO) {
							p2Airborne = true;
						}
					}

					// Falling off the ground level
					if (!p2KO && (p2.getHitBox().getMaxX() < 115 || p2.getHitBox().getMinX() > 827)) {
						p2Airborne = true;
						p2CurFloor = 560 + p2.getHeight();
					}

					// Updating coordinates
					p2X += p2XSpeed;
					p2Y -= p2YSpeed;

					// Updating the hit box with the new coordinates
					p2.updateBoxes(p2X, p2Y, p2FacingLeft);
				}

				// PLAYER 1
				// Walk animations
				if (p1Right || p1Left) {
					walkAnimation("p1");
				}
				// PLAYER 2
				// Walk animations
				if (p2Right || p2Left) {
					walkAnimation("p2");
				}

			}
		}

	}

	// Description: This helps to keep characters within the set bounds (bottom,
	// left, right. etc.)
	public static void keepInBound() {

		if (gameState == 3 && !paused) {
			// PLAYER 1
			if (p1X < 0) {
				// Can't go too left
				p1X = 0;
			} else if (p1X > 960 - 20) {
				// Can't go too right
				p1X = 960 - 20;
			}

			// Sees if P1 has passed curFloor or not
			if (p1Y >= p1CurFloor - p1.getHeight()) {
				p1Y = p1CurFloor - p1.getHeight();
				p1Airborne = false;
				p1Jump = false;
				p1Jump2 = false;
				p1DoubleJumped = false;
				p1YSpeed = 0;
			}

			// If they fall off or have already fallen off
			if (p1Y >= 560 || p1FallenOff) {
				p1FallenOff = true;
				p1KO = true;
				respawn();
			}

			// Calculating the current lowest ledge/ground the character is on
			p1CurLedge = selectedStage.getCurLedge(p1.getHitBox(), p1YSpeed, p1Down);
			p1CurFloor = (int) p1CurLedge.getMinY();

			// PLAYER 2
			if (p2X < 0) {
				// Can't go too left
				p2X = 0;
			} else if (p2X > 960 - 20) {
				// Can't go too right
				p2X = 960 - 20;
			}

			// Sees if P1 has passed curFloor or not
			if (p2Y >= p2CurFloor - p2.getHeight()) {
				p2Y = p2CurFloor - p2.getHeight();
				p2Airborne = false;
				p2Jump = false;
				p2Jump2 = false;
				p2DoubleJumped = false;
				p2YSpeed = 0;
			}

			if (p2Y >= 560 || p2FallenOff) {
				p2FallenOff = true;
				p2KO = true;
				respawn();
			}

			// Calculating the current lowest ledge/ground the character is on
			p2CurLedge = selectedStage.getCurLedge(p2.getHitBox(), p2YSpeed, p2Down);
			p2CurFloor = (int) p2CurLedge.getMinY();

		}
	}

	// Description: This method facilitates the switching between images to create a
	// walking
	// animation
	// Parameters: A String that indicates the player number that is playing
	// Return: Void
	public static void walkAnimation(String player) {
		// Player 1
		if (player.equals("p1")) {
			if (p1WalkCounter > p1.getWalkAnimationSpeed()) {
				p1WalkImage++;
				p1WalkCounter = 0;
			}

			if (p1WalkImage > p1.getNumWalkSprites() - 1) {
				p1WalkImage = 0;
			}

			p1WalkCounter++;
		}
		// Player 2
		else {
			if (p2WalkCounter > p2.getWalkAnimationSpeed()) {
				p2WalkImage++;
				p2WalkCounter = 0;
			}

			if (p2WalkImage > p2.getNumWalkSprites() - 1) {
				p2WalkImage = 0;
			}

			p2WalkCounter++;
		}

	}

	// Description: This method facilitates the switching between images to create a
	// walking
	// animation
	// Parameters: A String that indicates the player number that is playing, an int
	// that represents
	// the number of pictures each character has for their attack
	// Return: Void
	public static void attackAnimation(String player, int numPics) {
		// Player 1
		if (player.equals("p1")) {
			if (p1AttackCounter > p1.getAttackSpeed() && (p1AttackImage >= numPics - 1)) {
				p1Attacking = false;
				p1AttackCounter = 0;
				p1AttackImage = 0;
			} else if (p1AttackCounter > p1.getAttackSpeed()) {
				// The image shown goes up
				p1AttackImage++;
				p1AttackCounter = 0;
			}

			p1AttackCounter++;
		}
		// Player 2
		else {
			if (p2AttackCounter > p2.getAttackSpeed() && (p2AttackImage >= numPics - 1)) {
				p2Attacking = false;
				p2AttackCounter = 0;
				p2AttackImage = 0;
			} else if (p2AttackCounter > p2.getAttackSpeed()) {
				// The image shown goes up
				p2AttackImage++;
				p2AttackCounter = 0;
			}

			p2AttackCounter++;
		}

	}

	// Description: This method facilitates the animation of their speical attacks
	// Parameters: A String that indicates the player number that is playing, an int
	// that represents
	// the number of pictures each character has for their attack
	// Return: Void
	public static void specialAnimation(String player, int numPics) {
		// Player 1
		if (player.equals("p1")) {
			if (p1SpecialCounter > p1.getSpecial().getDuration() && (p1SpecialImage >= numPics - 1)) {
				p1Special = false;
				p1SpecialCounter = 0;
				p1SpecialImage = 0;
			} else if (p1SpecialCounter > p1.getSpecial().getDuration()) {
				p1SpecialImage++;
				p1SpecialCounter = 0;
			}

			p1SpecialCounter++;
		}
		// Player 2
		else {
			if (p2SpecialCounter > p2.getSpecial().getDuration() && (p2SpecialImage >= numPics - 1)) {
				p2Special = false;
				p2SpecialCounter = 0;
				p2SpecialImage = 0;
			} else if (p2SpecialCounter > p2.getSpecial().getDuration()) {
				p2SpecialImage++;
				p2SpecialCounter = 0;
			}

			p2SpecialCounter++;
		}

	}

	// Description: This method is used for respawning characters after they've been
	// KO'ed
	// Parameters: None
	// Return: Void
	public static void respawn() {
		// If it is P1 who has been KO'ed
		if (p1KO) {
			// Respawn counter has not been started
			if (!p1RespawnStarted) {
				// Setting coordinates of respawn
				p1X = 455;
				p1Y = 90 - p1.getHeight();
				p1.updateBoxes(p1X, p1Y, false);

				// Starting the respawn
				p1RespawnStarted = true;
				p1Airborne = false;

				// One less life
				p1.decLivesLeft();

				// If they have lost all lives, they have lost the game
				if (p1.getLivesLeft() == 0) {
					winner = 2;
				}
			} else {
				// Increasing the respawn counter
				p1RespawnCounter++;

				// After a little bit of time, the respawn counter will reach 50
				if (p1RespawnCounter > 50) {
					// Letting the character fall off the respawn platform
					p1Airborne = true;
					p1.resetDamageTaken();
					p1KO = false;
					p1RespawnCounter = 0;
					p1RespawnStarted = false;
					p1FallenOff = false;

					// Character is invulnerable to attacks
					p1Invulnerable = true;
				}
			}
		}

		// If it is P2 who has been KO'ed
		if (p2KO) {
			// Respawn counter has not been started
			if (!p2RespawnStarted) {
				p2X = 455;
				p2Y = 90 - p2.getHeight();
				p2.updateBoxes(p2X, p2Y, false);

				// Starting the respawn counter
				p2RespawnStarted = true;
				p2Airborne = false;

				// One less life
				p2.decLivesLeft();

				// If they have lost all lives, they have lost the game
				if (p2.getLivesLeft() == 0) {
					winner = 1;
				}
			} else {
				// Increasing the respawn counter
				p2RespawnCounter++;

				// After a little bit of time, the respawn counter will reach 50
				if (p2RespawnCounter > 50) {
					// Letting the character fall off the respawn platform
					p2Airborne = true;
					p2.resetDamageTaken();
					p2KO = false;
					p2RespawnCounter = 0;
					p2RespawnStarted = false;
					p2FallenOff = false;

					// Character is invulnerable to attacks
					p2Invulnerable = true;
				}
			}
		}
	}

	// Description: This method deals with a character's invulnerability right after
	// respawning
	// Parameters: None
	// Return: Void
	public static void makeInvulnerable() {
		// Player 1
		if (p1Invulnerable) {
			// Counting up until a certain point
			p1InvulnerableCounter++;

			if (p1InvulnerableCounter > 50) {
				// No more invulnerability
				p1Invulnerable = false;
				p1InvulnerableCounter = 0;
			}
		}
		// Player 2
		else if (p2Invulnerable) {
			// Counting up until a certain point
			p2InvulnerableCounter++;

			if (p2InvulnerableCounter > 50) {
				// No more invulnerability
				p2Invulnerable = false;
				p2InvulnerableCounter = 0;
			}
		}
	}

	// Description: This resets a lot of variables for starting a new game
	// Paramters: None
	// Return: Void
	public static void resetGame() {
		p1Selected = false;
		p2Selected = false;
		p1KO = false;
		p2KO = false;

		p1X = 410;
		p1Y = 10;

		p2X = 480;
		p2Y = 10;

		p1Jump = false;
		p1Jump2 = false;
		p1DoubleJumped = false;
		p1Left = false;
		p1Right = false;
		p1Down = false;
		p1Attacking = false;
		p1Special = false;

		p2Jump = false;
		p2Jump2 = false;
		p2DoubleJumped = false;
		p2Left = false;
		p2Right = false;
		p2Down = false;
		p2Attacking = false;
		p2Special = false;

		p1WalkCounter = 0;
		p1WalkImage = 0;
		p1AttackCounter = 0;
		p1AttackImage = 0;
		p1SpecialCounter = 0;
		p1SpecialImage = 0;

		p2WalkCounter = 0;
		p2WalkImage = 0;
		p2AttackCounter = 0;
		p2AttackImage = 0;
		p2SpecialCounter = 0;
		p2SpecialImage = 0;

		winner = 0;

		optionSelected = 0;
	}

	// Description: This method sets the key variables to what they are in the map
	// Parameters: None
	// Return: Void
	public static void setKeyMap() {
		// Player 1
		p1UpKey = p1KeyMap.get("up");
		p1DownKey = p1KeyMap.get("down");
		p1LeftKey = p1KeyMap.get("left");
		p1RightKey = p1KeyMap.get("right");
		p1AttackKey = p1KeyMap.get("attack");
		p1SpecialKey = p1KeyMap.get("special");

		// Player 2
		p2UpKey = p2KeyMap.get("up");
		p2DownKey = p2KeyMap.get("down");
		p2LeftKey = p2KeyMap.get("left");
		p2RightKey = p2KeyMap.get("right");
		p2AttackKey = p2KeyMap.get("attack");
		p2SpecialKey = p2KeyMap.get("special");
	}

	// Description: This adds all the images for the keys that appear in the key
	// mapping settings
	// Paramters: None
	// Return: Void
	public static void loadKeyImages() {
		// Each image's name is their KeyEvent keycode
		try {
			keyImageMap.put(32, ImageIO.read(new File(keyboardFolder + "32.png")));

			for (int i = 37; i < 41; i++) {
				keyImageMap.put(i, ImageIO.read(new File(keyboardFolder + i + ".png")));
			}
			for (int i = 44; i < 48; i++) {
				keyImageMap.put(i, ImageIO.read(new File(keyboardFolder + i + ".png")));
			}

			keyImageMap.put(59, ImageIO.read(new File(keyboardFolder + "59.png")));
			keyImageMap.put(61, ImageIO.read(new File(keyboardFolder + "61.png")));

			for (int i = 65; i < 94; i++) {
				keyImageMap.put(i, ImageIO.read(new File(keyboardFolder + i + ".png")));
			}

			keyImageMap.put(222, ImageIO.read(new File(keyboardFolder + "222.png")));

		} catch (FileNotFoundException f) {
			System.out.println("image not found!");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	// Description: This method updates the timer and if the timer is depleted,
	// determines the winner
	// Parameters: None
	// Return: Void
	public static void updateTimer() {
		if (gameState == 3) {
			if (hasTimeLimit && timeLeft > 0) {
				// Increasing the count
				timerCount++;

				// Once reaching a certain point, 1 second has passed
				if (timerCount > 50) {
					timeLeft--;
					timerCount = 0;
				}
			}
			// If there is no time left and there is a time limit
			else if (hasTimeLimit && timeLeft == 0) {
				// Player 1 wins if they have more lives left
				if (p1.getLivesLeft() > p2.getLivesLeft()) {
					winner = 1;
				}
				// Player 2 wins if they have more lives left
				else if (p2.getLivesLeft() > p1.getLivesLeft()) {
					winner = 2;
				}
				// If they have the same number of lives left
				else {
					// If player 1 has taken less damage on that life
					if (p1.getDamageTaken() < p2.getDamageTaken()) {
						winner = 1;
					}
					// If player 2 has taken less damage on that life
					else if (p2.getDamageTaken() < p1.getDamageTaken()) {
						winner = 2;
					}
					// Tie - neither win
					else {
						winner = 3;
					}
				}
			}
		}
	}

	// Description: This method updates the number of times played and number of
	// wins for the characters
	// Parameters: None
	// Return: Void
	public static void updateStats() {
		// Local Variables
		String p1Char = p1.getName();
		String p2Char = p2.getName();

		// Kirby
		if (p1Char.equals("Kirby")) {
			// Played 1 more time
			Kirby.setNumTimesPlayed(Kirby.getNumTimesPlayed() + 1);
			charStatsList.get(0).setNumPlayed(Kirby.getNumTimesPlayed());
		}
		// Link
		else if (p1Char.equals("Link")) {
			// Played 1 more time
			Link.setNumTimesPlayed(Link.getNumTimesPlayed() + 1);
			charStatsList.get(1).setNumPlayed(Link.getNumTimesPlayed());
		}
		// Meta Knight
		else if (p1Char.equals("MetaKnight")) {
			// Played 1 more time
			MetaKnight.setNumTimesPlayed(MetaKnight.getNumTimesPlayed() + 1);
			charStatsList.get(2).setNumPlayed(MetaKnight.getNumTimesPlayed());
		}
		// Luigi
		else if (p1Char.equals("Luigi")) {
			// Played 1 more time
			Luigi.setNumTimesPlayed(Luigi.getNumTimesPlayed() + 1);
			charStatsList.get(3).setNumPlayed(Luigi.getNumTimesPlayed());
		}

		// Kirby
		if (p2Char.equals("Kirby")) {
			// Played 1 more time
			Kirby.setNumTimesPlayed(Kirby.getNumTimesPlayed() + 1);
			charStatsList.get(0).setNumPlayed(Kirby.getNumTimesPlayed());
		}
		// Link
		else if (p2Char.equals("Link")) {
			// Played 1 more time
			Link.setNumTimesPlayed(Link.getNumTimesPlayed() + 1);
			charStatsList.get(1).setNumPlayed(Link.getNumTimesPlayed());
		}
		// Meta Knight
		else if (p2Char.equals("MetaKnight")) {
			// Played 1 more time
			MetaKnight.setNumTimesPlayed(MetaKnight.getNumTimesPlayed() + 1);
			charStatsList.get(2).setNumPlayed(MetaKnight.getNumTimesPlayed());
		}
		// Luigi
		else if (p2Char.equals("Luigi")) {
			// Played 1 more time
			Luigi.setNumTimesPlayed(Luigi.getNumTimesPlayed() + 1);
			charStatsList.get(3).setNumPlayed(Luigi.getNumTimesPlayed());
		}

		// If the winner is player 1, the character they played gets 1 extra win
		if (winner == 1) {
			if (p1Char.equals("Kirby")) {
				Kirby.setNumWins(Kirby.getNumWins() + 1);
				charStatsList.get(0).setNumWon(Kirby.getNumWins());
			} else if (p1Char.equals("Link")) {
				Link.setNumWins(Link.getNumWins() + 1);
				charStatsList.get(1).setNumWon(Link.getNumWins());
			} else if (p1Char.equals("MetaKnight")) {
				MetaKnight.setNumWins(MetaKnight.getNumWins() + 1);
				charStatsList.get(2).setNumWon(MetaKnight.getNumWins());
			} else if (p1Char.equals("Luigi")) {
				Luigi.setNumWins(Luigi.getNumWins() + 1);
				charStatsList.get(3).setNumWon(Luigi.getNumWins());
			}
		}
		// If the winner is player 2, the character they played gets 1 extra win
		else if (winner == 2) {
			if (p2Char.equals("Kirby")) {
				Kirby.setNumWins(Kirby.getNumWins() + 1);
				charStatsList.get(0).setNumWon(Kirby.getNumWins());
			} else if (p2Char.equals("Link")) {
				Link.setNumWins(Link.getNumWins() + 1);
				charStatsList.get(1).setNumWon(Link.getNumWins());
			} else if (p2Char.equals("MetaKnight")) {
				MetaKnight.setNumWins(MetaKnight.getNumWins() + 1);
				charStatsList.get(2).setNumWon(MetaKnight.getNumWins());
			} else if (p2Char.equals("Luigi")) {
				Luigi.setNumWins(Luigi.getNumWins() + 1);
				charStatsList.get(3).setNumWon(Luigi.getNumWins());
			}
		}
	}

	// Description: This gets the current statistics from the text file
	// Parameters: None
	// Return: Void
	public static void readInStats() {
		// ArrayList to store the values
		charStatsList = new ArrayList<>();
		// Local variables
		int numPlayed;
		int numWins;

		try {
			BufferedReader inFile = new BufferedReader(new FileReader(assetsFolder + "Stats.txt"));

			// Reading in the lines
			for (int i = 0; i < 4; i++) {
				inFile.readLine();
				numPlayed = Integer.parseInt(inFile.readLine());
				numWins = Integer.parseInt(inFile.readLine());
				inFile.readLine();

				// Kirby
				if (i == 0) {
					Kirby.setNumTimesPlayed(numPlayed);
					Kirby.setNumWins(numWins);
					charStatsList.add(new Kirby(true));
				}
				// Link
				else if (i == 1) {
					Link.setNumTimesPlayed(numPlayed);
					Link.setNumWins(numWins);
					charStatsList.add(new Link(true));
				}
				// Meta Knight
				else if (i == 2) {
					MetaKnight.setNumTimesPlayed(numPlayed);
					MetaKnight.setNumWins(numWins);
					charStatsList.add(new MetaKnight(true));
				}
				// Luigi
				else if (i == 3) {
					Luigi.setNumTimesPlayed(numPlayed);
					Luigi.setNumWins(numWins);
					charStatsList.add(new Luigi(true));
				}
			}

			inFile.close();
		} catch (IOException e) {
			System.out.println("file error!");
		}
	}

	// Description: This writes in the new stats into the text file
	// Parameters: None
	// Return: Void
	public static void writeInStats() {
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter(assetsFolder + "Stats.txt"));

			// Writing the values in
			for (int i = 0; i < 4; i++) {
				outFile.println(charStatsList.get(i).getName());
				outFile.println(charStatsList.get(i).getNumPlayed());
				outFile.println(charStatsList.get(i).getNumOfWins());
				outFile.println();
			}

			outFile.close();
		} catch (IOException e) {
			System.out.println("file error!");
		}
	}

	// Description: This method looks for the character(s) with the most wins and
	// most times played
	// Parameters: None
	// Return: Void
	public static void findBestStats() {
		// Local Variables
		ArrayList<Character> cList = new ArrayList<>(charStatsList);
		ArrayList<Character> cList2 = new ArrayList<>(charStatsList);
		mostPlayed = new ArrayList<>();
		mostWins = new ArrayList<>();
		Character highestPlayed;
		Character highestWins;
		Character cur;

		// Sorting by wins
		Collections.sort(cList);
		highestWins = cList.get(0);
		// Finding all characters with that number of wins
		while (Collections.binarySearch(cList, highestWins) > -1) {
			cur = cList.get(Collections.binarySearch(cList, highestWins));
			mostWins.add(cur);
			cList.remove(cur);
		}

		// Sorting by times played
		Collections.sort(cList2, new SortByNumPlayed());
		highestPlayed = cList2.get(0);
		// Finding all characters with that number of times played
		while (Collections.binarySearch(cList2, highestPlayed, new SortByNumPlayed()) > -1) {
			cur = cList2.get(Collections.binarySearch(cList2, highestPlayed, new SortByNumPlayed()));
			mostPlayed.add(cur);
			cList2.remove(cur);
		}
	}

	// Description: This method finds the number of wins and number of times played
	// for a specific character
	// Parameters: A Character object
	// Return: Void
	public static void findSpecificStats(Character c) {
		// Local Variables
		ArrayList<Character> cList = new ArrayList<>(charStatsList);
		int index;

		// Sorting by name to help find the character
		Collections.sort(cList, new SortByName());
		index = Collections.binarySearch(cList, c, new SortByName());

		// Getting the number of times played and the number of wins
		selectedNumPlayed = cList.get(index).getNumPlayed();
		selectedNumWins = cList.get(index).getNumOfWins();

	}

	// Unused methods (useless)
	public void keyTyped(KeyEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
	}
}
