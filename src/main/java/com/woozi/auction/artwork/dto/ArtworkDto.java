package com.woozi.auction.artwork.dto;

import com.woozi.auction.artwork.type.ArtworkStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkDto {
    private Long id;
    private String title;
    private String description;
    private byte[] image;
    private LocalDateTime uploadDate;
    private Double startingPrice;
    private ArtworkStatus status;
    private Boolean isDeleted;
    private Long userId;
    private Long categoryId;

    public void setImageBytes(byte[] imageBytes) {
        this.image = imageBytes;
    }

}
