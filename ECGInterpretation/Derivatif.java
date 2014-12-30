package ECGInterpretation;

public class Derivatif {
	int y;
	int x[] = new int [4]; 
	
	public int derive(int k)
	{
		y = (k<<1) + x[3] - x[1] - (x[0]<<1);
		y>>=3;
		
		for(int i=0; i<3 ; i++)
		{
			x[i] = x[i+1];
		}
		x[3]=k;
		
		return y;
	}
}
