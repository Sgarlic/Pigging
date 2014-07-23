package com.boding.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.IntentRequestCode;
import com.boding.model.BankCard;
import com.boding.util.Util;
import com.boding.view.dialog.SearchBankcardDialog;
public class BankCardSelectActivity extends FragmentActivity {
	private ListView bankCardList;
	private boolean isCreditCardSelection = false;
	private TextView titleTextView;
	private BankCardListAdapter bankcardAdapter;
	private List<BankCard> allBankCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle arguments = getIntent().getExtras();
        if(arguments != null)
        	isCreditCardSelection = arguments.getBoolean(Constants.IS_CREDITCARD_SELECTION);
         
        setContentView(R.layout.activity_bankcard_select);
       
        initView();
        setTitle();
    }

    private void initView(){
        LinearLayout returnLogoLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
        returnLogoLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(BankCardSelectActivity.this, IntentRequestCode.BANKCARD_SELECTION);
			}
        });
        
        titleTextView = (TextView) findViewById(R.id.bankcard_select_title_textView);
        
        allBankCardList = new ArrayList<BankCard>();
        allBankCardList.add(new BankCard("工商银行",isCreditCardSelection));
        allBankCardList.add(new BankCard("招商银行",isCreditCardSelection));
        allBankCardList.add(new BankCard("建设银行",isCreditCardSelection));
        allBankCardList.add(new BankCard("农业银行",isCreditCardSelection));
        allBankCardList.add(new BankCard("中国银行",isCreditCardSelection));
        allBankCardList.add(new BankCard("交通银行",isCreditCardSelection));
        allBankCardList.add(new BankCard("广发银行",isCreditCardSelection));
        allBankCardList.add(new BankCard("民生银行",isCreditCardSelection));
        allBankCardList.add(new BankCard("中信银行",isCreditCardSelection));
        allBankCardList.add(new BankCard("光大银行",isCreditCardSelection));
        allBankCardList.add(new BankCard("平安银行",isCreditCardSelection));
        allBankCardList.add(new BankCard("花旗银行",isCreditCardSelection));
        allBankCardList.add(new BankCard("兴业银行",isCreditCardSelection));
        LinearLayout bankcardSearchLinearLayout = (LinearLayout)findViewById(R.id.bankcard_search_linearLayout);
        bankcardSearchLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				SearchBankcardDialog searchBankcardDialog = new SearchBankcardDialog(BankCardSelectActivity.this,allBankCardList);
				searchBankcardDialog.show();
			}
        });
        
        bankCardList = (ListView) findViewById(R.id.bankcard_select_listView);
        
        bankcardAdapter = new BankCardListAdapter(this,allBankCardList);
        bankCardList.setAdapter(bankcardAdapter);
        
        bankCardList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				BankCard selectedBankCard = bankcardAdapter.getItem(position);
			}
		});
    }
    
    private void setTitle(){
    	if(isCreditCardSelection)
    		titleTextView.setText("选择发卡银行");
    	else
    		titleTextView.setText("选择银行");
    }
    
    private class BankCardListAdapter extends BaseAdapter {
    	private LayoutInflater inflater;  
        private List<BankCard> contentList;
    	
    	public BankCardListAdapter(Context context, List<BankCard> bankCardList) {
    		this.inflater = LayoutInflater.from(context);
    		this.contentList = bankCardList;
    	}
    	
		@Override
		public int getCount() {
			return contentList.size();
		}

		@Override
		public BankCard getItem(int position) {
			return contentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {  
                convertView = inflater.inflate(R.layout.list_item_citysearch_city, null);
                holder = new ViewHolder();  
                holder.name = (TextView) convertView.findViewById(R.id.name);  
                convertView.setTag(holder);  
            } else {  
                holder = (ViewHolder) convertView.getTag(); 
                
            }  
			BankCard bankcard = contentList.get(position);  
            holder.name.setText(bankcard.getBankName());
            return convertView;  
		}
		
		private class ViewHolder {
            TextView name;  
		}
    	
    }
}
