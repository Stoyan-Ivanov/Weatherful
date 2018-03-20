package com.stoyan.weatherful.ui.forecast_activity;


import android.content.Intent;
import android.util.Log;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.network.NetworkManager;
import com.stoyan.weatherful.network.models.forecast_full_models.ForecastFullResponse;
import com.stoyan.weatherful.ui.forecast_pager_activity.ForecastPagerActivity;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.ForecastRecyclerviewAdapter;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.OnItemClickListener;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ForecastActivityPresenter implements ForecastActivityContract {
    private Location location;
    private ForecastActivity forecastActivity;
    private static CompositeDisposable disposables = new CompositeDisposable();

    public ForecastActivityPresenter(Intent intent, ForecastActivity activity) {
        getExtras(intent);
        forecastActivity = activity;
    }

    @Override
    public ForecastRecyclerviewAdapter getAdapter() {
            return new ForecastRecyclerviewAdapter(location, new OnItemClickListener() {
                @Override
                public void OnItemClick(final ArrayList<Data> data, final int position) {
                    startNewActivity(data, position);
                }
            });
    }

    @Override
    public String getHeader() {
        return location.getLocationName() + "," + location.getCountry();
    }


    @Override
    public void getExtras(Intent intent) {
        location = intent.getParcelableExtra(Constants.EXTRA_LOCATION);
    }

    private void startNewActivity(final ArrayList<Data> data, final int position) {
        Intent intent = new Intent(forecastActivity, ForecastPagerActivity.class);
        intent.putExtra(Constants.EXTRA_LOCATION, location);
        intent.putExtra(Constants.EXTRA_DATA, data);
        intent.putExtra(Constants.EXTRA_POSITION, position);

        forecastActivity.startActivity(intent);
    }


    public static void getWeeklyForecast(final Location location, final ForecastRecyclerviewAdapter adapter) {
        Observable<ForecastFullResponse> observableFullForecast = NetworkManager
                .getInstance()
                .getWeatherfulAPI()
                .getFullForecastResponse(location.getLatitude(),
                location.getLongitude());

        observableFullForecast
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getWeeklyForecastObserver(adapter));
    }

    private static DisposableObserver<ForecastFullResponse> getWeeklyForecastObserver(final ForecastRecyclerviewAdapter adapter) {
        DisposableObserver<ForecastFullResponse> weeklyForecastObserver = new DisposableObserver<ForecastFullResponse>() {
            @Override
            public void onNext(ForecastFullResponse forecastFullResponse) {
                adapter.setNewData(forecastFullResponse.getDaily().getData());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("SII", "onError: full forecast " + e.getMessage());
            }

            @Override
            public void onComplete() {}
        };

        disposables.add(weeklyForecastObserver);
        return weeklyForecastObserver;
    }

    @Override
    public void onViewDestroy() {
        forecastActivity = null;
        //disposables.dispose();
    }
}
