package org.udoo.clientcameraoverlayservice;

import org.udoo.clientcameraoverlayservice.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private Button connectButton;
	private EditText ip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		connectButton = (Button) findViewById(R.id.connectButton);
		ip = (EditText) findViewById(R.id.IPEditText);
		ip.setText("192.168.0.111");

		connectButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent joyIntent = new Intent(MainActivity.this, JoystickService.class);
				joyIntent.putExtra("incomingIP", ip.getText().toString());
				MainActivity.this.startService(joyIntent);
								
				finish();
			}
		});
		
	}

}
