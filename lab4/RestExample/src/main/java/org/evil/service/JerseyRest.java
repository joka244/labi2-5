package org.evil.service;

import org.evil.domain.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/shop")
public class JerseyRest {
    //store Clothes assortment
    private static Clothes[] clothesData = {
            new Clothes("1", "Addidas", "Winter clothes", "Jacket", "L",8000),
            new Clothes("2", "Puma", "Summer clothes", "T-shirt", "L",1500),
            new Clothes("3", "Nike", "Winter clothes", "Cap", "M",1200),
            new Clothes("4", "Nike", "Summer clothes", "Shorts", "XL",1500),
            new Clothes("5", "Puma ", "Winter clothes", "Jacket", "XXL",7000)
    };
    // the number of products in the store
    private static int[] quantityData = {0, 2, 3, 5, 1};
    private static StoreAssortment storeAssortment = new StoreAssortment(clothesData, quantityData);

    /**
     * // EXAMPLE POST REQUEST
     *
     * POST http://localhost:8081/RestExample/rest/shop/payment
     * Accept: application/json
     * Content-Type: application/json
     *
     * {
     *   "productId": "123",
     *   "quantity": 2
     * }
     *
     */

    @POST
    @Path("/payment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyClothes(UserOrder userOrder) {
        int size = storeAssortment.getClothess().length;
        for (int i = 0; i < size; i++) {
            Clothes currentClothes = storeAssortment.getClothess()[i];
            if (currentClothes.getId().equals(userOrder.getProductId())) {
                double orderPrice = currentClothes.getPrice() * userOrder.getQuantity();
                PaymentResult goodResult = new PaymentResult(
                        currentClothes.toString(),
                        userOrder.getQuantity(),
                        orderPrice,
                        "Buy successful!");
                return Response.ok(goodResult).build();
            }
        }
        PaymentResult badResult = new PaymentResult(
                userOrder.getProductId(),
                userOrder.getQuantity(),
                0,
                "Buy failure! Model with id = " + userOrder.getProductId() + " not found");
        return Response.ok(badResult).build();
    }

/*
    @GET
    @Path("/assortment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStoreCatalog() {
        return Response.ok(storeAssortment).build();
    }

 */
}
