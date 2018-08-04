package com.cherifcodes.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cherifcodes.sandwichclub.model.Sandwich;
import com.cherifcodes.sandwichclub.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mOriginTextView;
    TextView mAlsoKnownAsTextView;
    TextView mDescriptionTextView;
    TextView mIngredientsTextView;

    Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mOriginTextView = findViewById(R.id.origin_tv);
        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        //Display image
        Picasso.with(this)
                .load(mSandwich.getImage())
                .error(R.drawable.ic_missing_image_error) //Displays this image if image failed to load
                .into(ingredientsIv);

        populateUI();

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        //Display place of origin
        String placeOfOrigin = mSandwich.getPlaceOfOrigin();
        if (TextUtils.isEmpty(placeOfOrigin)) {
            mOriginTextView.setText(R.string.no_placeOfOriging_error_message);
        } else {
            mOriginTextView.setText(placeOfOrigin);
        }

        //Display nicknames
        List<String> nickNames = mSandwich.getAlsoKnownAs();
        if (nickNames.size() == 0) { //No nickname available, display error message
            mAlsoKnownAsTextView.setText(R.string.no_nickName_error_message);
        } else { //Display nicknames.
            for (String nickName: nickNames) {
                mAlsoKnownAsTextView.append(nickName + "\n");
            }
        }

        //Display description
        mDescriptionTextView.setText(mSandwich.getDescription());

        //Display ingredients
        for (String ingredient: mSandwich.getIngredients()) {
           mIngredientsTextView.append(ingredient + "\n");
        }
    }
}
