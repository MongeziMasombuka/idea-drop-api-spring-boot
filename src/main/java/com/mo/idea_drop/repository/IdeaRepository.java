package com.mo.idea_drop.repository;

import com.mo.idea_drop.domain.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;

public interface IdeaRepository extends JpaRepository<Idea, UUID>{
    List<Idea> findByOwnerUsername(String username);
}