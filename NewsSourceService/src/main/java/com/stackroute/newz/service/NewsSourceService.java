package com.stackroute.newz.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.stackroute.newz.dto.NewsSourceDto;
import com.stackroute.newz.util.exception.NewsSourceNotFoundException;

@Service
public interface NewsSourceService {
	
	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */

	NewsSourceDto addNewsSource(NewsSourceDto newssource);

	void deleteNewsSource(Long newsSourceId) throws NewsSourceNotFoundException;

	NewsSourceDto updateNewsSource(NewsSourceDto news, Long newsSourceId) throws NewsSourceNotFoundException;

	NewsSourceDto getNewsSourceById(String userId, Long newsSourceId) throws NewsSourceNotFoundException;

	List<NewsSourceDto> getAllNewsSourceByUserId(String userId);
	
}
