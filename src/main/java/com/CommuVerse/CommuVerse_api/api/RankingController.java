package com.CommuVerse.CommuVerse_api.api;

import com.CommuVerse.CommuVerse_api.dto.RankingDTO;
import com.CommuVerse.CommuVerse_api.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rankings")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @PostMapping("/rate")
    public ResponseEntity<RankingDTO> rateArticle(@RequestBody RankingDTO dto) {
        RankingDTO response = rankingService.rateArticle(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
