package com.cardsagainsthumanity.Entities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

import android.app.Activity;
import android.content.*;
import java.io.*;

public class Settings extends Activity
{
	private User user;
	private Game games;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Game getGames() {
		return games;
	}

	public void setGames(Game games) {
		this.games = games;
	}

	void logOut()
	{
		
	}
	
	void changeNumRounds()
	{
		
	}
	
	void editUserName()
	{
		
	}
	
	void editPassWord()
	{
		
	}
	
	void writeSettings()
	{
		/*
		 * Tutorial located at 
		 * http://stackoverflow.com/questions/1239026/how-to-create-a-file-in-android
		 */
		try
		{
			//write testString to file
			String testString = "test";
			FileOutputStream fOut = openFileOutput("samplefile.txt", Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			
			osw.write(testString);
			
			osw.flush();
			osw.close();
			
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	void readSettings()
	{
		try
		{
			FileInputStream fIn = openFileInput("samplefile.txt");
	        InputStreamReader isr = new InputStreamReader(fIn);
	        
	        char[] inputBuffer = new char[100];
	        isr.read(inputBuffer);
	        String readString = new String(inputBuffer);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}
