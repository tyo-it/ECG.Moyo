package ECGInterpretation;

public class MovingWindowIntegral 
{
	int x[] = new int[32];
	int n;
	long sum;
	long ly;
	int y;
	
	public int calculate(int k)
	{
		if((++n)==32){
			n=0;
		}
		sum -= x[n];
		sum += k;
		x[n]= k;
		ly = sum>>5;
		if(ly > 32400) y = 32400;
		else y =(int)ly;
		
		return(y);
	}
}
