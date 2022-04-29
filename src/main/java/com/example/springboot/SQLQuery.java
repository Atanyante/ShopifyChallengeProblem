package com.example.springboot;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;
import static org.springframework.data.relational.core.query.Update.update;

@Configuration
@Component("sqlBuilder")
public class SQLQuery {

    // Constants for the application
    private Constants constants = new Constants();

    // Connection to Postgres Server
    public ConnectionFactory conn = ConnectionFactories.get(
        constants.databaseURL
    );
    R2dbcEntityTemplate template = new R2dbcEntityTemplate(conn);

    public SQLQuery() throws SQLException {
    }

    // Insert or Update item object in postgres
    public String insert(Items item) {
        try {
            List<Items> queryResult =
                    template
                            .select(Items.class)
                            .from(constants.itemsTableName)
                            .matching(query(where(constants.itemsColumns.get(0)).is(item.getId())))
                            .all().collectList().block();
            // If item doesn't exist, add it
            if (queryResult.size() == 0) {
                template.insert(Items.class).into(constants.itemsTableName).using(item).block();
                return constants.itemCreated;
            }
            // If item exists, update it
            else {
                Update update =
                        Update.update(constants.itemsColumns.get(1), item.getName())
                                .set(constants.itemsColumns.get(4), item.getDesc())
                                .set(constants.itemsColumns.get(3), item.getQuantity())
                                .set(constants.itemsColumns.get(2), item.getItemType())
                                .set(constants.itemsColumns.get(5), item.getLocation());
                template
                        .update(Items.class)
                        .inTable(constants.itemsTableName)
                        .matching(query(where(constants.itemsColumns.get(0)).is(item.getId())))
                        .apply(update).block();
                return constants.itemUpdated;
            }
        }
        // Error
        catch (Exception e) {
            e.printStackTrace();
        }
        return constants.genericErrorMessage;
    }

    // Insert or update a warehouse/location
    public String insert(Locations location) {
        try {
            List<Locations> locationResult =
                    template
                            .select(Locations.class)
                            .from(constants.locationsTableName)
                            .matching(
                                    query(
                                            where(constants.locationsColumns.get(0))
                                                    .is(location.name)
                                    )
                            ).all().collectList().block();
            // Add location if doesn't exist
            if (locationResult != null && locationResult.size() == 0) {
                template.insert(Locations.class).into(constants.locationsTableName).using(location).block();
                return constants.locationCreated;
            }
            // Update location if exists
            else {
                template
                        .update(Locations.class)
                        .inTable(constants.locationsTableName)
                        .matching(
                                query(
                                        where(constants.locationsColumns.get(0))
                                                .is(location.name)
                                )
                        )
                        .apply(update(constants.locationsColumns.get(1), location.address))
                        .block();
            }
            return constants.locationUpdated;
        }
        catch (Exception e)  {
            e.printStackTrace();
        }
        return constants.genericErrorMessage;
    }

    // Get all items
    public List<Items> select() {
        List<Items> items = new ArrayList<>();
        try {
            List<Items> queryResult =
                    template
                            .select(Items.class)
                            .from(constants.itemsTableName)
                            .all()
                            .collectList()
                            .block();
            return queryResult;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    // Get location details and items inventoried into said location
    public Inventory select(Locations location) {
        Locations locationResult =
                template
                        .select(Locations.class)
                        .from(constants.locationsTableName)
                        .matching(
                                query(
                                        where(constants.locationsColumns.get(0))
                                                .is(location.name)
                                )
                        )
                        .one()
                        .block();
        List<Items> queryResult =
                template
                        .select(Items.class)
                        .from(constants.itemsTableName)
                        .matching(
                                query(
                                        where(constants.itemsColumns.get(5))
                                                .is(location.name)
                                )
                        )
                        .all()
                        .collectList()
                        .block();
        return new Inventory(locationResult, queryResult);
    }

    // delete item
    public String delete(Items item) {
        try {
            template.delete(Items.class).from(constants.itemsTableName)
                    .matching(query(where(constants.itemsColumns.get(0)).is(item.getId()))).all().block();
            return constants.itemDeleted;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return constants.genericErrorMessage;
    }

    // delete location and items stored at location will be unassigned a location
    public String delete(Locations location) {
        try {
            template.delete(Locations.class).from(constants.locationsTableName)
                    .matching(query(where(constants.locationsColumns.get(0)).is(location.name))).all().block();
            template
                    .update(Items.class)
                    .inTable(constants.itemsTableName)
                    .matching(
                            query(
                                    where(constants.itemsColumns.get(5))
                                            .is(location.name)
                            )
                    )
                    .apply(update(constants.itemsColumns.get(5), ""))
                    .block();
            return constants.locationDeleted;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return constants.genericErrorMessage;
    }
}
