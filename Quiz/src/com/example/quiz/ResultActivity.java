package com.example.quiz;

import java.util.ArrayList;

import listPac.ResultListAdapter;
import listPac.ResultListItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ResultActivity extends Activity {

	ListView resultList ;
	ResultListItem item ;
	ArrayList<ResultListItem> itemList ;
	ResultListAdapter adapter ;

	boolean[] errataArray ;
	int[] randomArray ;
	int[] imageResource ;
	String[] answer ;
	String[] selfAnswer ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result) ;
		/**
		 * 渡された情報をもとにArrayListを作っておく
		 **/
		Intent intent = getIntent() ;
		errataArray = intent.getBooleanArrayExtra("Errata") ; //正誤情報
		randomArray = intent.getIntArrayExtra("RandomArray") ; //問題のランダムインデックス
		imageResource = intent.getIntArrayExtra("Resource") ; //画像のリソース
		answer = intent.getStringArrayExtra("Answer") ; //答えの文字列
		selfAnswer = intent.getStringArrayExtra("SelfAnswer") ; //自分の答え
		
		itemList = new ArrayList<ResultListItem>() ;
		resultList = (ListView)findViewById(R.id.listView1) ;
		resultList.setOnItemClickListener(new ListSelectEvent()) ;
		adapter = new ResultListAdapter(this, itemList) ;
		resultList.setAdapter(adapter) ;
		
		new ListAddThread().start() ;
	}

	@Override
	public void finishActivity(int requestCode) {
		super.finishActivity(requestCode);
	}

	public class ListSelectEvent implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ResultListItem item = (ResultListItem)parent.getItemAtPosition(position) ;
			Intent intent = new Intent(ResultActivity.this, DetailActivity.class) ;
			intent.putExtra("Resource", item.getResource()) ;
			startActivity(intent) ;
		}
		
	}
	
	public class ListAddThread extends Thread{
		Handler handler = new Handler() ;
		@Override
		public void run() {
			super.run();
			for(int i = 0; i < errataArray.length; i++){
				Log.v("正誤", Boolean.toString(errataArray[i])) ;
				Log.v("問題のindex", Integer.toString(randomArray[i])) ;
				Log.v("自分の答え", selfAnswer[i]) ;
				//itemを作るがセットする正解の文字列は問題のランダムインデックスと答えの文字列を使ってセットする
				item = new ResultListItem() ;
				item.setErrata(errataArray[i]) ; //正誤セット
				item.setSelfAnswer(selfAnswer[i]) ; //自分の答えセット
				item.setAnswer(answer[ randomArray[i] ]) ; //答えのセット
				item.setResource(imageResource[ randomArray[i] ]) ;//画像のリソースセット
				itemList.add(item) ;
				adapter.notifyDataSetChanged() ;
			}
		}
	}
}
