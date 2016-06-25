package com.ahmedadel.twitterclient.customviews.roundedimageview;

/**
 * Created by ahmedadel on 20/06/16.
 *
 * Corner is like an enum class that has top left, top right, bottom left and bottom right fields
 *
 */

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        Corner.TOP_LEFT, Corner.TOP_RIGHT,
        Corner.BOTTOM_LEFT, Corner.BOTTOM_RIGHT
})
public @interface Corner {
    int TOP_LEFT = 0;
    int TOP_RIGHT = 1;
    int BOTTOM_RIGHT = 2;
    int BOTTOM_LEFT = 3;
}
