package com.annegretjunker.servicepattern.masterdataclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
public class MasterData {

    private char[] smallAlphabet;
    private char [] capitalAlphabet;

    public String SmallLetter;
    public String Capital;

    @Value("${smallalphabeturl:http://localhost:8083/small}")
    private String smallalphabeturl;

    @Value("${capitalalphabeturl:http://localhost:8083/capital}")
    private String capitalalphabeturl;



    MasterData(int index) {
        if (smallAlphabet==null) {
            initializeSmallAlphabet();
        }
        if (capitalAlphabet==null) {
            initializeCapitalAlphabet();
        }
        SmallLetter=String.valueOf(smallAlphabet[index]);
        Capital=String.valueOf(capitalAlphabet[index]);
    }

    public String getCapital() {
        return Capital;
    }

    public String getSmallLetter() {
        return SmallLetter;
    }

    public void initializeSmallAlphabet() {
        smallAlphabet= new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    }

    public void initializeCapitalAlphabet() {
        capitalAlphabet=new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    }

    public void setCapitalAlphabet(char[] capitalAlphabet) {
        this.capitalAlphabet = capitalAlphabet;
    }

    public void setSmallAlphabet(char[] smallAlphabet) {

    }

    public char[] getCapitalAlphabet() {
        return capitalAlphabet;
    }

    public char[] getSmallAlphabet() {
        return smallAlphabet;
    }
}
