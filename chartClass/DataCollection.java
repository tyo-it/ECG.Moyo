package chartClass;

import java.util.*;
import java.awt.*;
import java.awt.geom.*;

public class DataCollection {
	  public ArrayList<DataSeries> dataSeriesList;
      public int dataSeriesIndex = 0;
      
      public DataCollection()
      {
          dataSeriesList = new ArrayList<DataSeries>();
      }
      
      public void Add(DataSeries ds)
      {
          dataSeriesList.add(ds);
          if (ds.getSeriesName() == "Default Name")
          {
              ds.setSeriesName("DataSeries" + dataSeriesList.size());
          }
      }
      
      public void Insert(int dataSeriesIndex, DataSeries ds)
      {
          dataSeriesList.add(dataSeriesIndex, ds);
          if (ds.getSeriesName() == "Default Name")
          {
              dataSeriesIndex = dataSeriesIndex + 1;
              ds.setSeriesName("DataSeries" + dataSeriesIndex);
         }
      }
      
      public void Remove(String dataSeriesName)
      {
          if (dataSeriesList != null)
          {
              for (int i = 0; i < dataSeriesList.size(); i++)
              {
                  DataSeries ds = (DataSeries)dataSeriesList.get(i);
                  if (ds.getSeriesName()== dataSeriesName)
                  {
                      dataSeriesList.remove(i);
                  }
              }
          }
      }
      
      public void RemoveAll()
      {
          dataSeriesList.clear();
      }
      
      public void AddLines(Graphics g, ChartStyle cs)
      {
          // Plot lines:
    	  for(int i = 0 ; i<dataSeriesList.size() ; i++)
    	  {
    		  DataSeries ds = (DataSeries) dataSeriesList.get(i);
    		  if ( ds.getLineStyle().isVisible() == true)
              {
    			  Graphics g2 = ds.getLineStyle().setGraphics(g);
    			  g2.setClip(cs.getPlotArea().x,cs.getPlotArea().y,cs.getPlotArea().width,cs.getPlotArea().height);
    			  
                  for (int j = 1; j < ds.getPointList().size(); j++)
                  {
                      g2.drawLine( (int)cs.point((Point2D.Float)ds.getPointList().get(j-1)).x,
                    		  (int)cs.point((Point2D.Float)ds.getPointList().get(j-1)).y,
                    		  (int)cs.point((Point2D.Float)ds.getPointList().get(j)).x,
                    		  (int)cs.point((Point2D.Float)ds.getPointList().get(j)).y);
                  }
              }
    	  }
      }
}
