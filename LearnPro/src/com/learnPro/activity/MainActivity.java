package com.learnPro.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.example.learnpro.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button button = (Button) findViewById(R.id.bt);
		text = (TextView) findViewById(R.id.textView);
		Button otherButton=(Button)findViewById(R.id.otherButton);
		Button button1=(Button)findViewById(R.id.button1);
		
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this, TestFragmentActivity.class);
				startActivity(intent);
			}
		});

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PostFun pf = new PostFun();
				try {
					pf.execute(new URL(
							"http://192.168.115.1:8080/FM.Android.Server/PostTest"));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		otherButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle=new Bundle();
				bundle.putString("data","hehe");
				Intent intent=new Intent(MainActivity.this, OtherActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}});
	}

	class PostFun extends AsyncTask<URL, Integer, String> {

		@Override
		protected String doInBackground(URL... params) {
			// TODO Auto-generated method stub
			String result = "fail";
			HttpClient httpClient = new DefaultHttpClient();
			// HttpGet get = new HttpGet(
			// "http://10.0.1.103:8080/FM.Android.Server/PostTest");
			HttpPost post = new HttpPost(
					"http://10.0.1.103:8080/FM.Android.Server/PostTest");
			List<NameValuePair> param=new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("name","wsy"));
			param.add(new BasicNameValuePair("pw","123"));
			try {
				post.setEntity(new UrlEncodedFormEntity(param, HTTP.UTF_8));
				HttpResponse response = httpClient.execute(post);
				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = response.getEntity();
					InputStream inputStream = httpEntity.getContent();
					BufferedReader bf = new BufferedReader(
							new InputStreamReader(inputStream));
					StringBuffer str = new StringBuffer();
					str.append(bf.readLine());
					String cont = bf.readLine();
					while (cont != null) {
						str.append(cont);
						cont = bf.readLine();
					}
					result = str.toString();
//					try {
//						JSONObject jsonObject = new JSONObject(result);
//						String score1 = (String) jsonObject.get("chinese");
//						String socre2 = (String) jsonObject.get("math");
//						下面的這兩步實際上等於什麼事都沒做
//						JSONArray jsonArray=new JSONArray(result);
//						result = jsonArray.toString();
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			text.setText(result);
		}

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
}