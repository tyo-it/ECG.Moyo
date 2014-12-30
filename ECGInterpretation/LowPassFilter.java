package ECGInterpretation;

public class LowPassFilter {
	long y[] = new long [3];
	int  x[] = new int [26];
	int  n;
	
	public LowPassFilter()
	{
		n = 12;
	}
	
	public LowPassFilter(int init)
	{
		n = 12;
		for(int i = 0; i<x.length; i++) x[i] = init;
	}
	
	public long filter(int k)
	{
		x[n] = x[n+13] = k;
		y[0] = (y[1]<<1) - y[2] + x[n] - (x[n+6]<<1)+ x[n+12];
		y[2] = y[1];
		y[1] = y[0];
		y[0] >>= 5;
		if((--n) < 0){
			n=12;
		}
		
		return(y[0]);
	}
}
