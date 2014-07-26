package com.boding.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.boding.R;

/***
 * �Զ�������ListView
 * 
 * @author zhangjia
 * 
 */
public class DragListView extends ListView implements OnScrollListener{
	// ������ظ���ö������״̬
	private enum DListViewLoadingMore {
		LV_NORMAL, // ��ͨ״̬
		LV_LOADING, // ����״̬
		LV_NOMOREINFO;
	}

	private View mFootView;// β��mFootView
	private View mLoadMoreView;// mFootView ��view(mFootView)
	private TextView mLoadMoreTextView;// ���ظ���.(mFootView)
	private View mLoadingView;// ������...View(mFootView)

	private int mFirstItemIndex = -1;// ��ǰ��ͼ�ܿ����ĵ�һ���������

	// ���ڱ�֤startY��ֵ��һ��������touch�¼���ֻ����¼һ��
	private boolean mIsRecord = false;

	private int mStartY, mMoveY;// �����ǵ�y����,moveʱ��y����

	private DListViewLoadingMore loadingMoreState = DListViewLoadingMore.LV_NORMAL;// ���ظ���Ĭ��״̬.


	private OnRefreshLoadingMoreListener onRefreshLoadingMoreListener;// ����ˢ�½ӿڣ��Զ��壩

	private boolean isScroller = true;// �Ƿ�����ListView������
	
	private Context context;

	public DragListView(Context context) {
		super(context, null);
		this.context = context;
		initDragListView(context);
	}

	public DragListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initDragListView(context);
	}

	// ע������ˢ�½ӿ�
	public void setOnRefreshListener(
			OnRefreshLoadingMoreListener onRefreshLoadingMoreListener) {
		this.onRefreshLoadingMoreListener = onRefreshLoadingMoreListener;
	}

	/***
	 * ��ʼ��ListView
	 */
	public void initDragListView(Context context) {
		initLoadMoreView(context);// ��ʼ��footer

		setOnScrollListener(this);// ListView��������
	}

	/***
	 * ��ʼ���ײ����ظ���ؼ�
	 */
	private void initLoadMoreView(Context context) {
		mFootView = LayoutInflater.from(context).inflate(R.layout.listview_dragable_footer, null);

		mLoadMoreView = mFootView.findViewById(R.id.load_more_view);

		mLoadMoreTextView = (TextView) mFootView
				.findViewById(R.id.load_more_tv);

		mLoadingView = (LinearLayout) mFootView
				.findViewById(R.id.loading_layout);

		mLoadMoreView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				System.out.println("onclick");
				if(loadingMoreState == DListViewLoadingMore.LV_NOMOREINFO)
					return;
				// ��ֹ�ظ����
				if (onRefreshLoadingMoreListener != null
						&& loadingMoreState == DListViewLoadingMore.LV_NORMAL) {
					updateLoadMoreViewState(DListViewLoadingMore.LV_LOADING);
					onRefreshLoadingMoreListener.onLoadMore();// �����ṩ�������ظ���.
				}				// TODO Auto-generated method stub
				
			}
		});

		addFooterView(mFootView);
	}

	/***
	 * touch �¼�����
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		// ����
		case MotionEvent.ACTION_DOWN:
			doActionDown(ev);
			break;
		default:
			break;
		}
		/***
		 * �����ListView�������������ô����true������ListView�������϶�.
		 * �������ListView����������ô���ø��෽���������Ϳ�������ִ��.
		 */
		if (isScroller) {
			return super.onTouchEvent(ev);
		} else {
			return true;
		}

	}

	/***
	 * ���²���
	 * 
	 * ���ã���ȡ�����ǵ�y����
	 * 
	 * @param event
	 */
	void doActionDown(MotionEvent event) {
		if (mIsRecord == false && mFirstItemIndex == 0) {
			mStartY = (int) event.getY();
			mIsRecord = true;
		}
	}


	/***
	 * ������ظ���
	 * 
	 * @param flag
	 *            �����Ƿ���ȫ���������
	 *            true:�������
	 *            false:���ڼ���
	 */
	public void onLoadMoreComplete(boolean isComplete) {
		if (isComplete) {
			updateLoadMoreViewState(DListViewLoadingMore.LV_NOMOREINFO);
		} else {
			updateLoadMoreViewState(DListViewLoadingMore.LV_NORMAL);
		}

	}
	
	public void setOnLoadMoreText(String text){
		mLoadMoreTextView.setText(text);
	}

	public void hideOnLoadMoreTextView(){
		mLoadMoreTextView.setVisibility(View.GONE);
	}
	// ����Footview��ͼ
	private void updateLoadMoreViewState(DListViewLoadingMore state) {
		System.out.println(state);
		switch (state) {
		// ��ͨ״̬
		case LV_NORMAL:
			mLoadingView.setVisibility(View.GONE);
			mLoadMoreTextView.setVisibility(View.VISIBLE);
			mLoadMoreTextView.setText("�鿴���ඩ��");
			break;
		// ������״̬
		case LV_LOADING:
			mLoadingView.setVisibility(View.VISIBLE);
			mLoadMoreTextView.setVisibility(View.GONE);
			System.out.println("yes loading");
			break;
//		// û�и�����Ϣ
		case LV_NOMOREINFO:
			mLoadingView.setVisibility(View.GONE);
			mLoadMoreTextView.setVisibility(View.VISIBLE);
			mLoadMoreTextView.setText("û�и��ඩ��");
			break;
		default:
			break;
		}
		loadingMoreState = state;
	}

	/***
	 * ListView ��������
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		mFirstItemIndex = firstVisibleItem;
	}

	/***
	 * �Զ���ӿ�
	 */
	public interface OnRefreshLoadingMoreListener {
		/***
		 * ������ظ���
		 */
		void onLoadMore();
	}

}
