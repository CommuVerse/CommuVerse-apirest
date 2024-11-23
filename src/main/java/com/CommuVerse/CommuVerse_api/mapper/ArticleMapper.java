package com.CommuVerse.CommuVerse_api.mapper;

import com.CommuVerse.CommuVerse_api.dto.ArticleDTO;
import com.CommuVerse.CommuVerse_api.model.entity.Article;
import com.CommuVerse.CommuVerse_api.model.entity.ArticleImage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleMapper {

    private final ModelMapper modelMapper;

    public ArticleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ArticleDTO toDTO(Article article) {
        ArticleDTO dto = modelMapper.map(article, ArticleDTO.class);

        dto.setCreatorId(article.getCreator().getId());
        dto.setImages(
                article.getImages().stream()
                        .map(ArticleImage::getImage)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    public Article toEntity(ArticleDTO articleDTO) {

        Article article = modelMapper.map(articleDTO, Article.class);

        List<ArticleImage> images = articleDTO.getImages().stream()
                .map(img -> {
                    ArticleImage image = new ArticleImage();
                    image.setImage(img);
                    image.setArticle(article);
                    return image;
                })
                .collect(Collectors.toList());

        article.setImages(images);
        return article;
    }

    }

