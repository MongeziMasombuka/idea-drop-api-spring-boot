package com.mo.idea_drop.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "ideas")
public class Idea {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private String description;

    @ElementCollection
    @CollectionTable(name = "idea_tags", joinColumns = @JoinColumn(name = "idea_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Idea idea = (Idea) o;
        return Objects.equals(id, idea.id) && Objects.equals(title, idea.title) && Objects.equals(summary, idea.summary) && Objects.equals(description, idea.description) && Objects.equals(tags, idea.tags) && Objects.equals(owner, idea.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, summary, description, tags, owner);
    }
}