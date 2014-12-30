package ecgChart;

import chartClass.*;
import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TextArea;
import java.awt.geom.Point2D;
import java.awt.BorderLayout;

import java.awt.Graphics;

public class R2RDialog extends JDialog{

	public R2RDialog()
	{	
		this.setSize(900, 368);
		this.setLocation(270, 0);
		panel = new R2RPanel(this);
		bPanel = new BeatRatePanel();
		bPanel.setLocation(690,225 );
		ta = new TextArea();
		ta.setSize(690, 110);
		ta.setLocation(0, 225);
		this.getContentPane().setLayout(null);
		this.getContentPane().add(panel);
		this.getContentPane().add(ta);
		this.getContentPane().add(bPanel);
	}
	
	public void addPeak(int time, int qrstime)
	{
		if(initiated)
		{
			currentPeak = time;
			int r2rtime = currentPeak - lastPeak;
			panel.ds.addPoint(new Point2D.Float(r2rxtime,(float)r2rtime/200*1000));
			r2rxtime ++;
			bPanel.beatRate += (int)(60/(float)r2rtime*200);
			bPanel.beatRate /= 2;
			ta.append("\n R-R time : "+(float)r2rtime/200*1000+" ms \t BeatRate : "+(int)(60/(float)r2rtime*200)+" bpm \t QRS Time : "+ (qrstime/200f*1000)+" ms");
			this.repaint();
			lastPeak = currentPeak;
		}
		else
		{
			currentPeak = time;
			lastPeak = currentPeak;
			initiated = true;
		}
	}
	
	R2RPanel panel;
	TextArea ta;
	BeatRatePanel bPanel;
	boolean initiated;
	
	int currentPeak;
	int lastPeak;
	int r2rxtime;
}

class R2RPanel extends JPanel 
{
	public R2RPanel(JDialog frame)
	{	
		this.setSize(frame.getWidth(), 225);
		this.cs = new ChartStyle(this);
		this.cs.setTitle("R to R Time Chart");
		this.cs.setYMin(0);
		this.cs.setYMax(3000);
		this.cs.setXMin(0);
		this.cs.setXMax(35);
		this.cs.setYTick(500);
		this.cs.setXTick(1);
		this.cs.setXLabel("sample (unit)");
		this.cs.setYLabel("time (ms)");
		this.dc = new DataCollection();
		this.ds = new DataSeries();
		this.ds.setLineStyle(LineStyle.RED_THICK_ANTIALIASED);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		cs.AddChartStyle(g);
		ds.DrawLines(g, cs);
		dc.AddLines(g, cs);
	}
	
	DataSeries ds;
	DataCollection dc;
	ChartStyle cs;
}

class BeatRatePanel extends JPanel 
{
	public BeatRatePanel()
	{
		this.setSize(200, 100);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setFont(new Font("Arial", Font.BOLD,20));
		g2d.setColor(Color.BLUE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("Beat Rate :",30,20);
		
		g2d.setFont(new Font("Arial", Font.BOLD,60));
		g2d.setColor(Color.RED);
		g2d.drawString(String.valueOf(beatRate),80,80);
	}
	
	public int beatRate;
}