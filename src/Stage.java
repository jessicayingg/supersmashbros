// Name: Jessica Ying
// Date: June 18, 2023
// Description: This is the Stage class for a program that is a rendition of
// Super Smash Bros Ultimate

import java.awt.image.*;
import java.awt.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

public class Stage {

	private BufferedImage image;
	private Rectangle floor;
	private Rectangle[] ledgeBoxes;
	
	// EACH LEDGE IS 30 THICK AND 185 LENGTH
	
	// Constructor
	public Stage(String imageName, Rectangle floor, Rectangle ledge1, Rectangle ledge2, Rectangle ledge3)
	{
		// Adding the stage image
		try
		{
			this.image = ImageIO.read(new File(imageName));
		}
		catch(Exception e)
		{
			
		}
		this.floor = floor;
		this.ledgeBoxes = new Rectangle[3];
		this.ledgeBoxes[0] = ledge1;
		this.ledgeBoxes[1] = ledge2;
		this.ledgeBoxes[2] = ledge3;
		
	}
	
	// Getters
	public BufferedImage getImage()
	{
		return this.image;
	}
	public Rectangle getFloor()
	{
		return this.floor;
	}
	public Rectangle getLedge1()
	{
		return ledgeBoxes[0];
	}
	public Rectangle getLedge2()
	{
		return ledgeBoxes[1];
	}
	public Rectangle getLedge3()
	{
		return ledgeBoxes[2];
	}
	
	// Description: This method finds the ledge that a Character is supposed to be standing on
	// Parameters: The hitbox of the character, the character's current y speed, a boolean indicating if
	// the down button has been pressed or not
	// Return: A Rectangle related to the ledge that is the current ledge
	public Rectangle getCurLedge(Rectangle character, double charYSpeed, boolean down)
	{
		// Current floor begins as the lowest
		Rectangle curFloor = this.floor;
		
		int left = (int) character.getMinX();
		int right = (int) character.getMaxX();
		int bot = (int) character.getMaxY();
		
		// If the down button has been pressed, the current floor is the ground
		if(down)
		{
			return curFloor;
		}
		
		// Character must be falling for it to occur
		if(charYSpeed <= 0)
		{
			// Is above or on the ledge
			if(bot <= this.ledgeBoxes[0].getMinY())
			{
				// If the x-coordinates of the Character are in the bounds
				if(this.ledgeBoxes[0].getMinX() <= right && this.ledgeBoxes[0].getMaxX() >= left)
				{
					curFloor = this.ledgeBoxes[0];
				}
			}
			// Is above or on the ledge
			if(bot <= this.ledgeBoxes[1].getMinY())
			{
				// If the x-coordinates of the Character are in the bounds
				if(this.ledgeBoxes[1].getMinX() <= right && this.ledgeBoxes[1].getMaxX() >= left)
				{
					curFloor = this.ledgeBoxes[1];
				}
			}
			// Is above or on the ledge
			if(bot <= this.ledgeBoxes[2].getMinY())
			{
				// If the x-coordinates of the Character are in the bounds
				if(this.ledgeBoxes[2].getMinX() <= right && this.ledgeBoxes[2].getMaxX() >= left)
				{
					curFloor = this.ledgeBoxes[2];
				}
			}
		}
		
		return curFloor;
	}
	
}
