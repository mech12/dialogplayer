package com.example.getsubtitles;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SecondaryActivity extends Activity {
	//String u_name=getIntent().getExtras().getString("key");
//	String id=getIntent().getExtras().getString("id");
	
	//m6.show();
	int z =0;
	String[] l = new String[164];
	String[] c = new String[164];
	String[] N = new String[64];
	String u_name,id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		u_name = intent.getStringExtra("Key");
		id = intent.getStringExtra("id");
	//	Toast m6 = Toast.makeText(getBaseContext(),"kkkk"+datatoCollect+id, Toast.LENGTH_LONG);
		//m6.show();

		
		 final Button bt1,bt2,bt3,bt4,bt5,bt6,bt7;
		 bt1  = (Button)findViewById(R.id.button1);
		 bt2  = (Button)findViewById(R.id.button2);
		 bt3  = (Button)findViewById(R.id.button3);
		 bt4  = (Button)findViewById(R.id.button4);
		 bt5  = (Button)findViewById(R.id.button5);
		 bt6  = (Button)findViewById(R.id.button6);
		 bt7  = (Button)findViewById(R.id.button7);
		setContentView(R.layout.activity_secondary);
		Toast m = Toast.makeText(getBaseContext(),"ooooo", Toast.LENGTH_LONG);
		m.show();
		 
			 
		Languages2 lan1 =  new Languages2();
		l = lan1.getWebPage(u_name);
		final int leng = c.length;
		 
		 
		Toast m1 = Toast.makeText(getBaseContext(),"done", Toast.LENGTH_LONG);
		m1.show();
		
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowLanguages slan =  new ShowLanguages();
				slan.getWebPage(u_name);
				
				
			}
		});
		
		bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Download d = new Download();
				d.getWebPage(u_name, 1,id);
				Toast m1 = Toast.makeText(getBaseContext(),"Download Successful", Toast.LENGTH_LONG);
					m1.show();
			}
		});
		
		bt3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Download d = new Download();
				d.getWebPage(u_name, 1,id);
				Toast m1 = Toast.makeText(getBaseContext(),"Download Successful", Toast.LENGTH_LONG);
					m1.show();
				
			}
		});
		
		bt4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Download d = new Download();
				d.getWebPage(u_name, 1,id);
				Toast m1 = Toast.makeText(getBaseContext(),"Download Successful", Toast.LENGTH_LONG);
					m1.show();
				
			}
		});
		
			
		bt5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Download d = new Download();
				d.getWebPage(u_name, 1,id);
				Toast m1 = Toast.makeText(getBaseContext(),"Download Successful", Toast.LENGTH_LONG);
					m1.show();
				
			}
		});

		bt6.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Download d = new Download();
				d.getWebPage(u_name, 1,id);
				Toast m1 = Toast.makeText(getBaseContext(),"Download Successful", Toast.LENGTH_LONG);
					m1.show();
			}
		});

		bt7.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Download d = new Download();
				d.getWebPage(u_name, 1,id);
				Toast m1 = Toast.makeText(getBaseContext(),"Download Successful", Toast.LENGTH_LONG);
					m1.show();
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.secondary, menu);
		return true;
	}

}
