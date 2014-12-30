package ecgChart;

import javax.swing.JDialog;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class AppFrame extends JDialog implements MouseMotionListener, MouseListener{

	public AppFrame()
	{
		this.setSize(900, 368);
		this.setLocation(270, 368);
		this.setLayout(null);
		this.chartPanel1 = new ECGChartPanel(this);
		this.chartPanel1.setLocation(0,0);
		this.chartPanel1.cs.setXMax(2500);
		this.chartPanel1.cs.setXMin(0);
		this.chartPanel1.cs.setXTick(100);
		this.chartPanel1.cs.setYMax(1000);
		this.chartPanel1.cs.setYMin(0);
		this.chartPanel1.cs.setYTick(100);
		this.chartPanel1.cs.setXLabel("time (ms)");
		this.chartPanel1.cs.setYLabel("amplitude (mV)");
		this.add(chartPanel1);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("ECG Chart");
	}
	
	public void mouseDragged(MouseEvent e)
	{
		dx = x - e.getX();
		x = e.getX(); 
		
		int xMax = (int)this.chartPanel1.cs.getXMax()+dx*5;
		int xMin = (int)this.chartPanel1.cs.getXMin()+dx*5;
		
		if(xMin<=0)
		{
			this.chartPanel1.cs.setXMax(xMax-xMin);
			this.chartPanel1.cs.setXMin(0);
		}else
		{
			this.chartPanel1.cs.setXMax(xMax);
			this.chartPanel1.cs.setXMin(xMin);
		}
		this.repaint();
	}
	public void mouseMoved(MouseEvent e){}
	public void mousePressed(MouseEvent e)
	{
		 x = e.getX();
	}
	
	public void mouseReleased(MouseEvent e)
	{
	}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	
	private static int x;
	private static int dx;
	ECGChartPanel chartPanel1;
}
