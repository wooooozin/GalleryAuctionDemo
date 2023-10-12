package com.woozi.auction.artwork.service;


import com.woozi.auction.artwork.dto.ArtworkDto;
import com.woozi.auction.artwork.dto.ArtworkUpdateDto;
import com.woozi.auction.artwork.entity.Artwork;
import org.springframework.data.domain.Page;

public interface ArtworkService {

    Artwork createArtwork(ArtworkDto artworkDto);

    Artwork getArtworkById(Long artworkId);

    Page<Artwork> getAllArtworksPaged(int page, int size);

    Artwork updateArtwork(Long artworkId, ArtworkUpdateDto artworkUpdateDto);
}
