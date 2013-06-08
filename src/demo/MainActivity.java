package demo;

import gs.or.venator.lifecyclehelper.LifecycleActivity;
import gs.or.venator.lifecyclehelper.LifecycleApplication;
import gs.or.venator.lifecyclehelper.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends LifecycleActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity);

		TextView txt_activityname = (TextView) findViewById(R.id.txt_activityname);
		txt_activityname.setText(toString());

		TextView txt_extra = (TextView) findViewById(R.id.txt_extra);
		txt_extra.setText(getIntent().getStringExtra("foo"));

		findViewById(R.id.btn_next).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ChildActivity.class);
				LifecycleApplication.startActivity(MainActivity.this, intent);
			}
		});

		findViewById(R.id.btn_close).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LifecycleApplication.closeApp(MainActivity.this);
			}
		});

		findViewById(R.id.btn_restart).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent data = new Intent();
				data.putExtra("foo", "bar");
				LifecycleApplication.restartApp(MainActivity.this, data);
			}
		});
	}

	@Override
	protected void onRestartApp(Intent data) {
		super.onRestartApp(data);
		Log.e(getClass().getSimpleName(), "Activity " + this + " requested restart app");
	}

	@Override
	protected void onFinishRequested(Intent data) {
		if (isAppRestarting(data)) {
			Toast.makeText(this, "Restarting app, foo = " + data.getStringExtra("foo"), Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("foo", data.getStringExtra("foo"));
			LifecycleApplication.startActivity(this, intent);
		}
	}
}
