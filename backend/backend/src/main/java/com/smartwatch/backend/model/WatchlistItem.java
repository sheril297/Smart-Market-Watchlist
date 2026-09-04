package com.smartwatch.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "watchlist_item")
@Data
public class WatchlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String symbol;          // e.g. RELIANCE

    @Column(nullable = false)
    private String companyName;     // e.g. Reliance Industries

    // The current known price and when we last fetched it
    private Double currentPrice;
    private LocalDateTime priceUpdatedAt;

    // The price as of the last time the USER viewed this item
    // Used to compute "what changed since you last checked"
    private Double lastSeenPrice;
    private LocalDateTime lastSeenAt;

    // Computed/stored change info (since last seen)
    private Double changeAmount;
    private Double changePercent;
    private Boolean meaningfulChange = false;
}