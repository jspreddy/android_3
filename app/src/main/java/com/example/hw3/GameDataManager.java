/*********************************
 * Assignment #3
 * FileName: GameDataManager.java
 *********************************
 * Team Members:
 * Richa Kandlikar
 * Sai Phaninder Reddy Jonnala
 * *******************************
 */
package com.example.hw3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.util.Log;

public class GameDataManager {
	private int imgResources[] = { 
			R.drawable.icon1, 
			R.drawable.icon2,
			R.drawable.icon3, 
			R.drawable.icon4 
		};
	private int count_array[]={0,0,0,0};
	
	private ArrayList<Integer> gameList;

	/**
	 * instantiates the GameDataManager class
	 * @param count total count of images you need
	 * @param min_each minimum of each resource you need
	 */
	public GameDataManager(int count, int min_each){
		
		if(this.imgResources.length != this.count_array.length ){
			Log.e("DEBUG","GameManager(count,min_each): imgResource.length and count_array.length should be equal.");
			System.exit(1);
		}
		if(count < min_each*this.imgResources.length){
			Log.e("DEBUG","GameManager(count,min_each): count is less than min_each * totalResources.");
			System.exit(1);
		}
		if(count==0){
			Log.e("DEBUG","GameManager(count,min_each): count cant be 0");
			System.exit(1);
		}
		gameList=new ArrayList<Integer>();
		int item_number=0;
		int seq_count=min_each*this.imgResources.length;
		
		for(int i=0;i<seq_count;i++){
			if(item_number==this.imgResources.length){
				item_number=0;
			}
			this.gameList.add(item_number);
			this.count_array[item_number]++;
			item_number++;
		}
		if(count>seq_count){
			Random r=new Random();
			item_number=0;
			for(int i=seq_count;i<count;i++){
				item_number=r.nextInt(this.imgResources.length);
				this.gameList.add( Integer.valueOf(item_number) );
				this.count_array[item_number]++;
			}
		}
		
		Collections.shuffle(this.gameList);
	}
	
	/**
	 * returns the list of images
	 * @return
	 */
	ArrayList<Integer> get_list(){
		return gameList;
	}
	
	/**
	 * removes the image from the list
	 * @param number
	 */
	void remove(int number){
		if(this.count_array[number]!=0){
			this.count_array[number]--;
		}
		else{
			Log.e("DEBUG","count for :"+number+": is already zero.");
			System.exit(1);
		}
	}
	
	/**
	 * gets the count of the image
	 * @param number
	 * @return
	 */
	int get_count(int number){
		return this.count_array[number];
	}
	
	/**
	 * returns the boolean response for is it a win state
	 * @return
	 */
	boolean is_win_state(){
		int i;
		for(i=0;i<this.count_array.length;i++){
			if(this.count_array[i]!=0) return false;
		}
		return true;
	}
	
	/**
	 * returns the resource id of the image i.e R.Drawable.image
	 * @param number
	 * @return 
	 */
	int get_resource(int number){
		return this.imgResources[number];
	}
	
	/**
	 * returns a random number from the available resources that are not yet count 0
	 * @return int
	 */
	int get_random_resource_number(){
		Random r=new Random();
		int rand=r.nextInt(this.imgResources.length);
		while( this.count_array[rand]==0 ){
			rand=r.nextInt(this.imgResources.length);
		}
		return rand;
	}
}
