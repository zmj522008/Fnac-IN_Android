package com.fnacin.service;
//import static com.fnac.service.CommonUtilities.SERVER_URL;
import static com.fnacin.helper.UrlHelper.REGISTRE;
import static com.fnacin.service.CommonUtilities.TAG;
//import static com.fnac.service.CommonUtilities.displayMessage;


import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import android.content.Context;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

public final class ServerUtilities {
	 private static final int MAX_ATTEMPTS = 5;
	    private static final int BACKOFF_MILLI_SECONDS = 2000;
	    private static final Random random = new Random();
	 
	    /**
	     * Register this account/device pair within the server.
	     *
	     */
	    static public void register(final Context context, String sessionid, final String regid) {
// System.out.println("registering device (regId = " + regid + ")");
	        String serverUrl = REGISTRE;
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("reg_id", regid);
	        params.put("session_id", sessionid);
	         
	        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
	        // Once GCM returns a registration id, we need to register on our server
	        // As the server might be down, we will retry it a couple
	        // times.
	        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
//System.out.println("Attempt #" + i + " to register");
	            try {
// displayMessage(context, "Trying to register device on Demo Server");
	                post(serverUrl, params);
	                GCMRegistrar.setRegisteredOnServer(context, true);
	                //String message = "From Demo Server: successfully added device!";
//CommonUtilities.displayMessage(context, message);
	                return;
	            } catch (IOException e) {
	                // Here we are simplifying and retrying on any error; in a real
	                // application, it should retry only on unrecoverable errors
	                // (like HTTP error code 503).
//System.out.println("Failed to register on attempt " + i + ":" + e);
	                if (i == MAX_ATTEMPTS) {
	                    break;
	                }
	                try {
//System.out.println("Sleeping for " + backoff + " ms before retry");
	                    Thread.sleep(backoff);
	                } catch (InterruptedException e1) {
	                    // Activity finished before we complete - exit.
//System.out.println("Thread interrupted: abort remaining retries!");
	                    Thread.currentThread().interrupt();
	                    return;
	                }
	                // increase backoff exponentially
	                backoff *= 2;
	            }
	        }
	       // String message = "Could not register device on Demo Server after attempts.";
//CommonUtilities.displayMessage(context, message);
	    }
	 
	    /**
	     * Unregister this account/device pair within the server.
	     */
	  /*  static void unregister(final Context context, final String regId) {
	        Log.i(TAG, "unregistering device (regId = " + regId + ")");
	        String serverUrl = SERVER_URL + "/unregister";
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("regId", regId);
	        try {
	            post(serverUrl, params);
	            GCMRegistrar.setRegisteredOnServer(context, false);
	            String message = "From Demo Server: successfully removed device!";
//CommonUtilities.displayMessage(context, message);
	        } catch (IOException e) {
	            // At this point the device is unregistered from GCM, but still
	            // registered in the server.
	            // We could try to unregister again, but it is not necessary:
	            // if the server tries to send a message to the device, it will get
	            // a "NotRegistered" error message and should unregister the device.
	            String message = "Could not unregister device on Demo Server.";
//CommonUtilities.displayMessage(context, message);
	        }
	    }*/
	 
	    /**
	     * Issue a POST request to the server.
	     *
	     * @param endpoint POST address.
	     * @param params request parameters.
	     *
	     * @throws IOException propagated from POST.
	     */
	    private static void post(String endpoint, Map<String, String> params)
	            throws IOException {    
	         
	        URL url;
	        try {
	            url = new URL(endpoint);
System.out.println("URL----:"+url);
	        } catch (MalformedURLException e) {
	            throw new IllegalArgumentException("invalid url: " + endpoint);

	        }
	        StringBuilder bodyBuilder = new StringBuilder();
	        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
	        // constructs the POST body using the parameters
	        while (iterator.hasNext()) {
	            Entry<String, String> param = iterator.next();
	            bodyBuilder.append(param.getKey()).append('=')
	                    .append(param.getValue());
	            if (iterator.hasNext()) {
	                bodyBuilder.append('&');
	            }
	        }
	        String body = bodyBuilder.toString();
	        Log.v(TAG, "Posting '" + body + "' to " + url);
System.out.println("Posting body--:"+body+"--to--:"+url);
	        byte[] bytes = body.getBytes();
	        HttpURLConnection conn = null;
	        try {
	            Log.e("URL", "> " + url);
	            conn = (HttpURLConnection) url.openConnection();
	            conn.setDoOutput(true);
	            conn.setUseCaches(false);
	            conn.setFixedLengthStreamingMode(bytes.length);
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type",
	                    "application/x-www-form-urlencoded;charset=UTF-8");
	            // post the request
	            OutputStream out = conn.getOutputStream();
	            out.write(bytes);
	            out.close();
	            // handle the response
	            int status = conn.getResponseCode();
	            if (status != 200) {
	              throw new IOException("Post failed with error code " + status);
	            }
	        } finally {
	            if (conn != null) {
	                conn.disconnect();
	            }
	        }
	      }
}
