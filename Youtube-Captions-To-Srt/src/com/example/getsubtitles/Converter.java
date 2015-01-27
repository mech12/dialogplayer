package com.example.getsubtitles;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.R.string;

public class Converter {
    public void getWebSite(String id,String code,String lname ) {
        String finals = "http://video.google.com/timedtext?type=track&v="+id+"&name="+lname+"&lang="+code;
    try {
	    URL url = new URL(finals);
	    URLConnection urlc = url.openConnection();
	    BufferedInputStream buffer = new BufferedInputStream(urlc.getInputStream());
	    int byteRead,flag = 0,l=0, l1 = 0;
	    float[] start = new float[64];
	    float[] dur = new float[64];
	    
	    while ((byteRead = buffer.read()) != -1)
	    {
			StringBuilder builder = new StringBuilder();
			if((char)byteRead=='"')
			{
				byteRead = buffer.read();
				do
				{
					builder.append((char) byteRead);
					byteRead = buffer.read();
				}while ((char)byteRead  != '"');
	
				if(flag == 0)
				{
					String s = builder.toString();
					float f = Float.valueOf(s.trim()).floatValue();
					start[l] = f;
					l++;
					flag = 1;
				}
				else
				{
					String s = builder.toString();
					if(l1==0)
					{
						dur[l1] = 0;
					}
					else
					{ 
	        			float f2 = Float.valueOf(s.trim()).floatValue();
	            		dur[l1] = f2;
		            }
	        		l1++;
	        		flag = 0;
		         } 
		     }
      }
	  buffer.close();
	  Subtitle ss  = new Subtitle();
	  ss.getWebSite(start,dur,finals);
    } catch (MalformedURLException ex) {
        ex.printStackTrace();
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    }
/*    public static void main(String code,String l) {
        new Converter().getWebSite();
    }*/
}
