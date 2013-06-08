package demo;

import gs.or.venator.lifecyclehelper.LifecycleActivity;
import gs.or.venator.lifecyclehelper.LifecycleApplication;
import android.widget.Toast;

public class MyApp extends LifecycleApplication {

	@Override
	protected void onAppBackground() {
		Toast.makeText(this, "onAppBackground", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onAppForeground(LifecycleActivity activity) {
		Toast.makeText(this, "onAppForeground " + activity, Toast.LENGTH_SHORT).show();
	}

}
