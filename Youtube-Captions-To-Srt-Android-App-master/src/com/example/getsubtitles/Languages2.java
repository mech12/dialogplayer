package com.example.getsubtitles;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;



public class Languages2 
{
	public String[] getWebPage(String s  ) {
		String[] code = new String[64];
		String[] lang = new String[64];
       try {
           
           //URL url = new URL("http://video.google.com/timedtext?type=list&v=XraeBDMm2PM");
       	URL url = new URL(s);
           URLConnection urlc = url.openConnection();
           
           BufferedInputStream buffer = new BufferedInputStream(urlc.getInputStream());
           
           StringBuilder builder = new StringBuilder();
           int byteRead,i = 0, j,k =0;
           char[][] name = new char[512][512];Log.d("bbbbbbb", s);
           
           
           while ((byteRead = buffer.read()) != -1)
           {
           	j = 0;
           	//System.out.println((char)byteRead);
           	if((char)byteRead=='"')
           	{
           		byteRead = buffer.read();
           		while ((char)byteRead  != '"')
           		{
           			 name[i][j] = (char)byteRead;
           				//System.out.print((char)byteRead);
           				byteRead = buffer.read();
           				j++;
           		}
           		//System.out.print("\n");
           		i++;
           	}
               builder.append((char) byteRead);
           }
           buffer.close();

           for(int x = 0 ; x<=i; x++)
           {
           	if(name[x][0] == 'f' && name[x][1] == 'r')
           	{	
           		lang[k] = name[x-1].toString();
           		System.out.println(lang[k]);
           		code[k] = name[x].toString();
           		System.out.println(code[k]);
           		k++;
           	}
           	else
           		if(name[x][0] == 'j' && name[x][1] == 'a')
           		{
           			lang[k] = name[x-1].toString();
               		System.out.println(lang[k]);
               		code[k] = name[x].toString();
               		System.out.println(code[k]);
               		k++;
           		}
           		else
           			if(name[x][0] == 'e' && name[x][1] == 'n')
           			{
           				lang[k] = name[x-1].toString();
                   		System.out.println(lang[k]);
                   		code[k] = name[x].toString();
                   		System.out.println(code[k]);
                   		k++;
           			}
           			else
           				if(name[x][0] == 'e' && name[x][1] == 's')
           				{
           					lang[k] = name[x-1].toString();
                       		System.out.println(lang[k]);
                       		code[k] = name[x].toString();
                       		System.out.println(code[k]);
                       		k++;
           				}
           				else
           					if(name[x][0] == 'i' && name[x][1] ==  't')
           					{
           						lang[k] = name[x-1].toString();
                           		System.out.println(lang[k]);
                           		code[k] = name[x].toString();
                           		System.out.println(code[k]);
                           		k++;
           					}
               				else
               					if(name[x][0] == 'd' && name[x][1] ==  'e')
               					{
               						lang[k] = name[x-1].toString();
                               		System.out.println(lang[k]);
                               		code[k] = name[x].toString();
                               		System.out.println(code[k]);
                               		k++;
               					}
           }
       } catch (MalformedURLException ex) {
           ex.printStackTrace();
       } catch (IOException ex) {
           ex.printStackTrace();
       }
       return lang;
   }
}
