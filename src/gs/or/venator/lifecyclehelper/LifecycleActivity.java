package gs.or.venator.lifecyclehelper;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class LifecycleActivity extends Activity {

	public static final int FINISH_ACTIVITY_REQUEST = 3541;
	public static final int FINISH_ACTIVITY_RESULT = 897;

	public static final String LIFECYCLE_COMMAND = "gs.or.venator.lifecyclehelper.lifecycle_command";
	public static final String LIFECYCLE_COMMAND_CLOSE_APP = "close";
	public static final String LIFECYCLE_COMMAND_RESTART_APP = "restart";

	/**
	 * Use LifecycleApplication.startActivity(Activity source, Intent intent)
	 */
	@Override
	@Deprecated
	public void startActivity(Intent intent) {
		super.startActivity(intent);
	}

	/**
	 * Called on the activity instance that has requested an app restart, with the supplied data
	 * @param data
	 */
	protected void onRestartApp(Intent data) {
	}

	/**
	 * Called on the activity instance that has requested an app close, with the supplied data
	 * @param data
	 */
	protected void onCloseApp(Intent data) {
	}

	/**
	 * Called when this activity instance is terminating either because of an app close or restart request.
	 * This method will be called on each activity in the stack.
	 * @param data
	 */
	protected void onFinishRequested(Intent data) {
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == FINISH_ACTIVITY_REQUEST && resultCode == FINISH_ACTIVITY_RESULT) {
			Log.d("LifecycleActivity", "Requested finish for " + this);
			onFinishRequested(data);
			setResult(FINISH_ACTIVITY_RESULT, data);
			finish();
		}
	}

	public boolean isAppClosing(Intent data) {
		String command = data.getStringExtra(LifecycleActivity.LIFECYCLE_COMMAND);
		return LifecycleActivity.LIFECYCLE_COMMAND_CLOSE_APP.equals(command);
	}

	public boolean isAppRestarting(Intent data) {
		String command = data.getStringExtra(LifecycleActivity.LIFECYCLE_COMMAND);
		return LifecycleActivity.LIFECYCLE_COMMAND_RESTART_APP.equals(command);
	}

	@Override
	protected void onResume() {
		super.onResume();
		LifecycleApplication.activityResumed(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		LifecycleApplication.activityPaused();
	}

}
