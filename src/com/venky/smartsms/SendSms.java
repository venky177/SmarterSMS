package com.venky.smartsms;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SendSms extends Activity implements OnClickListener{
	
	public String alarm="This feature is useful for setting alarms in the phone of the specified contact.\n"+
							"You can also use it by sending a text message to the number.\n"+
							"SMS-->HH:MM where HH(0-23) MM(0-59)...eg:17:04 for 5:04PM or 6:22 for 6:22AM\n"+
							"Mostly useful to wake somone up if they are not picking up your calls.\n"+
							"If your phone recives a text in the above format it will set an apropriate alarm in your phone.\n";
	public String call="This feature is useful for \"getting called\" by the person you send the text message to.\n"+
							"SMS-->Call me.\n"+
							"Mostly useful for getting calls for free.\n"+
							"If your phone recives a text in the above format it will call the Text Sender.\n";
	public String gen="This  feature is useful for changing the sound profile of the text message recivers phone to \"General\" mode.\n"+
						"SMS-->General.\n"+
						"Mostly useful when your phone gets lost at home and it is on \"Silent\", can also be used to make people pick up calls if phones on silent.\n"+
						"If your phone recives a text in the above format it will set your sound profile mode to General.\n";
	public String findme="This  feature is useful for finding the position of the contact you send a text message to.\n"+
							"It works best when \"GPS\" mode is switched on. It shows you the location on Google Maps.\n"+
							"SMS-->Where are you.\n"+
							"Mostly useful for finding the location of people.\n "+
							"If your phone recives a text in the above format it will send your Coordinates to the Text Sender.\n";
	public String instructions[]={alarm,call,gen,findme};
	ImageButton Contacts;
	Button Sms;
	EditText etNumber;
	int Task;
	String[] smsms={"Alarm","Call me","General","Where are you"};
	TimePicker notifyTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sender);
		//back arrow
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	
		Task=getIntent().getExtras().getInt("Task");
		getActionBar().setTitle(smsms[Task]);
		TextView tvhow=(TextView)findViewById(R.id.tvHOW);
		tvhow.setText(instructions[Task]);
		notifyTime = ((TimePicker) findViewById(R.id.tpTime));
		if(Task!=0)
		notifyTime.setVisibility(View.GONE);
		
		Contacts=(ImageButton)findViewById(R.id.bcontacts);
		Sms=(Button)findViewById(R.id.bSend);
		
		Contacts.setOnClickListener(this);
		Sms.setOnClickListener(this);
		
		etNumber=(EditText)findViewById(R.id.etNum);
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bcontacts:
			Intent intentAL = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI); 
			startActivityForResult(intentAL, 1);
			break;

		case R.id.bSend:
			if(Task==0)
			{
				notifyTime.clearFocus();
			    
			    int hour = notifyTime.getCurrentHour();
			    int minute = notifyTime.getCurrentMinute();
				
			    String minutes = "";
		        if (minute < 10)
		            minutes = "0" + minute;
		        else
		            minutes = String.valueOf(minute);
		        smsms[Task]=hour+":"+minutes;
			}
			String mynum="";
			mynum=etNumber.getText().toString();
			
			if(mynum=="")
			{
				  Toast.makeText(this,"Enter Number ", Toast.LENGTH_SHORT).show();
			}
			else
			{
				SmsManager sms = SmsManager.getDefault();
			    sms.sendTextMessage(mynum, null,smsms[Task] , null, null);
			    Toast.makeText(this,"SMS Sent! ", Toast.LENGTH_SHORT).show();	
			}
			break;
		}
	}
	@Override  
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     // what goes here...
		String res[];
		res=new String[3];
		String phoneNo = "" ;
		if(data!=null)
		{
		Uri uri = data.getData();
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		cursor.moveToFirst();
		
		int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
		phoneNo = cursor.getString(phoneIndex);
		
		Log.e("dsfjb", phoneNo);
		if(phoneNo.indexOf("-")!=-1)
		{
		res=phoneNo.split("-");
		Log.e("Contact", res[0]+res[1]+res[2]);
		phoneNo=res[0]+res[1]+res[2];
		}
		etNumber.setText(phoneNo);
		}
	
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		 
		MenuInflater inflater = getMenuInflater();
		 
		inflater.inflate(R.menu.main, menu);
		 
		return true;
		 
		}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			break;
		case R.id.About:
			Intent i = new Intent(this,About.class);
			startActivity(i);	
			break;
		case R.id.action_settings:
			Intent ip = new Intent(this,Prefs.class);
			startActivity(ip);	
			break;
		
		}
		return super.onOptionsItemSelected(item);	
		}

}
