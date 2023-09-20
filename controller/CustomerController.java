package com.kks.Project.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kks.Project.entity.Customer;
import com.kks.Project.service.CustomerService;
import jakarta.servlet.http.HttpSession;


@CrossOrigin(origins = {"http://localhost:4200"},allowCredentials = "true")
@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	// Retrieve all customers
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers()
	{
		List<Customer> customerList = customerService.getAllCustomers();
		if(customerList.size() != 0)
			return new ResponseEntity<List<Customer>>(customerList,HttpStatus.OK);
		return new ResponseEntity<List<Customer>>(customerList,HttpStatus.NOT_FOUND);
	}
	
	// Retrieve a customer by customerId
	@GetMapping(value="/{customerId}", produces="application/json")
	public ResponseEntity<Customer> getCustomerByCustomerId(@PathVariable int customerId)
	{
		Customer customer = customerService.getCustomerByCustomerId(customerId);
		if(customer!=null)
			return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		return new ResponseEntity<Customer>(customer,HttpStatus.NOT_FOUND);
	}
	
	 // Insert a customer (for signup)
	@PostMapping(value="/signup", consumes="application/json")
	public HttpStatus insertCustomer(@RequestBody Customer customer)
	{
		customerService.insertOrModifyCustomer(customer);
		return HttpStatus.OK;
	}
	
	 // Modify a customer
	@PutMapping(value="/", consumes="application/json")
	public HttpStatus modifyCustomer(@RequestBody Customer customer)
	{
		customerService.insertOrModifyCustomer(customer);
		return HttpStatus.OK;
	}
	
	// Delete a customer by customerId
	@DeleteMapping("/{customerId}")
	public HttpStatus deletecustomer(@PathVariable int customerId)
	{
		if(customerService.deleteCustomerByCustomerId(customerId))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	
	// Login a customer and set session attribute
	@PostMapping(value="/login",consumes="application/json")
	public boolean countOfValidCustomer(@RequestBody Customer customer,HttpSession request) {
		Integer id = customerService.countOfCustomer(customer.getEmail(),customer.getPassword());
		if( id != null ) 
		{
			request.setAttribute("id", id);
	            System.out.println(request.getAttribute("id"));
		}
		return id != null;
	}
	
	// Logout a customer and destroy the session
	 @GetMapping("/logout")
	 public String logout(HttpSession session) {
//	     HttpSession session = request.getSession(false); // Get the existing session if it exists
	     if (session.getAttribute("id") != null) {
	        session.invalidate(); // Invalidate (destroy) the session
	     }
	     System.out.println(session.getAttribute("id"));
	     return "redirect:/login"; // Redirect to the login page or any other appropriate URL
	 }
}
