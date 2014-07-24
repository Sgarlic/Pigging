package com.boding.app;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.FlightDynamicQuery;
import com.boding.model.FlightDynamics;
import com.boding.task.FlightDynamicsTask;
import com.boding.util.Util;
import com.boding.view.dialog.NetworkUnavaiableDialog;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.WarningDialog;

public class FlightDynamicsListActivity extends BodingBaseActivity{
	private ListView flightDynamicsListView;
	private FlightDynamicsAdapter adapter;
	
	private boolean isFollowsList;
	private FlightDynamicQuery fdq;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flightdynamicslist);
		Bundle arguments = getIntent().getExtras();
    	isFollowsList = arguments.getBoolean(IntentExtraAttribute.IS_FOLLOWEDLIST);
    	if(!isFollowsList){
    		fdq = arguments.getParcelable(IntentExtraAttribute.FLIGHT_DYNAMIC_QUERY);
    	}
		warningDialog = new WarningDialog(this);
		progressBarDialog = new ProgressBarDialog(this);
		networkUnavaiableDialog = new NetworkUnavaiableDialog (this);
		initView();
		setViewContent();
	}
	
	public void setMyFollowedFlightDynamicsList(List<FlightDynamics> fdList){
		GlobalVariables.myFollowedFdList = fdList;
		if(isFollowsList){
			adapter = new FlightDynamicsAdapter(this, fdList);
			flightDynamicsListView.setAdapter(adapter);
		}
		progressBarDialog.dismiss();
	}
	
	public void setSearchedFlightDynamicsList(List<FlightDynamics> fdList){
		adapter = new FlightDynamicsAdapter(this, fdList);
		flightDynamicsListView.setAdapter(adapter);
		progressBarDialog.dismiss();
	}
	
	private void setViewContent(){
		if(!Util.isNetworkAvailable(FlightDynamicsListActivity.this)){
			networkUnavaiableDialog.show();
			return;
		}
		progressBarDialog.show();
		if(GlobalVariables.bodingUser != null){
			(new FlightDynamicsTask(this, HTTPAction.GET_MYFOLLOWED)).execute();
		}
		if(!isFollowsList){
			(new FlightDynamicsTask(this, HTTPAction.SEARCH_FLIGHTDYNAMICS)).execute(
				fdq.getFromCityCode(), fdq.getToCityCode(), fdq.getFlightNum(),fdq.getDate());
		}
	}
	
    private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(FlightDynamicsListActivity.this, IntentRequestCode.FLIGHTDYNAMICS_LIST);
			}
			
		});
		
		flightDynamicsListView = (ListView) findViewById(R.id.myfollowedflightdynamics_listView);
		flightDynamicsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				FlightDynamics dyn = adapter.getItem(position);
				if(!isFollowsList){
					int index = GlobalVariables.myFollowedFdList.indexOf(dyn);
					if(index != -1){
						dyn = GlobalVariables.myFollowedFdList.get(index);
					}
				}
				Intent intent = new Intent();
				intent.setClass(FlightDynamicsListActivity.this, FlightBoardActivity.class);
				intent.putExtra(IntentExtraAttribute.FLIGHT_DYNAMIC, dyn);
				startActivityForResult(intent, IntentRequestCode.FLIGHT_BOARD.getRequestCode());
			}
		});
	}
    
    public class FlightDynamicsAdapter extends BaseAdapter{
    	private List<FlightDynamics> flightDynamicsList;
        private Context context;
        
    	public FlightDynamicsAdapter(Context context,  List<FlightDynamics> flightDynamicsList) {
    		this.context = context;
    		this.flightDynamicsList = flightDynamicsList;
    	}
    	@Override
    	public int getCount() {
    		return flightDynamicsList.size();
    	}
    	@Override
    	public FlightDynamics getItem(int position) {
    		return flightDynamicsList.get(position);
    	}
    	@Override
    	public long getItemId(int position) {
    		return position;
    	}
    	
    	public void addFlightDynamics(FlightDynamics dynamics){
    		flightDynamicsList.add(dynamics);
    		notifyDataSetChanged();
    	}
    	
    	public void deleteFlightDynamics(int position){
    		flightDynamicsList.remove(position);
    		notifyDataSetChanged();
    	}

    	@Override
    	public View getView(final int position, View convertView, ViewGroup parent) {
    		final ViewHolder holder;
    		if (convertView == null) {  
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_flightdynamic, null);
                holder = new ViewHolder();  
                
                holder.flightStatusImageView = (ImageView) convertView.findViewById(R.id.listitemflightdynamic_flightstatus_imageView);
                holder.planeCodeTextView = (TextView) convertView.findViewById(R.id.listitemflightdynamic_planeCode_textView);
                holder.fromTerminalTextView = (TextView) convertView.findViewById(R.id.listitemflightdynamic_fromTerminal_textView);
                holder.fromTimeTextView = (TextView) convertView.findViewById(R.id.listitemflightdynamic_fromTime_textView);
                holder.flightCompanyTextView = (TextView) convertView.findViewById(R.id.listitemflightdynamic_flightCompany_textView);
                holder.toTerminalTextView = (TextView) convertView.findViewById(R.id.listitemflightdynamic_toTerminal_textView);
                holder.toTimeTextView = (TextView) convertView.findViewById(R.id.listitemflightdynamic_toTime_textView);
                
                convertView.setTag(holder);  
            } else {  
                holder = (ViewHolder) convertView.getTag();  
            }  
    		
    		final FlightDynamics dynamics = getItem(position);
    		holder.flightStatusImageView.setImageResource(dynamics.getFlightStatus().getFlightStatusDrawable());
    		holder.planeCodeTextView.setText(dynamics.getCarrier()+dynamics.getNum());
    		holder.fromTerminalTextView.setText(dynamics.getDep_airport_name()+dynamics.getDep_terminal());
    		holder.fromTimeTextView.setText(dynamics.getPlan_dep_time());
    		holder.flightCompanyTextView.setText(dynamics.getCar_name());
    		holder.toTerminalTextView.setText(dynamics.getArr_airport_name()+dynamics.getArr_terminal());
    		holder.toTimeTextView.setText(dynamics.getPlan_arr_time());
            return convertView;  
    	}

    	private class ViewHolder {
    		ImageView flightStatusImageView;
    		TextView planeCodeTextView;
    		TextView fromTerminalTextView;
    		TextView fromTimeTextView;
    		TextView flightCompanyTextView;
    		TextView toTerminalTextView;
    		TextView toTimeTextView;
    	}
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  super.onActivityResult(requestCode, resultCode, data);
		  if(data == null)
			  return;
		  if(GlobalVariables.bodingUser != null){
				(new FlightDynamicsTask(this, HTTPAction.GET_MYFOLLOWED)).execute();
		  }
	 }
}
