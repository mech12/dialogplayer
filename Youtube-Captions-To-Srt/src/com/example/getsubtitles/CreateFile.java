package com.example.getsubtitles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateFile {
    public void Create(String[] S,String[] E,String[] sub,int k) throws IOException{
         
    	//Writer output = null;
  	  	int i =0;
  	  	File file = new File("output.srt");
  	  	BufferedWriter output = new BufferedWriter(new FileWriter(file));
  	  	while(i<k)
  	  	{
  	  		output.write(String.valueOf(i));
  	  		output.write("\n");
  	  		output.write(S[i]+"		-->		"+E[i]);
  	  		output.write("\n");
  	  		output.write(sub[i]);
  	  		output.write("\n\n");
  	  		i++;
  	  	}
  	  	output.close();
    }
}