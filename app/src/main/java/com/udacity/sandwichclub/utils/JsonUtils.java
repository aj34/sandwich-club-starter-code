package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = new Sandwich();

        if (json != null) {
            JSONObject sandwichJson = new JSONObject(json);

            JSONObject name = sandwichJson.optJSONObject("name");
            sandwich.setMainName(name.getString("mainName"));

            JSONArray alsoKnownJson = name.optJSONArray("alsoKnownAs");
            sandwich.setAlsoKnownAs(jsonArrayToList(alsoKnownJson));

            JSONArray ingredientsJson = sandwichJson.optJSONArray("ingredients");
            sandwich.setIngredients(jsonArrayToList(ingredientsJson));

            sandwich.setDescription(sandwichJson.optString("description"));
            sandwich.setImage(sandwichJson.optString("image"));
            sandwich.setPlaceOfOrigin(sandwichJson.optString("placeOfOrigin"));
        }

        return sandwich;
    }

    private static List<String> jsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
