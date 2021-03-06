package com.example.tcpclient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wecandoit.jinju_mech_lib.*;

public class MainActivity extends Activity {

	//For debug
	private final String TAG = "MainActivity";
	
	//About the ui controls
	private EditText edit_ip = null;
	private EditText edit_port = null;
	private Button btn_connect = null;
	private EditText edit_receive = null;
	private EditText edit_send = null;
	private Button btn_send = null;
	//private boolean isConnected = false;
	
	//About the socket
	Handler handler;
	ClientThread mClientThread;
	
	
	
	/** Called when the activity is first created.*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		edit_ip = (EditText) this.findViewById(R.id.edit_ip);
		edit_port = (EditText) this.findViewById(R.id.edit_port);
		edit_receive = (EditText) this.findViewById(R.id.edit_receive);
		edit_send = (EditText) this.findViewById(R.id.edit_send);
		btn_connect = (Button) this.findViewById(R.id.btn_connect);
		btn_send = (Button) this.findViewById(R.id.btn_send);
		
		init();
		
		//Click here to connect
		btn_connect.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String ip = edit_ip.getText().toString();
				String port = edit_port.getText().toString();
				
				Log.d(TAG, ip + port);
				
				mClientThread = new ClientThread("tag", ip, port);
				new Thread(mClientThread).start();
				Log.d(TAG, "mClientThread is start!!");
				if(mClientThread.isConnect)
				{
					btn_connect.setText(R.string.btn_disconnect);
				}
			}});
		
		//Click here to Send Msg to Server
		btn_send.setOnClickListener(new OnClickListener(){

			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try
				{

				mClientThread.d(edit_send.getText().toString());
				//edit_send.setText("");
				}
				catch (Exception e)
				{
					Log.d(TAG, e.getMessage());
					e.printStackTrace();
				}
			}});
	}
	
	
	private void init()
	{
		//Load the datas from share preferences
		SharedPreferences sharedata = getSharedPreferences("data", 0);
		String ip = sharedata.getString("ip", "logio.test.clipeo.com");
		String port = sharedata.getString("port", "28177");
		edit_ip.setText(ip);
		edit_port.setText(port);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onDestory(){
		return true;
		
	}

}
