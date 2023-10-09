package com.woozi.auction.artwork.service;


import com.woozi.auction.artwork.dto.ArtworkDto;
import com.woozi.auction.artwork.entity.Artwork;

public interface ArtworkService {

    Artwork createArtwork(ArtworkDto artworkDto);

    Artwork getArtworkById(Long artworkId);
}
