package com.example.springboot;

import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@RestController
public class InventoryController {

    private SQLQuery sqlQuery = new SQLQuery();
    DSLContext stringBuilder = DSL.using(sqlQuery.conn);

    public InventoryController() throws SQLException {
    }

    //Quantity and id are integers in roman numerals.
    @PostMapping("/item")
    public String createItem(
            @RequestBody Items responseBody
    ) {
        return sqlQuery.insert(responseBody);
    }

    @GetMapping("/items")
    public List<Items> viewItems() {
        return sqlQuery.select();
    }

    @DeleteMapping("/item")
    public String deleteItem(
            @RequestBody Items item
    ) {
        return sqlQuery.delete(item);
    }

    @PostMapping("/location")
    public String addLocation(@RequestBody Locations location) {
        return sqlQuery.insert(location);
    }

    @GetMapping("/location")
    public Inventory getLocation(@RequestBody Locations location) {
        return sqlQuery.select(location);
    }

    @DeleteMapping("/location")
    public String deleteLocation(@RequestBody Locations location) {
        return sqlQuery.delete(location);
    }

}