package com.learnPro.activity;

import com.example.learnpro.R;
import com.learnPro.fragment.Callbacks;
import com.learnPro.fragment.TestFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class TestFragmentActivity extends ActionBarActivity implements
		Callbacks {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_fragment);
	}
	
		@Override
		public void onItemSelected(Integer id) {
			// TODO Auto-generated method stub
			// 创建一个新的fragment
			Bundle bundle = new Bundle();
			bundle.putInt("data", 1);
			TestFragment newFragment = new TestFragment();
			newFragment.setArguments(bundle);

			android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			
//			fragmentTransaction.add(R.id.book_detail_container, newFragment);
			 fragmentTransaction.replace(R.id.book_detail_container, newFragment);
			fragmentTransaction.commit();
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
}
