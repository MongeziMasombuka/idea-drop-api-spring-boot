package com.mo.idea_drop.controller;

import com.mo.idea_drop.domain.dto.idea.IdeaRequest;
import com.mo.idea_drop.domain.dto.idea.IdeaResponse;
import com.mo.idea_drop.domain.entity.Idea;
import com.mo.idea_drop.domain.mapper.IdeaMapper;
import com.mo.idea_drop.service.IdeaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ideas")
@RequiredArgsConstructor
public class IdeaController {
    private final IdeaService ideaService;
    private final IdeaMapper ideaMapper;

    @GetMapping
    public ResponseEntity<List<IdeaResponse>> getAll(){
        List<Idea> ideas = ideaService.getAll();
        List<IdeaResponse> ideaResponseList = ideaMapper.toDtoList(ideas);
        return ResponseEntity.ok(ideaResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdeaResponse> getById(@PathVariable UUID id){
        Idea idea =  ideaService.getById(id);
        IdeaResponse ideaResponse = ideaMapper.toDto(idea);
        return ResponseEntity.ok(ideaResponse);
    }

    @PostMapping
    public ResponseEntity<IdeaResponse> create(@Valid @RequestBody IdeaRequest request,
                                               Principal principal){
        Idea idea = ideaService.create(request,principal.getName());
        IdeaResponse createdIdeaResponse = ideaMapper.toDto(idea);
        return new ResponseEntity<>(createdIdeaResponse, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<IdeaResponse> update(@PathVariable UUID id,
                       @Valid @RequestBody IdeaRequest request,
                       Principal principal){
        Idea idea = ideaService.update(id,request, principal.getName());
        IdeaResponse updatedIdeaResponse = ideaMapper.toDto(idea);
        return ResponseEntity.ok(updatedIdeaResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, Principal principal){
        ideaService.delete(id , principal.getName());
        return ResponseEntity.noContent().build();
    }
}
