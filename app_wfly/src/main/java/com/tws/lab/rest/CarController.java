package com.tws.lab.rest;

import com.tws.lab.model.entity.Car;
import com.tws.lab.repository.CarRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/cars")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarController {
    @Inject
    private CarRepository carRepository;

    @GET
    @Path("/search")
    public Response searchCars(
            @QueryParam("query") String query,
            @DefaultValue("10") @QueryParam("limit") int limit,
            @DefaultValue("0") @QueryParam("offset") int offset) {
        try {
            List<Car> cars = carRepository.findCar(query, limit, offset);
            return Response.ok(cars).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing your request")
                    .build();
        }
    }
} 