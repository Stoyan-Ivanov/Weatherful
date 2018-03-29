package com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.ui.forecast_pager_activity.ForecastPagerActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.BaseViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ForecastViewHolder extends BaseViewHolder {

    @BindView(R.id.iv_weather_icon) ImageView weatherImage;
    @BindView(R.id.tv_temperature) TextView tvTemperature;
    @BindView(R.id.tv_chances_of_rain) TextView tvRainChance;
    @BindView(R.id.tv_date) TextView tvDateHolder;

    private ForecastViewHolderPresenter presenter;
    private Data currData;

    public ForecastViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(final ArrayList<Data> data, final int position, final Location location) {
        currData = data.get(position);
        presenter = new ForecastViewHolderPresenter(currData);

        setDate();
        setWeatherImage();
        setTemperature();
        setRainChance();

        itemView.setOnClickListener(v -> context.startActivity(
                ForecastPagerActivity.getIntent(context, location, data, position))
        );
    }

    private void setDate() {
        tvDateHolder.setText(presenter.getDateFromTimestamp());
    }

    private void setWeatherImage() {
        weatherImage.setImageDrawable(getDrawableByName(currData.getIcon()));
    }

    private void setTemperature() {
        tvTemperature.setText(presenter.getTemperature());
    }

    private void setRainChance() {
        tvRainChance.setText(presenter.getRainChance());
    }

    private Drawable getDrawableByName(Data.Icon icon) {

        return context.getResources().getDrawable(icon.getResourceId());
    }
}
