package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.TimeZone;

/**
 * Helper methods related to requesting and receiving article data from news api.
 */
public final class QueryUtils {

    /** Sample JSON response for a USGS query */
  /*  private static final String SAMPLE_JSON_RESPONSE = "{\"type\":\"FeatureCollection\",\"metadata\":{\"generated\":1462295443000,\"url\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmag=6&limit=10\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.5.2\",\"limit\":10,\"offset\":1,\"count\":10},\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":7.2,\"place\":\"88km N of Yelizovo, Russia\",\"time\":1454124312220,\"updated\":1460674294040,\"tz\":720,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us20004vvx\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004vvx&format=geojson\",\"felt\":2,\"cdi\":3.4,\"mmi\":5.82,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":798,\"net\":\"us\",\"code\":\"20004vvx\",\"ids\":\",at00o1qxho,pt16030050,us20004vvx,gcmt20160130032510,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,finite-fault,general-link,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":0.958,\"rms\":1.19,\"gap\":17,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.2 - 88km N of Yelizovo, Russia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[158.5463,53.9776,177]},\"id\":\"us20004vvx\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.1,\"place\":\"94km SSE of Taron, Papua New Guinea\",\"time\":1453777820750,\"updated\":1460156775040,\"tz\":600,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us20004uks\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004uks&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":4.1,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":572,\"net\":\"us\",\"code\":\"20004uks\",\"ids\":\",us20004uks,gcmt20160126031023,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,geoserve,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.537,\"rms\":0.74,\"gap\":25,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 94km SSE of Taron, Papua New Guinea\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[153.2454,-5.2952,26]},\"id\":\"us20004uks\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.3,\"place\":\"50km NNE of Al Hoceima, Morocco\",\"time\":1453695722730,\"updated\":1460156773040,\"tz\":0,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004gy9\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004gy9&format=geojson\",\"felt\":117,\"cdi\":7.2,\"mmi\":5.28,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":695,\"net\":\"us\",\"code\":\"10004gy9\",\"ids\":\",us10004gy9,gcmt20160125042203,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":2.201,\"rms\":0.92,\"gap\":20,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.3 - 50km NNE of Al Hoceima, Morocco\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-3.6818,35.6493,12]},\"id\":\"us10004gy9\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":7.1,\"place\":\"86km E of Old Iliamna, Alaska\",\"time\":1453631430230,\"updated\":1460156770040,\"tz\":-540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004gqp\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004gqp&format=geojson\",\"felt\":1816,\"cdi\":7.2,\"mmi\":6.6,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":1496,\"net\":\"us\",\"code\":\"10004gqp\",\"ids\":\",at00o1gd6r,us10004gqp,ak12496371,gcmt20160124103030,\",\"sources\":\",at,us,ak,gcmt,\",\"types\":\",cap,dyfi,finite-fault,general-link,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,trump-origin,\",\"nst\":null,\"dmin\":0.72,\"rms\":2.11,\"gap\":19,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.1 - 86km E of Old Iliamna, Alaska\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-153.4051,59.6363,129]},\"id\":\"us10004gqp\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.6,\"place\":\"215km SW of Tomatlan, Mexico\",\"time\":1453399617650,\"updated\":1459963829040,\"tz\":-420,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004g4l\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004g4l&format=geojson\",\"felt\":11,\"cdi\":2.7,\"mmi\":3.92,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":673,\"net\":\"us\",\"code\":\"10004g4l\",\"ids\":\",at00o1bebo,pt16021050,us10004g4l,gcmt20160121180659,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":2.413,\"rms\":0.98,\"gap\":74,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.6 - 215km SW of Tomatlan, Mexico\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-106.9337,18.8239,10]},\"id\":\"us10004g4l\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.7,\"place\":\"52km SE of Shizunai, Japan\",\"time\":1452741933640,\"updated\":1459304879040,\"tz\":540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004ebx\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004ebx&format=geojson\",\"felt\":51,\"cdi\":5.8,\"mmi\":6.45,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":720,\"net\":\"us\",\"code\":\"10004ebx\",\"ids\":\",us10004ebx,pt16014050,at00o0xauk,gcmt20160114032534,\",\"sources\":\",us,pt,at,gcmt,\",\"types\":\",associate,cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":0.281,\"rms\":0.98,\"gap\":22,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.7 - 52km SE of Shizunai, Japan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[142.781,41.9723,46]},\"id\":\"us10004ebx\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.1,\"place\":\"12km WNW of Charagua, Bolivia\",\"time\":1452741928270,\"updated\":1459304879040,\"tz\":-240,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004ebw\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004ebw&format=geojson\",\"felt\":3,\"cdi\":2.2,\"mmi\":2.21,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":573,\"net\":\"us\",\"code\":\"10004ebw\",\"ids\":\",us10004ebw,gcmt20160114032528,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":5.492,\"rms\":1.04,\"gap\":16,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 12km WNW of Charagua, Bolivia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-63.3288,-19.7597,582.56]},\"id\":\"us10004ebw\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.2,\"place\":\"74km NW of Rumoi, Japan\",\"time\":1452532083920,\"updated\":1459304875040,\"tz\":540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004djn\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004djn&format=geojson\",\"felt\":8,\"cdi\":3.4,\"mmi\":3.74,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":594,\"net\":\"us\",\"code\":\"10004djn\",\"ids\":\",us10004djn,gcmt20160111170803,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.139,\"rms\":0.96,\"gap\":33,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.2 - 74km NW of Rumoi, Japan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[141.0867,44.4761,238.81]},\"id\":\"us10004djn\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.5,\"place\":\"227km SE of Sarangani, Philippines\",\"time\":1452530285900,\"updated\":1459304874040,\"tz\":480,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004dj5\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004dj5&format=geojson\",\"felt\":1,\"cdi\":2.7,\"mmi\":7.5,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":650,\"net\":\"us\",\"code\":\"10004dj5\",\"ids\":\",at00o0srjp,pt16011050,us10004dj5,gcmt20160111163807,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":3.144,\"rms\":0.72,\"gap\":22,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.5 - 227km SE of Sarangani, Philippines\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[126.8621,3.8965,13]},\"id\":\"us10004dj5\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"Pacific-Antarctic Ridge\",\"time\":1451986454620,\"updated\":1459202978040,\"tz\":-540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004bgk\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004bgk&format=geojson\",\"felt\":0,\"cdi\":1,\"mmi\":0,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":554,\"net\":\"us\",\"code\":\"10004bgk\",\"ids\":\",us10004bgk,gcmt20160105093415,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":30.75,\"rms\":0.67,\"gap\":71,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - Pacific-Antarctic Ridge\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-136.2603,-54.2906,10]},\"id\":\"us10004bgk\"}],\"bbox\":[-153.4051,-54.2906,10,158.5463,59.6363,582.56]}";
*/
    /** Sample JSON response for a USGS query */
    /*private static final String SAMPLE_JSON_RESPONSE_COLOR = "{\"type\":\"FeatureCollection\",\"metadata\":{\"generated\":1462295443000,\"url\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmag=6&limit=10\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.5.2\",\"limit\":10,\"offset\":1,\"count\":10},\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":0.2,\"place\":\"88km N of Yelizovo, Russia\",\"time\":1454124312220,\"updated\":1460674294040,\"tz\":720,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us20004vvx\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004vvx&format=geojson\",\"felt\":2,\"cdi\":3.4,\"mmi\":5.82,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":798,\"net\":\"us\",\"code\":\"20004vvx\",\"ids\":\",at00o1qxho,pt16030050,us20004vvx,gcmt20160130032510,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,finite-fault,general-link,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":0.958,\"rms\":1.19,\"gap\":17,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.2 - 88km N of Yelizovo, Russia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[158.5463,53.9776,177]},\"id\":\"us20004vvx\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":1.1,\"place\":\"94km SSE of Taron, Papua New Guinea\",\"time\":1453777820750,\"updated\":1460156775040,\"tz\":600,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us20004uks\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004uks&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":4.1,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":572,\"net\":\"us\",\"code\":\"20004uks\",\"ids\":\",us20004uks,gcmt20160126031023,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,geoserve,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.537,\"rms\":0.74,\"gap\":25,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 94km SSE of Taron, Papua New Guinea\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[153.2454,-5.2952,26]},\"id\":\"us20004uks\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":2.3,\"place\":\"50km NNE of Al Hoceima, Morocco\",\"time\":1453695722730,\"updated\":1460156773040,\"tz\":0,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004gy9\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004gy9&format=geojson\",\"felt\":117,\"cdi\":7.2,\"mmi\":5.28,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":695,\"net\":\"us\",\"code\":\"10004gy9\",\"ids\":\",us10004gy9,gcmt20160125042203,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":2.201,\"rms\":0.92,\"gap\":20,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.3 - 50km NNE of Al Hoceima, Morocco\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-3.6818,35.6493,12]},\"id\":\"us10004gy9\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":3.1,\"place\":\"86km E of Old Iliamna, Alaska\",\"time\":1453631430230,\"updated\":1460156770040,\"tz\":-540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004gqp\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004gqp&format=geojson\",\"felt\":1816,\"cdi\":7.2,\"mmi\":6.6,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":1496,\"net\":\"us\",\"code\":\"10004gqp\",\"ids\":\",at00o1gd6r,us10004gqp,ak12496371,gcmt20160124103030,\",\"sources\":\",at,us,ak,gcmt,\",\"types\":\",cap,dyfi,finite-fault,general-link,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,trump-origin,\",\"nst\":null,\"dmin\":0.72,\"rms\":2.11,\"gap\":19,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.1 - 86km E of Old Iliamna, Alaska\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-153.4051,59.6363,129]},\"id\":\"us10004gqp\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":4.6,\"place\":\"215km SW of Tomatlan, Mexico\",\"time\":1453399617650,\"updated\":1459963829040,\"tz\":-420,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004g4l\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004g4l&format=geojson\",\"felt\":11,\"cdi\":2.7,\"mmi\":3.92,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":673,\"net\":\"us\",\"code\":\"10004g4l\",\"ids\":\",at00o1bebo,pt16021050,us10004g4l,gcmt20160121180659,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":2.413,\"rms\":0.98,\"gap\":74,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.6 - 215km SW of Tomatlan, Mexico\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-106.9337,18.8239,10]},\"id\":\"us10004g4l\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":5.7,\"place\":\"52km SE of Shizunai, Japan\",\"time\":1452741933640,\"updated\":1459304879040,\"tz\":540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004ebx\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004ebx&format=geojson\",\"felt\":51,\"cdi\":5.8,\"mmi\":6.45,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":720,\"net\":\"us\",\"code\":\"10004ebx\",\"ids\":\",us10004ebx,pt16014050,at00o0xauk,gcmt20160114032534,\",\"sources\":\",us,pt,at,gcmt,\",\"types\":\",associate,cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":0.281,\"rms\":0.98,\"gap\":22,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.7 - 52km SE of Shizunai, Japan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[142.781,41.9723,46]},\"id\":\"us10004ebx\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.1,\"place\":\"12km WNW of Charagua, Bolivia\",\"time\":1452741928270,\"updated\":1459304879040,\"tz\":-240,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004ebw\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004ebw&format=geojson\",\"felt\":3,\"cdi\":2.2,\"mmi\":2.21,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":573,\"net\":\"us\",\"code\":\"10004ebw\",\"ids\":\",us10004ebw,gcmt20160114032528,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":5.492,\"rms\":1.04,\"gap\":16,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 12km WNW of Charagua, Bolivia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-63.3288,-19.7597,582.56]},\"id\":\"us10004ebw\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":7.2,\"place\":\"74km NW of Rumoi, Japan\",\"time\":1452532083920,\"updated\":1459304875040,\"tz\":540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004djn\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004djn&format=geojson\",\"felt\":8,\"cdi\":3.4,\"mmi\":3.74,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":594,\"net\":\"us\",\"code\":\"10004djn\",\"ids\":\",us10004djn,gcmt20160111170803,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.139,\"rms\":0.96,\"gap\":33,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.2 - 74km NW of Rumoi, Japan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[141.0867,44.4761,238.81]},\"id\":\"us10004djn\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":8.5,\"place\":\"227km SE of Sarangani, Philippines\",\"time\":1452530285900,\"updated\":1459304874040,\"tz\":480,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004dj5\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004dj5&format=geojson\",\"felt\":1,\"cdi\":2.7,\"mmi\":7.5,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":650,\"net\":\"us\",\"code\":\"10004dj5\",\"ids\":\",at00o0srjp,pt16011050,us10004dj5,gcmt20160111163807,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":3.144,\"rms\":0.72,\"gap\":22,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.5 - 227km SE of Sarangani, Philippines\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[126.8621,3.8965,13]},\"id\":\"us10004dj5\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":9,\"place\":\"Pacific-Antarctic Ridge\",\"time\":1451986454620,\"updated\":1459202978040,\"tz\":-540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004bgk\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004bgk&format=geojson\",\"felt\":0,\"cdi\":1,\"mmi\":0,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":554,\"net\":\"us\",\"code\":\"10004bgk\",\"ids\":\",us10004bgk,gcmt20160105093415,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":30.75,\"rms\":0.67,\"gap\":71,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - Pacific-Antarctic Ridge\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-136.2603,-54.2906,10]},\"id\":\"us10004bgk\"}],\"bbox\":[-153.4051,-54.2906,10,158.5463,59.6363,582.56]}";
    */

    private static final String SAMPLE_RESPONSE = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":2547,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":255,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"technology/2018/aug/29/coding-algorithms-frankenalgos-program-danger\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2018-08-30T05:00:48Z\",\"webTitle\":\"Franken-algorithms: the deadly consequences of unpredictable code\",\"webUrl\":\"https://www.theguardian.com/technology/2018/aug/29/coding-algorithms-frankenalgos-program-danger\",\"apiUrl\":\"https://content.guardianapis.com/technology/2018/aug/29/coding-algorithms-frankenalgos-program-danger\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"commentisfree/2018/jul/26/tech-healthcare-ethics-artifical-intelligence-doctors-patients\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2018-07-26T11:48:00Z\",\"webTitle\":\"Algorithms may outperform doctors, but theyâ€™re no healthcare panacea | Ivana Bartoletti\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/jul/26/tech-healthcare-ethics-artifical-intelligence-doctors-patients\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/jul/26/tech-healthcare-ethics-artifical-intelligence-doctors-patients\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"commentisfree/2018/may/13/we-created-poverty-algorithms-wont-make-that-go-away\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2018-05-13T10:00:04Z\",\"webTitle\":\"We created poverty. Algorithms won't make that go away | Virginia Eubanks\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/may/13/we-created-poverty-algorithms-wont-make-that-go-away\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/may/13/we-created-poverty-algorithms-wont-make-that-go-away\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"world/commentisfree/2018/jul/12/algorithm-privacy-data-surveillance\",\"type\":\"article\",\"sectionId\":\"world\",\"sectionName\":\"World news\",\"webPublicationDate\":\"2018-07-12T09:00:37Z\",\"webTitle\":\"Algorithms are taking over â€“ and woe betide anyone they class as a 'deadbeat' | Zoe Williams\",\"webUrl\":\"https://www.theguardian.com/world/commentisfree/2018/jul/12/algorithm-privacy-data-surveillance\",\"apiUrl\":\"https://content.guardianapis.com/world/commentisfree/2018/jul/12/algorithm-privacy-data-surveillance\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"books/2018/jul/24/ada-lovelace-first-edition-pioneering-algorithm-program\",\"type\":\"article\",\"sectionId\":\"books\",\"sectionName\":\"Books\",\"webPublicationDate\":\"2018-07-24T13:23:22Z\",\"webTitle\":\"First edition of Ada Lovelace's pioneering algorithm sold for Â£95,000\",\"webUrl\":\"https://www.theguardian.com/books/2018/jul/24/ada-lovelace-first-edition-pioneering-algorithm-program\",\"apiUrl\":\"https://content.guardianapis.com/books/2018/jul/24/ada-lovelace-first-edition-pioneering-algorithm-program\",\"isHosted\":false,\"pillarId\":\"pillar/arts\",\"pillarName\":\"Arts\"},{\"id\":\"commentisfree/2018/apr/04/algorithms-powerful-europe-response-social-media\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2018-04-04T05:00:17Z\",\"webTitle\":\"Algorithms have become so powerful we need a robust, Europe-wide response | Marietje Schaake\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/apr/04/algorithms-powerful-europe-response-social-media\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/apr/04/algorithms-powerful-europe-response-social-media\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"science/2017/nov/03/from-health-to-crimefighting-ai-has-brought-us-to-the-threshold-of-a-new-era\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2017-11-03T11:00:22Z\",\"webTitle\":\"The algorithms that are already changing your life\",\"webUrl\":\"https://www.theguardian.com/science/2017/nov/03/from-health-to-crimefighting-ai-has-brought-us-to-the-threshold-of-a-new-era\",\"apiUrl\":\"https://content.guardianapis.com/science/2017/nov/03/from-health-to-crimefighting-ai-has-brought-us-to-the-threshold-of-a-new-era\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"commentisfree/2018/mar/05/algorithms-rate-credit-scores-finances-data\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2018-03-05T06:00:08Z\",\"webTitle\":\"The tyranny of algorithms is part of our lives: soon they could rate everything we do | John Harris\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/mar/05/algorithms-rate-credit-scores-finances-data\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/mar/05/algorithms-rate-credit-scores-finances-data\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"technology/2018/may/15/twitter-ranking-algorithm-change-trolling-harassment-abuse\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2018-05-15T16:00:23Z\",\"webTitle\":\"Twitter announces global change to algorithm in effort to tackle harassment\",\"webUrl\":\"https://www.theguardian.com/technology/2018/may/15/twitter-ranking-algorithm-change-trolling-harassment-abuse\",\"apiUrl\":\"https://content.guardianapis.com/technology/2018/may/15/twitter-ranking-algorithm-change-trolling-harassment-abuse\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"commentisfree/2018/aug/05/magical-thinking-about-machine-learning-will-not-bring-artificial-intelligence-any-closer\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2018-08-05T06:00:03Z\",\"webTitle\":\"Magical thinking about machine learning wonâ€™t bring the reality of AI any closer | John Naughton\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/aug/05/magical-thinking-about-machine-learning-will-not-bring-artificial-intelligence-any-closer\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/aug/05/magical-thinking-about-machine-learning-will-not-bring-artificial-intelligence-any-closer\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"}]}}";
    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = ArticleActivity.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                jsonResponse = String.valueOf(R.string.invalid_response_code_message);
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the article JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //Helper method to convert date time stamp from ISO 8601 to Unix time
    public static Date fromISO8601UTC(String dateStr) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);

        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            Log.e("QueryUtils", "Problem converting date time from ISO8601 to Unix", e);
        }


        return null;
    }

    /**
     * Query the news api and return a list of {@link Article} objects.
     */
    public static List<Article> fetchArticleData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
//jsonResponse = SAMPLE_RESPONSE;
        // Extract relevant fields from the JSON response and create a list of {@link Article}s
        List<Article> articles = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Article}s
        return articles;
    }

    /**
     * Return a list of {@link Article} objects that has been built up from
     * parsing a JSON response.
     */
    /**
     * Return a list of {@link Article} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Article> extractFeatureFromJson(String articleJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding articles to
        List<Article> articles = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(articleJSON).getJSONObject("response");

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of articles.

            JSONArray articleArray = baseJsonResponse.getJSONArray("results");
            Log.e(LOG_TAG, "articleArray results: "+articleArray);
            // For each earthquake in the articleArray, create an {@link Article} object
            for (int i = 0; i < articleArray.length(); i++) {

                // Get a single article at position i within the list of articles
                JSONObject currentArticle = articleArray.getJSONObject(i);

                // Extract the value for the key called "webTitle"
                String headline = currentArticle.getString("webTitle");
                Log.e(LOG_TAG, "Headline: "+headline);
                // Extract the value for the key called "sectionName"
                String category = currentArticle.getString("sectionName");
                Log.e(LOG_TAG, "Category: "+category);
                // Extract the value for the key called "webPublicaitonDate"
                String published = currentArticle.getString("webPublicationDate");
                Log.e(LOG_TAG, "Publication Date: "+published);
                long time = 0;
                if (published != null) {
                    time = fromISO8601UTC(published).getTime();
                }
                Log.e(LOG_TAG, "Time: "+time);
                // Extract the value for the key called "url"
                String url = currentArticle.getString("webUrl");
                Log.e(LOG_TAG, "url: "+url);
                // Create a new {@link Article} object with the headline, category, time,
                // and url from the JSON response.
                Article article = new Article(headline, category, time, url);

                // Add the new {@link Article} to the list of articles.
                articles.add(article);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the article JSON results", e);
        }

        // Return the list of articles
        return articles;
    }

}