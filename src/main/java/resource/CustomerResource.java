package resource;

import exception.CustomerNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Customer;
import storage.CustomerStorage;

import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @POST
    public Response addCustomer(Customer customer) {
        Customer added = CustomerStorage.addCustomer(customer);
        return Response.status(Response.Status.CREATED).entity(added).build();
    }

    @GET
    public Response getAllCustomers() {
        List<Customer> customers = CustomerStorage.getAllCustomers();
        return Response.ok(customers).build();
    }

    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") int id) {
        Customer customer = CustomerStorage.getCustomerById(id);
        if (customer == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Customer Not Found\", \"message\":\"Customer with ID " + id + " does not exist.\"}")
//                    .build();
            throw new CustomerNotFoundException("Customer with ID " + id + " does not exist.");
        }
        return Response.ok(customer).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        Customer customer = CustomerStorage.updateCustomer(id, updatedCustomer);
        if (customer == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Customer Not Found\", \"message\":\"Cannot update. Customer with ID " + id + " does not exist.\"}")
//                    .build();
            throw new CustomerNotFoundException("Cannot update. Customer with ID " + id + " does not exist.");
        }
        return Response.ok(customer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        boolean deleted = CustomerStorage.deleteCustomer(id);
        if (!deleted) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Customer Not Found\", \"message\":\"Cannot delete. Customer with ID " + id + " does not exist.\"}")
//                    .build();
            throw new CustomerNotFoundException("Cannot delete. Customer with ID " + id + " does not exist.");
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}