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
    private final String DESCRIPTION = "Build #%d %s (%s, %s)\n%s@%s";
    private final String BUTTON_TEXT = "Review this build";
    public void sendFailed(int buildID, String slug, String branch, String  by, String time, String buildURL)
    {
        String failedDescription = String.format(DESCRIPTION, buildID, "failed", by, time, slug, branch);

        sendMessage("{\n" +
                "    \"attachments\": [\n" +
                "        {\n" +
                "\t\t\t\"text\" : \""+ failedDescription +"\",\n" +
                "            \"fallback\": \""+FAILED_TITLE+"\",\n" +
                "            \"color\": \""+ FAILED_COLOUR+"\"\n" +
                "            \"actions\": [\n" +
                "                {\n" +
                "                    \"type\": \"button\",\n" +
                "                    \"text\": \"Review Build\",\n" +
                "                    \"url\": \"www.google.co.uk\"\n" +
                "                }\n" +
                "            ]" +
                "        }\n" +
                "    ], \"text\": \""+FAILED_TITLE+"\"\n" +
                "}");
       /**sendMessage("{\n" +
               "\"attachments\": [{\n" +
               "    \"text\": \""+failedDescription+"\",\n" +
               "    \"fallback\": \""+ failedDescription+"\",\n" +
               "    \"color\": \""+FAILED_COLOUR+"\",\n" +
               "\t\"actions\": [\n" +
               "\t\t{\n" +
               "\t\t\t\"type\": \"button\",\n" +
               "\t\t\t\"text\": \""+BUTTON_TEXT+"\",\n" +
           "\t\t\t\"url\": \""+buildURL+"\"\n" +
               "                }\n" +
               "            ]\n" +
               "  }]\n" +
               "}");*/
    }
    public void sendPassed(String whatpassed)
    {
        sendMessage("{\n" +
                "    \"attachments\": [\n" +
                "        {\n" +
                "\t\t\t\"text\" : \""+DESCRIPTION+"\",\n" +
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
        //Attempt to send POST request to slack plugin.
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
