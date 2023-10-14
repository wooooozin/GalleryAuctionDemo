package com.woozi.auction.artwork.dto;

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
public class ArtworkUpdateDto {

    @NotNull(message = "제목은 필수입니다.")
    @Size(min = 1, max = 100, message = "제목은 1~100자 이내로 작성해주세요.")
    private String title;

    @NotNull(message = "설명은 필수입니다.")
    @Size(min = 1, message = "설명을 작성해주세요.")
    private String description;

    @NotNull(message = "카테고리 ID는 필수입니다.")
    private Long categoryId;

    @NotNull(message = "경매 종료 시간은 필수입니다.")
    private String endedDate;

}
