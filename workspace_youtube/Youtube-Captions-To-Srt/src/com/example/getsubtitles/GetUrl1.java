package com.example.getsubtitles;


public class GetUrl1 {
	public String getWebSite(String s) 
    {
    	//String s = "http://www.youtube.com/watch?v=XraeBDMm2PM";
    	String result = new String(); 
    	String str = "http://video.google.com/timedtext?type=list&v=";
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
		result=str+result;
    	//System.out.print(result);
    	//Log.d(str, result);
    	return result;
    }
}
