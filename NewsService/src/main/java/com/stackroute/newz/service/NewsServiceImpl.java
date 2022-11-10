package com.stackroute.newz.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.stackroute.newz.dao.News;
import com.stackroute.newz.dao.NewsSource;
import com.stackroute.newz.dao.Reminder;
import com.stackroute.newz.dto.NewsDto;
import com.stackroute.newz.repository.NewsRepository;
import com.stackroute.newz.repository.NewssourceRepository;
import com.stackroute.newz.repository.ReminderRepository;
import com.stackroute.newz.util.exception.NewsAlreadyExistsException;
import com.stackroute.newz.util.exception.NewsNotFoundExeption;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */

@Service
public class NewsServiceImpl implements NewsService {


	private final NewsRepository repository;
	private final ReminderRepository reminderRepo;
	private final NewssourceRepository sourceRepo;


	private final ModelMapper mapper;
	
	public NewsServiceImpl(NewsRepository repository, ReminderRepository reminderRepo, NewssourceRepository sourceRepo, ModelMapper mapper) {
		this.repository = repository;
		this.reminderRepo = reminderRepo;
		this.sourceRepo = sourceRepo;
		this.mapper = mapper;
		
	} 
	

	@Override
	@Transactional
    //@PreAuthorize("hasRole('ROLE_User')")
	public NewsDto addNews(NewsDto newsdto) throws NewsAlreadyExistsException {
		Optional<News> newsOpt = repository.findNewsByTitle(newsdto.getTitle());
        if(newsOpt.isPresent()) {
        	throw new NewsAlreadyExistsException("News with the provided title already exists");
        }else {
        	News news = mapper.map(newsdto, News.class);
           	if(news.getReminder() != null) {
        		Reminder savedReminder = reminderRepo.save(news.getReminder());
        	}
        	if(news.getSource() != null) {
        		NewsSource savedSource = sourceRepo.save(news.getSource());
        	}         	
        	News savedNews = repository.save(news);
        	NewsDto returnedNews = mapper.map(savedNews, NewsDto.class);
    		return returnedNews;
        }						
	}

	/* This method should be used to delete an existing news. */
	@Override
	@Transactional
	public boolean deleteNews(String userId, Long newsId) throws NewsNotFoundExeption {
		News news = repository.findNewsByNewsIdAndUserId(userId, newsId).orElseThrow(() -> new NewsNotFoundExeption("News with the provided ids don't exist"));
		repository.deleteById(news.getNewsId());
		return true;
	}

	/* This method should be used to delete all news for a  specific userId. */
	@Override
	@Transactional
	public boolean deleteAllNewsOfAUser(String userId) throws NewsNotFoundExeption {
		getAllNewsByUserId(userId).stream().forEach(item -> repository.deleteById(item.getNewsId()));
		return true;
	}

	/*
	 * This method should be used to update a existing news.
	 */
	@Override
	@Transactional
	public NewsDto updateNews(NewsDto news, Long newsId, String userId) throws NewsNotFoundExeption {
		repository.findNewsByNewsIdAndUserId(userId, newsId).orElseThrow(() -> new NewsNotFoundExeption("News with the provided id don't exist"));
		News toBeSaved = mapper.map(news, News.class);
		News savedNews = repository.save(toBeSaved);
		return mapper.map(savedNews, NewsDto.class);
	}

	/*
	 * This method should be used to get a news by newsId created by specific user
	 */
	@Override
	@Transactional
	public NewsDto getNewsByNewsIdAndUserId(String userId, Long newsId) throws NewsNotFoundExeption {
		News news = repository.findNewsByNewsIdAndUserId(userId, newsId).orElseThrow(() -> new NewsNotFoundExeption("News with the provided id don't exist"));
		return mapper.map(news, NewsDto.class);
	}

	/*
	 * This method should be used to get all news for a specific userId.
	 */
	@Override
	@Transactional
	public List<NewsDto> getAllNewsByUserId(String userId) {
		List<News> news = repository.findNewsByUserId(userId);
		return news.stream().map(item -> mapper.map(item, NewsDto.class)).collect(Collectors.toList());
	}

}
