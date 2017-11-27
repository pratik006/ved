package com.prapps.ved.service;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.prapps.ved.persistence.BookEntity;
import com.prapps.ved.persistence.CommentaryEntity;
import com.prapps.ved.persistence.SutraEntity;

public class WorkerThread implements Runnable {

	String CHAPTER_SELECTOR = "#edit-field-chapter-value";
	String SUTRA_SELECTOR = "#edit-field-nsutra-value";
	String LANG_SCRIPT_SELECTOR = "#edit-language";
	String MOOL_VERSE_SELECTOR = "#block-system-main > div > div > div.view-content > div > div.custom_display_even > div > div.views-field.views-field-body > p:nth-child(3) > font";
	
	private WebDriver driver;
	private File file;
	private BookEntity book;
	
	public WorkerThread(BookEntity book, File file) {
		this.book = book;
		this.file = file;
	}
	
	@Override
	public void run() {
		driver = new ChromeDriver();
		driver.get("file://"+file.getAbsolutePath()+"#");
		
		//driver.findElement(By.cssSelector("#edit-textoptions > legend > span > a > b")).click();
		
		SutraEntity sutra = new SutraEntity();
		String language = new Select(driver.findElement(By.cssSelector(LANG_SCRIPT_SELECTOR))).getFirstSelectedOption().getText();
		String langCode = new Select(driver.findElement(By.cssSelector(LANG_SCRIPT_SELECTOR))).getFirstSelectedOption().getAttribute("value");
		sutra.setVerseNo(Integer.parseInt(new Select(driver.findElement(By.cssSelector(SUTRA_SELECTOR))).getFirstSelectedOption().getText()));
		sutra.setLangCode(langCode);
		sutra.setLanguage(language);
		sutra.setChapterNo(Integer.parseInt(new Select(driver.findElement(By.cssSelector(CHAPTER_SELECTOR))).getFirstSelectedOption().getText()));
		sutra.setContent(driver.findElement(By.cssSelector(MOOL_VERSE_SELECTOR)).getText());
		sutra.setBook(book);
		book.getVerses().add(sutra);
		
		
		//commentaries
		for (WebElement webElement : driver.findElements(By.cssSelector("#block-system-main > div > div > div.view-content > div > div.custom_display_even > div > div.views-field:not(.views-field-body),"
				+ "#block-system-main > div > div > div.view-content > div > div.custom_display_odd > div > div.views-field:not(.views-field-body)"))) {
			String txt = webElement.findElement(By.cssSelector("font > b")).getText();
			String commentator = txt.split("by|By|BY")[1].trim();
			
			CommentaryEntity commentary = new CommentaryEntity();
			commentary.setCommentator(commentator);
			commentary.setLanguage(txt.toLowerCase().contains("hindi")?"hindi":txt.toLowerCase().contains("sanskrit")?"sanskrit":txt.toLowerCase().contains("english")?"english":null);
			commentary.setContent(webElement.findElement(By.cssSelector("p > font")).getText());
			commentary.setSutra(sutra);
			sutra.getCommentaries().add(commentary);
		}
		driver.quit();
		
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
