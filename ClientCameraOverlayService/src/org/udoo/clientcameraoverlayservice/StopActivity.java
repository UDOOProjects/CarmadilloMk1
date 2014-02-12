package org.udoo.clientcameraoverlayservice;

import android.app.Activity;
import android.os.Bundle;

public class StopActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		JoystickService.stop();
			
		finish();
		
	}

}
