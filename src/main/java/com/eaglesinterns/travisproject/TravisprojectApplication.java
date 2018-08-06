package com.eaglesinterns.travisproject;

//import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@SpringBootApplication
public class TravisprojectApplication {
    //CONSTANTS
    static Map<String, String> env = System.getenv();

    private static final String REPO_SLUG = "mdsol%2fstudy_management";
    private static final String BRANCH_NAME = "develop";


    private static final String ENDPOINT_HOSTNAME = "https://api.travis-ci.com";
    private static final String ENDPOINT = "/repo/" + REPO_SLUG + "/branch/" + BRANCH_NAME;
    private static final String TRAVIS_AUTH_TOKEN = env.get("TRAVIS_TOKEN");

	public static void main(String[] args) {
		SpringApplication.run(TravisprojectApplication.class, args);

        try{
            URL obj = new URL(ENDPOINT_HOSTNAME + ENDPOINT);





            //for (int i = 0; i < 5; i++)
            //{
                hasFailed(obj);

            //}
            //postToSlack("this message is coming from the java application");

        }
        catch (MalformedURLException e) {
            System.out.println(e);
        }


	}

    /**
     * *
     * @param connection the http connections
     * @return true if selected repo/branch has failed.
     */
	private static boolean hasFailed(URL obj){
        //go through each line of the response.
        try
        {
            //create new connection
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            //setup request
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "application/API Explorer");
            //HEADERS
            connection.setRequestProperty("Travis-API-Version","3");
            connection.setRequestProperty("Authorization","token " + TRAVIS_AUTH_TOKEN);

            //Fetch response code
            int responseCode = connection.getResponseCode();

            if (responseCode != 200)
            {
                System.out.println("Returned " + responseCode);
            }
            //Fetch response object
            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            //JsonParser object
            JsonParser parser = new JsonParser() {
                @Override
                public Map<String, Object> parseMap(String json) throws JsonParseException {
                    System.out.println("Json Parse Exception");
                    return null;
                }

                @Override
                public List<Object> parseList(String json) throws JsonParseException {
                    return null;
                }
            };

            /**
            while (response.readLine() !=null ) {
                String jsonline = response.readLine();
                System.out.println(jsonline);
            }**/


             //getting state (passed or failed) and printing
             String state = "state";
            StringBuffer sb = new StringBuffer();
            try {

                String line = "";
                while((line=response.readLine())!=null){
                    line.trim();
                    sb.append(line);
                    sb.append("\n");


                }
                response.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            System.out.println("Json converted to stringBuffer" + sb.toString().trim());

            Map<String, String> jsonMap = new HashMap<String, String>();
            String lines[] = sb.toString().split("\n");
            for (String line : lines) {
                String elements[] = line.split(":");
                jsonMap.put(elements[0], elements[1]);
            }
             //map for json response
            String test = jsonMap.get("state"); //getting value of the state
             System.out.println("State of last build: "+ test); //print value of state
            System.out.println(jsonMap.entrySet());
            System.out.println(jsonMap.size());
             //while (response.readLine() != null) {
             //  String Json_data = response.toString();
             //}

             //JsonParser.parseMap(Json_data);


        }
        catch (IOException e)
        {

        }
        try
        {
            cooldown(1000);

        }
        catch(InterruptedException e)
        {}
        return false;
    }
    private static synchronized void cooldown(long ms) throws InterruptedException {
        Thread.sleep(ms);
    }
	private static void postToSlack(String message)
    {
        //Attempt to send POST request to slack plugin.
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://hooks.slack.com/services/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");
        String jsonToSend = "{\'text\':\'" + message + "!\'}";
        try{

            StringEntity entity = new StringEntity(jsonToSend);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);

        }
        catch (UnsupportedEncodingException e)
        {

        }
        catch (IOException e) {

        }

    }
}
