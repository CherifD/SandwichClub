package com.cherifcodes.sandwichclub.utils;

import com.cherifcodes.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    /**
     * Parses a JSON string to create a Sandwich object
     * @param json the JSON string to parse
     * @return the newly created Sandwich object
     */
    public static Sandwich parseSandwichJson(String json) {

        json = json.replaceAll("\\+", ""); //Remove all back-slashes.
        Sandwich sandwich = null;
        try {
            List<String> mAlsoKnownAs = new ArrayList<>();
            List<String> mIngredients = new ArrayList<>();

            JSONObject sandwichJson = new JSONObject(json);
            JSONObject name = sandwichJson.getJSONObject(NAME);
            String mainName = name.getString(MAIN_NAME);

            JSONArray alsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS);

            for (int i = 0; i < alsoKnownAs.length(); i++) {
                mAlsoKnownAs.add(alsoKnownAs.getString(i));
            }

            String placeOfOrigin = sandwichJson.getString(PLACE_OF_ORIGIN);
            String description = sandwichJson.getString(DESCRIPTION);
            String image = sandwichJson.getString(IMAGE);
            JSONArray ingredients = sandwichJson.getJSONArray(INGREDIENTS);

            for (int i = 0; i < ingredients.length(); i++) {
                mIngredients.add(ingredients.getString(i));
            }

            sandwich = new Sandwich(mainName, mAlsoKnownAs, placeOfOrigin, description,
                    image, mIngredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
