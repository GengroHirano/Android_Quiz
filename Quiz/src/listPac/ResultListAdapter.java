package listPac;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quiz.R;

public class ResultListAdapter extends ArrayAdapter<ResultListItem> {

	private LayoutInflater inflater ;
	
	public ResultListAdapter(Context context, List<ResultListItem> obj) {
		super(context, 0, obj);
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view ;
		ResultListItem item = getItem(position) ;
		if(convertView != null){
			view = convertView ;
		}
		else {
			view = inflater.inflate(R.layout.list_item, null) ;
			Log.v("はじめてーの", "作成") ;
		}
		String number = (position + 1) + "   :" ;
		if( ++position < 10 ){
			number = "  " + number ;
		}
		
		TextView text = (TextView)view.findViewById(R.id.number) ;
		text.setText(number) ;
		ImageView image = (ImageView)view.findViewById(R.id.anserView) ;
		image.setImageResource(item.getResource()) ;
		text = (TextView)view.findViewById(R.id.selfAnser) ;
		text.setText(item.getSelfAnswer()) ;
		text = (TextView)view.findViewById(R.id.anser) ;
		if( item.getErrata() ){
			text.setText("○") ;
			text.setTextColor(0xffff0000) ;
		}
		else{
			text.setText(item.getAnswer()) ;
			text.setTextColor(0xff000000) ;
		}
		
		return view ;
	}

}
