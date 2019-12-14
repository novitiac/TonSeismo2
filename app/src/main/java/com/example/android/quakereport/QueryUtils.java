/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Sample JSON response for a USGS query */
    private static final String SAMPLE_JSON_RESPONSE = "{\"type\":\"FeatureCollection\",\"metadata\":{\"generated\":1575427065000,\"url\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2019-11-01&endtime=2019-12-03&minmag=6&limit=20\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.8.1\",\"limit\":20,\"offset\":1,\"count\":18},\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"60km E of Amatignak Island, Alaska\",\"time\":1575262914693,\"updated\":1575349645803,\"tz\":-600,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70006f6d\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70006f6d&format=geojson\",\"felt\":1,\"cdi\":6.4000000000000004,\"mmi\":4.4429999999999996,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":554,\"net\":\"us\",\"code\":\"70006f6d\",\"ids\":\",at00q1vcn7,us70006f6d,ak019ffrk3rj,\",\"sources\":\",at,us,ak,\",\"types\":\",dyfi,geoserve,ground-failure,impact-link,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":0.86199999999999999,\"rms\":0.96999999999999997,\"gap\":104,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - 60km E of Amatignak Island, Alaska\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-178.24250000000001,51.321800000000003,27.329999999999998]},\"id\":\"us70006f6d\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"41km NW of Platanos, Greece\",\"time\":1574839422552,\"updated\":1575415107456,\"tz\":120,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70006dlt\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70006dlt&format=geojson\",\"felt\":108,\"cdi\":5.7000000000000002,\"mmi\":4.4619999999999997,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":615,\"net\":\"us\",\"code\":\"70006dlt\",\"ids\":\",us70006dlt,\",\"sources\":\",us,\",\"types\":\",dyfi,geoserve,ground-failure,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":1.3939999999999999,\"rms\":1.1599999999999999,\"gap\":23,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - 41km NW of Platanos, Greece\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[23.267299999999999,35.727200000000003,71.760000000000005]},\"id\":\"us70006dlt\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.4000000000000004,\"place\":\"16km WSW of Mamurras, Albania\",\"time\":1574736852594,\"updated\":1575421717816,\"tz\":60,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70006d0m\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70006d0m&format=geojson\",\"felt\":1346,\"cdi\":9.0999999999999996,\"mmi\":8.2789999999999999,\"alert\":\"orange\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":1910,\"net\":\"us\",\"code\":\"70006d0m\",\"ids\":\",us70006d0m,\",\"sources\":\",us,\",\"types\":\",dyfi,general-text,geoserve,ground-failure,impact-text,losspager,moment-tensor,origin,phase-data,poster,shakemap,\",\"nst\":null,\"dmin\":0.93700000000000006,\"rms\":0.57999999999999996,\"gap\":17,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.4 - 16km WSW of Mamurras, Albania\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[19.5151,41.511200000000002,20]},\"id\":\"us70006d0m\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.2999999999999998,\"place\":\"85km ESE of Adak, Alaska\",\"time\":1574556842199,\"updated\":1575421448251,\"tz\":-720,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70006cb6\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70006cb6&format=geojson\",\"felt\":5,\"cdi\":2.6000000000000001,\"mmi\":4.7210000000000001,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":612,\"net\":\"us\",\"code\":\"70006cb6\",\"ids\":\",at00q1g7u3,us70006cb6,ak019f2glesg,\",\"sources\":\",at,us,ak,\",\"types\":\",dyfi,geoserve,ground-failure,impact-link,losspager,moment-tensor,oaf,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":0.64600000000000002,\"rms\":1.1499999999999999,\"gap\":49,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.3 - 85km ESE of Adak, Alaska\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-175.55930000000001,51.527099999999997,25.059999999999999]},\"id\":\"us70006cb6\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.0999999999999996,\"place\":\"Papua region, Indonesia\",\"time\":1574511076261,\"updated\":1574715633040,\"tz\":540,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70006c6w\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70006c6w&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":3.1539999999999999,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":572,\"net\":\"us\",\"code\":\"70006c6w\",\"ids\":\",us70006c6w,\",\"sources\":\",us,\",\"types\":\",geoserve,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":4.5490000000000004,\"rms\":1.1000000000000001,\"gap\":38,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - Papua region, Indonesia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[132.78540000000001,1.6286,10]},\"id\":\"us70006c6w\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.0999999999999996,\"place\":\"31km ESE of Chaloem Phra Kiat, Thailand\",\"time\":1574293844205,\"updated\":1574778233172,\"tz\":420,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70006ara\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70006ara&format=geojson\",\"felt\":384,\"cdi\":4.9000000000000004,\"mmi\":6.6289999999999996,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":761,\"net\":\"us\",\"code\":\"70006ara\",\"ids\":\",us70006ara,\",\"sources\":\",us,\",\"types\":\",dyfi,geoserve,ground-failure,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":2.3559999999999999,\"rms\":0.77000000000000002,\"gap\":33,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 31km ESE of Chaloem Phra Kiat, Thailand\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[101.3449,19.4512,10]},\"id\":\"us70006ara\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.2999999999999998,\"place\":\"267km NW of Ozernovskiy, Russia\",\"time\":1574238367542,\"updated\":1574324940055,\"tz\":600,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70006a9e\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70006a9e&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":0,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":611,\"net\":\"us\",\"code\":\"70006a9e\",\"ids\":\",us70006a9e,\",\"sources\":\",us,\",\"types\":\",geoserve,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":2.4180000000000001,\"rms\":0.98999999999999999,\"gap\":38,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.3 - 267km NW of Ozernovskiy, Russia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[153.68520000000001,53.1633,486.81]},\"id\":\"us70006a9e\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.2999999999999998,\"place\":\"111km SW of Puerto Madero, Mexico\",\"time\":1574224025921,\"updated\":1574579863405,\"tz\":-360,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70006a6q\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70006a6q&format=geojson\",\"felt\":76,\"cdi\":5.5,\"mmi\":3.944,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":652,\"net\":\"us\",\"code\":\"70006a6q\",\"ids\":\",us70006a6q,\",\"sources\":\",us,\",\"types\":\",dyfi,geoserve,ground-failure,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":1.492,\"rms\":1.1899999999999999,\"gap\":67,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.3 - 111km SW of Puerto Madero, Mexico\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-93.129800000000003,13.9824,10.970000000000001]},\"id\":\"us70006a6q\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"136km NW of Kota Ternate, Indonesia\",\"time\":1573765974750,\"updated\":1574057931983,\"tz\":480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us60006bpw\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us60006bpw&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":5.1680000000000001,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":554,\"net\":\"us\",\"code\":\"60006bpw\",\"ids\":\",us60006bpw,\",\"sources\":\",us,\",\"types\":\",geoserve,ground-failure,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":1.2370000000000001,\"rms\":1.29,\"gap\":18,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - 136km NW of Kota Ternate, Indonesia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[126.4136,1.5672999999999999,23]},\"id\":\"us60006bpw\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":7.0999999999999996,\"place\":\"138km E of Bitung, Indonesia\",\"time\":1573748260457,\"updated\":1574190683695,\"tz\":480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us60006bjl\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us60006bjl&format=geojson\",\"felt\":37,\"cdi\":5.7999999999999998,\"mmi\":6.4470000000000001,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":797,\"net\":\"us\",\"code\":\"60006bjl\",\"ids\":\",pt19318001,at00q0yvxj,us60006bjl,\",\"sources\":\",pt,at,us,\",\"types\":\",dyfi,finite-fault,general-text,geoserve,ground-failure,impact-link,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":1.2769999999999999,\"rms\":1.05,\"gap\":11,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.1 - 138km E of Bitung, Indonesia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[126.4144,1.6294,33]},\"id\":\"us60006bjl\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.0999999999999996,\"place\":\"146km NW of Pangai, Tonga\",\"time\":1573513408056,\"updated\":1573600235085,\"tz\":-720,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us60006adv\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us60006adv&format=geojson\",\"felt\":6,\"cdi\":6.7999999999999998,\"mmi\":4.415,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":577,\"net\":\"us\",\"code\":\"60006adv\",\"ids\":\",us60006adv,\",\"sources\":\",us,\",\"types\":\",dyfi,geoserve,ground-failure,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":5.1440000000000001,\"rms\":0.75,\"gap\":21,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 146km NW of Pangai, Tonga\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-175.35980000000001,-18.8857,10]},\"id\":\"us60006adv\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.5,\"place\":\"167km SSW of Ndoi Island, Fiji\",\"time\":1573209884876,\"updated\":1573296412562,\"tz\":-720,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us6000693m\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us6000693m&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":2.427,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":650,\"net\":\"us\",\"code\":\"6000693m\",\"ids\":\",us6000693m,\",\"sources\":\",us,\",\"types\":\",geoserve,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":4.7949999999999999,\"rms\":0.89000000000000001,\"gap\":32,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.5 - 167km SSW of Ndoi Island, Fiji\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-179.49109999999999,-21.968800000000002,582.91999999999996]},\"id\":\"us6000693m\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"33km ENE of Sola, Vanuatu\",\"time\":1573000749303,\"updated\":1573782615583,\"tz\":660,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us700063sp\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us700063sp&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":5.9790000000000001,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":554,\"net\":\"us\",\"code\":\"700063sp\",\"ids\":\",us700063sp,\",\"sources\":\",us,\",\"types\":\",geoserve,ground-failure,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":1.8129999999999999,\"rms\":1.0700000000000001,\"gap\":36,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - 33km ENE of Sola, Vanuatu\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[167.80860000000001,-13.720800000000001,10]},\"id\":\"us700063sp\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"22km ENE of Sola, Vanuatu\",\"time\":1572995845042,\"updated\":1573689169085,\"tz\":660,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us700063rx\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us700063rx&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":5.7679999999999998,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":554,\"net\":\"us\",\"code\":\"700063rx\",\"ids\":\",us700063rx,\",\"sources\":\",us,\",\"types\":\",geoserve,ground-failure,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":1.712,\"rms\":1.0900000000000001,\"gap\":38,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - 22km ENE of Sola, Vanuatu\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[167.74039999999999,-13.8056,10]},\"id\":\"us700063rx\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.2999999999999998,\"place\":\"East of the South Sandwich Islands\",\"time\":1572987121296,\"updated\":1573081662888,\"tz\":-60,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us700063mh\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us700063mh&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":0,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":611,\"net\":\"us\",\"code\":\"700063mh\",\"ids\":\",us700063mh,\",\"sources\":\",us,\",\"types\":\",geoserve,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":14.018000000000001,\"rms\":1.3700000000000001,\"gap\":73,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.3 - East of the South Sandwich Islands\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-9.2700999999999993,-57.964300000000001,10]},\"id\":\"us700063mh\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.5999999999999996,\"place\":\"136km W of Neiafu, Tonga\",\"time\":1572907412280,\"updated\":1573617199326,\"tz\":-720,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us7000635e\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us7000635e&format=geojson\",\"felt\":7,\"cdi\":3.7999999999999998,\"mmi\":4.3109999999999999,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":673,\"net\":\"us\",\"code\":\"7000635e\",\"ids\":\",pt19308001,at00q0gv4p,us7000635e,\",\"sources\":\",pt,at,us,\",\"types\":\",dyfi,geoserve,ground-failure,impact-link,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":5.0860000000000003,\"rms\":0.93000000000000005,\"gap\":48,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.6 - 136km W of Neiafu, Tonga\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-175.27199999999999,-18.5747,10]},\"id\":\"us7000635e\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.0999999999999996,\"place\":\"30km SW of Illapel, Chile\",\"time\":1572904405320,\"updated\":1575087831586,\"tz\":-240,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70006345\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70006345&format=geojson\",\"felt\":293,\"cdi\":6.0999999999999996,\"mmi\":6.7249999999999996,\"alert\":\"yellow\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":829,\"net\":\"us\",\"code\":\"70006345\",\"ids\":\",us70006345,\",\"sources\":\",us,\",\"types\":\",dyfi,geoserve,ground-failure,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":0.70699999999999996,\"rms\":1.1399999999999999,\"gap\":23,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 30km SW of Illapel, Chile\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-71.375299999999996,-31.836200000000002,53]},\"id\":\"us70006345\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.0999999999999996,\"place\":\"129km NNE of Visokoi Island, South Georgia and the South Sandwich Islands\",\"time\":1572718121079,\"updated\":1572970462890,\"tz\":-120,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us700062cg\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us700062cg&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":3.5379999999999998,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":572,\"net\":\"us\",\"code\":\"700062cg\",\"ids\":\",us700062cg,\",\"sources\":\",us,\",\"types\":\",geoserve,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":6.0650000000000004,\"rms\":1.29,\"gap\":23,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 129km NNE of Visokoi Island, South Georgia and the South Sandwich Islands\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-26.233799999999999,-55.665900000000001,8]},\"id\":\"us700062cg\"}],\"bbox\":[-179.4911,-57.9643,8,167.8086,53.1633,582.92]}";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakes() {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the SAMPLE_JSON_RESPONSE string
            JSONObject baseJsonResponse = new JSONObject(SAMPLE_JSON_RESPONSE);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < earthquakeArray.length(); i++) {

                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);

                // For a given earthquake, extract the JSONObject associated with the
                // key called "properties", which represents a list of all properties
                // for that earthquake.
                JSONObject properties = currentEarthquake.getJSONObject("properties");

                // Extract the value for the key called "mag"
                double magnitude = properties.getDouble("mag");

                // Extract the value for the key called "place"
                String location = properties.getString("place");

                // Extract the value for the key called "time"
                long time = properties.getLong("time");

                // Extract the value for the key called "url"
                String url = properties.getString("url");

                // Create a new {@link Earthquake} object with the magnitude, location, time,
                // and url from the JSON response.
                Earthquake earthquake = new Earthquake(magnitude, location, time, url);

                // Add the new {@link Earthquake} to the list of earthquakes.
                earthquakes.add(earthquake);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}
