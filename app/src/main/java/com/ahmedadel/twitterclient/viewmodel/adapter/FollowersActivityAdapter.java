package com.ahmedadel.twitterclient.viewmodel.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ahmedadel.twitterclient.R;
import com.ahmedadel.twitterclient.databinding.FollowersItemListBinding;
import com.ahmedadel.twitterclient.model.User;
import com.ahmedadel.twitterclient.viewmodel.FollowersItemListViewModel;

import java.util.List;

/**
 * Created by ahmedadel on 24/06/16.
 * <p/>
 * FollowersActivityAdapter is an adapter for FollowersActivity view
 */
public class FollowersActivityAdapter extends RecyclerView.Adapter {

    private List<User> userList;

    // these to fields to differ from user item list and loading view item list
    private final int VIEW_ITEM = 1; // this for user items
    private final int VIEW_PROGRESSBAR = 0; // this for loading item

    private int visibleThreshold = 1; // this field for indicate the scroll that we need to fire the onLoadMore listener when
    // the user catch the last index of the adapter or when the last index of the adapter is partially visible

    private int lastVisibleItem, totalItemCount; // lastVisibleItem is to check with visibleThreshold
    // and totalItemCount is indicates the total number visible items

    private boolean loading; // is a flag that helps us to trigger onLoadMore callback method

    private OnLoadMoreListener onLoadMoreListener; // onLoadMoreListener is a callback interface of onLoadMore method

    public FollowersActivityAdapter(RecyclerView recyclerView, List<User> userList) {
        this.userList = userList;
        // check if layout manager of recycler view is from type of LinearLayoutManager or not
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            // fire a scroll list listener to listener every scroll action happens
            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                            if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore(true);
                                }
                                loading = true;
                            } else {
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore(false);
                                }
                            }
                        }
                    });
        }
    }

    /**
     * Create the view holder of specific view holder according to viewType
     * return view holder of the item list according to the viewType
     *
     * @param parent   is the viewGroup that holds recycler view
     * @param viewType is the viewType that we set it below according to the list and make the difference in showing
     *                 user item list or loading item list
     * @return return view holder of the item list according to the viewType
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            FollowersItemListBinding followersItemListViewBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.followers_item_list,
                            parent, false);
            viewHolder = new FollowersViewHolder(followersItemListViewBinding);
        } else if (viewType == VIEW_PROGRESSBAR) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.loading_progress_item, parent, false);

            viewHolder = new ProgressViewHolder(view);
        }
        return viewHolder;
    }

    /**
     * Bind view holder according to the holder instance if it is FollowersViewHolder or ProgressViewHolder
     *
     * @param holder   is the view holder that will be according to this adapter either FollowersViewHolder or ProgressViewHolder
     * @param position of the item list
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FollowersViewHolder) {
            ((FollowersViewHolder) holder).bindFollowers(userList.get(position));
        } else if (holder instanceof ProgressViewHolder) {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    /**
     * @param position of the item list
     * @return the view type of the adapter, it is a workaround since we are going to check if item list inside the List
     * is equal to null then it is loading view otherwise it will be data of Followers List
     */
    @Override
    public int getItemViewType(int position) {
        return userList.get(position) != null ? VIEW_ITEM : VIEW_PROGRESSBAR;
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    /**
     * setLoad to handle it in the addScroll listener
     *
     * @param isLoaded boolean flag that we will check it during scroll handling
     */
    public void setLoaded(boolean isLoaded) {
        loading = isLoaded;
    }

    /**
     * set on load more listener from FollowersActivity
     *
     * @param onLoadMoreListener is the callback interface that responsible for callbacks of infinite scrolling
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /**
     * view holder of followers item list
     */
    public class FollowersViewHolder extends RecyclerView.ViewHolder {

        FollowersItemListBinding followersItemListViewBinding;

        public FollowersViewHolder(FollowersItemListBinding followersItemListViewBinding) {
            super(followersItemListViewBinding.followersItemListCardView);
            this.followersItemListViewBinding = followersItemListViewBinding;
        }

        void bindFollowers(User user) {
            if (followersItemListViewBinding.getFollowersItemListViewModel() == null) {
                followersItemListViewBinding.setFollowersItemListViewModel(new FollowersItemListViewModel(user, itemView.getContext()));
            } else {
                followersItemListViewBinding.getFollowersItemListViewModel().setUser(user);
            }
        }
    }

    /**
     * view holder of loading
     */
    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.loading_progressBar);
        }
    }

}
