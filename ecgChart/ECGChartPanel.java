package ecgChart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import chartClass.*;

public class ECGChartPanel extends JPanel{
	
	public ECGChartPanel()
	{	
		this.setSize(600,400);
		this.cs = new ChartStyle(this);
		this.dc = new DataCollection();
	}
	
	public ECGChartPanel(Container c)
	{	
		this.setSize(c.getWidth(),c.getHeight()-35);
		this.cs = new ChartStyle(this);
		this.dc = new DataCollection();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		cs.AddChartStyle(g);
		dc.AddLines(g, cs);
		ds1.DrawLines(g, cs);
		ds2.DrawLines(g, cs);
	}
	
	DataSeries ds1;
	DataSeries ds2;
	DataSeries ds3;
	DataCollection dc;
	ChartStyle cs;
}
