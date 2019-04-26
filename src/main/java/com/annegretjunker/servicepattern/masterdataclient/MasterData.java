package com.annegretjunker.servicepattern.masterdataclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RefreshScope
public class MasterData {

    enum KindOfAlphabet {
        SMALLLETTER,
        CAPITALLETTER
    }

    public char[] smallAlphabet;
    public char[] capitalAlphabet;

    public String smallLetter;
    public String capital;

    @Value("${smallalphabeturl:http://localhost:8083/small}")
    private static String smallalphabeturl="http://localhost:8083/small";

    @Value("${capitalalphabeturl:http://localhost:8083/capital}")
    private static String capitalalphabeturl="http://localhost:8083/capital";

    @Value("${index:1}")
    static int index;

    MasterData(int index) throws Exception {
        if (smallAlphabet==null) {
            initializeAlphabet(KindOfAlphabet.SMALLLETTER);
        }
        if (capitalAlphabet==null) {
            initializeAlphabet(KindOfAlphabet.CAPITALLETTER);
        }
        smallLetter=String.valueOf(smallAlphabet[index]);
        capital=String.valueOf(capitalAlphabet[index]);
    }

    private String getCapital() {
        return capital;
    }

    private String getSmallLetter() {
        return smallLetter;
    }

    public void initializeAlphabet(KindOfAlphabet kindOfAlphabet) throws Exception {

        try {
            URL url;
            if (kindOfAlphabet == KindOfAlphabet.SMALLLETTER)
                url = new URL(smallalphabeturl);
            else
                url = new URL(capitalalphabeturl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() != 200) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "According masterdata not found");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));
            String output;
            output = br.readLine();
            //TODO think about timeouts
            connection.disconnect();
            if (kindOfAlphabet == KindOfAlphabet.SMALLLETTER)
                smallAlphabet = output.toCharArray();
            else
                capitalAlphabet = output.toCharArray();
            return;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Full master data not available");
        }
    }

    public void setCapitalAlphabet(char[] capitalAlphabet) {
        this.capitalAlphabet = capitalAlphabet;
    }

    public void setSmallAlphabet(char[] smallAlphabet) {
        this.smallAlphabet= smallAlphabet;

    }
}
