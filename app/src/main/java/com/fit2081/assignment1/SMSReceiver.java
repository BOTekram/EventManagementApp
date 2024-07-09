package com.fit2081.assignment1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    //Define constants for the intent filter action and message key
    public static final String SMS_FILTER = "SMS_FILTER";
    public static final String SMS_MSG_KEY = "SMS_MSG_KEY";

    /*
     * This method 'onReceive' will be invoked with each new incoming SMS
     * */
    @Override
    public void onReceive(Context context, Intent intent) {
        /*
         * Use the Telephony class to extract the incoming messages from the intent
         * */
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        //loop through each received message
        for (SmsMessage currentMessage : messages) {
            //Extract the message body
            String message = currentMessage.getDisplayMessageBody();

            //Create a new intent to broadcast the received message
            Intent msgIntent = new Intent();
            //Set the action to identify this broadcast
            msgIntent.setAction(SMS_FILTER);
            //Put the message as an extra in the intent
            msgIntent.putExtra(SMS_MSG_KEY, message);
            //Send the broadcast
            context.sendBroadcast(msgIntent);
        }
    }
}