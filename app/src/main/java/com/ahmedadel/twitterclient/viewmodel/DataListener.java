package com.ahmedadel.twitterclient.viewmodel;

import android.content.Context;

/**
 * Created by ahmedadel on 24/06/16.
 *
 * DataListener is an interface that acts like a callback for all reposes of Observables
 *
 */
public interface DataListener {

    Context getContext();

    void dataListenerLoaded(Object object, boolean isPullToRefresh, boolean isLoadMore);

    void errorListenerLoaded(String message);

}
