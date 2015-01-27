package com.example.getsubtitles;

public class GetVideoId {
	public String getWebSite(String s) 
    {
    	//String s = "http://www.youtube.com/watch?v=XraeBDMm2PM";
    	String result = new String(); 
    	int len = s.length(),j=0;
    	char[] charArray = s.toCharArray();
    	while(charArray[j]!= '=')
			j++;
    	j++;
    	while(j < len)
    	{
    		result += charArray[j];
    		j++;
    	}
    	return result;
    }

}
