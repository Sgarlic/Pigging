package com.boding.adapter;

import com.boding.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreClassInfoAdapter extends BaseExpandableListAdapter{
	private String[] group = { "A��"}; 
    private String[][] child = { { ">9��", "25", "���ò�7.0��", "790" }, 
    		{ ">9��", "25", "���ò�7.0��", "790" }, { ">9��", "25", "���ò�7.0��", "790" }, 
    		{ ">9��", "25", "���ò�7.0��", "790" } }; 
    private Context mContext; 
	
    public MoreClassInfoAdapter(Context mContext){
    	super();
    	this.mContext = mContext;
    }
    
	@Override
	public String[] getChild(int groupPosition, int childPosition) {
		return child[childPosition]; 
	}

	@Override
    public int getGroupCount() { 
        return group.length; 
    } 
 
    @Override
    public int getChildrenCount(int groupPosition) { 
            return child[groupPosition].length; 
    } 
 
    @Override
    public Object getGroup(int groupPosition) { 
            return group[groupPosition]; 
    } 
 
    @Override
    public long getGroupId(int groupPosition) { 
            return groupPosition; 
    } 
    @Override
    public boolean hasStableIds() { 
            return true; 
    } 
	@Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) { 
		// ʵ���������ļ�
        View groupView = (View) LayoutInflater.from(mContext).inflate(R.layout.group_class_info, null); 
        ImageView imageView = (ImageView) groupView.findViewById(R.id.group_more_class_imageView); 
        // �жϷ����Ƿ�չ�����ֱ��벻ͬ��ͼƬ��Դ
        if (isExpanded) { 
        	imageView.setImageResource(R.drawable.datechoice2_2x); 
        } else { 
        	imageView.setImageResource(R.drawable.datechoice_2x); 
        } 
        return groupView; 
    }

	@Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) { 
            // ʵ���������ļ�
		View childView = (View) LayoutInflater.from(mContext).inflate(R.layout.elist_class_info, null); 
        TextView leftTicketTextView = (TextView)childView.findViewById(R.id.child_class_left_ticket_textView); 
        leftTicketTextView.setText(getChild(groupPosition, childPosition)[0].toString());
        TextView returnMoneyTextView = (TextView)childView.findViewById(R.id.child_class_return_money_textView); 
        leftTicketTextView.setText(getChild(groupPosition, childPosition)[1].toString());
        TextView discountTextView = (TextView)childView.findViewById(R.id.child_class_discount_textView); 
        leftTicketTextView.setText(getChild(groupPosition, childPosition)[2].toString());
        TextView priceTextView = (TextView)childView.findViewById(R.id.child_class_price_textView); 
        leftTicketTextView.setText(getChild(groupPosition, childPosition)[3].toString());
        ImageView buyImageView  = (ImageView)childView.findViewById(R.id.child_class_buy_imageView);
        return childView; 
    }
	

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}

	@Override
    public long getChildId(int groupPosition, int childPosition) { 
            return childPosition; 
    }
}
	