package com.homeproject.nabz;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity
{
	
	Cursor cursorStream=null;
	SimpleCursorAdapter mAdapter;
	
	private ListView lv;
	final Context context = this;  
 //	String urlPlugin = null;
	DatabaseHelper databaseHelper = new DatabaseHelper(this);
    UrlHelper urlHelper = new UrlHelper();
	TextView tvConnect;
    
    Globals g = Globals.getInstance();

    Spinner mySpinnerTmp; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        tvConnect = (TextView) findViewById(R.id.tvIamConnect);
       
        lv = (ListView) findViewById(R.id.listView1);
       
        String[] pi = getResources().getStringArray(R.array.array_plugin); 
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, pi);        
        lv.setAdapter(adapter);
        
        
        if(isConnected()){
        	//tvSonoConnesso.setBackgroundColor(0xFF00CC00);
        	tvConnect.setText(R.string.net_ok);
        }
        else{
        	tvConnect.setText(R.string.net_ko);
        }

 
        String[] tmp=null;
		tmp = databaseHelper.getC();
		g.setServer(tmp[2]);
		//Log.d("","");
       // String urlo=null;
/*
 * CONNECT...
 */
        //urlo = tmp[2] + "/ojn_api/accounts/auth?login=" + tmp[0] + "&pass=" + tmp[1];

        String urlo = urlHelper.getAuthUrl( tmp[2], tmp[0],tmp[1] );
        new HttpAsync().execute( urlo );

        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
               int position, long id) {
              
             /*
              Toast.makeText(getApplicationContext(),
                "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                .show();
                */              
    			Button dialogButton;
    			final Dialog dialog = new Dialog(context);
              
			final Spinner mySpinnerRadio;
			switch (position) {
              case 0:
				// *************************************
				// EARS
				// *************************************
				
				dialog.setContentView(R.layout.plugin_ambient_ear);
				dialog.setTitle(R.string.plugin_title1);//"Ambient ears");
				dialogButton = (Button) dialog.findViewById(R.id.bntAmbientEar);

				mySpinnerTmp = (Spinner) dialog.findViewById(R.id.spAmbientEar);
                List <ListBunnies> listLR = new LinkedList <ListBunnies>();
                for(int i=0;i<g.getBunniesName().size();i++) {
                  listLR.add(new ListBunnies(g.getBunniesName().get(i),g.getBunniesMac().get(i),g.getBunniesOnLine().get(i),false));
                }
                mySpinnerTmp.setAdapter(new MyAdapterBunnies(getApplicationContext(), R.layout.custom_spinner,listLR));//spinnerValues
				
				SeekBar seekBarLeft = (SeekBar)dialog.findViewById(R.id.sbLeft); 
				final TextView seekBarValueLeft = (TextView)dialog.findViewById(R.id.textSeekBarSx); 
				
				seekBarLeft.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 
				   @Override 
				   public void onProgressChanged(SeekBar seekBarLeft, int progress, 
				     boolean fromUser) { 
				    // TODO Auto-generated method stub 
				    seekBarValueLeft.setText(String.valueOf(progress)); 
				   } 
				
				   @Override 
				   public void onStartTrackingTouch(SeekBar seekBar) { 
				    // TODO Auto-generated method stub 
				   } 
				
				   @Override 
				   public void onStopTrackingTouch(SeekBar seekBar) { 
				    // TODO Auto-generated method stub 
				   } 
				   });
				
				SeekBar seekBarRight = (SeekBar)dialog.findViewById(R.id.sbRight); 
				final TextView seekBarValueRight = (TextView)dialog.findViewById(R.id.textSeekBarDx); 
				seekBarRight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 
				   @Override 
				   public void onProgressChanged(SeekBar seekBarRight, int progress, 
				     boolean fromUser) { 
				    // TODO Auto-generated method stub 
				    seekBarValueRight.setText(String.valueOf(progress)); 
				   } 
				
				   @Override 
				   public void onStartTrackingTouch(SeekBar seekBar) { 
				    // TODO Auto-generated method stub 
				   } 
				
				   @Override 
				   public void onStopTrackingTouch(SeekBar seekBar) { 
				    // TODO Auto-generated method stub 
				   } 
				 }); 


				dialogButton.setOnClickListener(new OnClickListener() {
          		    @Override
          		    public void onClick(View v) {
          			   
					ListBunnies bunnyChoice = (ListBunnies) mySpinnerTmp.getSelectedItem();
					
			//		String urlPlugin = g.getServer();//tmp[2]; //server
				//	urlPlugin = urlPlugin + "/ojn_api/bunny/";
					//urlPlugin = urlPlugin + bunnyChoice.getMac();///tmp[3]; //mac
				//	urlPlugin = urlPlugin + "/packet/sendPacket?data=";
										
					TextView tv;
					tv = (TextView) dialog.findViewById(R.id.textSeekBarSx);
					String sbSx = (String) tv.getText().toString();
					tv = (TextView) dialog.findViewById(R.id.textSeekBarDx);
					String sbDx = (String) tv.getText().toString();
					sbSx = String.format( "%02X", Integer.parseInt(sbSx));
					//Integer.toHexString( Integer.parseInt(sbSx));
					sbDx = String.format( "%02X", Integer.parseInt(sbDx));
                      /*
                       * String.format("#%x", number)
                	  $pacchetto  = pack("C",0x7F);
                	  $pacchetto .= pack("C",0x04); //ambient
                	  $pacchetto .= pack("C",0x00);
                	  $pacchetto .= pack("C",0x00);
                	  $pacchetto .= pack("C",0x08);//lunghezza dati
                	  $pacchetto .= pack("C",0x7F);
                	  $pacchetto .= pack("C",0xFF);
                	  $pacchetto .= pack("C",0xFF);
                	  $pacchetto .= pack("C",0xFE);
                	  $pacchetto .= pack("C",0x04); //orecchio dx
                	  $pacchetto .= pack("C",$dx);
                	  $pacchetto .= pack("C",0x05); //orecchio sx
                	  $pacchetto .= pack("C",$sx);
                	  $pacchetto .= pack("C",0xFF);
                      */
					String packet = "7F040000087FFFFFFE04" + sbDx + "05" + sbSx + "FF";
//					urlPlugin = urlPlugin + "7F040000087FFFFFFE04" + sbDx + "05" + sbSx + "FF";
	//				urlPlugin = urlPlugin + "&token=" + g.getToken();//token
		//			urlPlugin = urlPlugin.replace(" ", "%20");

					
	                String urlPlugin = urlHelper.getBunnySendPacket( g.getServer(),bunnyChoice.getMac(), packet);
	                urlPlugin = urlHelper.addToken(urlPlugin, g.getToken());
	                urlPlugin = urlPlugin.replace(" ", "%20");

					new HttpAsync().execute(urlPlugin);
					//	Toast.makeText(getApplicationContext(), "send to bunny.....", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
          		}
				});
				dialog.show();              
				break;
				// *************************************** end..ears
  
 
              case 1:
        		// *************************************
         		// send text
        		// *************************************
        		dialog.setContentView(R.layout.plugin_send_text);
        		dialog.setTitle(R.string.plugin_title2);
        		dialogButton = (Button) dialog.findViewById(R.id.bntSendText);

				mySpinnerTmp = (Spinner) dialog.findViewById(R.id.spSendText);
                List <ListBunnies> list = new LinkedList <ListBunnies>();
                for(int i=0;i<g.getBunniesName().size();i++)
                {
                  list.add(new ListBunnies(g.getBunniesName().get(i),g.getBunniesMac().get(i),g.getBunniesOnLine().get(i),false));
                }
                mySpinnerTmp.setAdapter(new MyAdapterBunnies(getApplicationContext(), R.layout.custom_spinner,list));//spinnerValues
        		
             	dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				
//				String urlPlugin = g.getServer();//tmp[2]; //server
	//			urlPlugin = urlPlugin + "/ojn_api/bunny/";
				
				ListBunnies bunnyChoice = (ListBunnies) mySpinnerTmp.getSelectedItem();
				
				//Globals g = Globals.getInstance();
				EditText et = (EditText) dialog.findViewById(R.id.edSendText);
				String tmpx = et.getText().toString();

	/*			
				String token=g.getToken();
				urlPlugin = urlPlugin + bunnyChoice.getMac();///tmp[3]; //mac
				urlPlugin = urlPlugin + "/tts/say?text=";
				urlPlugin = urlPlugin + tmpx;
                urlPlugin = urlPlugin + "&token=" + token;
*/
                String urlPlugin = urlHelper.getBunnySendText( g.getServer(),bunnyChoice.getMac(), tmpx);
                urlPlugin = urlHelper.addToken(urlPlugin, g.getToken());
                urlPlugin = urlPlugin.replace(" ", "%20");
				new HttpAsync().execute(urlPlugin);
//	              		        Toast.makeText(getApplicationContext(), "send to bunny.....", Toast.LENGTH_SHORT).show();
	            dialog.dismiss();
	            }
	            });
	      		dialog.show();              
        		break;
            	  // *************************************** end send text
              case 2:
        		// *************************************
        		// Led breath
        		// *************************************

        		dialog.setContentView(R.layout.plugin_led_resipro);
        		dialog.setTitle(R.string.plugin_title3);
        		
        		mySpinnerTmp = (Spinner) dialog.findViewById(R.id.spLedRespiro);
                List <ListBunnies> listAE = new LinkedList <ListBunnies>();
                for(int i=0;i<g.getBunniesName().size();i++)
                {
                  listAE.add(new ListBunnies(g.getBunniesName().get(i),g.getBunniesMac().get(i),g.getBunniesOnLine().get(i),false));
                }
                mySpinnerTmp.setAdapter(new MyAdapterBunnies(getApplicationContext(), R.layout.custom_spinner,listAE));//spinnerValues

        		Spinner spinner = (Spinner) dialog.findViewById(R.id.spColori);
         	    //String[] colori = getResources().getStringArray(R.array.array_colori); 
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
        		        R.array.array_colori, android.R.layout.simple_spinner_item);
        		// Specify the layout to use when the list of choices appears
        		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		// Apply the adapter to the spinner
        		spinner.setAdapter(adapter);
        		  
        		dialogButton = (Button) dialog.findViewById(R.id.bntSendLedRespiro);

	         	dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

					//String urlPlugin = g.getServer();//tmp[2]; //server
					//urlPlugin = urlPlugin + "/ojn_api/bunny/";
						
					ListBunnies bunnyChoice = (ListBunnies) mySpinnerTmp.getSelectedItem();
					//String token=g.getToken();
					//urlPlugin = urlPlugin + bunnyChoice.getMac();///tmp[3]; //mac

					//urlPlugin = urlPlugin + "/colorbreathing/setColor?name=";
										 
					Spinner spinner = (Spinner) dialog.findViewById(R.id.spColori);
					String tmpx = String.valueOf(spinner.getSelectedItem());
					
					//urlPlugin = urlPlugin + tmpx;
					//urlPlugin = urlPlugin + "&token=" + token;
					//urlPlugin = urlPlugin.replace(" ", "%20");
									   
	                String urlPlugin = urlHelper.getBunnyColorBreathing( g.getServer(),bunnyChoice.getMac(), tmpx);
	                urlPlugin = urlHelper.addToken(urlPlugin, g.getToken());
	                urlPlugin = urlPlugin.replace(" ", "%20");
					new HttpAsync().execute(urlPlugin);
				   
//				    Toast.makeText(getApplicationContext(), "send to bunny.....", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				   }
				   });
    	  		  dialog.show();              
            	  break;
            	  //******************* end led breath
            	  
              case 3:
        		// *************************************
         		// NOSE
        		// *************************************
        		dialog.setContentView(R.layout.plugin_naso);
        		dialog.setTitle(R.string.plugin_title4);
        		dialogButton = (Button) dialog.findViewById(R.id.bntNaso);


        		mySpinnerTmp = (Spinner) dialog.findViewById(R.id.spSendText);
                List <ListBunnies> listNaso = new LinkedList <ListBunnies>();
                for(int i=0;i<g.getBunniesName().size();i++)
                {
                  listNaso.add(new ListBunnies(g.getBunniesName().get(i),g.getBunniesMac().get(i),g.getBunniesOnLine().get(i),false));
                }
                mySpinnerTmp.setAdapter(new MyAdapterBunnies(getApplicationContext(), R.layout.custom_spinner,listNaso));//spinnerValues
        		
             	dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
        		RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radioGroupNaso);
        		int radioButtonID = rg.getCheckedRadioButtonId();
        		View radioButton = rg.findViewById(radioButtonID);
        		int numberFlash = rg.indexOfChild(radioButton);
					
				//String urlPlugin = g.getServer();//tmp[2]; //server
				//urlPlugin = urlPlugin + "/ojn_api/bunny/";

				ListBunnies bunnyChoice = (ListBunnies) mySpinnerTmp.getSelectedItem();
//Log.d("spinner",bunnyChoice.getMac());

                //String token=g.getToken();
				//urlPlugin = urlPlugin + bunnyChoice.getMac();///tmp[3]; //mac
				//urlPlugin = urlPlugin + "/packet/sendPacket?data=";
									
				String valueNose = String.format( "%02X", numberFlash);
// Clignotement du nez
// disable, clignote, double clignote
				String packet = "7F040000067FFFFFFE08" + valueNose + "FF";
				//urlPlugin = urlPlugin + "7F040000067FFFFFFE08" + valueNose + "FF";
				//urlPlugin = urlPlugin + "&token=" + token;
				//urlPlugin = urlPlugin.replace(" ", "%20");
				
                String urlPlugin = urlHelper.getBunnySendPacket( g.getServer(),bunnyChoice.getMac(), packet);
                urlPlugin = urlHelper.addToken(urlPlugin, g.getToken());
                urlPlugin = urlPlugin.replace(" ", "%20");				
				new HttpAsync().execute(urlPlugin);
   		        //Toast.makeText(getApplicationContext(), "naso.....", Toast.LENGTH_SHORT).show();
	            dialog.dismiss();
	            }
	            });
	      		dialog.show();              
        		break;
            	  // *************************************** end nose
              case 4:
        		// *************************************
         		// SLEEP
        		// *************************************
          		dialog.setContentView(R.layout.plugin_sleep_wakeup);
          		dialog.setTitle(R.string.plugin_title5);
          		dialogButton = (Button) dialog.findViewById(R.id.bntSleepWakeup);


          		mySpinnerTmp = (Spinner) dialog.findViewById(R.id.spSleepWakeup);
                  List <ListBunnies> listSleepWakeup = new LinkedList <ListBunnies>();
                  for(int i=0;i<g.getBunniesName().size();i++)
                  {
                	  listSleepWakeup.add(new ListBunnies(g.getBunniesName().get(i),g.getBunniesMac().get(i),g.getBunniesOnLine().get(i),false));
                  }
                  mySpinnerTmp.setAdapter(new MyAdapterBunnies(getApplicationContext(), R.layout.custom_spinner,listSleepWakeup));
          		
               	dialogButton.setOnClickListener(new OnClickListener() {
  				@Override
  				public void onClick(View v) {
  					
          		RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radioGroupSleepWakeup);
          		int radioButtonID = rg.getCheckedRadioButtonId();
          		View radioButton = rg.findViewById(radioButtonID);
          		int sleepWakeup = rg.indexOfChild(radioButton);
  					
  				//String urlPlugin = g.getServer();//tmp[2]; //server
  				//urlPlugin = urlPlugin + "/ojn_api/bunny/";

  				ListBunnies bunnyChoice = (ListBunnies) mySpinnerTmp.getSelectedItem();
  //Log.d("spinner",bunnyChoice.getMac());
  				String urlPlugin;
                //String token=g.getToken();
  				//urlPlugin = urlPlugin + bunnyChoice.getMac();///tmp[3]; //mac
				if (sleepWakeup==0) {
				  	//urlPlugin = urlPlugin + "/sleep/sleep?";
	                urlPlugin = urlHelper.getBunnySleep( g.getServer(),bunnyChoice.getMac());
				}
				else {
					//urlPlugin = urlPlugin + "/sleep/wakeup?";
	                urlPlugin = urlHelper.getBunnyWakeUp( g.getServer(),bunnyChoice.getMac());
				}
  									
  				//urlPlugin = urlPlugin + "token=" + token;
  				//urlPlugin = urlPlugin.replace(" ", "%20");
  				

                //String urlPlugin = urlHelper.getBunnySleep( g.getServer(),bunnyChoice.getMac());
                urlPlugin = urlHelper.addToken(urlPlugin, g.getToken());
                urlPlugin = urlPlugin.replace(" ", "%20");				
  				new HttpAsync().execute(urlPlugin);
     		        //Toast.makeText(getApplicationContext(), "naso.....", Toast.LENGTH_SHORT).show();
  	            dialog.dismiss();
  	            }
  	            });
  	      		dialog.show();              

            	  break;
            	  // *************************************** end sleep
              case 5:
        		// *************************************
         		// url radio/stream
        		// *************************************
          		dialog.setContentView(R.layout.plugin_webradio_url);
          		dialog.setTitle(R.string.plugin_title6);
          		dialogButton = (Button) dialog.findViewById(R.id.bntWebradio);

                final String comandStream = "ST ";
                //final String comandStreamSetName = "SI ";
                //final String comandStreamStop = "SE ";
                //final String tmpNameStream = "tmpstream";

          		mySpinnerTmp = (Spinner) dialog.findViewById(R.id.spinnerRabbit);
                List <ListBunnies> listWebradio = new LinkedList <ListBunnies>();
                for(int i=0;i<g.getBunniesName().size();i++) {
                 listWebradio.add(new ListBunnies(g.getBunniesName().get(i),g.getBunniesMac().get(i),g.getBunniesOnLine().get(i),false));
                }
                mySpinnerTmp.setAdapter(new MyAdapterBunnies(getApplicationContext(), R.layout.custom_spinner,listWebradio));

                ArrayList<Stream> ls = databaseHelper.getStreamData();
   				mySpinnerRadio = (Spinner) dialog.findViewById(R.id.spRadio);

   				mySpinnerRadio.setAdapter(
                		new MyAdapterStreams(getApplicationContext(), R.layout.custom_sp_streams,ls));

               	dialogButton.setOnClickListener(new OnClickListener() {

  				@Override
  				public void onClick(View v) {

                    ListBunnies bunnyChoice = (ListBunnies) mySpinnerTmp.getSelectedItem();
                    String urlCommand;
                    String urlPlugin;
                    String messageCrypt;
/* in study ...
                    CheckBox cbStop = (CheckBox) dialog.findViewById(R.id.cbStopStream);
                    Boolean cbYes = cbStop.isChecked();
                    if (cbYes) {
                        Toast.makeText(getApplicationContext(), "check si", Toast.LENGTH_SHORT).show();
                        urlCommand = comandStreamStop + tmpNameStream + String.valueOf(Character.toChars(10));
                        Log.d("url","stop stream "+urlCommand);
                        // create packet
                        messageCrypt = createPacket( urlCommand);
                        urlPlugin = urlHelper.getBunnySendPacket( g.getServer(),bunnyChoice.getMac(), messageCrypt);

                        urlPlugin = urlHelper.addToken(urlPlugin, g.getToken());
                        urlPlugin = urlPlugin.replace(" ", "%20");
                        new HttpAsync().execute(urlPlugin);

                    } else {
                        Toast.makeText(getApplicationContext(), "check NO", Toast.LENGTH_SHORT).show();
*/
                    /*
// assign name
                    urlCommand = comandStreamSetName + tmpNameStream + String.valueOf(Character.toChars(10));
                    Log.d("url","name "+urlCommand);
                    // create packet
                    messageCrypt = createPacket( urlCommand );
                    urlPlugin = urlHelper.getBunnySendPacket( g.getServer(),bunnyChoice.getMac(), messageCrypt);

                    urlPlugin = urlHelper.addToken(urlPlugin, g.getToken());
                    urlPlugin = urlPlugin.replace(" ", "%20");
                    g.setUrlPrep(urlPlugin);
                        //new HttpAsync().execute(urlPlugin);
                        
*/
                    Stream urlChoice = (Stream) mySpinnerRadio.getSelectedItem();
                    urlCommand = comandStream + urlChoice.getStreamUrl() + String.valueOf(Character.toChars(10));
                    Log.d("url","cmd "+urlCommand);
                    // create packet
                    messageCrypt = createPacket( urlCommand );
                    urlPlugin = urlHelper.getBunnySendPacket( g.getServer(),bunnyChoice.getMac(), messageCrypt);

                    urlPlugin = urlHelper.addToken(urlPlugin, g.getToken());
                    urlPlugin = urlPlugin +"&afterStreamName=1";
                    urlPlugin = urlPlugin.replace(" ", "%20");
//Log.d("u",urlPlugin);
                    new HttpAsync().execute(urlPlugin);
                    //} // no check
                        //Toast.makeText(getApplicationContext(), "naso.....", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
  	            }
  	            });
  	      		dialog.show();              

            	break;
            	// *************************************** end webradio

        		
               default:
         			// *************************************
         			//
         			// *************************************


                    break;              
              }

            }

            private String createPacket(String url1) {
                String tmpz;
                Character[] inversion_table = new Character[] { 1, 171, 205, 183, 57, 163, 197, 239, 241, 27, 61, 167, 41, 19, 53, 223, 225, 139, 173, 151, 25, 131, 165, 207, 209, 251, 29, 135, 9, 243, 21, 191, 193, 107, 141, 119, 249, 99, 133, 175, 177, 219, 253, 103, 233, 211, 245, 159, 161, 75, 109, 87, 217, 67, 101, 143, 145, 187, 221, 71, 201, 179, 213, 127, 129, 43, 77, 55, 185, 35, 69, 111, 113, 155, 189, 39, 169, 147, 181, 95, 97,11, 45, 23, 153, 3, 37, 79, 81, 123, 157, 7, 137, 115, 149, 63, 65, 235, 13, 247, 121, 227, 5, 47, 49, 91, 125, 231, 105, 83, 117, 31, 33, 203, 237, 215, 89, 195, 229, 15, 17, 59, 93, 199, 73, 51, 85, 255 };

// prepare head of message
// add lenght
                String tmpy="7F0A0000";
                tmpz = String.format( "%02X", url1.length()+1);
                tmpy += tmpz;
// go! crypt
                ArrayList<Character>tmp= new ArrayList<Character>();
                // Obfuscating algorithm by Sache
                Character previousChar = 35;
                tmp.add ((char) 0);
                tmpz = String.format( "%02X", 0);
                tmpy+=tmpz;
                Character currentChar;
                for(int i = 0; i < url1.length(); i++) {
                    currentChar = (Character) url1.charAt(i);// P.get(i);
                    int z = ( inversion_table[ previousChar % 128 ] * currentChar + 47) & 0xFF ;
                    tmp.add ((char) z);
                    previousChar = currentChar;
                    tmpz = String.format( "%02X", z);
                    tmpy+=tmpz;
                }
// add 0xff to close message
                tmpy += "FF";
                //Log.d("string",tmpy);

                return tmpy;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.debug) {

           final Dialog dialog = new Dialog(context);
           // Include dialog.xml file
           dialog.setContentView(R.layout.debug);
           // Set dialog title
           dialog.setTitle("Debug!");

           String lastURL = g.getLastURL();
           TextView text1 = (TextView) dialog.findViewById(R.id.textDebugURL);
           text1.setText(lastURL);
           String lastResponse = g.getLastResponse();
           TextView text2 = (TextView) dialog.findViewById(R.id.textDebugResponse);
           text2.setText(lastResponse);

           dialog.show();
            
           Button declineButton = (Button) dialog.findViewById(R.id.bntDebugOk);
           // if decline button is clicked, close the custom dialog
           declineButton.setOnClickListener(new OnClickListener() {
               @Override
               public void onClick(View v) {
                   // Close dialog
                   dialog.dismiss();
               }
           });        	
       	
       }
        
        
        if (id == R.id.about) {
        	 // Crea custom dialog object
            final Dialog dialog = new Dialog(context);
            // layout
            dialog.setContentView(R.layout.about);
            // Set title
            //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setTitle("About");

            TextView text = (TextView) dialog.findViewById(R.id.textDialog);
            text.setText("Nabz version 3.0 :-) by carlo64");

            dialog.show();
             
            Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
            // click ...esco...
            declineButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    dialog.dismiss();
                }
            });        	
        	
        }
        
        if (id == R.id.action_settings) {

            //Toast.makeText(getApplicationContext(),"settaggi",Toast.LENGTH_LONG).show();  
    		// *************************************
    		//
    		// *************************************
    		final Dialog dialog = new Dialog(context);
    		dialog.setContentView(R.layout.custom);
    		dialog.setTitle(R.string.dialog_setting);

    		String[] tmp=null;
    		tmp = databaseHelper.getC();
    		
    		EditText loginUtente= (EditText) dialog.findViewById(R.id.utente);
    		loginUtente.setText(tmp[0]);
    		
    		EditText loginPassword= (EditText) dialog.findViewById(R.id.password);
    		loginPassword.setText(tmp[1]);

    		EditText loginServer= (EditText) dialog.findViewById(R.id.server);
    		loginServer.setText(tmp[2]);

    		/*EditText loginMac= (EditText) dialog.findViewById(R.id.coniglio);
    		loginMac.setText(tmp[3]);*/
    		
    		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
    		// click... esco
    		dialogButton.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				dialog.dismiss();
    			}
    		});

    		
    		Button dialogSalva = (Button) dialog.findViewById(R.id.salvaDati);
    		dialogSalva.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				String a1=null;
    				EditText loginUtente= (EditText) dialog.findViewById(R.id.utente);
    				a1 = loginUtente.getText().toString();
    				String a2=null;
    				EditText loginPassword= (EditText) dialog.findViewById(R.id.password);
    				a2 = loginPassword.getText().toString();
    				String a3=null;
    				EditText loginServer= (EditText) dialog.findViewById(R.id.server);
    				a3 = loginServer.getText().toString();
    				/*String a4=null;
    				EditText loginMac= (EditText) dialog.findViewById(R.id.coniglio);
    				a4 = loginMac.getText().toString();*/
    				databaseHelper.updateDati(a1,a2,a3);//,a4);

    				Toast.makeText(getApplicationContext(), R.string.update_ok, Toast.LENGTH_SHORT).show();

                    String urlo = urlHelper.getAuthUrl( a3, a1, a2);
//                    String urlo=null;
  //  		        urlo = a3 + "/ojn_api/accounts/auth?login=" + a1 + "&pass=" + a2;
    		        new HttpAsync().execute( urlo );
    				dialog.dismiss();
    			}
    		});
    		dialog.show();
            
            
            
            
            
            return true;     
        }

        //
        if (id == R.id.action_stream) {

    		// *************************************
    		// RADIO / STREAM
    		// *************************************
    		final Dialog dialog = new Dialog(context);
    		dialog.setContentView(R.layout.stream);
    		dialog.setTitle(R.string.dialog_stream);

            final EditText mText = (EditText) dialog.findViewById(R.id.name);
            final EditText etSite = (EditText) dialog.findViewById(R.id.site);
            
            Button mAdd = (Button) dialog.findViewById(R.id.add);
            
            final ListView mList = (ListView) dialog.findViewById(R.id.list);
            cursorStream = databaseHelper.getStreams(); 	

            String[] headers = new String[] {"NAMESTREAM", "URLSTREAM"};
            mAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
            		cursorStream, headers, new int[]{android.R.id.text1, android.R.id.text2},0);
            mList.setAdapter(mAdapter);
        
    		mAdd.setOnClickListener(new OnClickListener() {
    			
    		@Override
    		public void onClick(View arg0) {
    			// TODO Auto-generated method stub
    			String a1 = mText.getText().toString();
   				String a2 = etSite.getText().toString();
   				databaseHelper.insertDatiStream( databaseHelper.getWritableDatabase(), a1,a2);

   				Toast.makeText(getApplicationContext(), "save", Toast.LENGTH_SHORT).show();						
   				cursorStream = databaseHelper.getStreams(); 
   				mAdapter.changeCursor(cursorStream);
                // blank input
                mText.setText("");
                etSite.setText("");
   			}
    		});
    		
    		
    		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long id) {
				// TODO Auto-generated method stub
					
				final int p = position;
				AlertDialog.Builder miaAlert = new AlertDialog.Builder(context);
				miaAlert.setMessage(R.string.msg_alert);
				miaAlert.setTitle(R.string.title_alert);
				String yes = getResources().getString(R.string.choice_yes);
				String no = getResources().getString(R.string.choice_no);
				
				miaAlert.setCancelable(false);
				miaAlert.setPositiveButton(yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
//					  Toast.makeText(getApplicationContext(), "SI", Toast.LENGTH_SHORT).show();
					//Toast.makeText(getApplicationContext(), Integer.toString(p), Toast.LENGTH_SHORT).show();						


                    cursorStream.moveToPosition(p);
				    //Get the id value of this row
				    String rowId = cursorStream.getString(0); //Column 0 of the cursor is the id
				    databaseHelper.deleteStream( databaseHelper.getWritableDatabase(), new String[] {rowId});
				    cursorStream = databaseHelper.getStreams(); 
				    mAdapter.changeCursor(cursorStream);

				}
				});
					    
				miaAlert.setNegativeButton(no, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
//						  Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_SHORT).show();
			    }
				});
				AlertDialog alert = miaAlert.create();
				alert.show();
//					return;
				}
			});
    		
    		dialog.show();
            
           
            return true;     
        }

        
        //
        return super.onOptionsItemSelected(item);
    }



    
  
      // check network connection
      public boolean isConnected(){
          ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
              NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
              if (networkInfo != null && networkInfo.isConnected())
                  return true;
              else
                  return false;  
      }    

      public class MyAdapterBunnies extends ArrayAdapter<ListBunnies> {

//    	    //Globals g = Globals.getInstance();
      		public MyAdapterBunnies(Context ctx, int txtViewResourceId, List <ListBunnies> objects) {
      			super(ctx, txtViewResourceId, objects);
      		}

      		@Override
      		public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
      			return getCustomView(position, cnvtView, prnt);
      		}
      		@Override
      		public View getView(int pos, View cnvtView, ViewGroup prnt) {
      			return getCustomView(pos, cnvtView, prnt);
      		}
      		public View getCustomView(int position, View convertView,
      				ViewGroup parent) {
      			//LayoutInflater inflater = getLayoutInflater();
      			
      			
      		    View mySpinner = convertView;

      		    if (mySpinner == null) {

      		      LayoutInflater vi;
      		      vi = LayoutInflater.from(getContext());
      		      mySpinner = vi.inflate(R.layout.custom_spinner, null);

      		    }
      			
  /*    			
      			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
      			View mySpinner = inflater.inflate(R.layout.custom_spinner, parent,
      					false);
*/
      			ListBunnies ite = getItem(position);
      			TextView main_text = (TextView) mySpinner
      					.findViewById(R.id.text_main_seen);
      			//main_text.setText(spinnerValues[position]);
                String o=" ("+getResources().getString(R.string.bunny_off)+")";
      			if(ite.getOnLine()){ o=" ("+getResources().getString(R.string.bunny_on)+")"; }
      			//subSpinner.setBackgroundColor(Color.YELLOW);
      			main_text.setTextColor(Color.BLACK);
     			main_text.setText(ite.getNome()+o);

      			TextView subSpinner = (TextView) mySpinner
      					.findViewById(R.id.sub_text_seen);
//      			subSpinner.setText(spinnerSubs[position]);
      			subSpinner.setTextColor(Color.BLUE);
      			subSpinner.setText(ite.getMac());

      			ImageView left_icon = (ImageView) mySpinner
      					.findViewById(R.id.left_pic);
      			
      			if(ite.getOnLine()){ 
          			left_icon.setImageResource(R.drawable.ic_nano);//total_images[position]);
      			}
      			else {
          			left_icon.setImageResource(R.drawable.ic_nanoff);//total_images[position]);
      			}      			
      			
      			

      			return mySpinner;
      		}
      	}
      public class MyAdapterStreams extends ArrayAdapter<Stream> {

//  	    //Globals g = Globals.getInstance();
    		public MyAdapterStreams(Context ctx, int txtViewResourceId, List <Stream> objects) {
    			super(ctx, txtViewResourceId, objects);
    		}

    		@Override
    		public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
    			return getCustomView(position, cnvtView, prnt);
    		}
    		@Override
    		public View getView(int pos, View cnvtView, ViewGroup prnt) {
    			return getCustomView(pos, cnvtView, prnt);
    		}
    		public View getCustomView(int position, View convertView,
    				ViewGroup parent) {
    			//LayoutInflater inflater = getLayoutInflater();
    			
    			
    		    View mySpinner = convertView;

    		    if (mySpinner == null) {

    		      LayoutInflater vi;
    		      vi = LayoutInflater.from(getContext());
    		      mySpinner = vi.inflate(R.layout.custom_sp_streams, null);

    		    }
    			
    			Stream ite = getItem(position);
    			
    			TextView main_text = (TextView) mySpinner
    					.findViewById(R.id.text_s1);
    			//main_text.setText(spinnerValues[position]);
              //String o=" ("+getResources().getString(R.string.bunny_off)+")";
    		main_text.setTextColor(Color.BLACK);
   			main_text.setText(ite.getStreamName());

    			TextView subSpinner = (TextView) mySpinner
    					.findViewById(R.id.text_s2);
//    			subSpinner.setText(spinnerSubs[position]);
    			subSpinner.setTextColor(Color.BLACK);
    			subSpinner.setText(ite.getStreamUrl());

    			ImageView left_icon = (ImageView) mySpinner
    					.findViewById(R.id.pp);
    			
        			left_icon.setImageResource(R.drawable.ic_radio);

    			return mySpinner;
    		}
    	}


}


