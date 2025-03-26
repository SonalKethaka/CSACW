package resource;

import exception.BookNotFoundException;
import exception.CartNotFoundException;
import exception.CustomerNotFoundException;
import exception.OutOfStockException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Book;
import model.CartItem;
import storage.BookStorage;
import storage.CartStorage;
import storage.CustomerStorage;

import java.util.List;

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    @POST
    @Path("/items")
    public Response addItemToCart(@PathParam("customerId") int customerId, CartItem item) {
        if (!CustomerStorage.exists(customerId)) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Customer Not Found\", \"message\":\"Customer with ID " + customerId + " does not exist.\"}")
//                    .build();
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
        }

        Book book = BookStorage.getBookById(item.getBookId());
        if (book == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Book Not Found\", \"message\":\"Book with ID " + item.getBookId() + " does not exist.\"}")
//                    .build();
            throw new BookNotFoundException("Book with ID " + item.getBookId() + " does not exist.");
        }

        if (book.getStock() < item.getQuantity()) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{\"error\":\"Out of Stock\", \"message\":\"Requested quantity exceeds stock.\"}")
//                    .build();
            throw new OutOfStockException("Requested quantity exceeds stock.");
        }

        CartStorage.addToCart(customerId, item);
        return Response.status(Response.Status.CREATED).entity(item).build();
    }

    @GET
    public Response getCart(@PathParam("customerId") int customerId) {
        if (!CustomerStorage.exists(customerId)) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Customer Not Found\", \"message\":\"Customer with ID " + customerId + " does not exist.\"}")
//                    .build();
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
        }

        List<CartItem> cart = CartStorage.getCart(customerId);
        return Response.ok(cart).build();
    }

    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(@PathParam("customerId") int customerId,
                                   @PathParam("bookId") int bookId,
                                   CartItem updatedItem) {
        if (!CustomerStorage.exists(customerId)) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Customer Not Found\", \"message\":\"Customer with ID " + customerId + " does not exist.\"}")
//                    .build();
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
        }

        Book book = BookStorage.getBookById(bookId);
        if (book == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Book Not Found\", \"message\":\"Book with ID " + bookId + " does not exist.\"}")
//                    .build();
            throw new BookNotFoundException("Book with ID " + bookId + " does not exist.");
        }

        if (book.getStock() < updatedItem.getQuantity()) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{\"error\":\"Out of Stock\", \"message\":\"Requested quantity exceeds stock.\"}")
//                    .build();
            throw new OutOfStockException("Requested quantity exceeds stock.");
        }

        boolean updated = CartStorage.updateItem(customerId, bookId, updatedItem.getQuantity());
        if (!updated) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Cart Item Not Found\", \"message\":\"Book not found in cart.\"}")
//                    .build();
            throw new CartNotFoundException("Book not found in cart.");
        }

        return Response.ok(updatedItem).build();
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeCartItem(@PathParam("customerId") int customerId,
                                   @PathParam("bookId") int bookId) {
        if (!CustomerStorage.exists(customerId)) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Customer Not Found\", \"message\":\"Customer with ID " + customerId + " does not exist.\"}")
//                    .build();
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
        }

        boolean removed = CartStorage.removeItem(customerId, bookId);
        if (!removed) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Cart Item Not Found\", \"message\":\"Book not found in cart.\"}")
//                    .build();
            throw new CartNotFoundException("Book not found in cart.");
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}