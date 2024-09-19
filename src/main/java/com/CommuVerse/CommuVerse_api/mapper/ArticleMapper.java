package com.CommuVerse.CommuVerse_api.mapper;

import com.CommuVerse.CommuVerse_api.dto.ArticleDTO;
import com.CommuVerse.CommuVerse_api.model.entity.Article;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    private final ModelMapper modelMapper;

    public ArticleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ArticleDTO toDTO(Article article) {
        ArticleDTO dto = modelMapper.map(article, ArticleDTO.class);
        dto.setCreatorId(article.getCreator().getId()); 
        return dto;
    }

    public Article toEntity(ArticleDTO articleDTO) {
        return modelMapper.map(articleDTO, Article.class); 
    }
}
