package com.stoyan.weatherful.ui.day_forecast_fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.ui.base_ui.contract.BaseViewContract;
import com.stoyan.weatherful.ui.base_ui.fragment.BaseFragment;

import butterknife.BindView;

public class DayForecastFragment extends BaseFragment<DayForecastFragmentPresenter> implements BaseViewContract {

    @BindView(R.id.iv_fragment_weather_image) ImageView mWeatherImage;
    @BindView(R.id.tv_fragment_date) TextView mTvDate;
    @BindView(R.id.tv_fragment_temp) TextView mTvTemperature;
    @BindView(R.id.tv_fragment_wind_speed) TextView mTvWindSpeed;
    @BindView(R.id.tv_fragment_rain_chance) TextView mTvRainChance;
    @BindView(R.id.tv_fragment_humidity) TextView mTvHumidity;
    @BindView(R.id.tv_fragment_forecast_summary) TextView mTvForecastSummary;
    @BindView(R.id.tv_fragment_sunrise) TextView mTvSunrise;
    @BindView(R.id.tv_fragment_sunset) TextView mTvSunset;

    DayForecastFragmentViewModel mViewModel;

    public static DayForecastFragment newInstance(Data data) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(Constants.EXTRA_DATA, data);

        DayForecastFragment fragment = new DayForecastFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(DayForecastFragmentViewModel.class);

        return inflateCurrentView(inflater, R.layout.fragment_day_forecast, container);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setWeatherImage();
        mTvDate.setText(presenter.getDate());
        mTvTemperature.setText(presenter.getTemperature());
        mTvWindSpeed.setText(presenter.getWindSpeed());
        mTvRainChance.setText(presenter.getRainChance());
        mTvHumidity.setText(presenter.getHumidity());
        mTvForecastSummary.setText(presenter.getForecastSummary());
        mTvSunrise.setText(presenter.getSunriseTime());
        mTvSunset.setText(presenter.getSunsetTime());
    }

    private void setWeatherImage() {
        mViewModel.getImageIconId().observe(this, iconId -> {
            if(iconId != null) {
                mWeatherImage.setImageDrawable(getResources().getDrawable(iconId));
            }
        });
    }

//    private void setDate() {
//        mViewModel.getDate().observe(this, new O)
//    }

    private void setTemperature() {
    }
}
