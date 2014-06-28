package com.boding.app;


import org.json.JSONException;
import org.json.JSONObject;

import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.model.FlightQuery;
import com.boding.util.Util;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUnderstander;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class VoiceSearchActivity extends Activity {
	private ImageView searchButton;
	private SpeechUnderstander mSpeechUnderstander;
	
	private SharedPreferences mSharedPreferences;
	
	// 语义理解对象（文本到语义）。
	private TextUnderstander   mTextUnderstander;	
	private FlightQuery flightQuery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voice_search);
        
		// 设置你申请的应用appid
		com.iflytek.cloud.SpeechUtility.createUtility(this, SpeechConstant.APPID+"=53982af1");
		mSpeechUnderstander = SpeechUnderstander.createUnderstander(this, speechUnderstanderListener);
		//参数设置，暂时直接写，后面应该用配置文件。
		mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		
		mTextUnderstander = new TextUnderstander(this, textUnderstanderListener);
		initView();
	}
	
	/**
     * 初始化监听器（语音到语义）。
     */
    private InitListener speechUnderstanderListener = new InitListener() {
		@Override
		public void onInit(int code) {
			Log.d("info", "speechUnderstanderListener init() code = " + code);
        	if (code == ErrorCode.SUCCESS) {
        		System.out.println("语音到语义监听器初始化成功。");
        	}			
		}
    };
    
    /**
     * 初始化监听器（文本到语义）。
     */
    private InitListener textUnderstanderListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d("info", "textUnderstanderListener init() code = " + code);
        	if (code == ErrorCode.SUCCESS) {
        		System.out.println("文本到语义监听器初始化成功。");
        	}
		}
    };
	
    private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(VoiceSearchActivity.this, IntentRequestCode.VOICE_SEARCH);
			}
			
		});
		
		searchButton = (ImageView)findViewById(R.id.voicesearch_searchbutton_imageView);
		
		searchButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					System.out.println(v);
					if(mSpeechUnderstander.isUnderstanding()){// 开始前检查状态
						mSpeechUnderstander.stopUnderstanding();
					}else {
						int ret = mSpeechUnderstander.startUnderstanding(mRecognizerListener);
						if(ret != 0){
							System.out.println("语义理解失败,错误码:"	+ ret);
						}else {
							System.out.println("开始录制");
						}
					}
				}
				if(event.getAction() == MotionEvent.ACTION_UP){
					mSpeechUnderstander.stopUnderstanding();
					System.out.println("停止语音输入。");
				}
				return true;
			}
		});
	}
	
	/**
	 * 参数设置
	 * @param param
	 * @return 
	 */
	@SuppressLint("SdCardPath")
	public void setParam(){
		String lag = mSharedPreferences.getString("understander_language_preference", "mandarin");
		if (lag.equals("en_us")) {
			// 设置语言
			mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "en_us");
		}else {
			// 设置语言
			mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			// 设置语言区域
			mSpeechUnderstander.setParameter(SpeechConstant.ACCENT,lag);
		}
		// 设置语音前端点
		mSpeechUnderstander.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("understander_vadbos_preference", "4000"));
		// 设置语音后端点
		mSpeechUnderstander.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("understander_vadeos_preference", "1000"));
		// 设置标点符号
		mSpeechUnderstander.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("understander_punc_preference", "1"));
		// 设置音频保存路径
		mSpeechUnderstander.setParameter(SpeechConstant.ASR_AUDIO_PATH, "/sdcard/iflytek/wavaudio.pcm");

	}
	
	/**
     * 识别回调。
     */
    private SpeechUnderstanderListener mRecognizerListener = new SpeechUnderstanderListener() {
        @Override
        public void onVolumeChanged(int v) {
        	System.out.println("onVolumeChanged："	+ v);
        }
        
        @Override
        public void onEndOfSpeech() {
        	System.out.println("onEndOfSpeech");
        }
        
        @Override
        public void onBeginOfSpeech() {
        	System.out.println("onBeginOfSpeech");
        }

		@Override
		public void onError(SpeechError arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, String arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onResult(final com.iflytek.cloud.UnderstanderResult result) {
			String text = result.getResultString();
			try {
				flightQuery = parseJson(text);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if(flightQuery.getRc() != 0){
				if(mTextUnderstander.isUnderstanding()){
					mTextUnderstander.cancel();
				}else {
					int ret = mTextUnderstander.understandText(text, textListener);
					if(ret != 0)
					{
						System.out.println("语义理解失败,错误码:"+ ret);
					}
				}
			}else{
				goToSearch();
			}
			
		}

    };
    
    private TextUnderstanderListener textListener = new TextUnderstanderListener() {
		
		@Override
		public void onResult(final UnderstanderResult result) {
	       	runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (null != result) {
			            	// 显示
							Log.d("info" , "understander result：" + result.getResultString());
							String text = result.getResultString();
							if (!TextUtils.isEmpty(text)) {
								System.out.println("文字！：   " +text);
								try {
									flightQuery = parseJson("航班"+ text);
									if(flightQuery.getRc() == 0)
										goToSearch();
									else
										;//......................................................................语义无法理解的提示界面！
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
			            } else {
			                Log.d("info", "understander result:null");
			            }
					}
				});
		}
		
		@Override
		public void onError(SpeechError error) {
			System.out.println("onError Code："	+ error.getErrorCode());
			
		}
	};
    
    private FlightQuery parseJson(String jsonStr) throws JSONException{
    	FlightQuery fq = new FlightQuery();
    	
    	JSONObject result = new JSONObject(jsonStr);
    	int rc = result.getInt("rc");
    	fq.setText(result.getString("text"));
    	fq.setRc(rc);
    	if(rc == 0){
    		JSONObject semantic = result.getJSONObject("semantic");
    		JSONObject slots = semantic.getJSONObject("slots");
    		JSONObject startDate = slots.getJSONObject("startDate");
    		JSONObject startLoc = slots.getJSONObject("startLoc");
    		JSONObject endLoc = slots.getJSONObject("endLoc");
    		
    		if(startDate != null)
    			fq.setStartdate(startDate.getString("date"));
    		if(startLoc != null)
    			fq.setFromcity(startLoc.getString("cityAddr"));
    		if(endLoc != null)
    			fq.setTocity(endLoc.getString("cityAddr"));
    	}
    	return fq;
    }
    
    private void goToSearch(){
    	Intent intent = new Intent();
		intent.setClass(VoiceSearchActivity.this, TicketSearchResultActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable("query", flightQuery);
		intent.putExtras(bundle);
		startActivityForResult(intent,IntentRequestCode.TICKET_SEARCH.getRequestCode());
    }
	
}
