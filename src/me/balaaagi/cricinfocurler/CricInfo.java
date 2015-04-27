package me.balaaagi.cricinfocurler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CricInfo {
final String FILE_NAME="match_config.txt";
String match_URL=null;
HashMap<String,String> overallScoreList=new HashMap<String,String>();
HashMap<String,String> currentlyBatting=new HashMap<String,String>();

String match_status=null;
String match_title=null;
String matchCurrentScore=null;
String matchCurrentOvers=null;
String currentBowler=null;
boolean behindProxy=false;
String proxyIPAdrress=null;


String proxyPort=null;
URL request_url=null;
Proxy proxy=null;
HttpURLConnection http_conn=null;

public String getProxyIPAdrress() {
	return proxyIPAdrress;
}

public void setProxyIPAdrress(String proxyIPAdrress) {
	this.proxyIPAdrress = proxyIPAdrress;
}

public String getProxyPort() {
	return proxyPort;
}

public void setProxyPort(String proxyPort) {
	this.proxyPort = proxyPort;
}



public boolean isBehindProxy() {
	return behindProxy;
}

public void setBehindProxy(boolean behindProxy) {
	this.behindProxy = behindProxy;
}
public HashMap<String, String> getCurrentlyBatting() {
	return currentlyBatting;
}



public void connectESPNCricInfo() throws Exception{
	 this.request_url= new URL(this.match_URL);
	 
	if(this.isBehindProxy()){
		this.proxy= new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.getProxyIPAdrress().toString(), Integer.parseInt(this.getProxyPort())));
		this.http_conn=(HttpURLConnection)request_url.openConnection(this.proxy);
	}else{
		this.http_conn=(HttpURLConnection)request_url.openConnection();
	}
	this.http_conn.setFollowRedirects(true);

	this.http_conn.setConnectTimeout(100000);
	this.http_conn.setReadTimeout(100000);
	this.http_conn.setInstanceFollowRedirects(true);

}
public void getScoreCardFromCricInfo() throws Exception{
	BufferedReader in = new BufferedReader(new InputStreamReader(
	                                    this.http_conn.getInputStream()));
	        String inputLine;
	        String titleLine = "",descriptionLine = null;
	        while ((inputLine = in.readLine()) != null) {

	            if(inputLine.indexOf("<title>")>-1){
	            	if(titleLine.length()<1)
	            		titleLine=inputLine;
	            }
	            	
	                
	            if(inputLine.indexOf("innings-requirement")>-1)
	            {
	            	descriptionLine=in.readLine();
	            	break;
	            }
	                
	        }
	            
	        in.close();
	        
	       this.match_title=descriptionLine;

	        titleLine=titleLine.substring(8,titleLine.indexOf("</title>"));
	        this.match_status=titleLine.substring(titleLine.lastIndexOf(")")+2,titleLine.indexOf("|", titleLine.lastIndexOf(")")+2));
	        this.matchCurrentScore=titleLine.substring(0,titleLine.indexOf("("));
	       
	        String mainscore=titleLine.substring(titleLine.indexOf("(")+1,titleLine.indexOf(")"));
	        String[] scoresplit=mainscore.split(",");
	        

	                this.matchCurrentOvers=scoresplit[0].split(" ")[0];
	                
	                
	                if(scoresplit.length>3){
	                	this.currentlyBatting.put(scoresplit[1].substring(0, scoresplit[1].lastIndexOf(" "))+"*", scoresplit[1].substring(scoresplit[1].lastIndexOf(" "),scoresplit[1].lastIndexOf("*")));
		                
		                this.currentlyBatting.put(scoresplit[2].substring(0, scoresplit[2].lastIndexOf(" ")), scoresplit[2].substring(scoresplit[2].lastIndexOf(" "),scoresplit[2].lastIndexOf("*")));
		                
	                    this.currentBowler=scoresplit[3];
	                }

	                else{
	                	this.currentlyBatting.put(scoresplit[1].substring(0, scoresplit[1].lastIndexOf(" ")), scoresplit[1].substring(scoresplit[1].lastIndexOf(" "),scoresplit[1].lastIndexOf("*")));
	                	this.currentBowler=scoresplit[2];
	                }
	                    
	    
}

public void printCurrentScore(){
	
	System.out.println("****"+(this.match_status.replaceAll("-", " ").length()>0?this.match_status.replaceAll("-", " "):"Live Score")+"****");
	System.out.println(this.match_title.trim());
	System.out.println("______________________________");
	System.out.println(this.matchCurrentScore);
	System.out.println("Overs "+this.matchCurrentOvers);
	System.out.println("______________________________");
	Set<String> batsmans=this.currentlyBatting.keySet();
	boolean striker=true;
	for(String batsman: batsmans){
		if(striker){
			System.out.println(batsman.toString()+" \t"+this.currentlyBatting.get(batsman).replaceAll("\\t",""));
			striker=false;
		}else{
			System.out.println(batsman.toString()+" \t"+this.currentlyBatting.get(batsman).replaceAll("\\t",""));
		}
	}
	
	System.out.println();
	
	System.out.println("Current Bowler "+this.currentBowler);
	
	
	
}

public String getMatchCurrentOvers() {
	return matchCurrentOvers;
}

public void setMatchCurrentOvers(String matchCurrentOvers) {
	this.matchCurrentOvers = matchCurrentOvers;
}

public String getMatchCurrentScore() {
	return matchCurrentScore;
}

public void setMatchCurrentScore(String matchCurrentScore) {
	this.matchCurrentScore = matchCurrentScore;
}

public String getMatch_status() {
	return match_status;
}

public void setMatch_status(String match_status) {
	this.match_status = match_status;
}





public String getMatch_URL() {
	return match_URL;
}

public void setMatch_URL(String match_URL) {
	this.match_URL = match_URL;
}

public void batsmanOut(String playerName,String playerScore){
	
	this.currentlyBatting.remove(playerName);
}

public void initialize() throws Exception {
	// TODO Auto-generated method stub
	File file = new File(FILE_NAME);
	String lineContent = null;
    try{
    	
    
        final FileReader fileReader = new FileReader(file);
        final BufferedReader br = new BufferedReader(fileReader);
        int lineCount=0;
        ArrayList<String> match_configs=new ArrayList<String>();
        while ((lineContent = br.readLine()) != null) {
        	
        	match_configs.add(lineContent);
        	
            lineCount++;

        }
        
        br.close();
         
        
        if(match_configs.size()>1){
        	
        	this.setMatch_URL(match_configs.get(0));
        	this.setBehindProxy(true);
        	this.setProxyIPAdrress(match_configs.get(1));
        	this.setProxyPort(match_configs.get(2));
        }else{
        	this.setMatch_URL(match_configs.get(0));
        	this.setBehindProxy(false);
        }
    }catch(Exception e){
    	throw e;
    }

    
	
}

public boolean configDetails(String match_url2, String behindproxy2,
		String proxyIP, String proxyPort2) throws Exception {
	// TODO Auto-generated method stub
	try{
		File fw = new File(FILE_NAME);
		PrintWriter writer = new PrintWriter(fw);
		writer.println(match_url2);
		if(Boolean.parseBoolean(behindproxy2))
		{
			writer.println(proxyIP);
			writer.println(proxyPort2);
		}
		writer.flush();
		writer.close();
			return true;
	}catch(Exception e){
		return false;
	}
		
	
	
}

 
	
}
