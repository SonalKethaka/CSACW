package resource;

import exception.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Book;
import model.CartItem;
import model.Order;
import storage.BookStorage;
import storage.CartStorage;
import storage.CustomerStorage;
import storage.OrderStorage;

import java.util.ArrayList;
import java.util.List;

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @POST
    public Response placeOrder(@PathParam("customerId") int customerId) {
        if (!CustomerStorage.exists(customerId)) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Customer Not Found\", \"message\":\"Customer with ID " + customerId + " does not exist.\"}")
//                    .build();
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
        }

        List<CartItem> cart = CartStorage.getCart(customerId);
        if (cart == null || cart.isEmpty()) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{\"error\":\"Cart Not Found\", \"message\":\"Cart is empty or missing.\"}")
//                    .build();
            throw new CartNotFoundException("Cart is empty or missing.");
        }

        double total = 0;
        for (CartItem item : cart) {
            Book book = BookStorage.getBookById(item.getBookId());
            if (book == null) {
                throw new BookNotFoundException("Book ID " + item.getBookId() + " does not exist.");
            }
            if (book.getStock() < item.getQuantity()) {
                throw new OutOfStockException("Book ID " + item.getBookId() + " is out of stock.");
            }
            total += item.getQuantity() * book.getPrice();
        }

        // Deduct stock
        for (CartItem item : cart) {
            Book book = BookStorage.getBookById(item.getBookId());
            book.setStock(book.getStock() - item.getQuantity());
        }

        Order newOrder = new Order(0, customerId, new ArrayList<>(cart), total);
        OrderStorage.addOrder(customerId, OrderStorage.getOrders(customerId), newOrder);
        CartStorage.getCart(customerId).clear(); // clear cart after order

        return Response.status(Response.Status.CREATED).entity(newOrder).build();
    }

    @GET
    public Response getAllOrders(@PathParam("customerId") int customerId) {
        if (!CustomerStorage.exists(customerId)) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Customer Not Found\", \"message\":\"Customer with ID " + customerId + " does not exist.\"}")
//                    .build();
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
        }

        return Response.ok(OrderStorage.getOrders(customerId)).build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrderById(@PathParam("customerId") int customerId,
                                 @PathParam("orderId") int orderId) {
        if (!CustomerStorage.exists(customerId)) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Customer Not Found\", \"message\":\"Customer with ID " + customerId + " does not exist.\"}")
//                    .build();
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
        }

        Order order = OrderStorage.getOrderById(customerId, orderId);
        if (order == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Order Not Found\", \"message\":\"Order with ID " + orderId + " does not exist.\"}")
//                    .build();
            throw new InvalidInputException("Order with ID " + orderId + " does not exist.");
        }

        return Response.ok(order).build();
    }
}