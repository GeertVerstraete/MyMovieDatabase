package aryastark.com.mymoviedatabase;

import android.app.Application;
import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.config.Configuration;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.results.ResultList;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static aryastark.com.mymoviedatabase.R.id.recyclerView;


/**
 * Created by aryastark on 17/10/16.
 */

public class AppController extends Application {
    private static final java.lang.String API_KEY = "8ab7ac7914a70882f89399859d2bb935";
    private List<MovieBasic> movieList = new ArrayList<>();

    private static AppController instance;
    private List<OnMovieChangedListener> allListeners = new ArrayList<>();

    public List<MovieBasic> getMovieList() {
        return movieList;
    }


    public AppController() {
    }

    private static final String TAG = "AppController";


    Configuration configuration;
    TheMovieDbApi api;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.ic_hourglass_empty_black_24dp)
                .showImageOnLoading(R.drawable.ic_hourglass_empty_black_24dp).displayer(new FadeInBitmapDisplayer(500)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .denyCacheImageMultipleSizesInMemory()
                .build();

        ImageLoader.getInstance().init(config);


        try {
            api = new TheMovieDbApi(API_KEY);
            AppController.FetchConfiguration fetchConfiguration = new FetchConfiguration();
            fetchConfiguration.execute();
            AppController.FetchMovieInfo fetchMovieInfo = new FetchMovieInfo();
            fetchMovieInfo.execute();

        } catch (MovieDbException e) {
            e.printStackTrace();
            Log.e("TheMovieDbApi", "Error" + e.getMessage());
        }


    }

    private class FetchMovieInfo extends AsyncTask<Void, Void, ResultList<MovieBasic>> {

        @Override
        protected ResultList<MovieBasic> doInBackground(Void... params) {
            try {
                return api.getDiscoverMovies(new Discover());
            } catch (MovieDbException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResultList<MovieBasic> movieBasicResultList) {
            super.onPostExecute(movieBasicResultList);
            Log.v("Found: ", movieBasicResultList.toString());
            movieList.clear();
            movieList.addAll(movieBasicResultList.getResults());
            notifyAllListeners();
//                Log.v("Movie 0:", movieBasicResultList.getResults().get(0).getTitle() + " " + movieBasicResultList.getResults().get(0).getOverview());
        }
    }

    public void fetchUniqueMovie(int id) {
        FetchUniqueMovieInfo fetchUniqueMovieInfo= new FetchUniqueMovieInfo();
        fetchUniqueMovieInfo.execute(id);
    }

    private class FetchUniqueMovieInfo extends AsyncTask<Integer, Void, MovieInfo> {

        @Override
        protected MovieInfo doInBackground(Integer... params) {
            try {
                return api.getMovieInfo(params[0].intValue(), "en");
            } catch (MovieDbException e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(MovieInfo movieInfo) {
            super.onPostExecute(movieInfo);
            Intent intent = new Intent(AppController.this, MovieDetails.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("movieInfo", movieInfo);
            //TODO intent to movieDetails
            startActivity(intent);
        }
    }

    private class FetchConfiguration extends AsyncTask<Void, Void, Configuration> {
        @Override
        protected Configuration doInBackground(Void... params) {
            try {
                return api.getConfiguration();
            } catch (MovieDbException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Configuration configuration) {
            AppController.this.configuration = configuration;
        }
    }

    public interface OnMovieChangedListener {
        void onMovieChanged();

    }


    public static AppController getInstance() {
        return instance;
    }

    public void addMovieListener(OnMovieChangedListener listener) {
        allListeners.add(listener);
    }

    public void removeMovieListener(OnMovieChangedListener listener) {
        allListeners.remove(listener);
    }

    private void notifyAllListeners() {
        for (OnMovieChangedListener listener : allListeners) {
            listener.onMovieChanged();
        }
    }

    public URL createImageUrl(String imagePath, String size) {
        try {
            return configuration.createImageUrl(imagePath, size);
        } catch (MovieDbException e) {
            e.printStackTrace();
        }
        return null;
    }


}
