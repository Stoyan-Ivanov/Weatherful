package com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.ui.forecast_pager_activity.ForecastPagerActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.BaseViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ForecastViewHolder extends BaseViewHolder {

    @BindView(R.id.iv_weather_icon) ImageView mWeatherImage;
    @BindView(R.id.tv_temperature) TextView mTvTemperature;
    @BindView(R.id.tv_chances_of_rain) TextView mTvRainChance;
    @BindView(R.id.tv_date) TextView mTvDateHolder;

    private ForecastViewHolderPresenter mPresenter;
    private Data mCurrData;

    public ForecastViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(final ArrayList<Data> data, final int position, final Location location) {
        mCurrData = data.get(position);
        mPresenter = new ForecastViewHolderPresenter(mCurrData);

        setDate();
        setWeatherImage();
        setTemperature();
        setRainChance();

        itemView.setOnClickListener(v -> mContext.startActivity(
                ForecastPagerActivity.getIntent(mContext, location, data, position))
        );
    }

    private void setDate() {
        mTvDateHolder.setText(mPresenter.getDateFromTimestamp());
    }

    private void setWeatherImage() {
        mWeatherImage.setImageDrawable(getDrawableByName(mCurrData.getIcon()));
    }

    private void setTemperature() {
        mTvTemperature.setText(mPresenter.getTemperature());
    }

    private void setRainChance() {
        mTvRainChance.setText(mPresenter.getRainChance());
    }

    private Drawable getDrawableByName(Data.Icon icon) {

        return mContext.getResources().getDrawable(icon.getResourceId());
    }
}
