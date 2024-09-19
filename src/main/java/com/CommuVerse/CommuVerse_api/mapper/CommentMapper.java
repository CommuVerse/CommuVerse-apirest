package com.CommuVerse.CommuVerse_api.mapper;

import com.CommuVerse.CommuVerse_api.dto.CommentDTO;
import com.CommuVerse.CommuVerse_api.model.entity.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    private final ModelMapper modelMapper;

    public CommentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CommentDTO toDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

    public Comment toEntity(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }
}