package com.venky.smartsms;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Beep extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String sms=getIntent().getExtras().getString("Sms");
		String number=getIntent().getExtras().getString("Number");
		setContentView(R.layout.number);
		//back arrow
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Smart SMS");
		TextView tv=(TextView)findViewById(R.id.tvWhom);
		tv.setText("Your phone was controlled by "+number);
		if(sms.contains("Call me"))
		{
			Intent phoneIntent = new Intent(Intent.ACTION_CALL);
			phoneIntent.setData(Uri.parse("tel:"+number));
			try {
		         startActivity(phoneIntent);
		         finish();
		         Log.i("Finished making a call...", "");
		      } catch (android.content.ActivityNotFoundException ex) {
		         Toast.makeText(this, 
		         "Call faild, please try again later.", Toast.LENGTH_SHORT).show();
		      }
		}
		else if(sms.contains("General"))
		{
			AudioManager audioManager =(AudioManager) getSystemService(Context.AUDIO_SERVICE);
			audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 7, 0);
			audioManager.setStreamVolume(AudioManager.STREAM_RING, 7, 0);
			audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 7, 0);
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
			Toast.makeText(this,"Profile Mode General", Toast.LENGTH_LONG).show();
			
		}
		else if(sms.contains("Where are you"))
		{
			Intent getloc=new Intent(this,Location.class);
			getloc.putExtra("Number", number);
			startActivity(getloc);
		}
		else if(sms.contains("Lat:"))
		{
			int v=sms.indexOf(":");
			int a=sms.indexOf(";");
			
			String lati=sms.substring(v+1, a-1);
			v=sms.indexOf("-");
			a=sms.indexOf("/");
			String longi=sms.substring(v+1, a-1);
			
			Log.e(lati,longi);
			String uri = "geo:"+ lati + "," + longi;
			Uri geoLocation=Uri.parse("geo:"+lati+","+longi).buildUpon().build();
			
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(geoLocation);
			if(intent.resolveActivity(getPackageManager())!=null)
					{
				startActivity(intent);
					}
			else
			{
				Log.e("Location"," Not found");
			}
			
		}
		
		else
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
	         String mock="2014:11:26:00:56:00";
	         Date date= new Date();
	 		try {
	 			date = dateFormat.parse(date.getYear()+":"+date.getMonth()+":"+date.getDate()+":"+sms);
	 		} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
			createAlarm("Get UP",date.getHours(), date.getMinutes());	
		}
		 
	}
	public void createAlarm(String message, int hour, int minutes) {
	    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
	            .putExtra(AlarmClock.EXTRA_MESSAGE, message)
	            .putExtra(AlarmClock.EXTRA_HOUR, hour)
	            .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
	    if (intent.resolveActivity(getPackageManager()) != null) {
	        startActivity(intent);
	    }
	}
	
	public void dialPhoneNumber(String phoneNumber) {
	    Intent intent = new Intent(Intent.ACTION_DIAL);
	    intent.setData(Uri.parse("tel:" + phoneNumber));
	    if (intent.resolveActivity(getPackageManager()) != null) {
	        startActivity(intent);
	    }
	}
	
	public void addEvent(String title, String location,String description,boolean allday, long begin,long end) {
	    Intent intent = new Intent(Intent.ACTION_INSERT)
	            .setData(Events.CONTENT_URI)
	            .putExtra(Events.TITLE, title)
	            .putExtra(Events.EVENT_LOCATION, location)
	            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
	            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
	    if (intent.resolveActivity(getPackageManager()) != null) {
	        startActivity(intent);
	    }
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
