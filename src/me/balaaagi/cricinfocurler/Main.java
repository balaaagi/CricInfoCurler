package me.balaaagi.cricinfocurler;

import java.io.FileNotFoundException;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		if((args.length==0)||(args[0].equalsIgnoreCase("-h"))){
			System.out.println("Please refer the usage below");
			System.out.println("usage to add match details: java -jar CricInfoCurler.jar [-m match-url] [-p behind proxy or not] [-pa ipadress port] [-a to config matchd details] [-h]");
			System.out.println("usage for score details: java -jar CricInfoCurler.jar -s ");
			System.out.println("");
		}else{
			try{
				
			
			CricInfo cricinfo=new CricInfo();
			String match_url=null,behindproxy=null,proxyIP=null,proxyPort=null;
			for(int i=0;i<args.length;i++){
				switch(args[i]){
				case "-m":
					if (i+1<args.length)
						match_url=args[i+1];
					break;
				case "-p":
					if (i+1<args.length)
						behindproxy=args[i+1];
					break;
				case "-pa":
					if (i+2<args.length){
						proxyIP=args[i+1];
						proxyPort=args[i+2];
					}
					break;
				case "-s":
					cricinfo.initialize();
					cricinfo.connectESPNCricInfo();
					cricinfo.getScoreCardFromCricInfo();
					cricinfo.printCurrentScore();
					break;
					
				case "-a":
					if(cricinfo.configDetails(match_url,behindproxy,proxyIP,proxyPort))
						System.out.println("Match Configurations Successfully Added...");
					else
						System.out.println("Match Configurations Failed!!!...");
					break;
				default:
					break;
				}
		}
			
		
	
		
			}
			catch(FileNotFoundException e){
				System.out.println("Config File not Found!! ");
			}catch(Exception e){
				System.out.println("Something Went Wrong with Configurations!!");
			}
	}

}
}
