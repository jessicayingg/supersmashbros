// Name: Jessica Ying
// Date: June 18, 2023
// Description: This is the SpecialAttack class for a program that is a rendition of
// Super Smash Bros Ultimate

import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class SpecialAttack {

	private String type;
	private Rectangle specialBox;
	private Rectangle attackingSize;
	private double damage;
	private int duration;
	private ArrayList <BufferedImage> spritesR;
	private ArrayList <BufferedImage> spritesL;
	private int numSprites;
	private boolean isHorizontal;
	private boolean isVertical;
	
	// Constructor
	public SpecialAttack(String type, Rectangle specialBox, Rectangle attackingSize, double damage, int duration, boolean horizontal, boolean vertical)
	{
		this.type = type;
		this.specialBox = specialBox;
		this.damage = damage;
		this.duration = duration;
		this.attackingSize = attackingSize;
		
		this.spritesR = new ArrayList <> ();
		this.spritesL = new ArrayList <> ();
		this.isHorizontal = horizontal;
		this.isVertical = vertical;
	}
	
	// Getters
	public String getType()
	{
		return this.type;
	}
	public Rectangle getSpecialBox()
	{
		return this.specialBox;
	}
	public Rectangle getAttackingSize()
	{
		return this.attackingSize;
	}
	public double getDamage()
	{
		return this.damage;
	}
	public int getDuration()
	{
		return this.duration;
	}
	public boolean getIsHorizontal()
	{
		return this.isHorizontal;
	}
	public boolean getIsVertical()
	{
		return this.isVertical;
	}
	public ArrayList <BufferedImage> getSprites(String dir)
	{
		if(dir.equals("R"))
		{
			return this.spritesR;
		}
		else
		{
			return this.spritesL;
		}
	}
	public int getNumSprites()
	{
		return this.numSprites;
	}
	
	// Setters
	public void setSprites(ArrayList <BufferedImage> a, String dir)
	{
		// If the character is facing right
		if(dir.equals("R"))
		{
			this.spritesR.addAll(a);
		}
		// If the character is facing left
		else
		{
			this.spritesL.addAll(a);
		}
		// Updating number of sprites
		this.numSprites = a.size();

	}
	
	// Description: This method updates the special hitbox for the character
	// Parameters: The Character whose box is being modified, the Character's x and y coordinates, the Character's
	// hitbox, and a boolean stating whether the Character is facing left or not
	// Return: Void
	public void updateBox(Character c, int x, int y, Rectangle hitBox, boolean left)
	{
		// Facing right
		if(!left)
		{
			// If the special is in one place (Kirby)
			if(!c.getSpecial().getIsHorizontal() && !c.getSpecial().getIsVertical())
			{
				this.specialBox.x = x;
				this.specialBox.y = y;
			}
			// If the special is vertical in a column (Meta Knight)
			else if(c.getSpecial().getIsVertical())
			{
				this.specialBox.x = x;
				this.specialBox.y = (int) (hitBox.getMinY() - c.getSpecial().getSpecialBox().getHeight());
			}
			// Regular horizontal special (Link, Luigi)
			else
			{
				this.specialBox.x = (int) hitBox.getMaxX();
				this.specialBox.y = y;
			}
		}
		// Facing left
		else
		{
			// If the special is in one place (Kirby)
			if(!c.getSpecial().getIsHorizontal() && !c.getSpecial().getIsVertical())
			{
				this.specialBox.x = x;
				this.specialBox.y = y;
			}
			// If the special is vertical in a column (Meta Knight)
			else if(c.getSpecial().getIsVertical())
			{
				this.specialBox.x = x;
				this.specialBox.y = (int) (hitBox.getMinY() - c.getSpecial().getSpecialBox().getHeight());
			}
			// Regular horizontal special (Link, Luigi)
			else
			{
				this.specialBox.x = (int) (hitBox.getMinX() - this.specialBox.getWidth());
				this.specialBox.y = y;
			}
		}
	}
}
