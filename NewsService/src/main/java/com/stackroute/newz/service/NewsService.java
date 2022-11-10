package com.stackroute.newz.service;

import java.util.List;

import com.stackroute.newz.dto.NewsDto;
import com.stackroute.newz.util.exception.NewsAlreadyExistsException;
import com.stackroute.newz.util.exception.NewsNotFoundExeption;

public interface NewsService {

	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */

	NewsDto addNews(NewsDto news) throws NewsAlreadyExistsException;

	boolean deleteNews(String userId, Long newsId) throws NewsNotFoundExeption;

	boolean deleteAllNewsOfAUser(String userId) throws NewsNotFoundExeption;

	NewsDto updateNews(NewsDto news, Long id, String userId) throws NewsNotFoundExeption;

	NewsDto getNewsByNewsIdAndUserId(String userId, Long newsId) throws NewsNotFoundExeption;

	List<NewsDto> getAllNewsByUserId(String userId);

}
