package com.woozi.auction.artwork.dto;

import com.woozi.auction.artwork.type.ArtworkStatus;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkDto {
    private Long id;

    @NotNull(message = "제목은 필수입니다.")
    @Size(min = 1, max = 100, message = "제목은 1~100자 이내로 작성해주세요.")
    private String title;

    @NotNull(message = "설명은 필수입니다.")
    @Size(min = 1, message = "설명을 작성해주세요.")
    private String description;

    private byte[] image;

    private LocalDateTime uploadDate;

    @NotNull(message = "시작 가격은 필수입니다.")
    @Min(value = 1, message = "시작 가격은 1 이상이어야 합니다.")
    private Double startingPrice;

    private ArtworkStatus status;

    private Boolean isDeleted;

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @NotNull(message = "카테고리 ID는 필수입니다.")
    private Long categoryId;

    public void setImageBytes(byte[] imageBytes) {
        this.image = imageBytes;
    }
}
