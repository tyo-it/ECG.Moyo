package ECGInterpretation;

public class HighPassFilter 
{
	long y[] = new long [2];
	int x[] = new int [66];
	int n;
	
	public HighPassFilter()
	{
		n = 32;
	}
	
	public long filter(int k)
	{
		x[n] = x[n+33] = k;
		y[0] = y[1] + x[n] - x[n+32];
		y[1] = y[0];
		if((--n)<0){ 
			n = 32;
		}
		
		return (x[n+16] - (y[0]>>5));
	}
}
