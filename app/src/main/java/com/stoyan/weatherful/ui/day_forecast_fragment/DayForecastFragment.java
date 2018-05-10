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

    private DayForecastFragmentViewModel mViewModel;

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
        setDate();
        setTemperature();
        setWindSpeed();
        setRainChance();
        setHumidity();
        setSunriseTime();
        setSunsetTime();
        setForecastSummary();
    }

    private void setWeatherImage() {
        mViewModel.getImageIconId().observe(this, iconId -> {
            if(iconId != null) {
                mWeatherImage.setImageDrawable(getResources().getDrawable(iconId));
            }
        });
    }

    private void setDate() {
        mViewModel.getDate().observe(this, date -> mTvDate.setText(date));
    }

    private void setTemperature() {
        final int LOW_TEMPERATURE = 0;
        final int HIGH_TEMPERATURE = 1;

        mViewModel.getTemperature().observe(this, temperatures ->
                mTvTemperature.setText(getString(R.string.temperature_field,
                        temperatures.get(LOW_TEMPERATURE),
                        temperatures.get(HIGH_TEMPERATURE))));
    }

    private void setWindSpeed() {
        mViewModel.getWindSpeed().observe(this, windSpeed -> mTvWindSpeed.setText(getString(R.string.wind_speed_field, windSpeed)));
    }

    private void setRainChance() {
        mViewModel.getRainChance().observe(this, rainChance -> mTvRainChance.setText(getString(R.string.rain_chance_field, rainChance)));
    }

    private void setHumidity() {
        mViewModel.getHumidity().observe(this, humidity -> mTvHumidity.setText(getString(R.string.humidity_field, humidity)));
    }

    private void setForecastSummary() {
        mViewModel.getForecastSummary().observe(this, summary -> mTvForecastSummary.setText(summary));
    }

    private void setSunriseTime() {
        mViewModel.getSunriseTime().observe(this, sunriseTime -> mTvSunrise.setText(sunriseTime));
    }
    private void setSunsetTime() {
        mViewModel.getSunsetTime().observe(this, sunsetTime -> mTvSunrise.setText(sunsetTime));
    }

}
