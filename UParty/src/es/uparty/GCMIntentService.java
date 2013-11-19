/*
 * Copyright 2012 Indigo Rose Software Design Corporation
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.uparty;

import static es.uparty.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static es.uparty.CommonUtilities.EXTRA_MESSAGE;
import static es.uparty.CommonUtilities.GOOGLE_PROJECT_NUMBER;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
//import com.airbop.gcm.demo.R;


/**
 * IntentService responsible for handling GCM messages.\
 * Docs: http://developer.android.com/guide/google/gcm/client-javadoc/com/google/android/gcm/GCMBaseIntentService.html
 */
public class GCMIntentService extends GCMBaseIntentService {

    //@SuppressWarnings("hiding")
    private static final String TAG = "GCMIntentService";
        
    public GCMIntentService(boolean withLocation) {
        super(GOOGLE_PROJECT_NUMBER);
    }
    
    public GCMIntentService() {
        super(GOOGLE_PROJECT_NUMBER);
    }

    /**
	 * Called after the app has been registered on the GCM service
	 * We now have the regID that we can use to register with the
	 * AirBop servers.
	 */
    @Override
    protected void onRegistered(Context context, String registrationId) {
    	
        Log.i(TAG, "Device registered: regId = " + registrationId);
        //Get our data for the server
        AirBopServerUtilities server_data = AirBopServerUtilities.fillDefaults(registrationId);
        //server_data.loadCurrentLocation(context);
        server_data.loadDataFromPrefs(context);
        // Get rid of the location from the prefs so we requery next time
        AirBopServerUtilities.clearLocationPrefs(context);
        AirBopServerUtilities.register(getApplicationContext()
    			, server_data);
    }

    /**
	 * Called after the device has been unregisterd from the GCM server.
	 * We we are registered on the AirBop servers we should unregister
	 * from there as well.
	 */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
    	
        Log.i(TAG, "Device unregistered");
        //If we are still registered with AirBop it is time to unregister
        if (GCMRegistrar.isRegisteredOnServer(context)) {
        	AirBopServerUtilities.unregister(context, registrationId);
        } else {
            // This callback results from the call to unregister made on
            // ServerUtilities when the registration to the server failed.
        	Log.i(TAG, "Ignoring unregister callback");
        	
        }
    }
    /**
	 * We have received a push notification from GCM, analyze
	 * the intents bundle for the payload.
	 */
    @Override
    protected void onMessage(Context context, Intent intent) {
    	
        Log.i(TAG, "Received message");
        String message = null;
        String title = null;
        String url = null;
        
        if (intent != null) {
        	
        	//Check the bundle for the pay load body and title
	        Bundle bundle = intent.getExtras();
	 	   	if (bundle != null) {
//	 	   		displayMessage(context, "Message bundle: " +  bundle);
	 	   		
	 	   		Log.i(TAG, "Message bundle: " +  bundle);
	 	   		message = bundle.getString("elements");
	 	   		message = "{\"elements\":"+message+"}";
	 	   			 	   		
	 	   		title = bundle.getString("title");
	 	   		
	 	   		url = bundle.getString("url");
		 	   	Intent i = new Intent(DISPLAY_MESSAGE_ACTION);
		        i.putExtra(EXTRA_MESSAGE, message);
		        context.sendBroadcast(i);
	 	   	} 
        }
    }

	@Override
    protected void onDeletedMessages(Context context, int total) {
		/*
		 * Called when the GCM servers tells that app that 
		 * pending messages have been deleted because the
		 * device was idle.
		 */
        Log.i(TAG, "Received deleted messages notification");
        // notifies user
    }

	/**
	 * Called on registration or unregistration error. 
	 * Whatever this error is, it is not recoverable
	 */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
    }

    /**
     * Called on a registration error that could be retried.
	 * By default, it does nothing and returns true, but could be
	 * overridden to change that behavior and/or display the error. 
	 */
    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
    	// log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        return super.onRecoverableError(context, errorId);
    }
}
