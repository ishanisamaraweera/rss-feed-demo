package com.ishanis.assignment.rssfeeddemo.repositories;

import com.ishanis.assignment.rssfeeddemo.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RSSItemsRepository extends JpaRepository<Item, String> {
    Optional<Item> findByTitle(String title);
}

