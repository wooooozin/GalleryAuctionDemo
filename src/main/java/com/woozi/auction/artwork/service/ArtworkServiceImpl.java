package com.woozi.auction.artwork.service;

import com.woozi.auction.artwork.dto.ArtworkDto;
import com.woozi.auction.artwork.dto.ArtworkUpdateDto;
import com.woozi.auction.artwork.entity.Artwork;
import com.woozi.auction.artwork.repository.ArtworkRepository;
import com.woozi.auction.category.entity.Category;
import com.woozi.auction.category.repository.CategoryRepository;
import com.woozi.auction.exception.EntityNotFoundException;
import com.woozi.auction.user.entity.User;
import com.woozi.auction.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtworkServiceImpl implements ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Artwork createArtwork(ArtworkDto artworkDto) {
        User user = userRepository.findById(artworkDto.getUserId())
            .orElseThrow(
                () -> new EntityNotFoundException(artworkDto.getUserId(), "유저를 찾을 수 없습니다. ")
            );

        Category category = categoryRepository.findById(artworkDto.getCategoryId())
            .orElseThrow(
                () -> new EntityNotFoundException(artworkDto.getCategoryId(), "카테고리를 찾을 수 없습니다. ")
            );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime endedDate = LocalDateTime.parse(artworkDto.getEndedDate(), formatter);


        log.info("Received endedDate: " + artworkDto.getEndedDate());
        Artwork artwork = Artwork.builder()
            .title(artworkDto.getTitle())
            .description(artworkDto.getDescription())
            .uploadDate(LocalDateTime.now())
            .endedDate(endedDate)
            .image(artworkDto.getImage())
            .startingPrice(artworkDto.getStartingPrice())
            .currentPrice(artworkDto.getStartingPrice())
            .user(user)
            .category(category)
            .build();
        return artworkRepository.save(artwork);

    }

    @Override
    public Artwork getArtworkById(Long artworkId) {
        return artworkRepository.findById(artworkId)
            .orElseThrow(() -> new EntityNotFoundException(artworkId, "작품을 찾을 수 없습니다."));
    }

    @Override
    public Page<Artwork> getAllArtworksPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return artworkRepository.findAll(pageable);
    }

    @Override
    public Artwork updateArtwork(Long artworkId, ArtworkUpdateDto artworkUpdateDto) {
        Artwork artwork = artworkRepository.findById(artworkId)
            .orElseThrow(() -> new EntityNotFoundException(artworkId, "작품을 찾을 수 없습니다."));
        Category category = categoryRepository.findById(artworkUpdateDto.getCategoryId())
            .orElseThrow(
                () -> new EntityNotFoundException(artworkUpdateDto.getCategoryId(), "카테고리를 찾을 수 없습니다.")
            );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime endedDate = LocalDateTime.parse(artworkUpdateDto.getEndedDate(), formatter);

        artwork.setCategory(category);
        artwork.setTitle(artworkUpdateDto.getTitle());
        artwork.setDescription(artworkUpdateDto.getDescription());
        artwork.setLastModifiedDate(LocalDateTime.now());
        artwork.setEndedDate(endedDate);

        return artworkRepository.save(artwork);
    }

    @Override
    public void deleteArtwork(Long artworkId) {
        Artwork artwork = artworkRepository.findById(artworkId)
            .orElseThrow(() -> new EntityNotFoundException(artworkId, "작품을 찾을 수 없습니다."));

        if(artwork.getIsDeleted()) {
            throw new IllegalStateException("작품 ID " + artworkId + "은(는) 이미 삭제되었습니다.");
        }

        artwork.setIsDeleted(true);
        artworkRepository.save(artwork);
    }
}
