package gs.or.venator.lifecyclehelper;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

public class LifecycleApplication extends Application {

	private static LifecycleApplication application;

	private static int activityCount;
	private static boolean changingActivity;

	@Override
	public void onCreate() {
		super.onCreate();
		LifecycleApplication.application = this;
	}

	public static LifecycleApplication get() {
		return application;
	}

	public static void activityResumed(LifecycleActivity activity) {
		if (activityCount == 0 && !changingActivity) {
			application.onAppForeground(activity);
		}
		activityCount++;
		changingActivity = false;
	}

	public static void activityPaused() {
		activityCount--;
		if (activityCount == 0 && !changingActivity) {
			application.onAppBackground();
		}
	}

	protected void onAppBackground() {
	}

	protected void onAppForeground(LifecycleActivity activity) {
	}

	public static final void startActivity(Activity source, Intent intent) {
		changingActivity = true;
		source.startActivityForResult(intent, LifecycleActivity.FINISH_ACTIVITY_REQUEST);
	}

	public static final void closeApp(LifecycleActivity activity) {
		closeApp(activity, null);
	}

	public static final void closeApp(LifecycleActivity activity, Intent data) {
		if (data == null) {
			data = new Intent();
		}
		data.putExtra(LifecycleActivity.LIFECYCLE_COMMAND, LifecycleActivity.LIFECYCLE_COMMAND_CLOSE_APP);
		activity.onCloseApp(data);
		activity.onFinishRequested(data);
		activity.setResult(LifecycleActivity.FINISH_ACTIVITY_RESULT, data);
		activity.finish();
	}

	public static final void restartApp(LifecycleActivity activity) {
		LifecycleApplication.restartApp(activity, null);
	}

	public static final void restartApp(LifecycleActivity activity, Intent data) {
		if (data == null) {
			data = new Intent();
		}
		data.putExtra(LifecycleActivity.LIFECYCLE_COMMAND, LifecycleActivity.LIFECYCLE_COMMAND_RESTART_APP);
		activity.onRestartApp(data);
		activity.onFinishRequested(data);
		activity.setResult(LifecycleActivity.FINISH_ACTIVITY_RESULT, data);
		activity.finish();
	}

}
