// Name: Jessica Ying
// Date: June 18, 2023
// Description: This is the Kirby class for a program that is a rendition of
// Super Smash Bros Ultimate

import java.awt.Rectangle;
import java.awt.image.*;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;

public class Kirby extends Character {

	private ArrayList<BufferedImage> sprites;
	private ArrayList<BufferedImage> walkLSprites;
	private ArrayList<BufferedImage> walkRSprites;
	private BufferedImage attackRSprite;
	private BufferedImage attackLSprite;
	private ArrayList<BufferedImage> attackLSprites;
	private ArrayList<BufferedImage> attackRSprites;

	private ArrayList<BufferedImage> specialLSprites;
	private ArrayList<BufferedImage> specialRSprites;

	private SpecialAttack special;

	private static int numTimesPlayed;
	private static int numWins;

	// Constructor for fighting Kirbys
	public Kirby() {
		// Calling superclass's constructor (Character)
		super("Kirby", 1, 15, 5, new Rectangle(50, 50), new Rectangle(40, 50), new Rectangle(100, 50), 1, 8, 10, 5);
		this.sprites = new ArrayList<>();
		this.walkLSprites = new ArrayList<>();
		this.walkRSprites = new ArrayList<>();

		this.attackRSprites = new ArrayList<>();
		this.attackLSprites = new ArrayList<>();

		this.specialLSprites = new ArrayList<>();
		this.specialRSprites = new ArrayList<>();

		// Adding special
		this.special = new SpecialAttack("damage", new Rectangle(50, 50), new Rectangle(50, 50), 5, 3, false, false);
		super.addSpecial(special);

		// Adding sprites
		try {
			sprites.add(ImageIO.read(new File(kirbyFolder + "KirbyR.png")));
			sprites.add(ImageIO.read(new File(kirbyFolder + "KirbyL.png")));

			// Walking
			for (int i = 0; i < 10; i++) {
				walkRSprites.add(ImageIO.read(new File(kirbyFolder + "KirbyRWalk" + (i + 1) + ".png")));
				walkLSprites.add(ImageIO.read(new File(kirbyFolder + "KirbyLWalk" + (i + 1) + ".png")));
			}

			// Attacking
			for (int i = 0; i < 8; i++) {
				attackRSprites.add(ImageIO.read(new File(kirbyFolder + "KirbyRAttack" + (i + 1) + ".png")));
				attackLSprites.add(ImageIO.read(new File(kirbyFolder + "KirbyLAttack" + (i + 1) + ".png")));
			}

			// Special Attack
			for (int i = 0; i < 4; i++) {
				specialRSprites.add(ImageIO.read(new File(kirbyFolder + "KirbyRSpecial" + (i + 1) + ".png")));
				specialLSprites.add(ImageIO.read(new File(kirbyFolder + "KirbyLSpecial" + (i + 1) + ".png")));
			}
		} catch (Exception e) {
			System.out.println("Image not found");
		}

		// Adding the special sprites
		super.addSpecialSprites(specialRSprites, "R");
		super.addSpecialSprites(specialLSprites, "L");
	}

	// Constructor for Kirbys that help keep track of stats
	public Kirby(boolean hi) {
		super("Kirby", numTimesPlayed, numWins);
	}

	// Used for stats:
	// Getters
	public static int getNumTimesPlayed() {
		return numTimesPlayed;
	}

	public static int getNumWins() {
		return numWins;
	}

	// Setters
	public static void setNumTimesPlayed(int value) {
		numTimesPlayed = value;
	}

	public static void setNumWins(int value) {
		numWins = value;
	}

	// Description: This gets an ArrayList of sprites for Kirby
	// Parameters: A String indicating the type of sprite that is desired
	// Return: An ArrayList of BufferedImage sprites
	public ArrayList<BufferedImage> getSprites(String type) {
		// Walking facing right
		if (type.equals("walkR")) {
			return this.walkRSprites;
		}
		// Walking facing left
		else if (type.equals("walkL")) {
			return this.walkLSprites;
		}
		// Attacking facing right
		else if (type.equals("attackR")) {
			return this.attackRSprites;
		}
		// Attacking facing left
		else if (type.equals("attackL")) {
			return this.attackLSprites;
		}
		// Special facing right
		else if (type.equals("specialR")) {
			return this.specialRSprites;
		}
		// Special facing left
		else if (type.equals("specialL")) {
			return this.specialLSprites;
		}
		// Idle facing left + right
		else {
			return sprites;
		}

	}

}
