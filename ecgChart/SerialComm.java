package ecgChart;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

public class SerialComm 
{
	private SerialPort serialPort;
	private InputStream in;
	private OutputStream out;
	
	public void connect(String portName, int baudRate) throws Exception
	{
		this.connect(portName, 	baudRate, 
				SerialPort.DATABITS_8, 
				SerialPort.PARITY_NONE, 
				SerialPort.STOPBITS_1, 
				SerialPort.FLOWCONTROL_NONE);
	}
	
	public void connect(String portName, int baudRate,int dataBits, int parity, int stopBits, int flowControl) throws Exception
	{
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
        	CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
        	
        	if ( commPort instanceof SerialPort )
            {
        		serialPort = (SerialPort) commPort;
        		serialPort.setSerialPortParams(baudRate, dataBits, stopBits, parity);
        		serialPort.setFlowControlMode(flowControl);
        		in = serialPort.getInputStream();
                out = serialPort.getOutputStream();
        		serialPort.notifyOnDataAvailable(true);
            }else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
	}
	
	public void disconect()
	{
		serialPort.close();
	}
	
	public InputStream getInputStream()
	{
		return in;
	}
	
	public OutputStream getOutputStream()
	{
		return out;
	}
	
	public SerialPort getSerialPort()
	{
		return serialPort;
	}
}
