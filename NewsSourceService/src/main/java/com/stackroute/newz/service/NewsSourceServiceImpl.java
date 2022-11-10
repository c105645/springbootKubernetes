package com.stackroute.newz.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.stackroute.newz.dto.NewsSourceDto;
import com.stackroute.newz.model.NewsSource;
import com.stackroute.newz.repository.NewsSourceRepository;
import com.stackroute.newz.util.exception.NewsSourceAlreadyExists;
import com.stackroute.newz.util.exception.NewsSourceNotFoundException;

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
public class NewsSourceServiceImpl implements NewsSourceService {

	
	private final NewsSourceRepository repository;
	private final ModelMapper mapper;

	public NewsSourceServiceImpl(NewsSourceRepository repository, ModelMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;

	}

	@Override
	public NewsSourceDto addNewsSource(NewsSourceDto newsSource) {
		Optional<NewsSource> newsOpt = repository.findNewsByName(newsSource.getNewssourceName());
        if(newsOpt.isPresent()) {
        	throw new NewsSourceAlreadyExists("News Source with the provided name already exists");
        }else {
        	NewsSource source = mapper.map(newsSource, NewsSource.class);       	   	
        	NewsSource savedSource = repository.save(source);
        	return mapper.map(savedSource, NewsSourceDto.class);
        }	
	}

	 /* This method should be used to get all newsSource for a specific userId.*/

	@Override
	public List<NewsSourceDto> getAllNewsSourceByUserId(String createdBy) {
		List<NewsSource> newsSources = repository.findNewsSourceByUserId(createdBy);
		return newsSources.stream().map(item -> mapper.map(item, NewsSourceDto.class)).collect(Collectors.toList());
		
	}

	@Override
	public void deleteNewsSource(Long newsSourceId) throws NewsSourceNotFoundException {
		NewsSource news = repository.findById(newsSourceId).orElseThrow(() -> new NewsSourceNotFoundException("News source with the provided ids don't exist"));
		repository.deleteById(newsSourceId);
	}

	@Override
	public NewsSourceDto updateNewsSource(NewsSourceDto newsSourceDto, Long newsSourceId) throws NewsSourceNotFoundException {
		repository.findById(newsSourceId).orElseThrow(() -> new NewsSourceNotFoundException("News source with the provided id don't exist"));
		NewsSource toBeSaved = mapper.map(newsSourceDto, NewsSource.class);
		NewsSource savedSource = repository.save(toBeSaved);
		return mapper.map(savedSource, NewsSourceDto.class);
	}

	@Override
	public NewsSourceDto getNewsSourceById(String userId, Long newsSourceId) throws NewsSourceNotFoundException {
		NewsSource source =  repository.findNewsSourceBySourceIdAndUserId(userId, newsSourceId).orElseThrow(() -> new NewsSourceNotFoundException("News source with the provided ids don't exist"));
		return mapper.map(source, NewsSourceDto.class);
	}



}
