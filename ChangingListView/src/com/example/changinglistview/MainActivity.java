package com.example.changinglistview;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private ListView listView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	// ****************init****************
	private void init() {
		findViews();
		initListView();
	}

	private void findViews() {
		listView1 = (ListView) findViewById(R.id.listView1);
	}

	private void initListView() {
		infos = new ArrayList<Integer>();
		for (int i = 0; i < 30; i++) {
			infos.add(i);
		}
		listView1.setAdapter(adapter);
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				infos.remove(position);
				adapter.notifyDataSetChanged();
			}
		});
	}

	private List<Integer> infos;

	private BaseAdapter adapter = new BaseAdapter() {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (null == convertView) {
				convertView = new TextView(MainActivity.this);
			}
			((TextView) convertView).setText("position:" + getItem(position));
			return convertView;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public Integer getItem(int position) {
			// TODO Auto-generated method stub
			return infos.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return infos.size();
		}
	};
}
