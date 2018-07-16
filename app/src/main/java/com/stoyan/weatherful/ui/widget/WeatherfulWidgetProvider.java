package com.stoyan.weatherful.ui.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.stoyan.weatherful.DataManager;
import com.stoyan.weatherful.LocationTracker;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.dependency_injection.components.AppComponent;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.persistence.models.LocationForecastSummaryWrapper;
import com.stoyan.weatherful.ui.location_activity.LocationActivity;
import com.stoyan.weatherful.utils.StartActivityEnum;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherfulWidgetProvider extends AppWidgetProvider {


    @SuppressLint("CheckResult")
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weatherful_widget);

            mLocationTracker.getCurrentLocation().subscribe(location -> {
                views.setTextViewText(R.id.tv_widget_location, location.getLocationName());

                Observable.just(location).flatMap(location1 -> mDataManager
                        .getCurrentLocationDataObservable(new LocationForecastSummaryWrapper(location1)))
                        .subscribe(locationForecastSummaryWrapper -> {
                            views.setTextViewText(R.id.tv_widget_forecast_summary,
                                    locationForecastSummaryWrapper.getForecastSummaryResponse().getHourly().getSummary());

                            views.setTextViewText(R.id.tv_widget_temperature,
                                    String.valueOf(locationForecastSummaryWrapper
                                            .getForecastSummaryResponse().getHourly().getData().get(0).getTemperature()));
                        });
            });


            appWidgetManager.updateAppWidget(appWidgetIds, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);


    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

