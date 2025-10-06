package com.mo.idea_drop.service.impl;

import com.mo.idea_drop.domain.dto.idea.IdeaRequest;
import com.mo.idea_drop.domain.entity.Idea;
import com.mo.idea_drop.domain.entity.User;
import com.mo.idea_drop.repository.IdeaRepository;
import com.mo.idea_drop.repository.UserRepository;
import com.mo.idea_drop.service.IdeaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdeaServiceImpl implements IdeaService {

    private final IdeaRepository ideaRepository;
    private final UserRepository userRepository;

    @Override
    public List<Idea> getAll() {
        return ideaRepository.findAll();
    }

    @Override
    public Idea getById(UUID id) {
        return ideaRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Summaruy not found with id: "+id));
    }

    @Override
    public Idea create(IdeaRequest req, String username) {
        String title = req.getTitle();
        String summary = req.getSummary();
        String description = req.getDescription();
        List<String> tags = req.getTags();

        User owner = userRepository.findByEmail(username)
                .orElseThrow(()->new EntityNotFoundException("User not found"));


        Idea idea = Idea.builder()
                .title(title)
                .summary(summary)
                .description(description)
                .tags(tags)
                .owner(owner)
                .build();

        return ideaRepository.save(idea);
    }

    @Override
    public Idea update(UUID id, IdeaRequest req, String username) {
        String title = req.getTitle();
        String summary = req.getSummary();

        Idea idea = getById(id);

        if(!idea.getOwner().getUsername().equals(username)){
            throw new IllegalStateException("You are not the owner of this summary");
        }

        idea.setTitle(title);
        idea.setSummary(summary);

        return ideaRepository.save(idea);
    }

    @Override
    public void delete(UUID id, String username) {
        Idea idea = getById(id);

        if(!idea.getOwner().getUsername().equals(username)){
            throw new IllegalStateException("You are not the owner of this summary");
        }
        ideaRepository.delete(idea);
    }
}
