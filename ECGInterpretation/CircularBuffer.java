package ECGInterpretation;

public class CircularBuffer {
	
	public CircularBuffer(int size)
	{
		this.size = size;
		buffer = new int[size];
	}
	
	public void add(int sample)
	{
		buffer[pointer++] = sample;
		
		if(pointer>=size){
			pointer = 0;
		}
	}
	
	public int maxSearch(int val)
	{
		int counter = 0;
		int point = pointer;
		int limitpoint = pointer;
		//System.out.println("max : "+val);
		
		do{
			if(buffer[point++]>=val) break;
			
			counter++;
			//System.out.println(counter+" : "+buffer[point]);
		
			if(point>=size){
				point = 0;
			}
		}
		while(point!=limitpoint);
		
		//System.out.println("counter : "+counter);
		return counter;
	}
	
	int pointer;
	public int size;
	int buffer[];
}
