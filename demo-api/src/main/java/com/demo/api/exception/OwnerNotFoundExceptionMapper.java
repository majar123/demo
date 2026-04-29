package com.demo.api.exception;

import com.demo.service.exception.OwnerNotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class OwnerNotFoundExceptionMapper implements ExceptionMapper<OwnerNotFoundException> {

    @Override
    public Response toResponse(OwnerNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(Map.of("error", exception.getMessage()))
                .build();
    }
}
