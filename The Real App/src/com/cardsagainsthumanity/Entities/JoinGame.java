package com.cardsagainsthumanity.Entities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class JoinGame extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.joingame);

		Button returns = (Button) findViewById(R.id.ReturnToMenu);
		returns.setOnClickListener(new OnClickListener()
		{
	
			@Override
			public void onClick(View v) 
			{
				finish();
			}
			
		});
	} 
}
