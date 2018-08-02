package com.eaglesinterns.travisproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
public class TravisprojectApplication {
    //CONSTANTS
    private static final String ENDPOINT_HOSTNAME = "https://api.travis-ci.com";
    private static final String ENDPOINT = "/repo/mdsol%2fstudy_management/branch/develop";
    private static final String TRAVIS_AUTH_TOKEN = "bW4Fft4w7nzAwiZR1muYXw";

	public static void main(String[] args) {
		SpringApplication.run(TravisprojectApplication.class, args);

        try{
            URL obj = new URL(ENDPOINT_HOSTNAME + ENDPOINT);

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

            //Fetch response
            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine;


            //TEMP
            //go through each line of the response.
            while((responseLine = response.readLine()) != null)
            {
                System.out.println(responseLine);
            }



        }
        catch (MalformedURLException e) {
            System.out.println(e);
        }

        catch (IOException e)
        {
            System.out.println(e);
        }
	}
}
