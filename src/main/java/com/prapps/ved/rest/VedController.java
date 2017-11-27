package com.prapps.ved.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prapps.ved.dto.Sutra;
import com.prapps.ved.service.VedService;

@RestController
@RequestMapping("/rest")
public class VedController {

	@Autowired VedService vedService;
	
	@RequestMapping("/{bookId}/{chapterNo}/{sutraNo}")
	public Sutra getSutra(@PathVariable Long bookId,@PathVariable Integer chapterNo, @PathVariable Integer sutraNo, @RequestParam String script) {
		long start = System.currentTimeMillis();
		Sutra sutra = vedService.getSutra(bookId, chapterNo, sutraNo, script);
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end-start) + " miliseconds");
		return sutra;
	}
}
