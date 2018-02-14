package com.example.layout.assig17_2;

import android.app.Service;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import java.util.Date;

/**
 * Created by Pri on 10/24/2017.
 */

public class BoundService extends Service {
    //A Service is an application component representing either an application's desire to perform a longer-running operation while not
    // interacting with the user or to supply functionality for other applications to use.
    IBinder iBinder = new LocalBinder();
//Base interface for a remotable object, the core part of a lightweight remote procedure call mechanism designed for high performance when performing in-process and cross-process calls.
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return iBinder;
    }

    public class LocalBinder extends Binder {
        public BoundService getService() {
            // Return this instance of BoundService
            return BoundService.this;
        }

    }

    public String getTime() {//Method for GetTime()
        //creating object of SimpleDateFormat
        SimpleDateFormat simpleDateFormat = null;
        //Applying the condition for getting the date and time
        //build version  will help us to run the program
        ////The user-visible SDK version of the framework; its possible values are defined in Build.VERSION_CODES
        //Enumeration of the currently known SDK version codes. These are the values that can be found in SDK. Version numbers increment monotonically with each official platform release.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //date format will print in this way
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return simpleDateFormat.format(new Date());
        }
            return simpleDateFormat.format(new Date());


    }
}