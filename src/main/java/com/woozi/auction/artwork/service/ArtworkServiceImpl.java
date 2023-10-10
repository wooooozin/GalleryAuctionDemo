package com.woozi.auction.artwork.service;

import com.woozi.auction.artwork.dto.ArtworkDto;
import com.woozi.auction.artwork.entity.Artwork;
import com.woozi.auction.artwork.repository.ArtworkRepository;
import com.woozi.auction.category.entity.Category;
import com.woozi.auction.category.repository.CategoryRepository;
import com.woozi.auction.exception.EntityNotFoundException;
import com.woozi.auction.user.entity.User;
import com.woozi.auction.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

        Artwork artwork = Artwork.builder()
            .title(artworkDto.getTitle())
            .description(artworkDto.getDescription())
            .uploadDate(LocalDateTime.now())
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
}
