package com.example.booksss;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search{
    /* < class member >
     * num : 응답받은 item 수
     * cur : 현재까지 반환한 아이템 위치
     * list : item 저장 리스트 */
    long num;
    long cur;
    String key;
    ArrayList<Book> list = new ArrayList<>();

    /* json var */
    JSONObject jsonObj;
    JSONArray jsonArray;

    /* Application Client Id/Secret */
    String clientId = "YANTquGeGpLmy39HdXoV";
    String clientSecret = "0m7vWQluJe";

    void initSearch(){
        /* make URL + get response */
        String apiURL = "https://openapi.naver.com/v1/search/book?query=" + key + "&start=" + Long.toString(cur+1);
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL, requestHeaders);

        /* parsing response String to obj */
        try {
            jsonObj = new JSONObject(responseBody);
            /* get number of data */
            num = (long) jsonObj.get("total");
            jsonArray = (JSONArray) jsonObj.get("items"); // "items"항목 Array에 저장
        }
        catch(JSONException e){}
    }

    void initSearch(String text) {
        key = text;
        cur = 0;
        /* encoding */
        try{
            key = URLEncoder.encode(key, "UTF-8");
        } catch(UnsupportedEncodingException e){
            throw new RuntimeException("Encoding false", e);
        }

        /* make URL + get response */
        String apiURL = "https://openapi.naver.com/v1/search/book?query=" + key + "&start=" + Long.toString(cur+1);
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL, requestHeaders);

        /* parsing response String to obj */
        try {
            jsonObj = new JSONObject(responseBody);
            num = (long)jsonObj.get("total");
            jsonArray = (JSONArray) jsonObj.get("items"); // "items"항목 Array에 저장
        }catch(JSONException e){ }
        /* get number of data */
    }

    ArrayList<Book> Search() {
        int i;
        list.clear();
        if(cur != 0) initSearch();
        for(i = 0; i<jsonArray.size() && i < 10; i++) {
            jsonObj = (JSONObject) jsonArray.get(i);
            String image = (String) jsonObj.get("image");
            String author = (String) jsonObj.get("author");
            String price = (String) jsonObj.get("price");
            String discount = (String) jsonObj.get("discount");
            String link = (String) jsonObj.get("link");
            String publisher = (String) jsonObj.get("publisher");
            String description = (String) jsonObj.get("description");
            String title = (String) jsonObj.get("title");
            Book add = new Book(image, author, price, discount, link, publisher, description, title);
            list.add(add);
            cur++;
        }
        return list;
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}