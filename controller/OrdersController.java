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
import com.kks.Project.entity.Orders;
import com.kks.Project.service.OrdersService;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = {"http://localhost:4200"},allowCredentials = "true")
@RestController
@RequestMapping("/orders")
public class OrdersController {
	@Autowired
	private OrdersService ordersService;
	
	// Retrieve all orders
	@GetMapping
	public ResponseEntity<List<Orders>> getAllOrderss()
	{
		List<Orders> orderList = ordersService.getAllOrderss();
		if(orderList.size() != 0)
			return new ResponseEntity<List<Orders>>(orderList,HttpStatus.OK);
		return new ResponseEntity<List<Orders>>(orderList,HttpStatus.NOT_FOUND);
	}
	
	// Retrieve orders for a specific customer (by customer ID from session)
	@GetMapping("/myorders")
	public ResponseEntity<List<Orders>> getBooksByOrdersId(HttpSession session)
	{
		
		Customer customer = new Customer();
		int id = (int)session.getAttribute("id");
		customer.setCustomerID(id);
		List<Orders> orderList = ordersService.getBooksByCustomrId(customer);
		if(orderList.size() != 0)
			return new ResponseEntity<List<Orders>>(orderList,HttpStatus.OK);
		return new ResponseEntity<List<Orders>>(orderList,HttpStatus.NOT_FOUND);
	}
	
	// Retrieve an order by ordersId
	@GetMapping(value="/{ordersId}", produces="application/json")
	public ResponseEntity<Orders> getOrdersByOrdersId(@PathVariable int ordersId)
	{
		Orders orders = ordersService.getOrdersByOrdersId(ordersId);
		if(orders!=null)
			return new ResponseEntity<Orders>(orders, HttpStatus.OK);
		return new ResponseEntity<Orders>(orders,HttpStatus.NOT_FOUND);
	}
	
	// Insert an order
	@PostMapping(value="/", consumes="application/json")
	public HttpStatus insertOrders(@RequestBody Orders orders)
	{
		ordersService.insertOrModifyOrders(orders);
		return HttpStatus.OK;
	}
	
	// Modify an order
	@PutMapping(value="/", consumes="application/json")
	public HttpStatus modifyOrders(@RequestBody Orders orders)
	{
		ordersService.insertOrModifyOrders(orders);
		return HttpStatus.OK;
	}
	
	// Delete an order by ordersId
	@DeleteMapping("/{ordersId}")
	public HttpStatus deleteorders(@PathVariable int ordersId)
	{
		if(ordersService.deleteOrdersByOrdersId(ordersId))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}

}
