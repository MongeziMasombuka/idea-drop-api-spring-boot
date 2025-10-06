package com.mo.idea_drop.service;

import com.mo.idea_drop.domain.dto.idea.IdeaRequest;
import com.mo.idea_drop.domain.entity.Idea;

import java.util.List;
import java.util.UUID;

public interface IdeaService {
    List<Idea> getAll();
    Idea getById(UUID id);
    Idea create(IdeaRequest req, String username);
    Idea update(UUID id, IdeaRequest req, String username);
    void delete(UUID id, String username);
}
