package com.venky.smartsms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.Telephony;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.widget.Toast;

public class GetSms extends BroadcastReceiver{
	public static String myfile="SharedNumbers";
	public SharedPreferences mysp;
	public Context context;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context=context;
		if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
			mysp=context.getSharedPreferences(myfile, 0);
			String num1=mysp.getString("Num1", "no");
			String num2=mysp.getString("Num2", "no");
			String num3=mysp.getString("Num3", "no");
			String num4=mysp.getString("Num4", "no");
			String num5=mysp.getString("Num5", "no");
			
            for (android.telephony.SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String sms = smsMessage.getMessageBody();
                String number=smsMessage.getOriginatingAddress();
               // Log.e("Numbers",num1+" "+num2+" "+num3+" ");
               
                 if(number.contains(num1)||number.contains(num2)||number.contains(num3)||number.contains(num4)||number.contains(num5))
                {
                Intent i = new Intent(context,Beep.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("Sms", sms);
                i.putExtra("Number", number);
        		context.startActivity(i);
                	
                }
                else
                {
                	Toast.makeText(context, "Number Not Registered", Toast.LENGTH_LONG).show();
                }
            }
       
		}
	}
	

}
