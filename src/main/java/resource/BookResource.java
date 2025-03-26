package resource;

import exception.BookNotFoundException;
import exception.InvalidInputException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Book;
import storage.BookStorage;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @POST
    public Response addBook(Book book) {
        // Validate input (e.g., future publication year)
        if (book.getPublicationYear() > 2024) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{\"error\":\"Invalid Input\", \"message\":\"Publication year cannot be in the future.\"}")
//                    .build();
            throw new InvalidInputException("Publication year cannot be in the future.");
        }

        Book added = BookStorage.addBook(book);
        return Response.status(Response.Status.CREATED).entity(added).build();
    }

    @GET
    public Response getAllBooks() {
        List<Book> books = BookStorage.getAllBooks();
        return Response.ok(books).build();
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") int id) {
        Book book = BookStorage.getBookById(id);
        if (book == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Book Not Found\", \"message\":\"Book with ID " + id + " does not exist.\"}")
//                    .build();
            throw new BookNotFoundException("Book with ID " + id + " does not exist.");
        }
        return Response.ok(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book updatedBook) {
        Book book = BookStorage.updateBook(id, updatedBook);
        if (book == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Book Not Found\", \"message\":\"Cannot update. Book with ID " + id + " does not exist.\"}")
//                    .build();
            throw new BookNotFoundException("Cannot update. Book with ID " + id + " does not exist.");
        }
        return Response.ok(book).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        boolean deleted = BookStorage.deleteBook(id);
        if (!deleted) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Book Not Found\", \"message\":\"Cannot delete. Book with ID " + id + " does not exist.\"}")
//                    .build();
            throw new BookNotFoundException("Cannot delete. Book with ID " + id + " does not exist.");
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}