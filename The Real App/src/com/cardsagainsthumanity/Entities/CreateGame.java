package com.cardsagainsthumanity.Entities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateGame extends Activity implements OnItemSelectedListener 
{
	private int rounds;
	private String userName;
	Button v;
	String check;
    private TextView error;
    private String userId;
    
    final Context context = this;
    public static final String SPREF_USER = "othPrefs";
    private ProgressBar mProgress;
    
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

        //getActionBar().setDisplayShowTitleEnabled(false);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.creategame);
		
		error = (TextView) findViewById(R.id.error);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    userName = extras.getString("userName");
		    userId = extras.getString("userId");
		}
		
		getActionBar().setDisplayShowTitleEnabled(false);
		
		Button returns = (Button) findViewById(R.id.btnReturn);
		returns.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Intent mainMenuIntent = new Intent(context, MainMenu.class);
             	mainMenuIntent.putExtra("userName", userName);
             	mainMenuIntent.putExtra("userId", userId);
             	startActivityForResult(mainMenuIntent, 0);
             	CreateGame.this.finish();	//Close CreateGame page when MainMenu starts
			}
			
		});
		
		//Setup drop down for game rounds ---------------------------------------------------------------
		Spinner inputGameRounds = (Spinner) findViewById(R.id.roundSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.roundArray, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		inputGameRounds.setAdapter(adapter);
		//Set spinner to default game rounds if available
		SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
		if(othSettings.contains("defGameRounds"))
		{
			inputGameRounds.setSelection(othSettings.getInt("defGameRounds", 2) - 1);
		} 
		inputGameRounds.setOnItemSelectedListener(this);
		//End drop down ----------------------------------------------------------------------------------
		
		
		
		Button createButton = (Button) findViewById(R.id.btnCreate);
		
		createButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				String stringUrl = "http://54.225.225.185:8080/ServerAPP/CreateGame?User="+userId+"&rounds="+rounds;
				mProgress = (ProgressBar) findViewById(R.id.progressBar1);
	        	check = "Game";
	        	ConnectivityManager connMgr = (ConnectivityManager) 
	    		getSystemService(Context.CONNECTIVITY_SERVICE);
	            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	            //error.setText("creating");
	            Toast.makeText(CreateGame.this, "Creating your game!", Toast.LENGTH_SHORT).show();
	            
	            if (networkInfo != null && networkInfo.isConnected())
	            {
	                new DownloadWebpageText().execute(stringUrl);
	            }
	            else 
	            {
	                error.setText("No network connection available.");
	            }
			} 			
		});
	
	}  
	
	private class DownloadWebpageText extends AsyncTask {
        
		@Override
        protected void onPreExecute()
		{
			mProgress.setVisibility(ProgressBar.VISIBLE);
		}
    	
		@Override
        protected Object doInBackground(Object... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl((String) urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
    	
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Object result) {
        	
        mProgress.setVisibility(ProgressBar.INVISIBLE);
        
        if(result!=null){
        	String results = (String) result.toString();
        	if(results!=null){
	        	results = results.trim();
	        	String[] resultArr = results.split(":");
	            //check the result for the what's needed to move on
	            if(resultArr!=null && (resultArr[0]).equalsIgnoreCase(check)){
					error.setText("");
					
					//Goes to game lobby from create game -------------------------------------------------
					Intent gameLobbyIntent = new Intent(CreateGame.this, GameLobby.class);
	            	gameLobbyIntent.putExtra("gameId", resultArr[1]);
	            	SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
                	SharedPreferences.Editor spEditor = othSettings.edit();
                	spEditor.putString("CurGameID", resultArr[1]).commit(); //Stores current game ID
                	spEditor.putBoolean("inGame", true).commit();   //Sets flag for user being in game
	            	gameLobbyIntent.putExtra("userName", userName);
	            	gameLobbyIntent.putExtra("userId", userId);
	            	startActivity(gameLobbyIntent);
	            	CreateGame.this.finish();
	            	//End going to game lobby ---------------------------------------------------------------	
	            }
	            else{
	            	error.setText(results);
	            }
        	}
        }
        else{
        	error.setText("Result was null");
        }
       }

     }

 private String downloadUrl(String myurl) throws IOException {
      InputStream is = null;
      // Only display the first 500 characters of the retrieved
      // web page content.
      int len = 500;
          
      try {
          URL url = new URL(myurl);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setReadTimeout(10000 /* milliseconds */);
          conn.setConnectTimeout(15000 /* milliseconds */);
          conn.setRequestMethod("GET");
          conn.setDoInput(true);
          // Starts the query
          conn.connect();
          int response = conn.getResponseCode();
          is = conn.getInputStream();

          // Convert the InputStream into a string
          String contentAsString = readIt(is, len);
          return contentAsString;
          
      // Makes sure that the InputStream is closed after the app is
      // finished using it.
      } finally {
          if (is != null) {
              is.close();
          } 
      }
  }
	
  	//Reads an InputStream and converts it to a String.
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
	   Reader reader = null;
	   reader = new InputStreamReader(stream, "UTF-8");        
	   char[] buffer = new char[len];
	   reader.read(buffer);
	   return new String(buffer);
	}  
	
	//Back button functionality------------------------------------
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) 
		{
			
		    if (keyCode == KeyEvent.KEYCODE_BACK) 
		    {
		    	Intent mainMenuIntent = new Intent(context, MainMenu.class);
             	mainMenuIntent.putExtra("userName", userName);
             	mainMenuIntent.putExtra("userId", userId);
             	startActivityForResult(mainMenuIntent, 0);
             	CreateGame.this.finish();	//Close CreateGame page when MainMenu starts
		    }
				
		    return super.onKeyDown(keyCode, event);
		}  //End back button functionality---------------------------------

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) 
		{
			// TODO Auto-generated method stub
			((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
			//Toast.makeText(getApplicationContext(), (String)parent.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
			
			String numRounds = (String)parent.getItemAtPosition(pos);
			
			rounds = Integer.parseInt(numRounds);
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) 
		{
			// TODO Auto-generated method stub
		}



}
