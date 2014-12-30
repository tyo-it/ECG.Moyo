package ecgChart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/*
 * This class will be designed to handle for all input sample like file, serial com, usb, etc
 */
public class SampleReader {
	File sampleFile;
	BufferedReader inSampleFile;
	
	public SampleReader(File inFile)
	{
		this.sampleFile = inFile;
		try
		{
			this.inSampleFile = new BufferedReader(new FileReader(sampleFile));
		}
		catch(FileNotFoundException e){System.out.println("file not found");}
	}
	
	public Integer readSample()
	{
		String s = "";
		try
		{
			s = inSampleFile.readLine();
		}catch(Exception exc){System.out.println("\n Error Reading");};
		
		if(s == null)
		{
			return null;
		}else
		{
			Integer val = null;
			try	
			{
				val = Integer.valueOf(s);
			}catch(Exception e){e.printStackTrace();}
			
			return val;
		}
	}
}
