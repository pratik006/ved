package com.prapps.ved.auto;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class UrlSaverAutomation extends TestBaseSetup {

    @Before
    public void setUp() throws IOException {
        super.setUp();
    }

    @Test
    public void saveAllPAges() {
        for (int chapter=1;chapter<=18;chapter++) {
            System.out.println(chapter+" "+ chapterSutraMap.get(chapter));
            int ch = chapter;
            for (int sutra = 1; sutra<= chapterSutraMap.get(chapter); sutra++) {
                int sutraNo = sutra;
                //Arrays.stream(languages).parallel().forEach(lang -> {
                    try {
                        savePage(ch, sutraNo, "ro");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                //});
            }
        }
    }

    public void savePage(int chapter, int sutra, String lang) throws IOException {

        URL url = new URL("https://www.gitasupersite.iitk.ac.in/srimad"
                + "?language="+lang
                + "&field_chapter_value="+chapter
                + "&field_nsutra_value="+sutra
                + "&show_mool=1"
                + "&show_purohit=1"
                + "&show_tej=1"
                + "&htrskd=1"
                + "&httyn=1&htshg=1"
                + "&scsh=1"
                + "&hcchi=1"
                + "&hcrskd=1"
                + "&scang=1"
                + "&scram=1"
                + "&scanand=1"
                + "&scjaya=1"
                + "&scmad=1&scval=1&scms=1&scsri=1&scvv=1&scpur=1&scneel=1&scdhan=1&ecsiva=1&etsiva=1&etpurohit=1&etgb=1&setgb=1&etssa=1&etassa=1&etradi=1&etadi=1");
        //System.out.println("url: "+url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = null;
        FileOutputStream fos = new FileOutputStream(BASE_FOLDER+"gita-"+chapter+"-"+sutra+"-"+lang+".html");
        while ((line = reader.readLine()) != null) {
            fos.write(line.getBytes());
        }
        reader.close();
        fos.close();
    }
}
