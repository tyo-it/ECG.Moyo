package chartClass;

import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;

public enum LineStyle {
	THIN_DEFAULT (new BasicStroke(1.0f), Color.BLACK),
	MEDIUM_DEFAULT (new BasicStroke(1.5f), Color.BLACK),
	THIN_RED (new BasicStroke(1.0f), Color.RED),
	MEDIUM_RED (new BasicStroke(1.5f), Color.RED),
	THIN_BLUE (new BasicStroke(1.0f), Color.BLUE),
	MEDIUM_BLUE (new BasicStroke(1.5f), Color.BLUE),
	THIN_GREEN (new BasicStroke(1.0f), Color.GREEN),
	MEDIUM_GREEN (new BasicStroke(1.5f), Color.GREEN),
	THIN_YELLOW (new BasicStroke(1.0f), Color.YELLOW),
	MEDIUM_YELLOW (new BasicStroke(1.5f), Color.YELLOW),
	THIN_GRAY (new BasicStroke(1.0f), Color.GRAY),
	MEDIUM_GRAY (new BasicStroke(1.5f), Color.GRAY),
	THIN_GRID (new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 
			10.0f, new float[]{3.0f}, 0.0f), Color.LIGHT_GRAY),
	MEDIUM_GRID (new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 
								10.0f, new float[]{3.0f}, 0.0f), Color.LIGHT_GRAY),
	RED_THIN_ANTIALIASED (new BasicStroke(1.0f), Color.RED, true),
	GREEN_THIN_ANTIALIASED (new BasicStroke(1.0f), Color.GREEN, true),
	BLUE_THIN_ANTIALIASED (new BasicStroke(1.0f), Color.BLUE, true),
	RED_THICK_ANTIALIASED (new BasicStroke(2.0f), Color.RED, true),
	GREEN_THICK_ANTIALIASED (new BasicStroke(2.0f), Color.GREEN, true),
	BLUE_THICK_ANTIALIASED (new BasicStroke(2.0f), Color.BLUE, true);
	
	private LineStyle(BasicStroke stroke, Color color)
	{
		this.lineStroke = stroke;
		this.lineColor = color;
	}
	
	private LineStyle(BasicStroke stroke, Color color, boolean antiAliased)
	{
		this.lineStroke = stroke;
		this.lineColor = color;
		this.isAntiAliased = antiAliased;
	}
	
	private BasicStroke lineStroke;
	private Color lineColor = Color.BLACK;
	private boolean isVisible = true;
	private boolean isAntiAliased = false;   

	public boolean isVisible()
	{
		return isVisible;
	}
	
	public boolean isAntiAliased()
	{
		return isAntiAliased;
	}
	
	public BasicStroke getStroke()
	{
		return lineStroke;
	}
	
	public Color getColor()
	{
		return lineColor;
	}

	public void setColor(Color color)
	{
		this.lineColor = color;
	}
	
	public void setVisible(boolean bool)
	{
		this.isVisible = bool;
	}
	
	public void setAntiAliased(boolean bool)
	{
		this.isAntiAliased = bool;
	}
	
	public Graphics setGraphics(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setStroke(this.lineStroke);
		g2d.setColor (this.lineColor);
		if(this.isAntiAliased)
		{
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
			
		return g2d;
	}
}
