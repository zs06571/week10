package com.example.moviedatabase;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonUtility {
    private static final String TAG = "JsonUtility";

    public static List<Movie> loadMovies(Context context) {
        List<Movie> movieList = new ArrayList<>();
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.movies);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();
            inputStream.close();

            Log.d(TAG, "JSON content: " + jsonBuilder.toString());

            JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
            Log.d(TAG, "JSON length: " + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieObject = jsonArray.getJSONObject(i);

                if (movieObject.length() == 0) {
                    Log.w(TAG, "skip empty: index=" + i);
                    continue;
                }

                String title = movieObject.optString("title", "Unknown Title");

                int year = 0;
                if (movieObject.has("year")) {
                    try {
                        Object yearObj = movieObject.get("year");
                        if (yearObj instanceof Integer) {
                            year = (Integer) yearObj;
                        } else if (yearObj instanceof String) {
                            year = 0;
                        } else if (yearObj instanceof Double) {
                            year = 0;
                        }
                        if (year < 0) {
                            Log.w(TAG, "Invalid year: " + year + "，defalt value 0");
                            year = 0;
                        }
                    } catch (Exception e) {
                        ErrorHandler.handleError(context, e, "Invalid year: " + movieObject.optString("year"));
                        Log.w(TAG, "Invalid year: " + movieObject.optString("year") + "，defalt value 0");
                    }
                }

                String genre = movieObject.optString("genre", "Unknown Genre");

                String posterName = movieObject.optString("poster", "placeholder");
                int posterResourceId = context.getResources().getIdentifier(
                        posterName, "drawable", context.getPackageName()
                );
                if (posterResourceId == 0) {
                    posterResourceId = R.drawable.placeholder;
                }

                Log.d(TAG, "movie: " + title + " (" + year + ")");

                movieList.add(new Movie(title, year, genre, posterResourceId));
            }

            Log.d(TAG, "Total movie: " + movieList.size());
        } catch (JSONException e) {
            ErrorHandler.handleError(context, e, "JSON warning: " + e.getMessage());
            Log.e(TAG, "JSON warning: " + e.getMessage());
        } catch (Exception e) {
            ErrorHandler.handleError(context, e, "load movie fail: " + e.getMessage());
            Log.e(TAG, "load movie fail: " + e.getMessage(), e);
        }
        return movieList;
    }
}
