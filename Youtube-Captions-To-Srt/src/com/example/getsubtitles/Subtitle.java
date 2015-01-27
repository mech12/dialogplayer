package com.example.getsubtitles;
 

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
public class Subtitle {
    public void getWebSite(float[] start,float[] dur,String finals) {
    	
        try {
            URL url = new URL( finals);
            URLConnection urlc = url.openConnection();
            int hours = 00,minutes = 00,seconds=0,restHour, semisec=0;
            float temp;
            BufferedInputStream buffer2 = new BufferedInputStream(urlc.getInputStream());
            int byteRead2, k =0;
            String[] sub = new String[512];
            String[] S = new String[512];
            String[] E = new String[512];
            int flag;
			while ((byteRead2 = buffer2.read()) != -1)
            {      	 
				
				 flag = 0;
				 StringBuilder builder2 = new StringBuilder();
            	 if((char)byteRead2 == '>')
            	{    
            		if((byteRead2 = buffer2.read()) == -1)
            			 break;
            		if((char)byteRead2 == '<')
            		{
            			continue;
            		}
            		do
            		{
						builder2.append((char) byteRead2);
        				if((byteRead2 = buffer2.read()) == -1){
        					flag=1;
        					break;
        				}
            		}while ((char)byteRead2 != '<');	
            		if(flag == 1)
            			break;
            		temp = start[k+1]*100;
                	semisec = (int)temp%100;
                	seconds =(int)start[k+1];
                	hours = seconds/3600;
                	restHour = seconds%3600;
                	minutes = restHour/60;
                	seconds = restHour%60;
                	S[k] =  String.format("%02d:%02d:%02d,%2d", hours, minutes,seconds,semisec);
                	
                	temp = (start[k+1]+dur[k+1])*100;
    	          	semisec = (int)temp%100;
    	          	seconds =(int)(start[k+1]+dur[k+1]);
    	          	hours = seconds/3600;
    	          	restHour = seconds%3600;
    	          	minutes = restHour/60;
    	          	seconds = restHour%60;
    	          	E[k] = String.format("%02d:%02d:%02d,%2d", hours, minutes,seconds,semisec);
					 
    	          	sub[k] = (String) builder2.toString();
					 k++;
            	}  
            } 
            buffer2.close();
            CreateFile cc = new CreateFile();
            cc.Create(S,E,sub,k);
            
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
