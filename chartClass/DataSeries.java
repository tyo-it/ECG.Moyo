package chartClass;

import java.awt.*;
import java.util.*;
import java.awt.geom.*;

public class DataSeries {
	private ArrayList<Point2D.Float> pointList;
	private LineStyle lineStyle = LineStyle.MEDIUM_DEFAULT;
	private String seriesName = "Default Name";

	/*
	 * Constructor
	 */
	public DataSeries()
	{
		pointList = new ArrayList<Point2D.Float>();
	}
	
	public DataSeries(String name)
	{
		this();
		this.seriesName = name;
	}
	
	public DataSeries(String name, LineStyle style)
	{
		this(name);
		this.lineStyle = style;
	}
	
	public void addPoint(Point2D.Float pt)
	{
			pointList.add(pt);
	}
	
	public void DrawLines(Graphics g, ChartStyle cs)
	{
		 if ( this.lineStyle.isVisible() == true)
         {
			 Graphics g2 = this.lineStyle.setGraphics(g);
			 g2.setClip(cs.getPlotArea().x,cs.getPlotArea().y,cs.getPlotArea().width,cs.getPlotArea().height);

             for (int j = 1; j < this.pointList.size(); j++)
             {
                 g2.drawLine( (int)cs.point((Point2D.Float)this.pointList.get(j-1)).x,
               		  (int)cs.point((Point2D.Float)this.pointList.get(j-1)).y,
               		  (int)cs.point((Point2D.Float)this.pointList.get(j)).x,
               		  (int)cs.point((Point2D.Float)this.pointList.get(j)).y);
             }
         }
	}
	
	/*
	 * Getter method
	 */
	public ArrayList getPointList()
	{
		return this.pointList;
	}
	
	public LineStyle getLineStyle()
	{
		return this.lineStyle;
	}
	
	public String getSeriesName()
	{
		return this.seriesName;
	}
	
	/*
	 * Setter method
	 */
	public void setPointList(ArrayList<Point2D.Float> pList)
	{
		this.pointList = pList;
	}
	
	public void setLineStyle(LineStyle style)
	{
		this.lineStyle = style;
	}
	
	public void setSeriesName(String name)
	{
		this.seriesName = name;
	}
}
