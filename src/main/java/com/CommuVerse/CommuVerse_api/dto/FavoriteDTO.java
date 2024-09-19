
package com.CommuVerse.CommuVerse_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.Date;
import com.CommuVerse.CommuVerse_api.model.entity.User;
import com.CommuVerse.CommuVerse_api.model.entity.Article;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDTO {
    private int id;
    private Date dateSaved;
    private User reader;
    private Article article; 

}