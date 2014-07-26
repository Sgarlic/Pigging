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
 * 自定义拖拉ListView
 * 
 * @author zhangjia
 * 
 */
public class DragListView extends ListView implements OnScrollListener{
	// 点击加载更多枚举所有状态
	private enum DListViewLoadingMore {
		LV_NORMAL, // 普通状态
		LV_LOADING, // 加载状态
		LV_NOMOREINFO;
	}

	private View mFootView;// 尾部mFootView
	private View mLoadMoreView;// mFootView 的view(mFootView)
	private TextView mLoadMoreTextView;// 加载更多.(mFootView)
	private View mLoadingView;// 加载中...View(mFootView)

	private int mFirstItemIndex = -1;// 当前视图能看到的第一个项的索引

	// 用于保证startY的值在一个完整的touch事件中只被记录一次
	private boolean mIsRecord = false;

	private int mStartY, mMoveY;// 按下是的y坐标,move时的y坐标

	private DListViewLoadingMore loadingMoreState = DListViewLoadingMore.LV_NORMAL;// 加载更多默认状态.


	private OnRefreshLoadingMoreListener onRefreshLoadingMoreListener;// 下拉刷新接口（自定义）

	private boolean isScroller = true;// 是否屏蔽ListView滑动。
	
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

	// 注入下拉刷新接口
	public void setOnRefreshListener(
			OnRefreshLoadingMoreListener onRefreshLoadingMoreListener) {
		this.onRefreshLoadingMoreListener = onRefreshLoadingMoreListener;
	}

	/***
	 * 初始化ListView
	 */
	public void initDragListView(Context context) {
		initLoadMoreView(context);// 初始化footer

		setOnScrollListener(this);// ListView滚动监听
	}

	/***
	 * 初始化底部加载更多控件
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
				// 防止重复点击
				if (onRefreshLoadingMoreListener != null
						&& loadingMoreState == DListViewLoadingMore.LV_NORMAL) {
					updateLoadMoreViewState(DListViewLoadingMore.LV_LOADING);
					onRefreshLoadingMoreListener.onLoadMore();// 对外提供方法加载更多.
				}				// TODO Auto-generated method stub
				
			}
		});

		addFooterView(mFootView);
	}

	/***
	 * touch 事件监听
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		// 按下
		case MotionEvent.ACTION_DOWN:
			doActionDown(ev);
			break;
		default:
			break;
		}
		/***
		 * 如果是ListView本身的拉动，那么返回true，这样ListView不可以拖动.
		 * 如果不是ListView的拉动，那么调用父类方法，这样就可以上拉执行.
		 */
		if (isScroller) {
			return super.onTouchEvent(ev);
		} else {
			return true;
		}

	}

	/***
	 * 摁下操作
	 * 
	 * 作用：获取摁下是的y坐标
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
	 * 点击加载更多
	 * 
	 * @param flag
	 *            数据是否已全部加载完毕
	 *            true:加载完毕
	 *            false:正在加载
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
	// 更新Footview视图
	private void updateLoadMoreViewState(DListViewLoadingMore state) {
		System.out.println(state);
		switch (state) {
		// 普通状态
		case LV_NORMAL:
			mLoadingView.setVisibility(View.GONE);
			mLoadMoreTextView.setVisibility(View.VISIBLE);
			mLoadMoreTextView.setText("查看更多订单");
			break;
		// 加载中状态
		case LV_LOADING:
			mLoadingView.setVisibility(View.VISIBLE);
			mLoadMoreTextView.setVisibility(View.GONE);
			System.out.println("yes loading");
			break;
//		// 没有更多信息
		case LV_NOMOREINFO:
			mLoadingView.setVisibility(View.GONE);
			mLoadMoreTextView.setVisibility(View.VISIBLE);
			mLoadMoreTextView.setText("没有更多订单");
			break;
		default:
			break;
		}
		loadingMoreState = state;
	}

	/***
	 * ListView 滑动监听
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
	 * 自定义接口
	 */
	public interface OnRefreshLoadingMoreListener {
		/***
		 * 点击加载更多
		 */
		void onLoadMore();
	}

}
