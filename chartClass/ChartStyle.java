package chartClass;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.JPanel;

public class ChartStyle {

	// Chart area properties
	private Rectangle chartArea;
	private Rectangle plotArea;
	private Color chartBackColor = Color.WHITE;
	private Color plotBackColor = Color.WHITE;
	private boolean chartAreaVisible = true;
	private boolean plotAreaVisible = true;
	
	// Chart tick properties
	private float xMin = 0f;
	private float xMax = 2500f;
	private float yMin = 500f;
	private float yMax = 1500f;
	private float xTick = 250f;
	private float yTick = 200f;
	private Color tickColor = Color.BLUE;
	private Font tickFont = new Font("Arial", Font.PLAIN,10);
	private Color tickFontColor = Color.BLACK;
	private boolean tickVisible = true;
    
    // Chart grid properties
	private boolean isXGridVisible = true;
    private boolean isYGridVisible = true;
    private LineStyle gridStyle = LineStyle.THIN_GRID;
    
    // Chart label properties
    private String xLabel = "X Axis";
    private String yLabel = "Y Axis";
    private String titleLabel = "ECG Chart";
    private Font labelFont = new Font("Arial", Font.BOLD,11);
    private Font titleFont = new Font("Arial", Font.BOLD, 12);
    private Color labelFontColor = Color.BLUE;
    private Color titleFontColor = Color.BLACK;
    private boolean xyLabelVisible = true;
    private boolean titleVisible = true;
    
    // Chart border properties
    private LineStyle plotBorderStyle = LineStyle.THIN_DEFAULT;
    private LineStyle chartBorderStyle = LineStyle.THIN_DEFAULT;
    private boolean plotBorderVisible = true;
    private boolean chartBorderVisible = true;
	
	public ChartStyle(){}
	
	public ChartStyle(JPanel p)
	{
		this.chartArea = p.getBounds();
		this.plotArea = this.chartArea.getBounds();
		this.plotArea.grow(-40,-30 );
	}
	
	public void AddChartStyle(Graphics g)
	{
		// Draw chart area
		if(this.chartAreaVisible == true)
		{
			g.setColor(this.chartBackColor);
			g.fillRect(chartArea.x, chartArea.y, chartArea.width, chartArea.height);
		}
		
		// Draw plot area
		if(this.plotAreaVisible == true)
		{
			g.setColor(this.plotBackColor);
			g.fillRect(plotArea.x, plotArea.y,plotArea.width, plotArea.height);
		}
		
		// Draw chart border
		if(this.chartBorderVisible == true){
			Graphics g2 = this.chartBorderStyle.setGraphics(g);
			g2.drawRect(chartArea.x, chartArea.y, chartArea.width-1, chartArea.height-1);
		}
		
		//Draw plot border
		if(this.plotBorderVisible == true){
			Graphics g2 = this.plotBorderStyle.setGraphics(g);
			g2.drawRect(plotArea.x, plotArea.y, plotArea.width, plotArea.height);
		}
		
		// Create vertical gridlines:
        float fX, fY;
        if (this.isYGridVisible == true)
        {
            Graphics g2 = this.gridStyle.setGraphics(g);
            for (fX = xMin+1; fX < xMax; fX ++)
            {
            	if(fX % xTick == 0)
            	{
            		Point2D.Float coord1 = new Point2D.Float(fX,yMin);
            		Point2D.Float coord2 = new Point2D.Float(fX,yMax);
            		g2.drawLine((int)point(coord1).x, (int)point(coord1).y, 
        					 	(int)point(coord2).x, (int)point(coord2).y);
            	}
            }
        }
        
        // Create horizontal gridlines:
        if (this.isXGridVisible == true)
        {
        	Graphics g2 = this.gridStyle.setGraphics(g);
            for (fY = yMin+yTick; fY < yMax; fY += yTick)
            {
        		Point2D.Float coord1 = new Point2D.Float(xMin,fY);
        		Point2D.Float coord2 = new Point2D.Float(xMax,fY);
        		g2.drawLine((int)point(coord1).x, (int)point(coord1).y, 
        					 (int)point(coord2).x, (int)point(coord2).y);
            }
        }
        
        // Create tick marks
        if(this.tickVisible)
        {
        	// Create the x-axis tick marks:
        	for (fX = xMin; fX <= xMax; fX ++)
        	{
        		if(fX % xTick == 0)
        		{
        			g.setColor(tickColor);
        			Point2D.Float coord1 = new Point2D.Float(fX,yMin);
        			g.drawLine((int)point(coord1).x, (int)point(coord1).y, 
    					 (int)point(coord1).x, (int)(point(coord1).y-5));
        			g.setFont(this.tickFont);
        			g.setColor(this.tickFontColor);
        			int fWidth = g.getFontMetrics().stringWidth(new Integer((int)fX).toString());
        			g.drawString(new Integer((int)fX).toString(), (int)point(coord1).x-fWidth/2, (int)point(coord1).y+10);
        		}
        	}
        
        	// Create the y-axis tick marks:
        	for (fY = yMin; fY <= yMax; fY += yTick)
        	{
        		Point2D.Float coord1 = new Point2D.Float(xMin,fY);
        		g.drawLine((int)point(coord1).x, (int)point(coord1).y, 
    					 (int)point(coord1).x+5, (int)(point(coord1).y));
        		g.setFont(this.tickFont);
        		g.setColor(this.tickFontColor);
        		int fHeight = g.getFontMetrics().getHeight();
        		int fWidth = g.getFontMetrics().stringWidth(new Integer((int)fY).toString());
        		g.drawString(new Integer((int)fY).toString(), (int)point(coord1).x-fWidth-2, (int)point(coord1).y+fHeight/2-2);
        	}
        }
        
        //create label
        this.AddLabels(g);
	}
	
	private void AddLabels(Graphics g)
    {
        float labelFontHeight = g.getFontMetrics(this.labelFont).getHeight();
        float titleFontHeight = g.getFontMetrics(this.labelFont).getHeight();
        
        if(this.xyLabelVisible == true){
        	// Add horizontal axis label:
        	g.setColor(this.labelFontColor);
        	g.setFont(this.labelFont);
        	float stringSize = g.getFontMetrics().stringWidth(xLabel);
        	g.drawString(xLabel, plotArea.x + plotArea.width/2 -(int)stringSize/2, chartArea.y+chartArea.height-(int)labelFontHeight/2);       
        
        	// Add vertical axis label:
        	stringSize = g.getFontMetrics().stringWidth(yLabel);
        	Graphics2D g2d = (Graphics2D) g;
        	AffineTransform at = new AffineTransform();
        	at.setToRotation(-Math.PI/2.0, chartArea.x+labelFontHeight, chartArea.y+chartArea.height/2+stringSize/2);
        	g2d.transform(at);
        	g2d.drawString(yLabel, chartArea.x+labelFontHeight, chartArea.y+(chartArea.height+stringSize)/2);
        
        	//reset 
        	at.setToRotation(+Math.PI/2.0, chartArea.x+labelFontHeight, chartArea.y+(chartArea.height+stringSize)/2);
        	g2d.transform(at);
        }
        
        // Add title label
        if(this.titleVisible){
        	g.setColor(this.titleFontColor);
        	g.setFont(this.titleFont);
        	float stringSize = g.getFontMetrics().stringWidth(this.titleLabel);
        	g.drawString(this.titleLabel, plotArea.x + plotArea.width/2 -(int)stringSize/2, chartArea.y+2*(int)titleFontHeight);
        }
    }

	public Point2D.Float point(Point2D.Float pt)
	{
        Point2D.Float aPoint = new Point2D.Float();
        aPoint.x = plotArea.x + (pt.x - xMin)* plotArea.width / (xMax - xMin);
        aPoint.y = plotArea.y + plotArea.height - (pt.y - yMin) * plotArea.height / (yMax - yMin);
        
        return aPoint;
	}
	
	/*
	 * getter method
	 */
	public Rectangle getChartArea()
	{
		return this.chartArea;
	}
	
	public Rectangle getPlotArea()
	{
		return this.plotArea;
	}

	public Color getChartBackColor()
	{
		return this.chartBackColor;
	}
	
	public Color getPlotBackColor()
	{
		return this.plotBackColor;
	}
	
	public boolean isChartAreaVisible()
	{
		return this.chartAreaVisible;
	}
	
	public boolean isPlotAreaVisible()
	{
		return this.plotAreaVisible;
	}
	
	public float getXMin()
	{
		return this.xMin;
	}
	
	public float getXMax()
	{
		return this.xMax;
	}
	
	public float getYMin()
	{
		return this.yMin;
	}
	
	public float getYMax()
	{
		return this.yMax;
	}
	
	public float getXTick()
	{
		return this.xTick;
	}
	
	public float getYTick()
	{
		return this.yTick;
	}
	
	public Color getTickColor()
	{
		return this.tickColor;
	}
	
	public Color getTickFontColor()
	{
		return this.tickFontColor;
	}
	
	public Font getTickFont()
	{
		return this.tickFont;
	}
	
	public boolean isTickVisible()
	{
		return this.tickVisible;
	}
	
	/*
	 * Setter method
	 */
	public void setXMax(int max)
	{
		this.xMax = max;
	}
	
	public void setXMin(int min)
	{
		this.xMin = min;
	}
	
	public void setYMax(float max)
	{
		this.yMax = max;
	}
	
	public void setYMin(float min)
	{
		this.yMin = min;
	}
	
	public void setXTick(float tick)
	{
		this.xTick = tick;
	}
	
	public void setYTick(float tick)
	{
		this.yTick = tick;
	}
	
	public void setTitle(String title)
	{
		this.titleLabel = title;
	}
	
	public void setXLabel(String label)
	{
		this.xLabel = label;
	}
	
	public void setYLabel(String label)
	{
		this.yLabel = label;
	}
}
