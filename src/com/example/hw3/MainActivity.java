/*********************************
 * Assignment #3
 * FileName: MainActivity.java
 *********************************
 * Team Members:
 * Richa Kandlikar
 * Sai Phaninder Reddy Jonnala
 * *******************************
 */
package com.example.hw3;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends Activity {

	public GameDataManager gdm;
	public TableLayout tlMain;
	public Context context;
	int HEIGHT=72;
	int WIDTH=72;
	long start=0;
	long stop=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.context=this;
		
		this.tlMain= (TableLayout) findViewById(R.id.tlMain);
		int gridSize=4;
		if (getIntent().getExtras() != null) {
			gridSize=getIntent().getExtras().getInt("gridsize");
		}
		if(gridSize<4){
			gridSize=4;
		}
		createTable(gridSize);
		this.gdm=new GameDataManager(gridSize*gridSize, 4);
		ArrayList<Integer> tmp=this.gdm.get_list();
		
		//set the first random search image
		set_random_search_image();
		this.start=System.nanoTime();
		//map images to grid.
		for(int i=0;i<gridSize*gridSize;i++){
			ImageView iv_temp=(ImageView) findViewById(i);
			int id=tmp.get(i).intValue();
			iv_temp.setImageResource(this.gdm.get_resource(id));
			iv_temp.setTag(""+id);
			iv_temp.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int tag=Integer.parseInt( v.getTag().toString() );
					int search_tag= Integer.parseInt(((ImageView) findViewById(R.id.ivSearch)).getTag().toString());
					if(tag==search_tag){
						gdm.remove(tag);
						v.setOnClickListener(null);
						v.setAlpha(0.3f);
						if(gdm.get_count(tag)==0){
							if(!gdm.is_win_state()){
								set_random_search_image();
							}
							else{
								Log.d("DEBUG","Win State Reached");
								stop=System.nanoTime();
								float diff=(stop-start)/1000000000;
								Log.d("DEBUG","TimeTaken: "+ diff);
								Intent i = new Intent(MainActivity.this, ResultActivity.class);
								i.putExtra("Time", diff);
								startActivity(i);
								finish();
							}
						}
					}
				}
			});
		}
		
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MainActivity.class);
				finish();
				startActivity(i);
			}
			
		});
		
		findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	private void set_random_search_image() {
		ImageView iv_search=(ImageView) findViewById(R.id.ivSearch);
		int search_id=this.gdm.get_random_resource_number();
		iv_search.setImageResource( this.gdm.get_resource(search_id) );
		iv_search.setTag(""+search_id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		if (item.getItemId() == R.id.action_settings) {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("GridSize");
			alert.setMessage("input a number (5 to 9 )");

			// Set an EditText view to get user input
			final EditText input = new EditText(this);
			input.setInputType(InputType.TYPE_CLASS_NUMBER);
			
			alert.setView(input);

			alert.setPositiveButton("Start", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					int value=4;
					try{
						value = Integer.parseInt(input.getText().toString());
					}
					catch(Exception e){
						Log.d("DEBUG", "input invalid, so value set to minimum.");
					}
					if(value > 9){
						value=9;
					}
					if(value<4){
						value=4;
					}
					Intent i = new Intent(MainActivity.this, MainActivity.class);
					i.putExtra("gridsize", value);
					startActivity(i);
					finish();
				}
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// Canceled.
				}
			});

			alert.show();
			// see
			// http://androidsnippets.com/prompt-user-input-with-an-alertdialog
		}
		return true;
	}
	
	private void createTable(int x){
		for (int i=0;i<x;i++){
			TableRow tr=new TableRow(this.tlMain.getContext());
			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			for(int j=0;j<x;j++){
				ImageView iv=new ImageView(this.tlMain.getContext());
				int height=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.HEIGHT, this.context.getResources().getDisplayMetrics());
				int width=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.WIDTH, this.context.getResources().getDisplayMetrics());
				iv.setLayoutParams(new TableRow.LayoutParams(height,width));
				
				iv.setId(i*x + j);
				iv.setImageResource(R.drawable.empty);
				tr.addView(iv);
			}
			this.tlMain.addView(tr);
		}
	}

}
