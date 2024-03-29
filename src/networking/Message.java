package networking;

import java.io.*;
import java.net.*;

public class Message implements Runnable
{
	enum States
	{
		notReady,
		hostSetup,
		clientSetup,	
		run,
	}

	States currentState = States.notReady;
	private PrintWriter out;
	private BufferedReader in;
	private Socket other;
	private ServerSocket listen;

	private String inMessages[];
	private int numInMessages;
	private String outMessages[];
	private int numOutMessages;
	private String hostName;

	Message()
	{
		inMessages = new String[10];
		numInMessages = 0;
		outMessages = new String[10];
		numOutMessages = 0;
		hostName = null;
	}
	public void setHostID(String hostID)
	{
		hostName = hostID;
	}
	public boolean hasMessage()
	{
		if(currentState == States.run)
			if(numInMessages > 0)
				return true;
		return false;
	}
	public String getMessage()
	{
		String s = null;
		if(currentState == States.run)
		{
			s = inMessages[0];
			for(int i = 0; i < numInMessages-1; ++i)
			{
				inMessages[i] = inMessages[i+1];
			}
			--numInMessages;
		}
		return s;
	}
	public void sendMessage(String m)
	{
		outMessages[numOutMessages] = m;
		++numOutMessages;
	}
	public void start(boolean host)
	{
		System.out.println("entered start");
		if(host)
		{
			currentState = States.hostSetup;
		}
		else
		{
			currentState = States.clientSetup;
		}
		Thread messageThread = new Thread(this);
		System.out.println("New thread made, beginning new thread.");
		messageThread.start();
	}
	public void run()
	{
		while(true)
		{
			switch (currentState)
			{
			case hostSetup:
				try
				{
					System.out.println("Trying to listen on socket 4321");
					listen = new ServerSocket(4321);
				}
				catch (IOException e)
				{
					System.out.println("Could not listen on port 4321");
					System.exit(-1);
				}
				try
				{
					System.out.println("Trying to accept on listen socket.");
					other = listen.accept();
					System.out.println("Accept completed.");
				}
				catch (IOException e)
				{
					System.out.println("Accept failed: 4321");
					System.exit(-1);
				}
				try
				{
					in = new BufferedReader(new InputStreamReader(other.getInputStream()));
					out = new PrintWriter(other.getOutputStream(), true);
				}
				catch (IOException e)
				{
					System.out.println("Read failed");
					System.exit(-1);
				}
				currentState = States.run;
				break;
			case clientSetup:
				if(hostName != null)
				{
					try
					{
						other = new Socket(hostName, 4321);
						out = new PrintWriter(other.getOutputStream(), true);
						in = new BufferedReader(new InputStreamReader(other.getInputStream()));
					}
					catch (UnknownHostException e)
					{
						System.out.println("Unknown host: " + hostName);
						System.exit(1);
					}
					catch (IOException e)
					{
						System.out.println("No I/O");
						System.exit(1);
					}
				}
				currentState = States.run;
				break;
			case run:
			{
				if(numOutMessages > 0)
				{
					out.write(outMessages[0]);
					out.flush();
					for(int i = 0; i < numOutMessages-1; ++i)
					{
						outMessages[i] = outMessages[i+1];
					}
					--numOutMessages;			}
			}
			try
			{
				String temp = in.readLine();
				if(temp != null)
				{
					inMessages[numInMessages] = temp;
					++numInMessages;
				}
			}
			catch (IOException e)
			{
				System.err.println("Error Reading from buffer");
				System.exit(1);
			}
			break;
			}
		}
	}
}