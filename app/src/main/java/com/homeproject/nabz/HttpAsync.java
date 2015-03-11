package com.homeproject.nabz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class HttpAsync extends AsyncTask<String, Void, String> {
	
	//private String responce=".";
	private String commandApi="x";
	private String token=".";

	    @Override
        protected String doInBackground(String... urls) {
 
	        String tmp="";
	        // array to string..
	        for(int i=0;i<urls.length;i++) {
	        	tmp+=urls[i];
	        }
	        //Log.d("urlrichiesto :",tmp);
	        Globals g = Globals.getInstance();
	        g.setLastURL( tmp );	// *************** DEBUG
	
			if (tmp.contains("accounts")) { this.commandApi ="a"; }
			if (tmp.contains("getListOfBunnies")) { this.commandApi ="b"; }
			if (tmp.contains("getListOfConnectedBunnies")) { this.commandApi ="c"; }
//            if (tmp.contains("afterStreamName")) { this.commandApi ="s"; }

            Log.d("hl",tmp);
            return GET(urls[0]);
        }

		// onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();

        	Globals g = Globals.getInstance();
        	String server = g.getServer();
        			
            if (this.commandApi.equals("a")){
                this.commandApi="";
            	Pattern pattern = Pattern.compile("<value>(.*?)</value>");
                Matcher matcher = pattern.matcher(result);
                while (matcher.find()) {
                	String t = matcher.group(1);
                    g.setToken(t);
					//Log.d("httplento","cercatoken: " +t);
//TODO
                    String urlPlugin = "";//"http://openznab.it";//campi[2]; //server
					urlPlugin = server + "/ojn_api/bunnies/getListOfBunnies?token=" + t;
//					urlPlugin = urlPlugin + t;
					urlPlugin = urlPlugin.replace(" ", "%20");
					new HttpAsync().execute(urlPlugin);
                }
            }
            if (this.commandApi.equals("b")){
                this.commandApi="";
            	g.clearBunnies();
            	
            	Pattern pattern = Pattern.compile("<value>(.*?)</value>");
                Matcher matcher = pattern.matcher(result);
                while (matcher.find()) {
//Log.d("httplento","numero conigli: "+matcher.group(1));
                	g.setBunniesName(matcher.group(1));
                }
            	pattern = Pattern.compile("<key>(.*?)</key>");
                matcher = pattern.matcher(result);
                while (matcher.find()) {
//Log.d("httplento","Mac conigli: "+matcher.group(1));
                	g.setBunniesMac(matcher.group(1));
                }
                String t = g.getToken();
//TODO
                String urlPlugin = "";//"http://openznab.it";//campi[2]; //server
                urlPlugin = server + "/ojn_api/bunnies/getListOfConnectedBunnies?token=" + t;
//                urlPlugin = urlPlugin + t;
                urlPlugin = urlPlugin.replace(" ", "%20");
                new HttpAsync().execute(urlPlugin);
            }
            if (this.commandApi.equals("c")){
                this.commandApi="";
//            	Log.d("http","find bunnies connects");
            	//------------------------
            	ArrayList<String> conigliOnLine = new ArrayList<String>();

            	Pattern pattern = Pattern.compile("<value>(.*?)</value>");
                Matcher matcher = pattern.matcher(result);
                while (matcher.find()) {
					conigliOnLine.add(matcher.group(1));
                }
                //
                Boolean trovato;
            	ArrayList<String> elenco = g.getBunniesName();
            	for(int ii=0;ii<elenco.size();ii++) {
            		trovato=false;
            		for(int i=0;i<conigliOnLine.size();i++){
                	  if (conigliOnLine.get(i).equals(elenco.get(ii))) {
                		  //trovato
                		  g.setBunniesOnLine(true);
                		  trovato = true;
                	  }
            		}
            		if (!trovato) { 
            			g.setBunniesOnLine(false);
            		}
                }
              }            
//            Log.d("la risposta :",result);
/*
            if (this.commandApi.equals("s")){
                    this.commandApi="";
                    String t = g.getUrlPrep();
                Log.d("urlhttp",t);
                    new HttpAsync().execute(t);

            }
*/

            Log.d("hl",result);
            g.setLastResponse(result); // ************DEBUG
       }
        
		public String getToken()
		{
		  return this.token;	
		}
    /*
		public String getRisultato()
		{
			  return this.responce;	
			}
		public void setRisultato(String r) {
		  this.responce = r;
		  return;
		}
*/
        private static String GET(String url){
            InputStream inputStream = null;
            String result = "";
            try {
     
                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();
     
                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
     
                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();
     
                // convert inputstream to string
                if(inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";
     
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
     
            return result;
        }
        
        // convert inputstream to String
        private static String convertInputStreamToString(InputStream inputStream) throws IOException{
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;
     
            inputStream.close();
            return result;
     
        }
        

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
         // Things to be done before execution of long running operation. For
         // example showing ProgessDialog
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        /*
        @Override
        protected void onProgressUpdate(String... text) {
         
         // Things to be done while execution of long running operation is in
         // progress. For example updating ProgessDialog
        }
        */

}
