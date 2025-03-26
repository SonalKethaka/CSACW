package exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException ex) {
        String errorType;
        Response.Status status;

        if (ex instanceof BookNotFoundException) {
            errorType = "Book Not Found";
            status = Response.Status.NOT_FOUND;
        } else if (ex instanceof AuthorNotFoundException) {
            errorType = "Author Not Found";
            status = Response.Status.NOT_FOUND;
        } else if (ex instanceof CustomerNotFoundException) {
            errorType = "Customer Not Found";
            status = Response.Status.NOT_FOUND;
        } else if (ex instanceof InvalidInputException) {
            errorType = "Invalid Input";
            status = Response.Status.BAD_REQUEST;
        } else if (ex instanceof OutOfStockException) {
            errorType = "Out of Stock";
            status = Response.Status.BAD_REQUEST;
        } else if (ex instanceof CartNotFoundException) {
            errorType = "Cart Not Found";
            status = Response.Status.BAD_REQUEST;
        } else {
            errorType = "Internal Error";
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        String json = String.format("{\"error\":\"%s\",\"message\":\"%s\"}", errorType, ex.getMessage());
        return Response.status(status).entity(json).type(MediaType.APPLICATION_JSON).build();
    }
}