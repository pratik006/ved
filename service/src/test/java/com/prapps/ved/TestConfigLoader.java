package com.prapps.ved;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prapps.ved.dto.Book;
import com.prapps.ved.dto.Commentary;
import com.prapps.ved.dto.Language;
import com.prapps.ved.dto.Sutra;
import com.prapps.ved.service.VedService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ApplicationStarter.class})
//@ActiveProfiles("local")
public class TestConfigLoader {
    @Autowired VedService service;
    private static final String DEFAULT_SCRIPT = "ro";

    @Test public void testLoadConfiguration() { }

    @Test public void testGetBooks() {
        assertTrue(!service.getBooks().isEmpty());
    }

    @Test public void testGetBookById() throws IOException {
        int chapterNo = 1;
        Set<Language> langs = new HashSet<>();


        Book book = service.getBookById(1l, chapterNo, DEFAULT_SCRIPT, 1, 18);
//        book.getSutras().forEach(sutra -> {
//            Language lang = new Language(sutra.getLangScript(), sutra.getLang());
//            langs.add(lang);
//        });
        book.setSutras(book.getSutras().stream().filter(sutra -> "dv".equals(sutra.getLangScript())).collect(Collectors.toList()));
        System.out.println("Size: "+book.getSutras().size());
        book.setAvailableLanguages(Arrays.asList(
                new Language("as", "Assamese"),
                new Language("bn", "Bengali"),
                new Language("dv", "Devnagari"),
                new Language("gu", "Gujrati"),
                new Language("kn", "Kannada"),
                new Language("ml", "Malayalam"),
                new Language("or", "Oriya"),
                new Language("pa", "Gurmukhi"),
                new Language("ro", "Roman"),
                new Language("ta", "Tamil"),
                new Language("te", "Telgu")
        ));

        writeFile("gita.json", book);
        //ssertTrue(!book.getChapters().isEmpty());
        //Chapter chapter = book.getChapters().get(chapterNo - 1);
        //assertEquals("ARJUN-VISHAD", chapter.getName());
        //assertTrue(!chapter.getSutras().isEmpty());
        //assertTrue(!book.getAvailableLanguages().isEmpty());
    }

    @Test public void testGetSutras() throws IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	List<Sutra> sutras = service.getSutras(1L, 1, "ro", 1, 47);
    	writeFile("chapter1.json", sutras);
        assertEquals(10, service.getSutras(1L, 1, "ro", 1, 10).size());
    }

    @Test public void testAvailableLanguages() {
        List<Language> lang = service.getAvailableLanguages(1L);
        assertTrue(!lang.isEmpty());
    }

    @Test public void testAvailableTranslations() {
        List<Commentary> lang = service.getAvailableTranslations(1L);
        assertTrue(!lang.isEmpty());
    }

    @Test public void testGetAllCommentaries() {
        Pattern p = Pattern.compile("(\\d+).(\\d+)");
        List<Commentary> commentaries = service.getAllCommentaries();
        commentaries.forEach(comm -> {
            if (comm.getContent().startsWith("<br><br>")) {
                String content = comm.getContent().replaceFirst("<br><br>", "");
                Matcher matcher = p.matcher(content);
                if(matcher.find()) {
                    int chapter = Integer.parseInt(matcher.group(1));
                    int verse = Integer.parseInt(matcher.group(2));
                    System.out.println(chapter);
                    System.out.println(verse);
                    System.out.println(getLang(content));
                }
            }

        });
    }

    public String getLang(String arg) {
        char[] chars = arg.toCharArray();
        for (int i=0;i<chars.length;i++) {
            int code = Character.codePointAt(chars, i);
            if (code >= 2432 && code <= 2531) {
                return "bn";
            }
        }

        return "ro";
    }

    private void writeFile(String filename, Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FileOutputStream os = new FileOutputStream(filename);
        os.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object).getBytes());
        os.close();
    }

}