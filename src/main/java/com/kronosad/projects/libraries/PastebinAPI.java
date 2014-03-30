
package com.kronosad.projects.libraries;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Used for pasting to Pastebin
 * @author russjr08
 */
public class PastebinAPI {
    
    private String APIKey;
    private String option;
    private String paste;
    
    private URL pastedUrl;


    /**
     *
     * @param APIKey Your developer API Key
     * @param format The format of the paste ('text' for none)
     * @param paste Text to paste
     */
    public PastebinAPI(String APIKey, String format, String paste) {
        this.APIKey = APIKey;
        this.option = format;
        this.paste = paste;
    }


    /**
     * Runs the request and returns the paste URL.
     * @return The URL of the paste
     * @throws MalformedURLException
     * @throws IOException
     */
    public String post() throws MalformedURLException, IOException {


        
        final HttpClient httpclient = HttpClientBuilder.create().build();

        final HttpPost post = new HttpPost("http://pastebin.com/api/api_post.php");

        post.setHeader("User-Agent", "kronostwit");


        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("api_dev_key", APIKey));
        params.add(new BasicNameValuePair("api_option", "paste"));
        params.add(new BasicNameValuePair("api_paste_code", paste));
        params.add(new BasicNameValuePair("api_paste_name", "KronosTwit Log"));
        params.add(new BasicNameValuePair("api_paste_format", option));

        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //Execute and get the response.
        StringWriter writer = null;


        HttpResponse response = null;
        try {
            response = httpclient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }


        HttpEntity entity = response.getEntity();

        if (entity != null) {
            InputStream instream = null;
            try {
                instream = entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {

                writer = new StringWriter();
                IOUtils.copy(instream, writer);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                try {
                    instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return writer.toString();

    }

    
    
    
}
