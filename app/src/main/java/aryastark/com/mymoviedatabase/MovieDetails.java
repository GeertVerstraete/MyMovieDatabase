package aryastark.com.mymoviedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.omertron.themoviedbapi.model.movie.MovieInfo;


public class MovieDetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        TextView descriptionTextView = (TextView) findViewById(R.id.detailDescription);
        TextView titleTextView = (TextView) findViewById(R.id.detailTitle);
        ImageView imageView = (ImageView) findViewById(R.id.detailImageView);
        //TODO get movieInfo from intent and fill up views
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Toast.makeText(this, "no details for this movie !", Toast.LENGTH_SHORT).show();
        }

        MovieInfo value = (MovieInfo) extras.get("movieInfo");
        if (value != null) {
            descriptionTextView.setText(value.getOverview());
            titleTextView.setText(value.getTitle());
            ImageLoader.getInstance().displayImage(AppController.getInstance().createImageUrl(value.getBackdropPath(), "w780").toString(), imageView);

        }

    }

}
