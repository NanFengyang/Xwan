package com.example.xwan.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xwan.Data.UsersDatas;
import com.example.xwan.Info.Users;
import com.example.xwan.R;
import com.example.xwan.Util.LogUtil;
import com.example.xwan.Util.ToastUtil;
import com.example.xwan.View.Image.photoview.PhotoView;
import com.example.xwan.View.flipviewpager.BaseFlipAdapter;
import com.example.xwan.View.flipviewpager.FlipSettings;
import com.example.xwan.base.TitleBaseActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserContactActivity extends TitleBaseActivity implements
		OnClickListener {
	private String TAG = "相关用户";
	private Context mContext;
	private View contentView;
	private int delay = 500;
	private RecyclerView mXListView;

	@Override
	protected View initContentView(Bundle savedInstanceState,
			LayoutInflater layoutInflater) {
		// TODO Auto-generated method stub
		showLoadingDialog(null);
		contentView = layoutInflater.inflate(R.layout.activity_user_contact,
				null);
		isRightTitleButtonVisibility(false);
		setTtitleTextAlpha((float) 0.1);
		setTtitleTextColor(getResources().getColor(android.R.color.white));
		setTitle(R.drawable.icon_back, TAG, -1);
		getTitle_leftImageButton().setOnClickListener(this);
		mContext = this;
		mXListView = (RecyclerView) getViewId(contentView, R.id.users_list);
		LinearLayoutManager mLayoutManager = new LinearLayoutManager(
				UserContactActivity.this);
		mXListView.setLayoutManager(mLayoutManager);
		mXListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
			private int newState = 0;
			private int distance = 0;

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				// TODO Auto-generated method stub
				super.onScrolled(recyclerView, dx, dy);
				LogUtil.d("onScrolled", "dy:" + dy);
				if (newState != 0) {
					distance += dy;
					LogUtil.d("onScrolled", "distance:" + distance);
					setAlphaBg(distance, false);
				} else {
					distance = 0;
				}

			}

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				// TODO Auto-generated method stub
				super.onScrollStateChanged(recyclerView, newState);
				this.newState = newState;
				LogUtil.d("onScrolled", "newState:" + newState);
			}

		});

		addView();
		return contentView;
	}

	private void addView() {
		FlipSettings settings = new FlipSettings.Builder().defaultPage(1)
				.build();
		mXListView.setAdapter(new FriendsAdapter(this, UsersDatas.Userss,
				settings));
		dismissLoadingDialog(500);
	}

	class FriendsAdapter extends BaseFlipAdapter {

		private final int PAGES = 3;
		private int[] IDS_INTEREST = { R.id.interest_1, R.id.interest_2,
				R.id.interest_3, R.id.interest_4, R.id.interest_5 };

		public FriendsAdapter(Context context, List<Users> items,
				FlipSettings settings) {
			super(context, items, settings);
		}

		@Override
		public View getPage(int position, View convertView, ViewGroup parent,
				Object friend1, Object friend2, CloseListener closeListener) {
			final FriendsHolder holder;

			if (convertView == null) {
				holder = new FriendsHolder();
				convertView = getLayoutInflater().inflate(
						R.layout.friends_merge_page, parent, false);
				holder.leftAvatar = (PhotoView) convertView
						.findViewById(R.id.first);
				holder.rightAvatar = (PhotoView) convertView
						.findViewById(R.id.second);
				holder.infoPage = getLayoutInflater().inflate(
						R.layout.friends_info, parent, false);
				holder.nickName = (TextView) holder.infoPage
						.findViewById(R.id.nickname);

				for (int id : IDS_INTEREST)
					holder.interests.add((TextView) holder.infoPage
							.findViewById(id));

				convertView.setTag(holder);
			} else {
				holder = (FriendsHolder) convertView.getTag();
			}
			switch (position) {
			// Merged page with 2 friends
			case 1:
				holder.leftAvatar.setImageResource(((Users) friend1)
						.getAvatar());
				if (friend2 != null)
					holder.rightAvatar.setImageResource(((Users) friend2)
							.getAvatar());
				break;
			default:
				fillHolder(holder.leftAvatar,holder, position == 0 ? (Users) friend1
						: (Users) friend2);
				holder.infoPage.setTag(holder);

				return holder.infoPage;
			}

			return convertView;
		}

		@Override
		public int getPagesCount() {
			return PAGES;
		}

		private void fillHolder(final PhotoView view,FriendsHolder holder,final Users friend) {
			if (friend == null)
				return;
			Iterator<TextView> iViews = holder.interests.iterator();
			Iterator<String> iInterests = friend.getInterests().iterator();
			while (iViews.hasNext() && iInterests.hasNext())
				iViews.next().setText(iInterests.next());
			holder.infoPage.setBackgroundColor(getResources().getColor(
					friend.getBackground()));
			holder.nickName.setText(friend.getNickname());
			holder.nickName.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					ToastUtil.showToast(friend.getNickname());
					showBigImage(view, friend.getAvatar());
				}
			});
		}

		class FriendsHolder {
			PhotoView leftAvatar;
			PhotoView rightAvatar;
			View infoPage;

			List<TextView> interests = new ArrayList<TextView>();
			TextView nickName;
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		finish();
	}

}
