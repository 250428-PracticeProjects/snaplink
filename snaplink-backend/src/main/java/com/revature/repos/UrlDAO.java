package com.revature.repos;

import com.revature.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlDAO extends JpaRepository<Url, Integer> {
}