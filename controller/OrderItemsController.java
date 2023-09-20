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
import com.kks.Project.entity.OrderItems;
import com.kks.Project.service.OrderItemsService;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = {"http://localhost:4200"},allowCredentials = "true")
@RestController
@RequestMapping("/orderitems")
public class OrderItemsController 
{
	@Autowired
	private OrderItemsService orderItemsService;
	
	// Retrieve all order items
	@GetMapping
	public ResponseEntity<List<OrderItems>> getAllOrderItemss()
	{
		List<OrderItems> orderItemsList = orderItemsService.getAllOrderItemss();
		if(orderItemsList.size() != 0)
			return new ResponseEntity<List<OrderItems>>(orderItemsList,HttpStatus.OK);
		return new ResponseEntity<List<OrderItems>>(orderItemsList,HttpStatus.NOT_FOUND);
	}
	
	 // Retrieve order items for a specific customer (by customer ID from session)
	@GetMapping("/myorderitems")
	public ResponseEntity<List<OrderItems>> getBooksByCustomerId(HttpSession session)
	{
		Customer customer = new Customer();
		int id = (int)session.getAttribute("id");
		customer.setCustomerID(id);
		List<OrderItems> orderItemsList = orderItemsService.getBooksByCustomrId(customer);
		if(orderItemsList.size() != 0)
			return new ResponseEntity<List<OrderItems>>(orderItemsList,HttpStatus.OK);
		return new ResponseEntity<List<OrderItems>>(orderItemsList,HttpStatus.NOT_FOUND);
	}
	

	// Retrieve an order item by orderItemsId
	@GetMapping(value="/{orderItemsId}", produces="application/json")
	public ResponseEntity<OrderItems> getOrderItemsByOrderItemsId(@PathVariable int orderItemsId)
	{
		OrderItems orderItems = orderItemsService.getOrderItemsByOrderItemsId(orderItemsId);
		if(orderItems!=null)
			return new ResponseEntity<OrderItems>(orderItems, HttpStatus.OK);
		return new ResponseEntity<OrderItems>(orderItems,HttpStatus.NOT_FOUND);
	}
	
	// Insert an order item
	@PostMapping(value="/", consumes="application/json")
	public HttpStatus insertOrderItems(@RequestBody OrderItems orderItems)
	{
		orderItemsService.insertOrModifyOrderItems(orderItems);
		return HttpStatus.OK;
	}
	
	// Modify an order item
	@PutMapping(value="/", consumes="application/json")
	public HttpStatus modifyOrderItems(@RequestBody OrderItems orderItems)
	{
		orderItemsService.insertOrModifyOrderItems(orderItems);
		return HttpStatus.OK;
	}
	
	// Delete an order item by orderItemsId
	@DeleteMapping("/{orderItemsId}")
	public HttpStatus deleteorderItems(@PathVariable int orderItemsId)
	{
		if(orderItemsService.deleteOrderItemsByOrderItemsId(orderItemsId))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}

}
