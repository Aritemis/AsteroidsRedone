/**
 * @author Ariana Fairbanks
 */

package view;

import java.awt.Color;

public enum ColorPreset
{
	OBLUE(Color.cyan, Color.blue, Color.black, Color.black, Color.red, Color.white, Color.black, Color.black);
	
	public final Color shipNLine1;
	public final Color shipNLine2;
	public final Color shipNFill1;
	public final Color shipNFill2;
	public final Color shipDLine1;
	public final Color shipDLine2;
	public final Color shipDFill1;
	public final Color shipDFill2;
	
	private ColorPreset(Color shipNLine1, Color shipNLine2, Color shipNFill1, Color shipNFill2, Color shipDLine1, Color shipDLine2, Color shipDFill1, Color shipDFill2)
	{
		this.shipNLine1 = shipNLine1;
		this.shipNLine2 = shipNLine2;
		this.shipNFill1 = shipNFill1;
		this.shipNFill2 = shipNFill2;
		this.shipDLine1 = shipDLine1;
		this.shipDLine2 = shipDLine2;
		this.shipDFill1 = shipDFill1;
		this.shipDFill2 = shipDFill2;
	}
	
}
