package com.venky.smartsms;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Prefs extends Activity implements OnClickListener{

	ImageButton con1,con2,con3,con4,con5;
	Button save;
	EditText et1,et2,et3,et5,et4;
	public BroadcastReceiver myreciver;
	public IntentFilter myfilter;
	public static String myfile="SharedNumbers";
	public SharedPreferences mysp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
         //                      WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.fragment_main);
		//back arrow
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Settings");
		
	
		save=(Button)findViewById(R.id.bstore);
		con1=(ImageButton)findViewById(R.id.bcon1);
		con2=(ImageButton)findViewById(R.id.bcon2);
		con3=(ImageButton)findViewById(R.id.bcon3);
		con4=(ImageButton)findViewById(R.id.bcon4);
		con5=(ImageButton)findViewById(R.id.bcon5);
		
		et1=(EditText)findViewById(R.id.number1);
		et2=(EditText)findViewById(R.id.number2);
		et3=(EditText)findViewById(R.id.number3);
		et4=(EditText)findViewById(R.id.number4);
		et5=(EditText)findViewById(R.id.number5);
		
		
		save.setOnClickListener(this);
		con1.setOnClickListener(this);
		con2.setOnClickListener(this);
		con3.setOnClickListener(this);
		con4.setOnClickListener(this);
		con5.setOnClickListener(this);
		
		mysp=getSharedPreferences(myfile, 0);
		String num1=mysp.getString("Num1", "Choose Contact ->");
		String num2=mysp.getString("Num2", "Choose Contact ->");
		String num3=mysp.getString("Num3", "Choose Contact ->");
		String num4=mysp.getString("Num4", "Choose Contact ->");
		String num5=mysp.getString("Num5", "Choose Contact ->");
		et1.setText(num1);
		et2.setText(num2);
		et3.setText(num3);
		et4.setText(num4);
		et5.setText(num5);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		
		case R.id.bstore:
			String number1=et1.getText().toString();
			String number2=et2.getText().toString();
			String number3=et3.getText().toString();
			String number4=et4.getText().toString();
			String number5=et5.getText().toString();
			SharedPreferences.Editor editor=mysp.edit();
			editor.putString("Num1", number1);
			editor.putString("Num2", number2);
			editor.putString("Num3", number3);
			editor.putString("Num4", number4);
			editor.putString("Num5", number5);
			editor.commit();
			Toast.makeText(this,"Contacts Stored! ", Toast.LENGTH_SHORT).show();
			break;
			 
		case R.id.bcon1:
			Intent intent1 = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI); 
			startActivityForResult(intent1, 1);
			break;
			
		case R.id.bcon2:
			Intent intent2 = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI); 
			startActivityForResult(intent2, 2);
			break;
		case R.id.bcon3:
			Intent intent3 = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI); 
			startActivityForResult(intent3, 3);
			break;
			
		case R.id.bcon4:
			Intent intent4 = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI); 
			startActivityForResult(intent4, 4);
			break;
			
		case R.id.bcon5:
			Intent intent5 = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI); 
			startActivityForResult(intent5, 5);
			break;
			
		}
		
	}
	  
	@Override  
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     // what goes here...
		String res[];
		res=new String[3];
		String phoneNo = null ;
		if(data!=null)
		{
			
		Uri uri = data.getData();
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		
		cursor.moveToFirst();
		
		int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
		Log.e("Number", " "+phoneIndex);
		if(phoneIndex!=0)
		phoneNo = cursor.getString(phoneIndex);
		
		Log.e("Number", phoneNo);
		if(phoneNo.indexOf("-")!=-1)
		{
		res=phoneNo.split("-");
		Log.e("Contact", res[0]+res[1]+res[2]);
		phoneNo=res[0]+res[1]+res[2];
		}
		
		switch(requestCode)
		{
		case 1:
			et1.setText(phoneNo);
			break;
		case 2:
			et2.setText(phoneNo);
			break;
		case 3:
			et3.setText(phoneNo);
			break;
		case 4:
			et4.setText(phoneNo);
			break;
		case 5:
			et5.setText(phoneNo);
			break;
		
		}
		
		}
	}


	public void addEvent(String title, String location,String description,boolean allday, long begin,long end) {
	    Intent intent = new Intent(Intent.ACTION_INSERT)
	            .setData(Events.CONTENT_URI)
	            .putExtra(Events.TITLE, title)
	            .putExtra(Events.EVENT_LOCATION, location)
	            .putExtra(Events.ALL_DAY, allday)
	            .putExtra(Events.DESCRIPTION, description)
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
 