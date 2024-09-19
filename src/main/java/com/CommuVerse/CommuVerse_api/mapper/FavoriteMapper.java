
package com.CommuVerse.CommuVerse_api.mapper;

import com.CommuVerse.CommuVerse_api.dto.FavoriteDTO;
import com.CommuVerse.CommuVerse_api.model.entity.Favorite;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FavoriteMapper {

    private final ModelMapper modelMapper;

    public FavoriteMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FavoriteDTO toDTO(Favorite favorite) {
        return modelMapper.map(favorite, FavoriteDTO.class);
    }

    public Favorite toEntity(FavoriteDTO favoriteDTO) {
        return modelMapper.map(favoriteDTO, Favorite.class);
    }

    public List<FavoriteDTO> toDTOs(List<Favorite> favorites) {
        return favorites.stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList());
    }
}
