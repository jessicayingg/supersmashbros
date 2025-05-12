// Name: Jessica Ying
// Date: June 18, 2023
// Description: This is the Luigi class for a program that is a rendition of
// Super Smash Bros Ultimate

import java.awt.Rectangle;
import java.awt.image.*;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;

public class Luigi extends Character {

	private ArrayList<BufferedImage> sprites;
	private ArrayList<BufferedImage> walkLSprites;
	private ArrayList<BufferedImage> walkRSprites;
	private ArrayList<BufferedImage> attackLSprites;
	private ArrayList<BufferedImage> attackRSprites;
	private ArrayList<BufferedImage> specialRSprites;
	private ArrayList<BufferedImage> specialLSprites;

	private SpecialAttack special;

	private static int numTimesPlayed;
	private static int numWins;

	// Constructor for fighting Luigis
	public Luigi() {
		// Calling superclass's constructor (Character)
		super("Luigi", 2, 15, 5, new Rectangle(60, 75), new Rectangle(30, 75), new Rectangle(80, 75), 3, 4, 8, 4);
		this.sprites = new ArrayList<>();
		this.walkLSprites = new ArrayList<>();
		this.walkRSprites = new ArrayList<>();
		this.attackRSprites = new ArrayList<>();
		this.attackLSprites = new ArrayList<>();
		this.specialRSprites = new ArrayList<>();
		this.specialLSprites = new ArrayList<>();

		// Adding special
		this.special = new SpecialAttack("damage", new Rectangle(30, 75), new Rectangle(90, 105), 4.5, 3, true, false);
		super.addSpecial(special);

		// Adding sprites
		try {
			sprites.add(ImageIO.read(new File(luigiFolder + "LuigiR.png")));
			sprites.add(ImageIO.read(new File(luigiFolder + "LuigiL.png")));

			// Walking
			for (int i = 0; i < 8; i++) {
				walkRSprites.add(ImageIO.read(new File(luigiFolder + "LuigiRWalk" + (i + 1) + ".png")));
				walkLSprites.add(ImageIO.read(new File(luigiFolder + "LuigiLWalk" + (i + 1) + ".png")));
			}

			// Attacking
			for (int i = 0; i < 4; i++) {
				attackRSprites.add(ImageIO.read(new File(luigiFolder + "LuigiRAttack" + (i + 1) + ".png")));
				attackLSprites.add(ImageIO.read(new File(luigiFolder + "LuigiLAttack" + (i + 1) + ".png")));
			}

			// Special Attack
			for (int i = 0; i < 6; i++) {
				specialRSprites.add(ImageIO.read(new File(luigiFolder + "LuigiRSpecial" + (i + 1) + ".png")));
				specialLSprites.add(ImageIO.read(new File(luigiFolder + "LuigiLSpecial" + (i + 1) + ".png")));
			}

			// Adding the special sprites
			super.addSpecialSprites(attackRSprites, "R");
			super.addSpecialSprites(attackLSprites, "L");

		} catch (Exception e) {
			System.out.println("Image not found");
		}

	}

	// Constructor for Luigis that help keep track of stats
	public Luigi(boolean hi) {
		super("Luigi", numTimesPlayed, numWins);
	}

	// Used for stats
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

	// Description: This gets an ArrayList of sprites for Luigi
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
