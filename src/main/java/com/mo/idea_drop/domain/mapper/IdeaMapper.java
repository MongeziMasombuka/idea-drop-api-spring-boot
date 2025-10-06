package com.mo.idea_drop.domain.mapper;

import com.mo.idea_drop.domain.dto.idea.IdeaResponse;
import com.mo.idea_drop.domain.entity.Idea;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IdeaMapper {
    @Mapping(source = "owner.id",target = "userId")
    IdeaResponse toDto(Idea idea);

    List<IdeaResponse> toDtoList(List<Idea> ideas);
}
