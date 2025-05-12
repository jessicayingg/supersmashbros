// Name: Jessica Ying
// Date: June 18, 2023
// Description: This is the Link class for a program that is a rendition of
// Super Smash Bros Ultimate

import java.awt.Rectangle;
import java.awt.image.*;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;

public class Link extends Character {

	private ArrayList<BufferedImage> sprites;
	private ArrayList<BufferedImage> attackLSprites;
	private ArrayList<BufferedImage> attackRSprites;
	private ArrayList<BufferedImage> walkLSprites;
	private ArrayList<BufferedImage> walkRSprites;
	private ArrayList<BufferedImage> specialRSprite;
	private ArrayList<BufferedImage> specialLSprite;

	private SpecialAttack special;

	private static int numTimesPlayed;
	private static int numWins;

	// Constructor for fighting Links
	public Link() {
		// Calling superclass's constructor (Character)
		super("Link", 1, 14, 5.2, new Rectangle(64, 80), new Rectangle(60, 80), new Rectangle(128, 120), 2, 5, 10, 5);
		this.sprites = new ArrayList<>();
		this.walkLSprites = new ArrayList<>();
		this.walkRSprites = new ArrayList<>();
		this.attackRSprites = new ArrayList<>();
		this.attackLSprites = new ArrayList<>();
		this.specialRSprite = new ArrayList<>();
		this.specialLSprite = new ArrayList<>();

		// Adding special
		this.special = new SpecialAttack("defense", new Rectangle(0, 0), new Rectangle(64, 80), 0, 20, true, false);
		super.addSpecial(special);

		// Adding sprites
		try {
			sprites.add(ImageIO.read(new File(linkFolder + "LinkR.png")));
			sprites.add(ImageIO.read(new File(linkFolder + "LinkL.png")));

			// Walking
			for (int i = 0; i < 10; i++) {
				walkRSprites.add(ImageIO.read(new File(linkFolder + "LinkRWalk" + (i + 1) + ".png")));
				walkLSprites.add(ImageIO.read(new File(linkFolder + "LinkLWalk" + (i + 1) + ".png")));
			}

			// Attacking
			for (int i = 0; i < 5; i++) {
				attackRSprites.add(ImageIO.read(new File(linkFolder + "LinkRAttack" + (i + 1) + ".png")));
				attackLSprites.add(ImageIO.read(new File(linkFolder + "LinkLAttack" + (i + 1) + ".png")));
			}

			specialRSprite.add(ImageIO.read(new File(linkFolder + "LinkRSpecial.png")));
			specialLSprite.add(ImageIO.read(new File(linkFolder + "LinkLSpecial.png")));

			// Adding the special sprites
			super.addSpecialSprites(specialRSprite, "R");
			super.addSpecialSprites(specialLSprite, "L");
		} catch (Exception e) {
			System.out.println("Image not found");
		}

	}

	// Constructor for Links that help keep track of stats
	public Link(boolean hi) {
		super("Link", numTimesPlayed, numWins);
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

	// Description: This gets an ArrayList of sprites for Link
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
			return this.specialRSprite;
		}
		// Special facing left
		else if (type.equals("specialL")) {
			return this.specialLSprite;
		}
		// Idle faciing left + right
		else {
			return sprites;
		}

	}

}
