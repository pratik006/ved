package com.prapps.ved.auto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.prapps.ved.dto.Commentary;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AutomationRetriever extends TestBaseSetup {

	private Map<String, ArrayNode> sutraMap = new HashMap<>();
	private ObjectNode gita;

	@Before
    public void setUp() throws IOException {
        super.setUp();
		gita = mapper.createObjectNode();
    }

	@Test
	public void uploadGita() throws IOException {
		JsonNode node = mapper.readTree(new File("gita.json"));
		((ObjectNode)node.get("commentaries")).fields().forEachRemaining(n -> {
			String lang = n.getKey();
			n.getValue().fields().forEachRemaining(m -> {
				try {
					if (m.getKey().startsWith("à¤®à¥\u0082")) {
						System.out.println("skipping "+m.getKey());
						return;
					}
					System.out.println("Before "+lang+" "+m.getKey());
					String json = mapper.writeValueAsString(m.getValue());
					httpPut("https://vedsangraha-187514.firebaseio.com/ved/gita/commentaries/"+lang+"/"+m.getKey().replaceAll(".", "")+".json",json);
					System.out.println("After "+lang+" "+m.getKey());
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		});


	}

	@Test
	public void createGita() throws IOException {
		ObjectNode info = mapper.createObjectNode();
		gita.set("info", info);
		info.put("name", "Shrimad Bhagwat Gita");
		info.put("code", "gita");
		info.put("chapters", 17);
		info.put("authorName", "Shri Krishna");

		ObjectNode commentaries = mapper.createObjectNode();
		gita.set("commentaries", commentaries);
		ObjectNode sutras = mapper.createObjectNode();
		gita.set("sutras", sutras);

		getCommStringMapMap().entrySet().forEach(langEntry -> {
			if (!commentaries.hasNonNull(langEntry.getKey())) {
				commentaries.set(langEntry.getKey(), mapper.createObjectNode());
			}
			ObjectNode commentariesLang = (ObjectNode) commentaries.get(langEntry.getKey());

			langEntry.getValue().entrySet().forEach(commEntry -> {
				try {
					commentariesLang.put(commEntry.getKey(), mapper.readTree(mapper.writeValueAsString(commEntry.getValue())));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		});

		for (int chapter=1;chapter<=18;chapter++) {
			int ch = chapter;
			for (int sutra = 1; sutra <= chapterSutraMap.get(chapter); sutra++) {
				ObjectNode node = (ObjectNode) gita.get("sutras");
				Arrays.stream(languages).forEach(lang -> {
					ArrayNode subnode = (ArrayNode) node.get(lang);
					Assert.assertEquals(701, subnode.size());
				});
			}
		}
		mapper.writeValue(new File("gita.json"), gita);
	}

    @Test
	public void readAllPAges() throws JsonProcessingException {
		Map<String, Map<String, List<Commentary>>> commentaryLangMap = getCommStringMapMap();

		commentaryLangMap.get("as").entrySet()
				.forEach(comm -> System.out.println(comm.getKey()));
		//System.out.println(commentaryLangMap.get("as").values().iterator().next());
		commentaryLangMap.entrySet().parallelStream().forEach(entry -> {
			entry.getValue().entrySet().forEach(comm -> {
				try {
					String filename = BASE_FOLDER+"gita-commentaries/"+comm.getKey()+"-"+entry.getKey()+".json";
					System.out.println(filename);
					//mapper.writeValue(new File(filename), comm.getValue());
					//restTemplate.put("", mapper.writeValueAsString(comm.getValue()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});

	}

	private List<Commentary> readPage(int chapter, int sutra, String lang) throws IOException {
		Document doc = Jsoup.parse(new File(BASE_FOLDER+"gita-"+chapter+"-"+sutra+"-"+lang+".html"), "UTF-8");
		String headingCss = "font > b";
		String contentCss = "p:nth-child(3)";
		List<Commentary> commentaries = new ArrayList<>();

		doc.select(".views-field-body").forEach(elem -> {
			ObjectNode s = mapper.createObjectNode();
			s.put("sutraNo", sutra);
			s.put("chapterNo", chapter);
			s.put("content", elem.selectFirst("p[align]").text().trim());
			if (!gita.get("sutras").hasNonNull(lang)) {
				((ObjectNode)gita.get("sutras")).set(lang, mapper.createArrayNode());
			}
			((ArrayNode)gita.get("sutras").get(lang)).add(s);
		});

		doc.select(".view-content .custom_display_odd .views-field").forEach(elem -> {
			String heading = null;
			String content = null;
			try {
				if (elem.selectFirst(headingCss) != null) {
					heading = elem.selectFirst(headingCss).html();
				} else if (elem.selectFirst("strong") != null) {
					heading = elem.selectFirst("strong").html();
				} else {
					System.out.println("Null: "+elem);
					return;
				}
			} catch(Exception ex) {
				System.out.println(chapter+" "+sutra+" "+lang+" "+elem);
				ex.printStackTrace();
				throw ex;
			}
			try {
				content = elem.selectFirst(contentCss).text();
			} catch(Exception ex) {
				System.out.println(chapter+" "+sutra+" "+lang+" "+elem);
				ex.printStackTrace();
				throw ex;
			}

			Commentary commentary = new Commentary();
			commentary.setLanguage(heading.split(" ")[0].trim());
			commentary.setCommentator(heading.split(" by|By|BY ")[heading.split(" by|By|BY ").length - 1].trim().replaceAll(".", ""));
			commentary.setContent(content);
			commentary.setChapterNo(chapter);
			commentary.setSutraNo(sutra);
			Assert.assertNotNull(commentary.getCommentator());
			Assert.assertNotNull(commentary.getLanguage());
			Assert.assertNotNull(commentary.getContent());
			Assert.assertNotNull(commentary.getChapterNo());
			Assert.assertNotNull(commentary.getSutraNo());
			commentaries.add(commentary);
		});

		doc.select(".view-content .custom_display_even .views-field").forEach(elem -> {
			if (elem.parent().classNames().contains("views-field-body"))
				return;
			if (elem.text() == null || elem.text().trim().length() == 0)
				return;

			String heading = null;
			String content = null;
			try {
				if (elem.selectFirst(headingCss) != null) {
					heading = elem.selectFirst(headingCss).html();
				} else {
					heading = elem.selectFirst("strong").html();
				}
			} catch(Exception ex) {
				System.out.println(chapter+" "+sutra+" "+lang+" "+elem);
				ex.printStackTrace();
				throw ex;
			}
			try {
				content = elem.selectFirst(contentCss).text();
			} catch(Exception ex) {
				System.out.println(elem.parent().classNames());
				System.out.println(chapter+" "+sutra+" "+lang+" "+elem);
				ex.printStackTrace();
				throw ex;
			}

			Commentary commentary = new Commentary();
			commentary.setLanguage(heading.split(" ")[0].trim());
			commentary.setCommentator(heading.split(" by|By|BY ")[heading.split(" by|By|BY ").length - 1].trim());
			commentary.setContent(content);
			commentary.setChapterNo(chapter);
			commentary.setSutraNo(sutra);
			Assert.assertNotNull(commentary.getCommentator());
			Assert.assertNotNull(commentary.getLanguage());
			Assert.assertNotNull(commentary.getContent());
			Assert.assertNotNull(commentary.getChapterNo());
			Assert.assertNotNull(commentary.getSutraNo());
			commentaries.add(commentary);
		});

		return commentaries;
	}

	private Map<String, Map<String, List<Commentary>>> getCommStringMapMap() {
		Map<String, Map<String, List<Commentary>>> commentaryLangMap = new ConcurrentHashMap();

		for (int chapter=1;chapter<=18;chapter++) {
			int ch = chapter;
			for (int sutra = 1; sutra<= chapterSutraMap.get(chapter); sutra++) {
				int sutraNo = sutra;
				Arrays.stream(languages).forEach(lang -> {
					if (!commentaryLangMap.containsKey(lang)) {
						commentaryLangMap.put(lang, new ConcurrentHashMap<>());
					}
					Map<String, List<Commentary>> commentaryMap = commentaryLangMap.get(lang);
					try {
						readPage(ch, sutraNo, lang).forEach(comm -> {
							String key = comm.getCommentator().trim().replaceAll(" ", "");
							if (!commentaryMap.containsKey(key)) {
								commentaryMap.put(key, new ArrayList<>());
							}

							commentaryMap.get(key).add(comm);
						});
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println("error: "+ch+" "+sutraNo+" "+lang);
						System.exit(1);
					}
				});
			}
		}

		return commentaryLangMap;
	}
}
