package com.learnPro.activity;

import com.example.learnpro.R;
import com.learnPro.fragment.Callbacks;
import com.learnPro.fragment.TestFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TestFragmentActivity extends ActionBarActivity implements
		Callbacks {
	private int count = 0;
	private TextView textView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_fragment);
		Button bu = (Button) findViewById(R.id.bu);
		textView1 = (TextView) findViewById(R.id.textView1);
		// foreFragment = (TestFragment) getSupportFragmentManager()
		// .findFragmentById(R.id.testFragment);
		bu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textView1.setText("times: " + count);
				count++;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// private TestFragment foreFragment;

	@Override
	public void onItemSelected(Integer id) {
		// TODO Auto-generated method stub
		// 创建一个新的fragment
		Bundle bundle = new Bundle();
		bundle.putInt("data", 1);
		TestFragment newFragment = new TestFragment();

		newFragment.setArguments(bundle);
		// foreFragment = (TestFragment) getSupportFragmentManager()
		// .findFragmentById(R.id.testFragment);

		android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		// 没起作用
		// if (null != foreFragment) {
		// fragmentTransaction.remove(foreFragment);
		// fragmentTransaction.add(R.id.LinearLayout1, newFragment);
		// }

		fragmentTransaction.replace(R.id.testFragment, newFragment);
		// fragmentTransaction
		// .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		fragmentTransaction.commit();
	}
}
