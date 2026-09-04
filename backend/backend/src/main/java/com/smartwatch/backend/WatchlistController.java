package com.smartwatch.backend;

import com.smartwatch.backend.model.WatchlistItem;
import com.smartwatch.backend.model.WatchlistItemRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
@CrossOrigin(origins = "*")
public class WatchlistController {

    private final WatchlistItemRepository repository;

    private static final double MEANINGFUL_CHANGE_THRESHOLD_PERCENT = 2.0;

    public WatchlistController(WatchlistItemRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public WatchlistItem addItem(@RequestBody WatchlistItem incoming) {
        WatchlistItem item = new WatchlistItem();
        item.setSymbol(incoming.getSymbol());
        item.setCompanyName(incoming.getCompanyName());
        item.setCurrentPrice(incoming.getCurrentPrice());
        item.setPriceUpdatedAt(LocalDateTime.now());

        item.setLastSeenPrice(incoming.getCurrentPrice());
        item.setLastSeenAt(LocalDateTime.now());
        item.setChangeAmount(0.0);
        item.setChangePercent(0.0);
        item.setMeaningfulChange(false);

        return repository.save(item);
    }

    @GetMapping
    public List<WatchlistItem> getWatchlist() {
        List<WatchlistItem> items = repository.findAll();

        for (WatchlistItem item : items) {
            recomputeChange(item);
            item.setLastSeenPrice(item.getCurrentPrice());
            item.setLastSeenAt(LocalDateTime.now());
        }

        return repository.saveAll(items);
    }

    private void recomputeChange(WatchlistItem item) {
        Double current = item.getCurrentPrice();
        Double lastSeen = item.getLastSeenPrice();

        if (current == null || lastSeen == null || lastSeen == 0.0) {
            item.setChangeAmount(0.0);
            item.setChangePercent(0.0);
            item.setMeaningfulChange(false);
            return;
        }

        double changeAmount = current - lastSeen;
        double changePercent = (changeAmount / lastSeen) * 100.0;

        item.setChangeAmount(changeAmount);
        item.setChangePercent(changePercent);
        item.setMeaningfulChange(Math.abs(changePercent) >= MEANINGFUL_CHANGE_THRESHOLD_PERCENT);
    }
        // Simulate the market moving — update currentPrice for an existing item
    @PutMapping("/{id}/price")
    public WatchlistItem updatePrice(@PathVariable Long id, @RequestBody PriceUpdateRequest request) {
        WatchlistItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Watchlist item not found: " + id));

        item.setCurrentPrice(request.getCurrentPrice());
        item.setPriceUpdatedAt(LocalDateTime.now());

        return repository.save(item);
    }
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

class PriceUpdateRequest {
    private Double currentPrice;

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }
}