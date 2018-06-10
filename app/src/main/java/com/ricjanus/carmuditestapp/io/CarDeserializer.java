package com.ricjanus.carmuditestapp.io;

import com.google.gson.*;
import com.ricjanus.carmuditestapp.model.Car;

import java.lang.reflect.Type;

public class CarDeserializer implements JsonDeserializer<Car> {
    @Override
    public Car deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        JsonObject carJson = json.getAsJsonObject().get("data").getAsJsonObject();
        JsonArray imagesArray = json.getAsJsonObject().get("images").getAsJsonArray();

        String sku = carJson.get("sku").getAsString();

        Car car = gson.fromJson(carJson, typeOfT);

        JsonObject priceData = carJson
            .get("simples")
            .getAsJsonObject()
            .get(sku)
            .getAsJsonObject()
            .get("meta")
            .getAsJsonObject();

        String name = carJson.get("original_name").getAsString();
        String brand = carJson.get("brand").getAsString();
        String brandModel = carJson.get("brand_model").getAsString();
        String currency = priceData.get("original_price_currency").getAsString();
        double price = priceData.get("price").getAsDouble();
        String imageURL = imagesArray.get(0).getAsJsonObject().get("url").getAsString();

        car.setName(name);
        car.setBrand(brand);
        car.setBrandModel(brandModel);
        car.setCurrency(currency);
        car.setPrice(price);
        car.setImageURL(imageURL);

        return car;
    }
}
