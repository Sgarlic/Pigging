package com.boding.app;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.model.BoardingPeople;
import com.boding.util.Util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseBoardingPeopleActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private LinearLayout addPeopleLinearLayout;
	private ListView boardingPeopleListView;
	
	
	private BoardingPeopleAdapter peopleAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_boardingpeople);
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
        
		initView();
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(ChooseBoardingPeopleActivity.this, IntentRequestCode.START_CHOOSE_BOARDINGPEOPLE);
			}
			
		});
		
		completeLinearLayout = (LinearLayout) findViewById(R.id.choose_boardingpeople_complete_linearLayout);
		addPeopleLinearLayout = (LinearLayout) findViewById(R.id.choose_boardingpeople_addBoardingPeople_linearLayout);
		boardingPeopleListView = (ListView) findViewById(R.id.choose_boardingpeople_boardingpeole_listView);
		
		List<BoardingPeople> peopleList = new ArrayList<BoardingPeople>();
		peopleList.add(new BoardingPeople(true, "李大嘴", "325478569852314569"));
		peopleList.add(new BoardingPeople(true, "李大232嘴", "256542d14589631452"));
		peopleList.add(new BoardingPeople(true, "李大嘴wew", "1225478965325468774"));
		peopleAdapter = new BoardingPeopleAdapter(this, peopleList);
		boardingPeopleListView.setAdapter(peopleAdapter);
	}
	
	private class BoardingPeopleAdapter extends BaseAdapter {
		private List<BoardingPeople> peopleList;
		private Context context;
		public BoardingPeopleAdapter(Context context, List<BoardingPeople> peopleList) {
			this.context = context;
			this.peopleList = peopleList;
		}
		@Override
		public int getCount() {
			return peopleList.size();
		}

		@Override
		public BoardingPeople getItem(int position) {
			return peopleList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public void addNewPeople(BoardingPeople people){
			peopleList.add(people);
			notifyDataSetChanged();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {  
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_chooseboardingpeople, null);
	            holder = new ViewHolder();  
	            
	            holder.choosePeopleLinearLayout = (LinearLayout) convertView.findViewById(R.id.chooseboardingpeople_choose_linearLayout);
	            holder.choosePeopleCheckBox = (CheckBox) convertView.findViewById(R.id.chooseboardingpeople_choose_checkBox);
	            holder.nameTextView = (TextView) convertView.findViewById(R.id.chooseboardingpeople_name_textView);
	            holder.idNumberTextView = (TextView) convertView.findViewById(R.id.chooseboardingpeople_idnumber_textView);
	            holder.idTypeTextView = (TextView) convertView.findViewById(R.id.chooseboardingpeople_idType_textView);
	            holder.editLinearLayout = (LinearLayout) convertView.findViewById(R.id.chooseboardingpeople_edit_linearLayout);
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
			BoardingPeople people = getItem(position);
            holder.nameTextView.setText(people.getName());
            holder.idNumberTextView.setText(people.getCardNumber());
            holder.choosePeopleLinearLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (holder.choosePeopleCheckBox.isChecked())
						holder.choosePeopleCheckBox.setChecked(false);
					else
						holder.choosePeopleCheckBox.setChecked(true);
					
				}
			});
	        return convertView;  
		}
		
		private class ViewHolder {
			LinearLayout choosePeopleLinearLayout;
			CheckBox choosePeopleCheckBox;
			TextView nameTextView;
			TextView idNumberTextView;
			TextView idTypeTextView;
			LinearLayout editLinearLayout;
		}
	}
}
