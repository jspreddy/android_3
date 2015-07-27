/*********************************
 * Assignment #3
 * FileName: ResultActivity.java
 *********************************
 * Team Members:
 * Richa Kandlikar
 * Sai Phaninder Reddy Jonnala
 * *******************************
 */
package com.example.hw3;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		TextView t1 = (TextView) findViewById(R.id.textView1);
		TextView t2 = (TextView) findViewById(R.id.textView2);
		ImageView i = (ImageView) findViewById(R.id.imageView1);

		if (getIntent().getExtras() != null) {
			float t = getIntent().getExtras().getFloat("Time");
			if (t < 11.0f) {
				t2.setText("Congratulations!!!");
				i.setImageResource(R.drawable.win);
			} else {
				t2.setText("What took you so long???");
				i.setImageResource(R.drawable.loose);
			}
			t1.setText("It took you " + t + "s to finish the game.");

		}

		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				Intent i = new Intent(ResultActivity.this,MainActivity.class);
				startActivity(i);
			}
		});
	}
}
