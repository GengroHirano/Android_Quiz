package com.example.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class DetailActivity extends Activity{

	ImageView detailImage ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail) ;
		detailImage = (ImageView)findViewById(R.id.Detail) ;
		Intent intent = getIntent() ;
		int resource = intent.getIntExtra("Resource", R.drawable.i001) ;
		detailImage.setImageResource(resource) ;
	}
}
