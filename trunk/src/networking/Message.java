package networking;

import java.io.*;
import java.net.*;

public class Message
{
	private boolean ready;
	private PrintWriter out;
	private BufferedReader in;
	private Socket other;
	private Socket listen;
	
	private String messages[];
	private int numMessages;
	
	Message()
	{
		ready = false;
		messages = new String[10];
		numMessages = 0;
	}
	public boolean isReady()
	{
		return ready;
	}
	public boolean setUpHost()
	{
		if(!ready)
		{
			try
			{
				other = new Socket();
				out = new PrintWriter(other.getOutputStream(),true);
				in = new BufferedReader(new InputStreamReader(other.getInputStream()));
				
			}
			catch(UnknownHostException e)
			{
				System.err.println("Cannot start as host.");
				System.exit(1);
			}
			catch(IOException e)
			{
				System.err.println("Could not access I/O as host.");
				System.exit(1);
			}
			return true;
		}
		return false;
	}
	public boolean setUpClient(String hostIP)
	{
		if(!ready)
		{
			try
			{
				other = new Socket(hostIP, 7);
				out = new PrintWriter(other.getOutputStream(),true);
				in = new BufferedReader(new InputStreamReader(other.getInputStream()));
				
			}
			catch(UnknownHostException e)
			{
				System.err.println("Cannot connect to host: " + hostIP);
				System.exit(1);
			}
			catch(IOException e)
			{
				System.err.println("Could not get I/O for connection to: " + hostIP);
				System.exit(1);
			}
			ready = true;
			return true;
		}
		return false;
	}
	public boolean sendMessage(String outMessage)
	{
		if(ready)
		{
			out.write(outMessage);
			return true;
		}
		return false;
		
	}
	public String getMessage()
	{
		if(ready)
		{
			String message = "InMessage";
			return message;
		}
		return null;
	}
	public void shutDown()
	{
		try
		{
			out.close();
			in.close();
			listen.close();
			other.close();
		}
		catch(IOException e)
		{
			System.err.println("Could not shutdown properly.");
			System.exit(1);
		}
	}
}
