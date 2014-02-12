package org.udoo.clientcameraoverlayservice;

import org.udoo.clientcameraoverlayservice.joystick.JoystickMovedListener;
import org.udoo.clientcameraoverlayservice.joystick.JoystickView;

import android.view.Gravity;
import android.widget.TextView;

import org.udoo.clientcameraoverlayservice.R;
import com.jawsware.core.share.OverlayService;
import com.jawsware.core.share.OverlayView;

public class JoystickOverlaySXView extends OverlayView {

	private JoystickView joystickSX;
	private TextView txtXSX, txtYSX;
	
	public JoystickOverlaySXView(OverlayService service) {
		super(service, R.layout.joystickoverlaysx, 1);
	}

	@Override
	public int getLayoutGravity() {
		return Gravity.BOTTOM + Gravity.LEFT ;
	}
	
	@Override
	protected void onInflateView() {
		joystickSX = (JoystickView) this.findViewById(R.id.joystickViewsx);
		txtXSX = (TextView)findViewById(R.id.TextViewXsx);
        txtYSX = (TextView)findViewById(R.id.TextViewYsx);
        
        joystickSX.setOnJostickMovedListener(new JoystickMovedListener() {

        	@Override
            public void OnMoved(int direction, int speed) { //direction = pan; tilt = speed
                    txtXSX.setText(Integer.toString(direction));
                    txtYSX.setText(Integer.toString(speed));
                    
                    JoystickService.sendCommandThread.setDirection((((double)direction+10)/20)*127);
                    JoystickService.sendCommandThread.setSpeed((((double)speed+10)/20)*127);
            }

            @Override
            public void OnReleased() {
                    txtXSX.setText("released");
                    txtYSX.setText("released");
            }
            
            public void OnReturnedToCenter() {
                    txtXSX.setText("stopped");
                    txtYSX.setText("stopped");
            }
        });
	}

	
	
}
