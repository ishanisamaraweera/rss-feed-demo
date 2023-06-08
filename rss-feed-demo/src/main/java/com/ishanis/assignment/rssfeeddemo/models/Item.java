package com.ishanis.assignment.rssfeeddemo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Represents an RSS feed item.
 */
@Entity
@Data
public class Item {
    @jakarta.persistence.Id
    @Id
    private String id;

    private String title;

    private String publicationDate;

    // using log because the description can be much longer than 255 characters
    @Lob
    private String description;
}
