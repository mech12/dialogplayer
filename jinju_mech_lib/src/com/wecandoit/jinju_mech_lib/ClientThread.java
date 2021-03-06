package com.wecandoit.jinju_mech_lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientThread implements Runnable
{
	//For debug
	private final String TAG = "ClientThread";
	
	private Socket socket;
	private String ip;
	private int port;
	private Handler receiveHandler;
	public Handler sendHandler;
	BufferedReader bufferedReader;
	private InputStream inputStream;
	private OutputStream outputStream;
	public boolean isConnect = false;
	int log_index=0;

	public void d(String tag, String str)
	{
		Send(tag,str);
	}

	public void d(String str)
	{
		Send("debug" ,str);
	}
	public void w(String str)
	{
		Send("Warn" , str);
	}
	public void e(String str)
	{
		Send("Error" , str);
	}
	public void Send(String tag ,String str)
	{
		
		Message msg = new Message();
		msg.what = 0x852;
		
		long now = System.currentTimeMillis();
		// 현재 시간을 저장 한다.
		Date date = new Date(now);
		// 시간 포맷으로 만든다.
		//SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm:ss");
		String strNow = sdfNow.format(date);
		
		String strSend = String.format("+log|%s|%s|info|[%d,%s]%s"
				, mAppTag
				, tag
				, log_index++
				, strNow
				, str 
				);
		//String strEnd = "+log|appName|tag|info|asdf1234asdf1234";
		msg.obj = strSend;
		sendHandler.sendMessage(msg);

	}
	String mAppTag;
	public ClientThread(String tagDefault, String ip, String port) {
		// TODO Auto-generated constructor stub
		mAppTag = tagDefault;
		Handler handler=null;
		/*
		 * 
		if(handler==null)
		{
			handler = new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{
					if(msg.what == 0x123)
					{
						//edit_receive.setText("\n" + msg.obj.toString());
					}
				}
			};
		}
		*/
		this.receiveHandler = handler;
		this.ip = ip;
		this.port = Integer.valueOf(port);
		Log.d(TAG, "ClientThread's construct is OK!!");
	}
	public ClientThread()
	{
		Log.d(TAG, "It is may be construct's problem...");
	}

	public void run()
	{
		try 
		{
			Log.d(TAG, "Into the run()");
			socket = new Socket(ip, port);
			isConnect = socket.isConnected();
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			
			//To monitor if receive Msg from Server
			new Thread()
			{
				@Override
				public void run()
				{
					byte[] buffer = new byte[1024];
					
					final StringBuilder stringBuilder = new StringBuilder();
					try
					{
						while(socket.isConnected())
						{
							int readSize = inputStream.read(buffer);
							Log.d(TAG, "readSize:" + readSize);
							
							//If Server is stopping
							if(readSize == -1)
							{
								inputStream.close();
								outputStream.close();
							}
							if(readSize == 0)continue;
							
							//Update the receive editText
							stringBuilder.append(new String(buffer, 0, readSize));
							Message msg = new Message();
							msg.what = 0x123;
							msg.obj = stringBuilder.toString();
							receiveHandler.sendMessage(msg);
						}
					}
					catch(IOException e)
					{
						Log.d(TAG, e.getMessage());
						e.printStackTrace();
					}
				}
				
			}.start();
			
			//To Send Msg to Server
			Looper.prepare();
			sendHandler = new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{
					if (msg.what == 0x852)
					{
						try
						{
							outputStream.write((msg.obj.toString() + "\r\n").getBytes());
							outputStream.flush();
						}
						catch (Exception e)
						{
							Log.d(TAG, e.getMessage());
							e.printStackTrace();
						}
					}
				}
			};
			Looper.loop();
			
		} catch (SocketTimeoutException e) 
		{
			// TODO Auto-generated catch block
			Log.d(TAG, e.getMessage());
			e.printStackTrace();
		}catch (UnknownHostException e) 
		{
			// TODO Auto-generated catch block
			Log.d(TAG, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			Log.d(TAG, e.getMessage());
			e.printStackTrace();
		}
	}
}
