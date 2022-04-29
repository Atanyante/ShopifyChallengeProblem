package com.example.springboot;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("constants")
public class Constants {
    String databaseURL =
            "r2dbc:postgres://qrmzgvhfjqvjhh:f3dd5462e62caebd54ee81d74261362dcad69c02707ab937c10bab94aad8d5c6@ec2-34-194-73-236.compute-1.amazonaws.com:5432/deqhuc49jqp38p?sslMode=require&ssl=true";
    String genericErrorMessage = "Something went wrong.";

    String itemUpdated = "Item Updated";
    String itemCreated = "Item Created";
    String itemDeleted = "Item Deleted";

    String locationCreated = "Location Created";
    String locationUpdated = "Location Updated";
    String locationDeleted = "Location Deleted";

    String itemsTableName = "items";
    String locationsTableName = "locations";

    List<String> itemsColumns =
            new ArrayList<>(
                    Arrays.asList(
                            "id",
                            "name",
                            "itemtype",
                            "quantity",
                            "description",
                            "location"
                    )
            );
    List<String> locationsColumns =
            new ArrayList<>(
                    Arrays.asList(
                            "name",
                            "address"
                    )
            );
}
