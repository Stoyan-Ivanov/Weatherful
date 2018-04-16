package com.stoyan.weatherful.db;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by stoyan.ivanov2 on 4/16/2018.
 */

public class Image implements Parcelable {
    private String mThumbnailUrl;
    private String mFullImageUrl;

    public Image() {
    }

    public Image(String mThumnailUrl, String mFullImageUrl) {
        this.mThumbnailUrl = mThumnailUrl;
        this.mFullImageUrl = mFullImageUrl;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.mThumbnailUrl = thumbnailUrl;
    }

    public String getFullImageUrl() {
        return mFullImageUrl;
    }

    public void setFullImageUrl(String fullImageUrl) {
        this.mFullImageUrl = fullImageUrl;
    }

    protected Image(Parcel in) {
        mThumbnailUrl = in.readString();
        mFullImageUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mThumbnailUrl);
        dest.writeString(mFullImageUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}