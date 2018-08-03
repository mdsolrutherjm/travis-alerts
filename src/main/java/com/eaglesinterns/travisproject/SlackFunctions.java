package com.eaglesinterns.travisproject;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class SlackFunctions {
    private final String PASSED_COLOUR = "#36a64f";
    private final String FAILED_COLOUR = "#ff0000";
    private final String FAILED_TITLE = "A test has failed!";
    private final String PASSED_TITLE = "A test has passed!";
    private final String FAILED_DESCRIPTION = "Build [BUILD] for [SLUG_AND_BRANCH] failed.";
    private final String PASSED_DESCRIPTION = "Build [BUILD] for [SLUG_AND_BRANCH] passed.";

    public void sendFailed(String whatfailed)
    {
        sendMessage("{\n" +
                "    \"attachments\": [\n" +
                "        {\n" +
                "\t\t\t\"text\" : \""+FAILED_DESCRIPTION+"\",\n" +
                "            \"fallback\": \""+FAILED_TITLE+"\",\n" +
                "            \"color\": \""+ FAILED_COLOUR+"\"\n" +
                "        }\n" +
                "    ], \"text\": \""+FAILED_TITLE+"\"\n" +
                "}");
    }
    public void sendPassed(String whatpassed)
    {
        sendMessage("{\n" +
                "    \"attachments\": [\n" +
                "        {\n" +
                "\t\t\t\"text\" : \""+PASSED_DESCRIPTION+"\",\n" +
                "            \"fallback\": \""+PASSED_TITLE+"\",\n" +
                "            \"color\": \""+ PASSED_COLOUR+"\"\n" +
                "        }\n" +
                "    ], \"text\": \""+PASSED_TITLE+"\"\n" +
                "}");
    }
    public void sendText(String text)
    {
        sendMessage("{\'text\':\'" + text + "\'}");
    }
    private void sendMessage(String json)
    {
GIT         //Attempt to send POST request to slack plugin.
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://hooks.slack.com/services/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");
        String jsonToSend = json;
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
