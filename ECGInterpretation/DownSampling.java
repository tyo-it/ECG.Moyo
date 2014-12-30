package ECGInterpretation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import ecgChart.SampleReader;

public class DownSampling {
	
	public DownSampling(int inputFreq, int outputFreq, SampleReader inSample)
	{
		int i = gcd(inputFreq, outputFreq);
		m = inputFreq/i;
		n = outputFreq/i;
		mn = m*n; 
		ot = it = 0 ;
		
		inputSample = inSample; 
		lastSample = this.getSample();
		recentSample = this.getSample();
	}
	
	public DownSampling(int inputFreq, int outputFreq, FileInputStream inSample)
	{
		int i = gcd(inputFreq, outputFreq);
		m = inputFreq/i;
		n = outputFreq/i;
		mn = m*n; 
		ot = it = 0 ;
		
		inStreamFile = inSample; 
		lastSample = this.getSample();
		recentSample = this.getSample();
	}
	
	public Integer nextSample()
	{
		while(ot > it)
		{
    		lastSample = recentSample ;
    		
    		Integer val = this.getSample();
    		if( val == null) return null;
    		else recentSample = val;
    		
    		if (it > mn) 
    		{ 	it -= mn; 
    			ot -= mn; 
    		}
    		it += n;
	    }		
    
		int nextSample = lastSample + (ot%n)*(recentSample - lastSample)/n;
    	ot += m;
    	
    	return nextSample;
	}
	
	// Greatest common divisor of x and y (Euclid's algorithm)
	private int gcd(int x, int y)
	{
		while (x != y) {
			if (x > y) x-=y;
			else y -= x;
		}
		return (x);
	}
	
	private Integer getSample()
	{
		return inputSample.readSample();
	}
	
	BufferedReader inputSampleReader;
	
	int m;	//scaled input sampling periode 
	int n;	//scaled output sampling periode 
	int mn; //m multiple n 
	
	int it;
	int ot;
	
	int lastSample;
	int recentSample;
	SampleReader inputSample;
	FileInputStream inStreamFile;
}
