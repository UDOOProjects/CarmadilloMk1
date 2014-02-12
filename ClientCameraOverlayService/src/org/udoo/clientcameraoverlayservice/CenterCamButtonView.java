package org.udoo.clientcameraoverlayservice;

import org.udoo.clientcameraoverlayservice.UDPsendcommand.UDPSendCommandThread;

import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.ImageButton;

import org.udoo.clientcameraoverlayservice.R;
import com.jawsware.core.share.OverlayService;
import com.jawsware.core.share.OverlayView;

public class CenterCamButtonView extends OverlayView {
	
	public CenterCamButtonView(OverlayService service) {
		super(service, R.layout.cantercambtoverlay, 1);
	}

	@Override
	public int getLayoutGravity() {
		return Gravity.CENTER + Gravity.LEFT ;
	}
	
	@Override
	protected void onInflateView() {
		ImageButton center = (ImageButton) this.findViewById(R.id.imageButton_centerCam);
	}

	@Override
	protected void onTouchEvent_Up(MotionEvent event) {
		JoystickService.sendCommandThread.setCommand(UDPSendCommandThread.CENTER_CAM_COMMAND);
	}	
	
}
