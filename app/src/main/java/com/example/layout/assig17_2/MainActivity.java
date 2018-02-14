package com.example.layout.assig17_2;
//Package objects contain version information about the implementation and specification of a Java package
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //public keyword is used in the declaration of a class,method or field;public classes,method and fields can be accessed by the members of any class.
//extends is for extending a class. implements is for implementing an interface
//AppCompatActivity is a class from e v7 appcompat library. This is a compatibility library that back ports some features of recent versions of
// Android to older devices.
    TextView textView;
    Button button;
    //A bound service is the server in a client-server interface
    BoundService boundService;
    boolean serviceBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//Variables, methods, and constructors, which are declared protected in a superclass can be accessed only by the subclasses
        // in other package or any class within the package of the protected members class.
        //void is a Java keyword.  Used at method declaration and definition to specify that the method does not return any type,
        // the method returns void.
        //onCreate Called when the activity is first created. This is where you should do all of your normal static set up: create views,
        // bind data to lists, etc. This method also provides you with a Bundle containing the activity's previously frozen state,
        // if there was one.Always followed by onStart().
        //Bundle is most often used for passing data through various Activities.
// This callback is called only when there is a saved instance previously saved using onSaveInstanceState().
// We restore some state in onCreate() while we can optionally restore other state here, possibly usable after onStart() has
// completed.The savedInstanceState Bundle is same as the one used in onCreate().
        // call the super class onCreate to complete the creation of activity like the view hierarchy
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //R means Resource
        //layout means design
        //  main is the xml you have created under res->layout->main.xml
        //  Whenever you want to change your current Look of an Activity or when you move from one Activity to another .
        // The other Activity must have a design to show . So we call this method in onCreate and this is the second statement to set
        // the design
        ///findViewById:A user interface element that displays text to the user.

        // UI component
        textView=(TextView)findViewById(R.id.textView);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
            //Parameters
            //View.OnClickListener: The callback that will run.This value may be null.
            @Override
            public void onClick(View view) {
               // Called when a view has been clicked.
                textView.setText(boundService.getTime());
                //setting the text and bound service will help us to get the time
            }
        });
    }

    @Override
    protected void onStart() {
        //this will start the activity
        super.onStart();
        //creating object of intent
        // Bind to LocalService
        Intent intent = new Intent(this, BoundService.class);
        //An Intent provides a facility for performing late runtime binding between the code in different applications.
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
        //Connect to an application service, creating it if needed
        /**Parameters
         service	Intent: Identifies the service to connect to. The Intent must specify an explicit component name.
         conn	ServiceConnection: Receives information as the service is started and stopped. This must be a valid ServiceConnection object; it must not be null.
         flags	int: Operation options for the binding. May be 0, BIND_AUTO_CREATE, BIND_DEBUG_UNBIND, BIND_NOT_FOREGROUND, BIND_ABOVE_CLIENT, BIND_ALLOW_OOM_MANAGEMENT, or BIND_WAIVE_PRIORITY.
         Returns
         boolean	If you have successfully bound to the service, true is returned; false is returned if the connection is not made so you will not receive the service object. However, you should still call unbindService(ServiceConnection) to release the connection.
**/

         }
    //creating object of serviceConnection
    ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceDisconnected(ComponentName name) {
            /**Called when a connection to the Service has been lost. This typically happens when the process hosting the service has crashed or been killed. This does not remove the ServiceConnection itself -- this binding to the service will remain active, and you will receive a call to onServiceConnected(ComponentName, IBinder) when the Service is next running.

             Parameters
             name	ComponentName: The concrete component name of the service whose connection has been lost. **/
             //Make a standard toast that just contains a text view with the text from a resource.

             //Parameters
             // context	Context: The context to use. Usually your Application or Activity object.
             //resId	int: The resource id of the string resource to use. Can be formatted text.
             //duration	int: How long to display the message. Either LENGTH_SHORT or LENGTH_LONG


             Toast.makeText(MainActivity.this, "Service is disconnected", Toast.LENGTH_SHORT).show();
            serviceBound=false;//servicebound is false

        }
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //creating method onServiceConected
           /** Called when a connection to the Service has been established, with the IBinder of the communication channel to the Service.

                    Parameters
            name	ComponentName: The concrete component name of the service that has been connected.
                    service	IBinder: The IBinder of the Service's communication channel, which you can now make calls on.**/

            Toast.makeText(MainActivity.this, "Service is connected", Toast.LENGTH_SHORT).show();
            serviceBound = true;
            // bounding  the LocalService,
            //The LocalBinder provides the getService() method for clients to retrieve the current instance of LocalService. This allows clients to call public methods in the service.
            BoundService.LocalBinder localBinder = (BoundService.LocalBinder) iBinder;
            //getting localService Instance
            boundService = localBinder.getService();
        }
    };


    protected void onStop(){
        //it will stop the activity
        MainActivity.super.onStop();
        // Unbind from the service
        if (serviceBound)
        {
            //it will cut the  connection from service
            unbindService(serviceConnection);
            serviceBound= false;

        }
    }
}