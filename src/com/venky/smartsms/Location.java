package com.venky.smartsms;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class Location extends Activity implements LocationListener {

	LocationManager lm;
	 public static int MIN_TIME_BW_UPDATES=100000;
	public static int MIN_DISTANCE_CHANGE_FOR_UPDATES=100;
	android.location.Location loc;
	double Longi,Lati;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Smart SMS");
		
		String number=getIntent().getExtras().getString("Number");
		SmsManager sms = SmsManager.getDefault();
		String ans=getLocation();
		if(ans.contains("Cant Find"))
			Toast.makeText(getBaseContext(), "Turn on GPS",Toast.LENGTH_LONG).show();
		else
	       sms.sendTextMessage(number, null,ans , null, null);
	}
	public String getLocation() {
	    try {
	        lm = (LocationManager)getSystemService(LOCATION_SERVICE);

	        // getting GPS status
	       boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

	        // getting network status
	       boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

	        if (!isGPSEnabled && !isNetworkEnabled) {
	            // no network provider is enabled
	        	Toast.makeText(getBaseContext(), "Turn on something"+isGPSEnabled+" "+isNetworkEnabled+" "+loc,Toast.LENGTH_LONG).show();
	        } else {
	            //this.canGetLocation = true;
	            if (isNetworkEnabled) {
	                lm.requestLocationUpdates(
	                        LocationManager.NETWORK_PROVIDER,
	                        MIN_TIME_BW_UPDATES,
	                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                Log.d("Network", "Network Enabled");
	                if (lm != null) {
	                    loc = lm
	                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	                    if (loc != null) {
	                        Lati = loc.getLatitude();
	                        Longi = loc.getLongitude();
	                    }
	                }
	            }
	            // if GPS Enabled get lat/long using GPS Services
	            if (isGPSEnabled) {
	                if (loc == null) {
	                    lm.requestLocationUpdates(
	                            LocationManager.GPS_PROVIDER,
	                            MIN_TIME_BW_UPDATES,
	                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                    Log.d("GPS", "GPS Enabled");
	                    if (lm != null) {
	                        loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	                        if (loc != null) {
	                            Lati = loc.getLatitude();
	                            Longi = loc.getLongitude();
	                        }
	                    }
	                }
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    if(Lati == 0.0 && Longi == 0.0  )
	    	return "Cant Find";
	    return "Lat:"+Lati+"; Long-"+Longi+"/";
	}
	@Override
	public void onLocationChanged(android.location.Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(getBaseContext(), "Turn on gps",Toast.LENGTH_LONG).show();
	}

}
