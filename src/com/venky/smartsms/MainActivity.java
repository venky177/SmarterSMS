package com.venky.smartsms;

import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import android.view.MenuItem;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity implements  OnItemClickListener{
	
	Button get,save,con1,con2,con3,con4,con5;
	EditText et1,et2,et3,et5,et4;
	public BroadcastReceiver myreciver;
	public IntentFilter myfilter;
	public static String myfile="SharedNumbers";
	public SharedPreferences mysp;
	public DrawerLayout drawerLayout;
	public ListView listView;
	private String[] Functions;
	private ActionBarDrawerToggle drawerListner;
	private MyAdapter myadap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
         //                      WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.drawer);
		TextView tv=(TextView)findViewById(R.id.tvBasic);
		String how = "Hey guys, this app will help you make your smartphone even smarter.\nIt lets you control activities in your phone via \"Text Messages\".\n It does not let you hack other phones. It helps others control your phone during emergencies.\n\n"+
				"Before you start using it Go To Menu-->Settings and initialize 5 numbers.Press \"Store\" and your good to go.\n\n"+
						"Only these numbers will be able to control your phone via \"Texts\".\n\n"+
				"This app lets you ...\n"+"*Create Alarms\n"+"*Get Calls \n"+"*Change Sound Profile\n"+"*Get Location\n"+
						"From other peoples phones i.e. only if their number is stored in the apps' Settings. \n\n"+"You can access these features from the drawer by swiping Left->Right or by tapping the top left Logo of the app.\nThese features can be used to control other phones having the same app and your contact in their App Settings.\n"+
						"You can use other messaging apps to control thier phones as well.";
		tv.setText(how);
		
		
		//drawer ui
		drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
		drawerListner=new ActionBarDrawerToggle(this, drawerLayout, R.drawable.drawer,
				R.string.opneNoti,R.string.closeNoti ){
			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerOpened(drawerView);
				
			}
			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);
				
				
			}
			
			
		};
		drawerLayout.setDrawerListener(drawerListner);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Smarter SMS");
		
		
		listView=(ListView)findViewById(R.id.drawerList);
		Functions=getResources().getStringArray(R.array.Functions);
		listView.setOnItemClickListener(this);
		//listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Functions));
		myadap= new MyAdapter(this);
		listView.setAdapter(myadap);
		
		//broad cast reciver
		myreciver= new GetSms();
		myfilter=new IntentFilter();
		myfilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(myreciver, myfilter);
		
		
		
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		//used to min n max 3 bars
		drawerListner.syncState();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		 
		MenuInflater inflater = getMenuInflater();
		 
		inflater.inflate(R.menu.main, menu);
		 
		return true;
		 
		}
	 
	
	//to open and close by clicking on menu item 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(drawerListner.onOptionsItemSelected(item))
		{
			return true;
		}
		switch (item.getItemId())
		{
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
	
	// for orientation changes 
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		drawerListner.onConfigurationChanged(newConfig);
	}

	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		Log.e("imismdf", "sdfkjsndkfjn");
		Intent i = new Intent(this,SendSms.class);
		i.putExtra("Task", position);
		startActivity(i);	
		
	}

}
class MyAdapter extends BaseAdapter{
	private Context context;
	String[] functions;
	int[] images={R.drawable.alarm,
			R.drawable.call,
			R.drawable.gen,
			R.drawable.map
	};
	public MyAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	functions=context.getResources().getStringArray(R.array.Functions);	
	}
	
	//number of strings in array
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return functions.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return functions[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = null;
		
		if(convertView==null)
		{	
			LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row=inflator.inflate(R.layout.custom_row, parent,false);
		}
		else
		{	
			row=convertView;
		}
		if(row!=null)
		{
			
		TextView titleTextView=(TextView)row.findViewById(R.id.tvRow);
		ImageView rowImage=(ImageView)row.findViewById(R.id.ivRow);
		titleTextView.setText(functions[position]);
		rowImage.setImageResource(images[position]);
		}
		
		return row;
	}
	
}
	
