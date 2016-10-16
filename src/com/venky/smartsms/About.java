package com.venky.smartsms;



import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class About extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("About Me");
		String me="Hey guys my name is  Venkatesh Lokare , I am currently pursuing my engineering degree from PICT Pune.\nIf you all have any doubts Email me at "+getResources().getString(R.string.my_link)+" .\n\n Cheers!! ";
		TextView tv=(TextView)findViewById(R.id.tvAbout);
		tv.setText(me);
		
	
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
		super.onBackPressed();
		return true;
		}
		return true;	
		}

}
