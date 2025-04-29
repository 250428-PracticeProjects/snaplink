package com.revature.repos;

import com.revature.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDAO extends JpaRepository<Tag, Integer> {
}