package com.example.learnpro;

import android.app.Activity;
import android.os.Bundle;

public class LocalFileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_file);
	}

	// final private String FILE_NAME = "img.jpb";

	// private void write(String content) {
	// FileOutputStream fos = null;
	// try {
	// fos = openFileOutput(FILE_NAME, MODE_APPEND);
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// PrintStream ps = new PrintStream(fos);
	// ps.println(content);
	// }
}
