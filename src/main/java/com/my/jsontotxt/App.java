package com.my.jsontotxt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class App {

    public static void main(String[] args) throws JSONException {

        BufferedReader br = null;
        PrintWriter writer = null;

        try {
            //Open a new file, paragraphs will be written here
            writer = new PrintWriter("/home/mypijika/Documents/newCreatedFile.txt", "UTF-8");

            //Test code with a file
            br = new BufferedReader(new FileReader("/home/mypijika/Documents/test.json"));
            String jsonText = readAll(br);
            JSONObject json = new JSONObject(jsonText);
            
            //It will replace with url read as below ..
            //JSONObject json = new JSONObject(readJsonFromUrl(args[0]));
            // or
            //JSONObject json = readJsonFromUrl("https://graph.facebook.com/19292868552");
            
            
            JSONArray jsonarr = json.getJSONObject("response").getJSONArray("docs");
            for (int i = 0; i < jsonarr.length(); i++) {
                JSONObject obj = jsonarr.getJSONObject(i);
                String txt = obj.getString("text"); 
                System.out.println(txt);
                writer.println(txt);
                writer.println("");
            }
            /*
            JSONArray txtArr = json.getJSONArray("article"); //Must change to the wiki's one, not "article"
            for (int i = 0; i < txtArr.length(); ++i) {
                JSONObject rec = txtArr.getJSONObject(i);
                String txt = rec.getString("text"); //Must change to the wiki's one, not "text"
                System.out.println(txt);
                writer.println(txt);
                writer.println("");
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        
        
        
        //KEEP this bcz I can know what exception it throws
        //Will delete it soon
        try {
            //JSONObject json = new JSONObject(readJsonFromUrl(args[0]));
            JSONObject json = readJsonFromUrl("https://graph.facebook.com/19292868552");

            /*
             JSONArray txtArr = json.getJSONArray("documents??"); 
             for (int i = 0; i < txtArr.length(); ++i) {
                JSONObject rec = txtArr.getJSONObject(i);
                String txt = rec.getString("text");
                System.out.println(txt);
                writer.println(txt);
                writer.println("");
            }
             */
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private static String readAll(Reader rd) throws IOException {

        StringBuilder sb = new StringBuilder();
        int cp;

        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }

        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json;
            json = new JSONObject(jsonText);
            return json;

        } finally {
            is.close();
        }
    }

}
