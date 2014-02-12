package org.udoo.clientcameraoverlayservice;

import org.udoo.clientcameraoverlayservice.UDPsendcommand.UDPSendCommandThread;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import org.udoo.clientcameraoverlayservice.R;
import com.jawsware.core.share.OverlayService;

public class JoystickService extends OverlayService {

	public static JoystickService instance;

	private JoystickOverlayDXView joystickDXView;
	private JoystickOverlaySXView joystickSXView;
	private CenterCamButtonView centerCamButton;
	
	public static UDPSendCommandThread sendCommandThread;

	@Override
	public void onCreate() {
		super.onCreate();
		
		instance = this;
		
		joystickDXView  = new JoystickOverlayDXView(this);
		joystickSXView  = new JoystickOverlaySXView(this);
		centerCamButton = new CenterCamButtonView(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {		
		
		String ip = (String) intent.getExtras().get("incomingIP");	
		sendCommandThread = new UDPSendCommandThread(ip);
        sendCommandThread.start();
        
        Log.i("Service", "server partito");
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();

		if (joystickDXView != null) {
			joystickDXView.destory();
		}
		if (joystickSXView != null) {
			joystickSXView.destory();
		}
		if (centerCamButton != null) {
			centerCamButton.destory();
		}
		
		sendCommandThread.stopRunning();
		

	}
	
	static public void stop() {
		if (instance != null) {
			instance.stopSelf();
		}
	}
	
	@Override
	protected Notification foregroundNotification(int notificationId) {
		Notification notification;

		notification = new Notification(R.drawable.ic_launcher, getString(R.string.app_name), System.currentTimeMillis());

		notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT | Notification.FLAG_ONLY_ALERT_ONCE;

		notification.setLatestEventInfo(this, getString(R.string.app_name), getString(R.string.message_notification), notificationIntent());

		return notification;
	}


	private PendingIntent notificationIntent() {
		Intent intent = new Intent(this, StopActivity.class);

		PendingIntent pending = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		return pending;
	}

}
