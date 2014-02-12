package org.udoo.clientcameraoverlayservice;

import org.udoo.clientcameraoverlayservice.joystick.JoystickMovedListener;
import org.udoo.clientcameraoverlayservice.joystick.JoystickView;

import android.view.Gravity;
import android.widget.TextView;

import org.udoo.clientcameraoverlayservice.R;
import com.jawsware.core.share.OverlayService;
import com.jawsware.core.share.OverlayView;

public class JoystickOverlayDXView extends OverlayView {

	private JoystickView joystickDX;
	private TextView txtXDX, txtYDX;
	
	public JoystickOverlayDXView(OverlayService service) {
		super(service, R.layout.joystickoverlaydx, 1);
	}

	@Override
	public int getLayoutGravity() {
		return Gravity.BOTTOM + Gravity.RIGHT ;
	}
	
	@Override
	protected void onInflateView() {
		
        txtXDX = (TextView)findViewById(R.id.TextViewXdx);
        txtYDX = (TextView)findViewById(R.id.TextViewYdx);
		joystickDX = (JoystickView) this.findViewById(R.id.joystickViewdx);
		joystickDX.setOnJostickMovedListener(new JoystickMovedListener() {

        	@Override
            public void OnMoved(int pan, int tilt) { //pan = orizzontale; tilt = verticale
                    txtXDX.setText(Integer.toString(pan));
                    txtYDX.setText(Integer.toString(tilt));
                    
                    JoystickService.sendCommandThread.setPan((((double)pan+10)/20)*127);
                    JoystickService.sendCommandThread.setTilt((((double)tilt+10)/20)*127);
            }

            @Override
            public void OnReleased() {
                    txtXDX.setText("released");
                    txtYDX.setText("released");
            }
            
            public void OnReturnedToCenter() {
                    txtXDX.setText("stopped");
                    txtYDX.setText("stopped");
            }
        });
	}

	
	
}

