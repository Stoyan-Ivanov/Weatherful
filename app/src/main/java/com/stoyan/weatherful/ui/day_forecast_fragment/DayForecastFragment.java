package com.stoyan.weatherful.ui.day_forecast_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DayForecastFragment extends Fragment {

    @BindView(R.id.iv_fragment_weather_image)
    ImageView weatherImage;

    @BindView(R.id.tv_fragment_date)
    TextView tvDate;

    @BindView(R.id.tv_fragment_min_temp)
    TextView tvMinTemperature;

    @BindView(R.id.tv_fragment_max_temp)
    TextView tvMaxTemperature;

    @BindView(R.id.tv_fragment_wind_speed)
    TextView tvWindSpeed;

    @BindView(R.id.tv_fragment_rain_chance)
    TextView tvRainChance;

    private Unbinder unbinder;
    private DayForecastFragmentPresenter presenter;

    public static DayForecastFragment newInstance(Data data) {

        Bundle arguments = new Bundle();
        arguments.putParcelable(Constants.EXTRA_DATA, data);

        DayForecastFragment fragment = new DayForecastFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public DayForecastFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_forecast, container, false);

        presenter = new DayForecastFragmentPresenter(getArguments());
        unbinder = ButterKnife.bind(this, view);

        weatherImage.setImageDrawable(presenter.getImageDrawable());

        tvDate.setText(presenter.getDate());

        tvMinTemperature.setText(presenter.getMinTemperature());

        tvMaxTemperature.setText(presenter.getMaxTemperature());

        tvWindSpeed.setText(presenter.getWindSpeed());

        tvRainChance.setText(presenter.getRainChance());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
