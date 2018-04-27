# Weatherful
Android weather app developed according to the MVP architectural pattern.

- Free APIs that have been used:
	- Dark Sky weather API ( https://darksky.net/dev ) - used for getting weather parameters.
	- Qwant API ( https://www.qwant.com/ ) - used for displaying images of the locations.
	- Geocode API ( https://maps.googleapis.com/maps/api/geocode/ ) - used for determining the location's coordinates from its name and country.


- Libraries that have been used:
	- Android Support Library
	- RecyclerView ( https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html )
	- Retrofit (by Square - http://square.github.io/retrofit/ ) - used for handling network requests.
	- Butter Knife (by Jake Wharton - http://jakewharton.github.io/butterknife/ ) - used for data binding.
	- Glide (by Bumptech - https://github.com/bumptech/glide ) - used for image loading.
	- Dagger2 (by Google - https://github.com/google/dagger) - used for dependency injection.
	- RxJava2 (by ReactiveX - https://github.com/ReactiveX/RxJava) - used for for composing asynchronous and event-based programs using the observable pattern. 
	- PagerIndicatorView (by romandanylyk - https://github.com/romandanylyk/PageIndicatorView) - used for indication of fragments in view pager.


- Analytics service:
	- Crashlytics (by Fabric - https://try.crashlytics.com/ )
	

- Database that has been used:
	- Room (Android Architecture Components - https://developer.android.com/topic/libraries/architecture/room)

- Functionalities:
	- Add locations (using the floating action button on the bottom right).
	- Delete locations (just by pressing longer on the location card).
	- Gain accurate summary of the weather conditions for the day.
	- Horizontally scroll through a weekly forecast for this certain location (just by clicking ones on the location card).
	- Get more detailed forecast information for a certain day (just by clicking on one of the forecast cards).
	- Horizontally scroll between the diffrent detailed forecasts.

 
