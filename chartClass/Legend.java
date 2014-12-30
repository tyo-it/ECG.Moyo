package chartClass;

import java.awt.*;
import java.util.*;

public class Legend {
	
	private Color legendBackColor;
	private Color textColor;
	private LegendPositionEnum legendPosition;
	private Font legendFont;
	private LineStyle borderStyle;
	private boolean isOpaque;
	private boolean isBorderVisible;
	private boolean isLegendVisible;
	
	public enum LegendPositionEnum
	{
		North,
		NorthWest,
		West,
		SouthWest,
		South,
		SouthEast,
		East,
		NorthEast
	}
	
	public Legend(){
		legendPosition = LegendPositionEnum.NorthEast;
		textColor = Color.BLACK;
		legendBackColor = Color.WHITE;
		isLegendVisible = true;
		isBorderVisible = true;
		isOpaque = true;
		legendFont = new Font("Arial", Font.PLAIN,11 );
		borderStyle = LineStyle.THIN_DEFAULT;
	}
	
	public void AddLegend(Graphics g, DataCollection dc, ChartStyle cs)
	{
		int numberOfDataSeries = dc.dataSeriesList.size();
		String[] legendLabels = new String[dc.dataSeriesList.size()];
		
		for(int i = 0 ; i < numberOfDataSeries ;i++)
		{
			DataSeries ds = (DataSeries)dc.dataSeriesList.get(i);
			legendLabels[i] = ds.getSeriesName();
		}
		
		float offSet = 10;
		float xc = 0f;
		float yc = 0f;
		g.setFont(this.legendFont);
		
		float legendWidth = 0;	
		
		for (int i = 0; i < legendLabels.length; i++)
		{
			int tempWidth = g.getFontMetrics().stringWidth(legendLabels[i]);
			if (legendWidth < tempWidth)
				legendWidth = tempWidth;
		}
		
		legendWidth = legendWidth + 50;
		
		float hWidth = legendWidth / 2;
		float legendHeight = 18.0f * numberOfDataSeries;
		float hHeight = legendHeight / 2;
		
		switch (legendPosition)
		{
			case East:
				xc = cs.getPlotArea().x + cs.getPlotArea().width - offSet - hWidth;
				yc = cs.getPlotArea().y + cs.getPlotArea().height / 2;
				break;
			case North:
				xc = cs.getPlotArea().x + cs.getPlotArea().width / 2;
				yc = cs.getPlotArea().y + offSet + hHeight;
				break;
			case NorthEast:
				xc = cs.getPlotArea().x + cs.getPlotArea().width - offSet - hWidth;
				yc = cs.getPlotArea().y + offSet + hHeight;
				break;
			case NorthWest:
				xc = cs.getPlotArea().x + offSet + hWidth;
				yc = cs.getPlotArea().y + offSet + hHeight;
				break;
			case South:
				xc = cs.getPlotArea().x + cs.getPlotArea().width / 2;
				yc = cs.getPlotArea().y + cs.getPlotArea().height - offSet - hHeight;
				break;
			case SouthEast:
				xc = cs.getPlotArea().x + cs.getPlotArea().width - offSet - hWidth;
				yc = cs.getPlotArea().y + cs.getPlotArea().height - offSet - hHeight;
				break;
			case SouthWest:
				xc = cs.getPlotArea().x + offSet + hWidth;
				yc = cs.getPlotArea().y + cs.getPlotArea().height - offSet - hHeight;
				break;
			case West:
				xc = cs.getPlotArea().x + offSet + hWidth;
				yc = cs.getPlotArea().y + cs.getPlotArea().height / 2;
				break;
			default:
				break;
		}
		
		if(legendLabels.length > 0){ 
			this.DrawLegend(g, xc, yc, hWidth, hHeight, dc, cs);
		}
	}
	
	private void DrawLegend(Graphics g, float xCenter, float yCenter, float hWidth, float hHeight,
							DataCollection dc, ChartStyle cs)
	{
		float spacing = 8.0f;
		float textHeight = 8.0f;
		float htextHeight = textHeight / 2.0f;
		float lineLength = 30.0f;
		float hlineLength = lineLength / 2.0f;
		Rectangle legendRectangle;
		if(isLegendVisible)
		{
			//calculate legend rectangle
			legendRectangle = new Rectangle((int)(xCenter - hWidth), (int)(yCenter - hHeight), (int)(2.0f * hWidth), (int)(2.0f * hHeight));
			
			// Draw Back Color
			if(this.isOpaque)
			{
				g.setColor(this.legendBackColor);
				g.fillRect(legendRectangle.x, legendRectangle.y, legendRectangle.width, legendRectangle.height);
			}
			
			//Draw Border
			if (this.isBorderVisible)
			{
				Graphics g2 = this.borderStyle.setGraphics(g);
				g2.drawRect(legendRectangle.x, legendRectangle.y, legendRectangle.width, legendRectangle.height);
			}
			
			//Draw text and line for every data series
			int numberOfDataSeries = dc.dataSeriesList.size();
			for(int i = 0 ; i < numberOfDataSeries ;i++)
			{
				DataSeries ds = (DataSeries)dc.dataSeriesList.get(i);
				
				//Draw lines and symbols:
				float xText = legendRectangle.x + 2 * spacing + lineLength;
				float yText = legendRectangle.y + (i+1) * spacing + (2 * i) * htextHeight;
				Graphics g2 = ds.getLineStyle().setGraphics(g);
				g2.drawLine((int)(legendRectangle.x + spacing), (int)yText, 
						(int)(legendRectangle.x + spacing + lineLength), (int)yText);
				
				//Draw text:
				g.setFont(this.legendFont);
				g.setColor(this.textColor);
				g.drawString(ds.getSeriesName(), (int)xText, (int)yText+5);
			}
		}			
	}
	
	/*
	 * Getter method
	 */
	public Color getBackColor()
	{
		return this.legendBackColor;
	}
	
	public Color getFontColor()
	{
		return this.textColor;
	}
	
	public Font getFont()
	{
		return this.legendFont;
	}
	
	public LineStyle getBorderStyle()
	{
		return this.borderStyle;
	}
	
	public boolean isOpaque()
	{
		return this.isOpaque;
	}
	
	public boolean isBorderVisible()
	{
		return this.isBorderVisible;
	}
	
	public boolean isLegendVisible()
	{
		return this.isLegendVisible;
	}
	
	
	/*
	 * Setter method
	 */
	public void setBackColor(Color color)
	{
		this.legendBackColor = color;
	}
	
	public void setTextColor(Color color)
	{
		this.textColor = color; 
	}
	
	public void setBorderVisibility(boolean visible)
	{
		this.isBorderVisible = visible;
	}
	
	public void setVisibility(boolean visible)
	{
		this.isLegendVisible = visible;
	}
	
	public void setOpaquenes(boolean opaq)
	{
		this.isOpaque = opaq;
	}
	
	public void setLegendPosition(LegendPositionEnum position)
	{
		this.legendPosition = position;
	}
}
