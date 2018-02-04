package net.khim.thatime;

import android.os.AsyncTask;
import android.util.Log;

import net.khim.thatime.values.ValueSrchPlaceHead;
import net.khim.thatime.values.ValueSrchPlaceItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by khlim on 2017. 10. 17..
 */

public class NaverApiManager extends AsyncTask<String, String, ValueSrchPlaceHead> {

    public String getInfo(String palce) throws IOException {

        // ref. https://developers.naver.com/docs/search/local/
        Log.d("AA", ">111");
        String clientId = "fxfvlk0ljYSQUfFP0UWu";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "jyZiSdeyux";//애플리케이션 클라이언트 시크릿값";

        String text = URLEncoder.encode(palce, "UTF-8");
        String apiURL = "https://openapi.naver.com/v1/search/local.json?query=" + text + "&display=100&start=1"; // json 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과

        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Naver-Client-Id", clientId);
        con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if (responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        Log.d("AA", ">222");
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
        Log.d("", ">" + response.toString());

        return new String(response.toString());


    }

    @Override
    protected ValueSrchPlaceHead doInBackground(String... strings) {

        String param = strings[0];

        ValueSrchPlaceHead vp = new ValueSrchPlaceHead();

        String rtn;

        try {

            rtn = getInfo(param);
            Log.d("AA", ">doInBackground" + rtn);

            // ref. https://www.tutorialspoint.com/android/android_json_parser.htm
            JSONObject jsonObj = new JSONObject(rtn);
            vp.setLastBuildDate(jsonObj.getString("lastBuildDate"));
            vp.setTotal(jsonObj.getString("total"));
            vp.setStart(jsonObj.getString("start"));
            vp.setDisplay(jsonObj.getString("display"));


            JSONArray items = jsonObj.getJSONArray("items");

            JSONObject item;
            ValueSrchPlaceItem vpItem;

            for(int i = 0 ; i < items.length() ; i++){

                item = items.getJSONObject(i);
                vpItem = new ValueSrchPlaceItem();

                vpItem.setTitle(item.getString("title"));
                vpItem.setLink(item.getString("link"));
                vpItem.setCategory(item.getString("category"));
                vpItem.setDescription(item.getString("description"));
                vpItem.setTelephone(item.getString("telephone"));
                vpItem.setAddress(item.getString("address"));
                vpItem.setRoadAddress(item.getString("roadAddress"));
                vpItem.setMapx(item.getString("mapx"));
                vpItem.setMapy(item.getString("mapy"));

                vp.addItem(vpItem);

                Log.d("BB", ">" + vpItem);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


        return vp;
    }
}
