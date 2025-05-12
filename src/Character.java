// Name: Jessica Ying
// Date: June 18, 2023
// Description: This is the Character class for a program that is a rendition of
// Super Smash Bros Ultimate

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Character implements Comparable<Character> {
	// Constants
	public static final String assetsFolder = "./assets/";
	public static final String imagesFolder = assetsFolder + "images/";
	public static final String charactersFolder = imagesFolder + "characters/";
	public static final String kirbyFolder = charactersFolder + "kirby/";
	public static final String linkFolder = charactersFolder + "link/";
	public static final String luigiFolder = charactersFolder + "luigi/";
	public static final String metaknightFolder = charactersFolder + "metaknight/";

	private String name;
	private double baseDmg;
	private double jumpSpeed;
	private double speed;
	private int width;
	private int height;
	private Rectangle hitBox;
	private Rectangle attackBox;
	private Rectangle attackSize;
	private double damageTaken;
	private int attackSpeed;
	private int numAttackSprites;
	private int numWalkSprites;
	private int livesLeft;
	private int walkAnimationSpeed;
	private SpecialAttack special;

	private int numTimesPlayed;
	private int numWins;

	// Constructor for fighting Characters
	public Character(String name, double damage, double jump, double speed, Rectangle hitBox, Rectangle attackBox,
			Rectangle attackCharSize, int attackSpeed, int numAttackSprites, int numWalkSprites,
			int walkAnimationSpeed) {
		this.name = name;
		this.baseDmg = damage;
		this.jumpSpeed = jump;
		this.speed = speed;
		this.hitBox = hitBox;
		this.attackBox = attackBox;
		this.width = (int) this.hitBox.getWidth();
		this.height = (int) this.hitBox.getHeight();
		this.attackSpeed = attackSpeed;
		this.damageTaken = 0;
		this.numAttackSprites = numAttackSprites;
		this.numWalkSprites = numWalkSprites;
		this.livesLeft = 3;
		this.attackSize = attackCharSize;
		this.walkAnimationSpeed = walkAnimationSpeed;
	}

	// Constructor for Characters that help keep track of stats
	public Character(String name, int numPlayed, int numWins) {
		this.name = name;
		this.numTimesPlayed = numPlayed;
		this.numWins = numWins;
	}

	// Getters
	public String getName() {
		return this.name;
	}

	public double getSpeed() {
		return speed;
	}

	public double getJumpSpeed() {
		return jumpSpeed;
	}

	public Rectangle getHitBox() {
		return this.hitBox;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public Rectangle getAttackBox() {
		return this.attackBox;
	}

	public int getAttackSpeed() {
		return this.attackSpeed;
	}

	public int getNumAttackSprites() {
		return this.numAttackSprites;
	}

	public int getNumWalkSprites() {
		return this.numWalkSprites;
	}

	public double getBaseDmg() {
		return this.baseDmg;
	}

	public double getDamageTaken() {
		return this.damageTaken;
	}

	public int getLivesLeft() {
		return this.livesLeft;
	}

	public int getAttackingWidth() {
		return (int) this.attackSize.getWidth();
	}

	public int getAttackingHeight() {
		return (int) this.attackSize.getHeight();
	}

	public int getWalkAnimationSpeed() {
		return this.walkAnimationSpeed;
	}

	public int getNumPlayed() {
		return this.numTimesPlayed;
	}

	public int getNumOfWins() {
		return this.numWins;
	}

	public ArrayList<BufferedImage> getSprites(String type) {
		return null;
	}

	public SpecialAttack getSpecial() {
		return this.special;
	}

	// Setters
	public void setLivesLeft(int lives) {
		this.livesLeft = lives;
	}

	public void setNumPlayed(int value) {
		this.numTimesPlayed = value;
	}

	public void setNumWon(int value) {
		this.numWins = value;
	}

	// Description: This method increases the damageTaken by a Character
	// Parameters: A double indicating how much to increase the damage taken by
	// Return: Void
	public void incDamageTaken(double damage) {
		this.damageTaken += damage;
	}

	// Description: This method sets the damageTaken to 0
	// Parameters: None
	// Return: Void
	public void resetDamageTaken() {
		this.damageTaken = 0;
	}

	// Description: This method decreases a Character's life count by 1
	// Parameters: None
	// Return: Void
	public void decLivesLeft() {
		this.livesLeft--;
	}

	// Description: This method sets the special sprites based on the parameters
	// given
	// Parameters: An ArrayList of sprite BufferedImages, a String indicating
	// direction
	// Return: Void
	public void addSpecialSprites(ArrayList<BufferedImage> a, String dir) {
		this.special.setSprites(a, dir);
	}

	// Description: This method sets the special based on the parameters given
	// Parameters: A SpecialAttack object
	// Return: Void
	public void addSpecial(SpecialAttack s) {
		this.special = s;
	}

	// Description: This method updates a Character's hitbox, attacking box, and
	// special box (range)
	// Parameters: The x and y coordinates of the Character, a boolean indicating
	// whether or not
	// the character is facing the left
	// Return: Void
	public void updateBoxes(int x, int y, boolean left) {
		// Setting hitbox coordinates
		hitBox.x = x;
		hitBox.y = y;

		// Facing right
		if (!left) {
			// Setting attack box (Character's size during an attack) coordinates
			attackBox.x = (int) hitBox.getMaxX();
			attackBox.y = y;

			// Setting special attack box
			special.updateBox(this, x, y, hitBox, left);
		}
		// Facing left
		else {
			// Setting attack box (Character's size during an attack) coordinates
			attackBox.x = (int) (hitBox.getMinX() - attackBox.getWidth());
			attackBox.y = y;

			// Setting special attack box
			special.updateBox(this, x, y, hitBox, left);
		}
	}

	// Description: This method determines whether a Character has been hit by
	// another Character's attack
	// Parameters: The Character attacking
	// Return: A boolean indicating whether or not the Character has been attacked
	public boolean isAttacked(Character c) {
		Rectangle att = c.getAttackBox();
		Rectangle body = this.getHitBox();

		// Seeing if the opponent's attack box intersects the hitbox of the Character
		return att.intersects(body);
	}

	// Description: This method determines whether a Character has been hit by
	// another Character's special
	// Parameters: The Character using their special
	// Return: A boolean indicating whether or not the Character has been hit with
	// the special
	public boolean isSpecialAttacked(Character c) {
		Rectangle att = c.special.getSpecialBox();
		Rectangle body = this.getHitBox();

		// Seeing if the opponent's special box intersects the hitbox of the Character
		return att.intersects(body);
	}

	// Description: This method determines whether or not two Characters are equal
	// Parameters: An object
	// Return: A boolean indicating whether or not the Characters are equal
	public boolean equals(Object o) {
		Character c = (Character) o;

		return c.name.equals(this.name);
	}

	// Description: This method compares two Characters through their number of wins
	// Parameters: A Character
	// Return: An integer determining the difference in the two Characters' number
	// of wins
	public int compareTo(Character c) {
		return c.numWins - this.numWins;
	}

	// Description: This turns a Character object into a String
	// Parameters: None
	// Return: The resultant String
	public String toString() {
		return this.name;
	}

}
