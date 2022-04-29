package com.example.springboot;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class InventoryController {

    private SQLQuery sqlQuery = new SQLQuery();
    DSLContext stringBuilder = DSL.using(sqlQuery.conn);

    public InventoryController() throws SQLException {
    }

    // Both create items and update item depending on if the item exists
    // Update could be its own put api
    @PostMapping("/item")
    public String createItem(
            @RequestBody Items responseBody
    ) {
        return sqlQuery.insert(responseBody);
    }

    // Gets all items
    @GetMapping("/items")
    public List<Items> viewItems() {
        return sqlQuery.select();
    }

    // Deletes specified item
    @DeleteMapping("/item")
    public String deleteItem(
            @RequestBody Items item
    ) {
        return sqlQuery.delete(item);
    }

    // Creates or updates location depending on the existence of said location
    // update could be its own put api
    @PostMapping("/location")
    public String addLocation(@RequestBody Locations location) {
        return sqlQuery.insert(location);
    }

    // get location details
    @GetMapping("/location")
    public Inventory getLocation(@RequestBody Locations location) {
        return sqlQuery.select(location);
    }

    // delete location and unassign all items of said location
    @DeleteMapping("/location")
    public String deleteLocation(@RequestBody Locations location) {
        return sqlQuery.delete(location);
    }

}