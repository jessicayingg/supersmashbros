// Name: Jessica Ying
// Date: June 18, 2023
// Description: This is the MetaKnight class for a program that is a rendition of
// Super Smash Bros Ultimate

import java.awt.Rectangle;
import java.awt.image.*;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;

public class MetaKnight extends Character {

	private ArrayList<BufferedImage> sprites;
	private ArrayList<BufferedImage> walkLSprites;
	private ArrayList<BufferedImage> walkRSprites;
	private ArrayList<BufferedImage> attackLSprites;
	private ArrayList<BufferedImage> attackRSprites;
	private ArrayList<BufferedImage> specialLSprites;
	private ArrayList<BufferedImage> specialRSprites;

	private SpecialAttack special;

	private static int numTimesPlayed;
	private static int numWins;

	// Constructor for fighting Meta Knights
	public MetaKnight() {

		// some ngtes: METAKNIGHT SPECIAL IS 48 WIDE
		// 65 37

		// Calling superclass's constructor (Character)
		super("MetaKnight", 2, 15, 5, new Rectangle(64, 65), new Rectangle(40, 65), new Rectangle(128, 80), 1, 7, 10,
				3);
		this.sprites = new ArrayList<>();
		this.walkLSprites = new ArrayList<>();
		this.walkRSprites = new ArrayList<>();
		this.attackRSprites = new ArrayList<>();
		this.attackLSprites = new ArrayList<>();
		this.specialLSprites = new ArrayList<>();
		this.specialRSprites = new ArrayList<>();

		// Adding special
		this.special = new SpecialAttack("damage", new Rectangle(90, 100), new Rectangle(90, 170), 2, 2, false, true);
		super.addSpecial(special);

		// Adding sprites
		try {
			sprites.add(ImageIO.read(new File(metaknightFolder + "MetaKnightR.png")));
			sprites.add(ImageIO.read(new File(metaknightFolder + "MetaKnightL.png")));

			// Walking
			for (int i = 0; i < 10; i++) {
				walkRSprites.add(ImageIO.read(new File(metaknightFolder + "MetaKnightRWalk" + (i + 1) + ".png")));
				walkLSprites.add(ImageIO.read(new File(metaknightFolder + "MetaKnightLWalk" + (i + 1) + ".png")));
			}

			// Attacking
			for (int i = 0; i < 7; i++) {
				attackRSprites.add(ImageIO.read(new File(metaknightFolder + "MetaKnightRAttack" + (i + 1) + ".png")));
				attackLSprites.add(ImageIO.read(new File(metaknightFolder + "MetaKnightLAttack" + (i + 1) + ".png")));
			}

			// Special Attack
			for (int i = 0; i < 9; i++) {
				specialRSprites.add(ImageIO.read(new File(metaknightFolder + "MetaKnightRSpecial" + (i + 1) + ".png")));
				specialLSprites.add(ImageIO.read(new File(metaknightFolder + "MetaKnightLSpecial" + (i + 1) + ".png")));
			}
		} catch (Exception e) {
			System.out.println("Image not found");
		}

		// Adding the special sprites
		super.addSpecialSprites(specialRSprites, "R");
		super.addSpecialSprites(specialLSprites, "L");

	}

	// Constructor for Meta Knights that help keep track of stats
	public MetaKnight(boolean hi) {
		super("Meta Knight", numTimesPlayed, numWins);
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

	// Description: This gets an ArrayList of sprites for Meta Knight
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
