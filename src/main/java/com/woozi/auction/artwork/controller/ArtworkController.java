package com.woozi.auction.artwork.controller;

import com.woozi.auction.artwork.dto.ArtworkDto;
import com.woozi.auction.artwork.dto.ArtworkUpdateDto;
import com.woozi.auction.artwork.entity.Artwork;
import com.woozi.auction.artwork.service.ArtworkService;
import com.woozi.auction.common.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artwork")
@Api(tags = "Artwork APIs")
public class ArtworkController {

    private final ArtworkService artworkService;

    @PostMapping("/{userId}/{categoryId}")
    @ApiOperation(value = "작품 등록")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Success"),
        @ApiResponse(code = 400, message = "Invalid Input")
    })
    public ResponseEntity<?> createArtwork(
        @Valid @PathVariable Long userId, // JWT 변경 필요
        @Valid @PathVariable Long categoryId,
        @Valid @RequestPart ArtworkDto artworkDto,
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
    @ApiOperation(value = "작품 조회하기")
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

    @GetMapping("/page")
    @ApiOperation(value = "작품 전체 조회")
    public ResponseEntity<?> getAllArtworkPaged(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Artwork> artworkPage = artworkService.getAllArtworksPaged(page, size);
        if (artworkPage.isEmpty()) {
            return new ResponseEntity<>(
                ResultResponse.error(HttpStatus.NOT_FOUND.value(), "등록된 작품이 없습니다."),
                HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
            ResultResponse.success(HttpStatus.OK.value(), "전체 작품 조회 성송", artworkPage),
            HttpStatus.OK
        );
    }

    @PutMapping("/{artworkId}")
    @ApiOperation(value = "작품 수정")
    public ResponseEntity<?> updateArtwork(
        @PathVariable Long artworkId,
        @Valid @RequestBody ArtworkUpdateDto artworkUpdateDto

    ) {
        Artwork updatedArtwork = artworkService.updateArtwork(artworkId, artworkUpdateDto);

        if (updatedArtwork == null) {
            return new ResponseEntity<>(
                ResultResponse.error(HttpStatus.NOT_FOUND.value(), "수정할 작품을 찾을 수 없습니다."),
                HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
            ResultResponse.success(HttpStatus.OK.value(), "작품이 수정되었습니다.", null),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{artworkId}")
    @ApiOperation(value = "작품 삭제")
    public ResponseEntity<?> deleteArtwork(@PathVariable Long artworkId) {
        artworkService.deleteArtwork(artworkId);
        return new ResponseEntity<>(
            ResultResponse.success(HttpStatus.OK.value(), "작품이 삭제되었습니다.", null),
            HttpStatus.OK
        );
    }

}
