package com.bedates.guncelhaberler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class UsingMyWebview extends Activity {
	
	public static String adres;
	WebView mWebView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		// Adds Progrss bar Support
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.main );
		
		// Makes Progress bar Visible
		getWindow().setFeatureInt(	Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON); 

	    // Get Web view
	    mWebView = (WebView) findViewById( R.id.MyWebview ); //This is the id you gave 
	    													 //to the WebView in the main.xml
	    mWebView.getSettings().setJavaScriptEnabled(true);   
	    mWebView.getSettings().setSupportZoom(true);		 //Zoom Control on web (You don't need this 
	    													 //if ROM supports Multi-Touch		
	    mWebView.getSettings().setBuiltInZoomControls(true); //Enable Multitouch if supported by ROM
	   
	    // Load URL
	    mWebView.loadUrl(adres);
	    
	    
	    // Sets the Chrome Client, and defines the onProgressChanged
	    // This makes the Progress bar be updated.
	    final Activity MyActivity = this;
	    mWebView.setWebChromeClient(new WebChromeClient() {
        public void onProgressChanged(WebView view, int progress)   
        {
        	//Make the bar disappear after URL is loaded, and changes string to Loading...
        	MyActivity.setTitle("Yükleniyor...");
        	MyActivity.setProgress(progress * 100); //Make the bar disappear after URL is loaded

        	// Return the app name after finish loading
            if(progress == 100)
            	MyActivity.setTitle(R.string.app_name);
          }
        });
	    
	    
	    
	}//End of Method onCreate

	public static String getAdres() {
		return adres;
	}

	public static void setAdres(String adres) {
		UsingMyWebview.adres = adres;
	}
	
}


