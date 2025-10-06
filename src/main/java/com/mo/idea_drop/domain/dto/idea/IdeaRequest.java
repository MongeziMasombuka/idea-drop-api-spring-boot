package com.mo.idea_drop.domain.dto.idea;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdeaRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String summary;

    @NotBlank
    private String description;

    private List<String> tags = new ArrayList<>();
}
