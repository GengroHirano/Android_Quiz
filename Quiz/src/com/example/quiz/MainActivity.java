package com.example.quiz;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button[] button = new Button[4] ;
	TextView current ;
	Random rand ; //乱数
	
	String[] answerArray ;//答えの文字配列
	String[] selfAnswer ; //自分の答え
	TypedArray array ; //何でも配列(イメージの配列をまずコレで取得する)
	
	int max ; //総問題数
	int imageRes[] ; //イメージのリソース配列	
	int answer ; //答え
	int currentQ ; //現在何問目か保存する
	int[] randArray ; //ランダム配列
	int randvalue ; //問題用ランダム変数
	boolean[] errata ; //正誤情報
	
	@SuppressLint("Recycle")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		max = Integer.parseInt(getString(R.string.max_q)) ; //文字列リソースから問題数を持ってくる
		array = getResources().obtainTypedArray(R.array.imageValues) ; //配列にdrableセット
		imageRes = new int[array.length()] ;
		for(int i = 0; i < imageRes.length; i++){
			imageRes[i] = array.getResourceId(i, 0) ;
		}
		answerArray = getResources().getStringArray(R.array.AnserString) ;

		button[0] = (Button)findViewById(R.id.q1) ;
		button[1] = (Button)findViewById(R.id.q2) ;
		button[2] = (Button)findViewById(R.id.q3) ;
		button[3] = (Button)findViewById(R.id.q4) ;
		current = (TextView)findViewById(R.id.CurrentQ) ;
		
		reset() ;

	}

	public void reset(){
		randArray = new int[max] ;
		selfAnswer = new String[max] ;
		errata = new boolean[max] ;
		int i = 0 ;
		while( i < max - 1 ){
			rand = new Random() ;
			randvalue = rand.nextInt(imageRes.length) ;
			if ( checkOverlap(randArray, randvalue) ) { //重複チェック
				randArray[i] = randvalue ;
				Log.i("重複しないランダム", Integer.toString(randArray[i])) ;
				i++ ;
			}
		}
		
		currentQ = 1 ;
		current.setText( Integer.toString(currentQ) ) ;
		setQ(currentQ) ;
	}
	
	public void setQ(int current){
		if(current > max){
			Log.v("問題", "終わってる") ;
			AlertDialog.Builder alert = new AlertDialog.Builder(this) ;
			alert.setTitle("別画面に遷移する予定") ;
			alert.setPositiveButton("結果へ", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//ここら辺で次の画面を作るために必要なResultListItemに必要なリストを作っておく
					Intent intent = new Intent(MainActivity.this, ResultActivity.class) ;
					//ここら辺に正誤情報、出題問題の配列、リソースの配列、答えの文字配列を渡す
					intent.putExtra("Errata", errata) ;
					intent.putExtra("RandomArray", randArray) ; //出題問題の配列
					intent.putExtra("Resource", imageRes) ; //リソース
					intent.putExtra("Answer", answerArray) ; //答えの文字列
					intent.putExtra("SelfAnswer", selfAnswer) ; //自分の答え
					startActivityForResult(intent, 123) ;
				}
			}) ;
			alert.show() ;
			return ;
		}
		
		ImageView image = (ImageView)findViewById(R.id.Quiz) ;
		answer = randArray[current - 1] ; //答え保存
		image.setImageResource(imageRes[answer]) ;
		
		//ボタンの文字重複を避けるために値を保存しておく配列
		String[] anserOverlap = new String[button.length] ;
		//------答えを４つあるボタンのどこかに配置------//
		randvalue = rand.nextInt(button.length) ;
		button[randvalue].setText( answerArray[answer] );
		int anserposition = randvalue ; //答えの位置を記録
		anserOverlap[anserposition] = answerArray[answer] ; //重複防止用配列に答えをセット
		//---------------------------------------//
		
		//-------答え以外のボタンに文字をセット-------//
		int i = 0 ;
		while( i < button.length ){
			rand = new Random() ;
			randvalue = rand.nextInt(imageRes.length) ;
			if( anserposition == i ){ //答えの位置とかぶってたら
				i++ ;
				continue ;
			}
			if( checkOverlap(anserOverlap, answerArray[randvalue]) ){ //重複チェック
				button[i].setText( answerArray[randvalue] );
				anserOverlap[i] = button[i].getText().toString() ; //セットした問題を格納
				i++ ;
			}
		}
		//-------------------------------------//
	}
	//重複検知:文字列(ボタンの文字列が重複してたらfalseを返す)
	public boolean checkOverlap(String[] overLaps, String anser){
		for(int i = 0; i < overLaps.length; i++){
			if( anser.equals(overLaps[i]) ){
				return false ;
			}
		}
		return true ;
	}
	//重複検知:整数(出題する問題が重複してたらfalseを返す)
	public boolean checkOverlap(int[] randArray, int rand){
		for(int i = 0; i < randArray.length; i++){
			if( rand == randArray[i]){
				return false ;
			}
		}
		return true ;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void buttonClick (View v){
		Button button = (Button)v ;
		String push = button.getText().toString() ;
		selfAnswer[currentQ - 1] = push ;
		if(push.equals(answerArray[answer])){
			Toast.makeText(this, "正解！", Toast.LENGTH_SHORT).show() ;
			errata[currentQ - 1] = true ;
		}
		else{
			Toast.makeText(this, "不正解だアホめ！", Toast.LENGTH_SHORT).show() ;
			errata[currentQ - 1] = false ;
		}
		currentQ++;
		if(currentQ <= max){
			current.setText( Integer.toString(currentQ) ) ;
		}
		setQ(currentQ) ;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//初期化
		currentQ = 0 ;
		randArray = null ;
		errata = null ;
		selfAnswer = null ;
		
		reset() ;
	}
}
