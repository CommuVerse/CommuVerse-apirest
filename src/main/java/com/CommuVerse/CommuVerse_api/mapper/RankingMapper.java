package com.CommuVerse.CommuVerse_api.mapper;

import com.CommuVerse.CommuVerse_api.dto.RankingDTO;
import com.CommuVerse.CommuVerse_api.model.entity.Ranking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RankingMapper {

    private final ModelMapper modelMapper;

    public RankingMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RankingDTO toDTO(Ranking ranking) {
        RankingDTO dto = modelMapper.map(ranking, RankingDTO.class);
        dto.setUserName(ranking.getUser().getName());
        return dto;
    }

    public Ranking toEntity(RankingDTO rankingDTO) {
        return modelMapper.map(rankingDTO, Ranking.class);
    }
}