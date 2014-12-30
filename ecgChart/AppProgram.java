package ecgChart;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

import chartClass.DataSeries;
import chartClass.LineStyle;
import ECGInterpretation.*;

public class AppProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AppProgram ecgprogram = new AppProgram();

		threadReadSample = new Thread(ecgprogram.new ReadSample());
		threadRepaint = new Thread(ecgprogram.new Repaint());
	}
	
	public AppProgram()
	{
		this.init();
		
		mainGUI = new MainGUI();
		mainGUI.setVisible(true);
		mainGUI.analyzeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ecgFrame.setVisible(true);
                rDialog.setVisible(true);
        		threadReadSample.start();
        		threadRepaint.start();
            }
        });
		
		ds = new DownSampling(360,200,sampleReader1);
		lpf = new LowPassFilter(900);
		hpf = new HighPassFilter();
		der = new Derivatif();
		mwin = new MovingWindowIntegral();
		pd  = new PeakDetector();
		cb = new CircularBuffer(50);
		rDialog = new R2RDialog();
	}
	DataSeries ecgData1;
	DataSeries lpSeries;
	AppFrame ecgFrame;
	R2RDialog rDialog;
	MainGUI	mainGUI;
	
	File sampleFile1;
	SampleReader sampleReader1;
	
	DownSampling ds;
	LowPassFilter lpf;
	HighPassFilter hpf;
	Derivatif der;
	MovingWindowIntegral mwin;
	PeakDetector pd;
	CircularBuffer cb;
	
	static Thread threadReadSample;
	static Thread threadRepaint;
	
	public void init()
	{
		ecgData1 = new DataSeries();
		ecgData1.setLineStyle(LineStyle.RED_THICK_ANTIALIASED);
		
		lpSeries = new DataSeries();
		lpSeries.setLineStyle(LineStyle.BLUE_THIN_ANTIALIASED);
		
		ecgFrame = new AppFrame();
		ecgFrame.chartPanel1.ds1 = ecgData1;
		ecgFrame.chartPanel1.ds2 = lpSeries;
		
		sampleFile1 = new File("SampleFile","allsample.txt");
		sampleReader1 = new SampleReader(sampleFile1);

	}
	
	class Repaint implements Runnable
	{
		public void run()
		{
			while(true)
			{
				ecgFrame.repaint();
				try
				{
					Thread.sleep(10);
				}catch(InterruptedException exc){}
		
			}
		}
	}
	
	class ReadSample implements Runnable
	{
		int n;
		int range;
		Integer sample;
		
		public ReadSample()
		{
			range = (int)(ecgFrame.chartPanel1.cs.getXMax() - ecgFrame.chartPanel1.cs.getXMin());
		}
		
		public void run()
		{
			while(true)
			{
				sample = ds.nextSample();
				
				if(sample == null)	//end of file reached
				{
					try
					{
						Thread.sleep(30000);
					}catch(InterruptedException exc){}
					
					try{
						sampleReader1.inSampleFile.close();
					}catch(IOException e){}
					
					sampleReader1 = new SampleReader(sampleFile1);
					ds = new DownSampling(360,200,sampleReader1);
					
					lpf = new LowPassFilter(900);
					hpf = new HighPassFilter();
					der = new Derivatif();
					mwin = new MovingWindowIntegral();
					pd  = new PeakDetector();
					cb = new CircularBuffer(50);
					
					rDialog.panel.ds.getPointList().clear();
					rDialog.r2rxtime = 0;
					rDialog.initiated = false;
					rDialog.repaint();
					
					ecgFrame.chartPanel1.dc.RemoveAll();
					ecgFrame.chartPanel1.ds1.getPointList().clear();
					ecgFrame.chartPanel1.ds2.getPointList().clear();
					//ecgFrame.chartPanel1.ds3.getPointList().clear();
					
					n = 0;
					ecgFrame.chartPanel1.cs.setXMin(n);
					ecgFrame.chartPanel1.cs.setXMax(n+range);
				}
				else
				{
					int lp = (int)lpf.filter(sample);
					int hlp =(int)hpf.filter(lp);
					int dhlp = (int)Math.pow(der.derive(hlp),2);
					int mdhlp = mwin.calculate(dhlp);
					cb.add(mdhlp);
					int pde;
					if((pde = pd.run(mdhlp))>0)
					{
						int peaktime = pd.sampleCounter-cb.size+cb.maxSearch((pde>>1))-20;	//cb.maxSearch():get sample number between peak and 
							  															//1/2 peak of moving window integral.
						int mwpeaktime = pd.sampleCounter-cb.size+cb.maxSearch(pde);
						int qrsintervaltime = mwpeaktime - peaktime;
					
						System.out.println("peaktime : "+peaktime);
					
						DataSeries dsPeakTime = new DataSeries();
						dsPeakTime.setLineStyle(LineStyle.THIN_GRAY);
						dsPeakTime.addPoint(new Point2D.Float(peaktime*5,ecgFrame.chartPanel1.cs.getYMin()));
						dsPeakTime.addPoint(new Point2D.Float(peaktime*5,ecgFrame.chartPanel1.cs.getYMax()));
					
						DataSeries dsMWPeakTime = new DataSeries();
						dsMWPeakTime.setLineStyle(LineStyle.THIN_GRAY);
						dsMWPeakTime.addPoint(new Point2D.Float(mwpeaktime*5,ecgFrame.chartPanel1.cs.getYMin()));
						dsMWPeakTime.addPoint(new Point2D.Float(mwpeaktime*5,ecgFrame.chartPanel1.cs.getYMax()));
					
						ecgFrame.chartPanel1.dc.Add(dsPeakTime);
						ecgFrame.chartPanel1.dc.Add(dsMWPeakTime);
						rDialog.addPeak(peaktime, qrsintervaltime);
					}
					ecgData1.addPoint(new Point2D.Float(n,sample*1000/2048));
					lpSeries.addPoint(new Point2D.Float(n,(mdhlp+300)*1000/2048));
					n+=5;
					
					if(n%10000 == 0)
					{
						DataSeries divider = new DataSeries();
						divider.setLineStyle(LineStyle.THIN_GRAY);
						divider.addPoint(new Point2D.Float(rDialog.r2rxtime-1,rDialog.panel.cs.getYMin()));
						divider.addPoint(new Point2D.Float(rDialog.r2rxtime-1,rDialog.panel.cs.getYMax()));
						rDialog.panel.dc.Add(divider);
						try
						{
							Thread.sleep(10000);
						}catch(InterruptedException exc){}
					}
					
					if(n>=ecgFrame.chartPanel1.cs.getXMax())
					{
						ecgFrame.chartPanel1.cs.setXMin(n);
						ecgFrame.chartPanel1.cs.setXMax(n+range);
					}
				
					try
					{
						Thread.sleep(10);
					}catch(InterruptedException exc){}
				}
			}
			
		}
	}
}
