package org.grameenfoundation.utils;

import android.app.Application;

public class MyApp extends Application {
	  
	  @Override
	  public void onCreate() {
	    TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Thin.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
	  }
	}