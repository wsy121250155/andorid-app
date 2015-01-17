package com.learnPro.fragment;

import com.example.learnpro.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//添加fragment的方法：直接在xml文件中添加该类
public class TestFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private int count = 0;
	private TextView textView1;
	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle saveInstanceState) {
		if( null == rootView){
			rootView = inflater.inflate(R.layout.fragment_test, container,
					false);
		}
		 

		// attention:fragment 中获得组件，要靠rootView获取
		Button bu = (Button) rootView.findViewById(R.id.bu);
		textView1 = (TextView) rootView.findViewById(R.id.textView1);
		bu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textView1.setText("count: " + count);
				count++;
			}
		});
		return rootView;
	}
}
