package com.homeproject.nabz;

import java.util.ArrayList;

public class Globals{
	   private static Globals instance;
	 
	   // Global variable
	   private int data=0;//test
	   private String token="";
	   private String server="";

//      private String urlPrep="";

    //
	   private ArrayList<String> bunniesName = new ArrayList<String>();
	   private ArrayList<String> bunniesMac = new ArrayList<String>();
	   private ArrayList<Boolean> bunniesOnLine = new ArrayList<Boolean>();
	   // DEBUG ***********
	   private String lastURL="none";
	   private String lastResponse="none";
	   //
	   // Restrict the constructor from being instantiated
	   private Globals(){}
	   //**********************
	   public void setData(int d){
	     this.data=d;
	   }
	   public int getData(){
	     return this.data;
	   }
       //******************************	 
	   public void setToken(String d){
		 this.token=d;
	   }
	   public String getToken(){
	     return this.token;
	   }
       //******************************	 
	   public void setServer(String d){
		 this.server=d;
	   }
	   public String getServer(){
	     return this.server;
	   }

       //*********************************
	   public void setBunniesOnLine(Boolean d){
		 this.bunniesOnLine.add(d);
	   }
	   public ArrayList<Boolean> getBunniesOnLine(){
	     return this.bunniesOnLine;
	   }

	   
	   
	   //*********************************
	   public void setBunniesName(String d){
		 this.bunniesName.add(d);
	   }
	   public ArrayList<String> getBunniesName(){
	     return this.bunniesName;
	   }
       //*********************************
	   public void setBunniesMac(String d){
		 this.bunniesMac.add(d);
	   }
	   public ArrayList<String> getBunniesMac(){
	     return this.bunniesMac;
	   }
	   //****************
	   public void clearBunnies(){
			 this.bunniesName.clear();
			 this.bunniesMac.clear();
			 this.bunniesOnLine.clear();
	   }
	   	   
	   
/*
 * per debug	   
 */
	   public void setLastURL(String d){
		 this.lastURL=d;
	   }
	   public String getLastURL(){
		     return this.lastURL;
	   }
	   public void setLastResponse(String d){
		 this.lastResponse=d;
	   }
	   public String getLastResponse(){
		     return this.lastResponse;
	   }

/*    public void setUrlPrep(String d){
        this.urlPrep=d;
    }
    public String getUrlPrep(){
        return this.urlPrep;
    }
*/
	   
	   public static synchronized Globals getInstance(){
	     if(instance==null){
	       instance=new Globals();
	     }
	     return instance;
	   }
	}