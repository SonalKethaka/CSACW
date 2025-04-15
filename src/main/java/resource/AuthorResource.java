package resource;

import exception.AuthorNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Author;
import model.Book;
import storage.AuthorStorage;
import storage.BookStorage;

import java.util.List;
import java.util.stream.Collectors;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    @POST
    public Response addAuthor(Author author) {
        Author added = AuthorStorage.addAuthor(author);
        return Response.status(Response.Status.CREATED).entity(added).build();
    }

    @GET
    public Response getAllAuthors() {
        return Response.ok(AuthorStorage.getAllAuthors()).build();
    }

    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") int id) {
        Author author = AuthorStorage.getAuthorById(id);
        if (author == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Author Not Found\", \"message\":\"Author with ID " + id + " does not exist.\"}")
//                    .build();
            throw new AuthorNotFoundException("Author with ID " + id + " does not exist.");
        }
        return Response.ok(author).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        Author author = AuthorStorage.updateAuthor(id, updatedAuthor);
        if (author == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Author Not Found\", \"message\":\"Cannot update. Author with ID " + id + " does not exist.\"}")
//                    .build();
            throw new AuthorNotFoundException("Cannot update. Author with ID " + id + " does not exist.");
        }
        return Response.ok(author).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        boolean deleted = AuthorStorage.deleteAuthor(id);
        if (!deleted) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Author Not Found\", \"message\":\"Cannot delete. Author with ID " + id + " does not exist.\"}")
//                    .build();
            throw new AuthorNotFoundException("Cannot delete. Author with ID " + id + " does not exist.");
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}/books")
    public Response getBooksByAuthor(@PathParam("id") int authorId) {
        if (!AuthorStorage.exists(authorId)) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Author Not Found\", \"message\":\"Author with ID " + authorId + " does not exist.\"}")
//                    .build();
            throw new AuthorNotFoundException("Author with ID " + authorId + " does not exist.");
        }

        List<Book> books = BookStorage.getAllBooks().stream()
                .filter(book -> book.getAuthor().getId() == authorId)
                .collect(Collectors.toList());

        return Response.ok(books).build();
    }
}