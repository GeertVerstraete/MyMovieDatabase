package aryastark.com.mymoviedatabase;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by aryastark on 19/10/16.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {
    private TextView releaseYearTextView;
    private CardView cardView;
    private ImageView imageView;
    private TextView titleTextView;
    private TextView taglineTextView;
    private TextView scoreTextView;

    public MovieViewHolder(View view) {
        super(view);
        cardView = (CardView) view.findViewById(R.id.cardView);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        titleTextView = (TextView) view.findViewById(R.id.title);
        taglineTextView = (TextView) view.findViewById(R.id.tagline);
        scoreTextView = (TextView) view.findViewById(R.id.score);
        releaseYearTextView = (TextView) view.findViewById(R.id.releaseYear);
    }

    public TextView getScoreTextView() {
        return scoreTextView;
    }

    public TextView getReleaseYearTextView() {
        return releaseYearTextView;
    }

    public void setReleaseYearTextView(TextView releaseYearTextView) {
        this.releaseYearTextView = releaseYearTextView;
    }

    public void setScoreTextView(TextView scoreTextView) {
        this.scoreTextView = scoreTextView;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setTitleTextView(TextView titleTextView) {
        this.titleTextView = titleTextView;
    }

    public TextView getTaglineTextView() {
        return taglineTextView;
    }

    public void setTaglineTextView(TextView taglineTextView) {
        this.taglineTextView = taglineTextView;
    }


}
