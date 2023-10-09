package com.woozi.auction.artwork.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.woozi.auction.artwork.dto.ArtworkDto;
import com.woozi.auction.artwork.entity.Artwork;
import com.woozi.auction.artwork.repository.ArtworkRepository;
import com.woozi.auction.category.entity.Category;
import com.woozi.auction.category.repository.CategoryRepository;
import com.woozi.auction.exception.EntityNotFoundException;
import com.woozi.auction.user.entity.User;
import com.woozi.auction.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ArtworkServiceImplTest {
    @InjectMocks
    private ArtworkServiceImpl artworkService;

    @Mock
    private ArtworkRepository artworkRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateArtwork() {
        // Given
        ArtworkDto artworkDto = new ArtworkDto();
        artworkDto.setTitle("잔망루피 사진");
        artworkDto.setDescription("잔망루피 사진");
        artworkDto.setStartingPrice(500.0);
        artworkDto.setUserId(1L);
        artworkDto.setCategoryId(1L);

        User user = new User(1L, "잔망루피");
        Category category = new Category(1L, "사진", LocalDateTime.now(), null, false);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(artworkRepository.save(any(Artwork.class))).then(invocation -> invocation.getArgument(0));

        // When
        Artwork createdArtwork = artworkService.createArtwork(artworkDto);

        // Then
        assertEquals(artworkDto.getTitle(), createdArtwork.getTitle());
    }

    @Test
    void testGetArtworkById() {
        // Given
        when(artworkRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        assertThrows(EntityNotFoundException.class, () -> {
            // When
            artworkService.getArtworkById(1L);
        });
    }

    @Test
    void createArtworkFailUserNotFound() {
        // given
        ArtworkDto artworkDto = new ArtworkDto();
        artworkDto.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> artworkService.createArtwork(artworkDto));

        // then
        assertEquals("유저를 찾을 수 없습니다. id: " + artworkDto.getUserId(), exception.getMessage());
    }
}
