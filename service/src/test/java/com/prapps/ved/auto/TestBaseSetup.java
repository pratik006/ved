package com.prapps.ved.auto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TestBaseSetup {
	protected String BASE_FOLDER = "/media/ext0/iitk-supersite/backup-from-code/gita/";
	protected String[] languages = new String[] {"as", "bn", "dv", "gu", "pa", "kn", "ml", "or", "ro", "ta", "te"};
	protected Map<Integer, Integer> chapterSutraMap = new HashMap<>();
	ObjectMapper mapper = new ObjectMapper();

	public void setUp() throws IOException {
		chapterSutraMap.put(1, 47);
		chapterSutraMap.put(2, 72);
		chapterSutraMap.put(3, 43);
		chapterSutraMap.put(4, 42);
		chapterSutraMap.put(5, 29);
		chapterSutraMap.put(6, 47);
		chapterSutraMap.put(7, 30);
		chapterSutraMap.put(8, 28);
		chapterSutraMap.put(9, 34);
		chapterSutraMap.put(10, 42);
		chapterSutraMap.put(11, 55);
		chapterSutraMap.put(12, 20);
		chapterSutraMap.put(13, 35);
		chapterSutraMap.put(14, 27);
		chapterSutraMap.put(15, 20);
		chapterSutraMap.put(16, 24);
		chapterSutraMap.put(17, 28);
		chapterSutraMap.put(18, 78);
	}

	public String  readFile(int chapter, int sutra, String lang) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(BASE_FOLDER+"gita-"+chapter+"-"+sutra+"-"+lang+".html")
		));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		return sb.toString();
	}

	protected void httpPut(String uri, String content) throws IOException {
		URL url = new URL(uri);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setReadTimeout(1000000);
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		httpCon.setRequestProperty("Content-Type", "application/json");
		httpCon.setRequestProperty("Accept", "application/json");
		OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
		out.write(content);
		out.close();
		httpCon.getInputStream();
	}
}