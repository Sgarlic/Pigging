package com.boding.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boding.R;
import com.boding.model.FlightDynamics;;


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
	
	public void addSubscribe(FlightDynamics dynamics){
		flightDynamicsList.add(dynamics);
		notifyDataSetChanged();
	}
	
	public void deleteSubscribe(int position){
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
		holder.fromTerminalTextView.setText(dynamics.getDep_terminal());
		holder.fromTimeTextView.setText(dynamics.getPlan_dep_time());
		holder.flightCompanyTextView.setText(dynamics.getCar_name());
		holder.toTerminalTextView.setText(dynamics.getArr_terminal());
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
