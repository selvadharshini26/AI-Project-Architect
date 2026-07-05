package com.projectarchitect.repository;

import com.projectarchitect.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for {@link Project} documents.
 */
@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

    Page<Project> findByOwnerId(String ownerId, Pageable pageable);

    Page<Project> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Project> findByOwnerIdAndTitleContainingIgnoreCase(String ownerId, String title, Pageable pageable);
}
