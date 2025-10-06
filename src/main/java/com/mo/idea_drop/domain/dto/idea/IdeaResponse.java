package com.mo.idea_drop.domain.dto.idea;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdeaResponse {

    private String title;

    private String summary;

    private String description;

    private List<String> tags = new ArrayList<>();

    private UUID userId;
}
