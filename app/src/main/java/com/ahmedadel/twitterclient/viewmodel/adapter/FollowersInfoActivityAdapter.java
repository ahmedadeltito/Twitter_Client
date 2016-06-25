package com.ahmedadel.twitterclient.viewmodel.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ahmedadel.twitterclient.R;
import com.ahmedadel.twitterclient.databinding.FollowersInfoItemListBinding;
import com.ahmedadel.twitterclient.model.Tweets;
import com.ahmedadel.twitterclient.viewmodel.FollowersInfoItemListViewModel;

import java.util.List;

/**
 * Created by ahmedadel on 24/06/16.
 *
 * FollowersInfoActivityAdapter is an adapter for FollowersInfoActivity view
 *
 */
public class FollowersInfoActivityAdapter extends RecyclerView.Adapter<FollowersInfoActivityAdapter.FollowersInfoViewHolder> {

    private List<Tweets> tweetsList;

    public FollowersInfoActivityAdapter(List<Tweets> tweetsList) {
        this.tweetsList = tweetsList;
    }

    /**
     * Create the view holder of specific view holder according to viewType
     * return view holder of the item list according to the viewType
     * @param parent is the viewGroup that holds recycler view
     * @param viewType is the viewType that is returned from getViewType() method
     * @return return view holder of the item list according to the viewType
     */
    @Override
    public FollowersInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FollowersInfoItemListBinding followersInfoItemListBinding = DataBindingUtil.inflate
                (LayoutInflater.from(parent.getContext()), R.layout.followers_info_item_list, parent, false);
        return new FollowersInfoViewHolder(followersInfoItemListBinding);
    }
    //----------------------------------------------------------------------------------------------------------//

    /**
     * Bind view holder according to the holder instance if it is FollowersInfoViewHolder
     * @param holder is the view holder that will be according to this adapter FollowersInfoViewHolder
     * @param position of the item list
     */
    @Override
    public void onBindViewHolder(FollowersInfoViewHolder holder, int position) {
        holder.bindFollowersInfo(tweetsList.get(position));
    }
    //----------------------------------------------------------------------------------------------------------//

    @Override
    public int getItemCount() {
        return tweetsList.size();
    }
    //----------------------------------------------------------------------------------------------------------//

    /**
     * view holder of tweets item list
     */

    public class FollowersInfoViewHolder extends RecyclerView.ViewHolder {

        FollowersInfoItemListBinding followersInfoItemListBinding;

        public FollowersInfoViewHolder(FollowersInfoItemListBinding followersInfoItemListBinding) {
            super(followersInfoItemListBinding.followersInfoItemListCardView);
            this.followersInfoItemListBinding = followersInfoItemListBinding;
        }

        void bindFollowersInfo(Tweets tweets) {
            if (followersInfoItemListBinding.getFollowersInfoItemViewModel() == null) {
                followersInfoItemListBinding.setFollowersInfoItemViewModel(new FollowersInfoItemListViewModel(tweets, itemView.getContext()));
            } else {
                followersInfoItemListBinding.getFollowersInfoItemViewModel().setTweets(tweets);
            }
        }
    }

}