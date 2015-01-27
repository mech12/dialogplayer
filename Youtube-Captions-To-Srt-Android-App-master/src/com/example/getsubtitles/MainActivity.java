package com.example.getsubtitles;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	 String t;
	 String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String[] lang = new String[64];
        String[] code = new String[64];
         
		final EditText et;
        final Button b, b2;
        et = (EditText)findViewById(R.id.editText1);
        b  = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);
        b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String text =et.getText().toString();
				Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
				msg.show();
				GetUrl1 geturl1 = new GetUrl1();
				t = geturl1.getWebSite(text);
				GetVideoId getvideoid = new GetVideoId();
				id = getvideoid.getWebSite(text);
				Toast msg1 = Toast.makeText(getBaseContext(),t, Toast.LENGTH_LONG);
				msg1.show();
			}
		});
        
        b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				// TODO Auto-generated method stub
				//Toast msg1 = Toast.makeText(getBaseContext(),"hi"+t, Toast.LENGTH_LONG);
				//msg1.show();
				//Intent passIntent = new Intent(MainActivity.this,SecondaryActivity.class);
	            //passIntent.putExtra("key", t);
	            //passIntent.putExtra("id", id);
	            //startActivity(passIntent);
				Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
				intent.putExtra("Key", t);
				intent.putExtra("id", id);
				startActivity(intent);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
