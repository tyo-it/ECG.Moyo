package ECGInterpretation;

public class PeakDetector {
	int max, lastSample, peak;
	public int sampleCounter, timeSinceMax, tempTime;
	final int valThreshold = 50;
	final int timeThreshold = 50;
	boolean startIgnore;
	int timer;
	int time;
	
	public int run(int sample)
	{
		++sampleCounter;
		time++;
		
		if(!startIgnore)
		{
			if(timeSinceMax > 0)
			{
				++timeSinceMax;
			}

			if((sample>lastSample)&&(sample>max))
			{
				max = sample;
				if(max > valThreshold){
				timeSinceMax = 1;
				}
			}
			else if(sample < (max>>1)&&(timeSinceMax>0))
			{
				peak = max;
				//System.out.println("sample : "+sample);
				//System.out.println("max : "+max);
				//System.out.println("time : "+time);
				max = 0;
				tempTime = timeSinceMax; 
				timeSinceMax = 0;
				lastSample = sample;
				startIgnore = true;				
				return peak;
			}
		}
		else
		{
			timer--;
			if(timer<=0)
			{
				timer = timeThreshold;
				startIgnore = false;	
			}
		}
		
		lastSample = sample;
		return 0;
	}
}
