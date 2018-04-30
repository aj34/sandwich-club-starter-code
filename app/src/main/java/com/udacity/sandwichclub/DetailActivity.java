package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mDescription;
    private TextView mOrigin;
    private TextView mAlsoKnownAs;
    private TextView mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        try {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);

            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (!sandwich.getDescription().isEmpty()) {
            mDescription = (TextView) findViewById(R.id.description_tv);
            mDescription.setText(sandwich.getDescription());
        } else {
            hideLabel((TextView) findViewById(R.id.description_label));
        }

        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            mOrigin = (TextView) findViewById(R.id.origin_tv);
            mOrigin.setText(sandwich.getPlaceOfOrigin());
        } else {
            hideLabel((TextView) findViewById(R.id.origin_label));
        }

        if (sandwich.getIngredients().size() > 0) {
            mIngredients = (TextView) findViewById(R.id.ingredients_tv);
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                if (i == 0 ) {
                    mIngredients.setText(sandwich.getIngredients().get(i));
                } else {
                    mIngredients.setText(mIngredients.getText() + ", " + sandwich.getIngredients().get(i));
                }
            }
        } else {
            hideLabel((TextView) findViewById(R.id.ingredients_label));
        }

        if (sandwich.getAlsoKnownAs().size() > 0) {
            mAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                mAlsoKnownAs.setText(mAlsoKnownAs.getText() + "\t \u2022 " + sandwich.getAlsoKnownAs().get(i) + "\n");
            }
        } else {
            hideLabel((TextView) findViewById(R.id.also_known_label));
        }
    }

    private void hideLabel(TextView textView) {
        textView.setVisibility(View.INVISIBLE);
    }
}
