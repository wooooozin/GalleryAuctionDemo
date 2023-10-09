package com.woozi.auction.artwork.controller;

import com.woozi.auction.artwork.dto.ArtworkDto;
import com.woozi.auction.artwork.entity.Artwork;
import com.woozi.auction.artwork.service.ArtworkService;
import com.woozi.auction.common.ResultResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artwork")
public class ArtworkController {

    private final ArtworkService artworkService;

    @PostMapping("/{userId}/{categoryId}") // 이 부분이 빠져 있어서 추가했습니다.
    public ResponseEntity<?> createArtwork(
        @Valid @PathVariable Long userId,
        @Valid @PathVariable Long categoryId,
        @Valid @RequestPart("artworkDto") ArtworkDto artworkDto,
        @RequestPart("imageFile") MultipartFile imageFile) {

        if (artworkDto == null) {
            throw new IllegalArgumentException("작품 등록 정보가 누락되었습니다..");
        }

        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("imageFile이 누락되었습니다.");
        }

        artworkDto.setUserId(userId);
        artworkDto.setCategoryId(categoryId);

        try {
            artworkDto.setImageBytes(imageFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("이미지 파일을 처리하는 데 실패했습니다", e);
        }

        artworkService.createArtwork(artworkDto);
        HttpStatus status = HttpStatus.CREATED;

        return new ResponseEntity<>(
            ResultResponse.success(
                status.value(), "작품이 등록되었습니다.", null), status
        );
    }

    @GetMapping("/{artworkId}")
    public ResponseEntity<?> getArtworkById(
        @PathVariable Long artworkId
    ) {
        Artwork artwork = artworkService.getArtworkById(artworkId);
        if (artwork == null) {
            return new ResponseEntity<>(
                ResultResponse.error(HttpStatus.NOT_FOUND.value(), "작품을 찾을 수 없습니다."), HttpStatus.NOT_FOUND
            );
        }

        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(
            ResultResponse.success(status.value(), "작품 조회 성공", artwork), status
        );
    }
}
