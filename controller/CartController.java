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
import com.kks.Project.entity.Book;
import com.kks.Project.entity.Cart;
import com.kks.Project.entity.Customer;
import com.kks.Project.entity.Inventory;
import com.kks.Project.service.CartService;
import com.kks.Project.service.InventoryService;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = {"http://localhost:4200"},allowCredentials = "true")
@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	@Autowired
	private InventoryService inventoryService;
	
	 // Retrieve all carts
	@GetMapping
	public ResponseEntity<List<Cart>> getAllCarts()
	{
		List<Cart> cart = cartService.getAllCarts();
		if(cart.size() != 0)
			return new ResponseEntity<List<Cart>>(cart,HttpStatus.OK);
		return new ResponseEntity<List<Cart>>(cart,HttpStatus.NOT_FOUND);
	}
	
	 // Retrieve books in the cart for the current customer
	@GetMapping("/mybooks")
	public ResponseEntity<List<Cart>> getBooksByCustomrId(HttpSession session)
	{
		
		Customer customer = new Customer();
		int id = (int)session.getAttribute("id");
		customer.setCustomerID(id);
		List<Cart> cart = cartService.getBooksByCustomrId(customer);
		if(cart.size() != 0)
			return new ResponseEntity<List<Cart>>(cart,HttpStatus.OK);
		return new ResponseEntity<List<Cart>>(cart,HttpStatus.NOT_FOUND);
	}
	
	// Retrieve a cart item by cartId
	@GetMapping(value="/{cartId}", produces="application/json")
	public ResponseEntity<Cart> getCartByCartId(@PathVariable int cartId)
	{
		Cart cart = cartService.getCartByCartId(cartId);
		if(cart!=null)
			return new ResponseEntity<Cart>(cart, HttpStatus.OK);
		return new ResponseEntity<Cart>(cart,HttpStatus.NOT_FOUND);
	}
	
	// Insert a cart item
	@PostMapping(value="/", consumes="application/json")
	public HttpStatus insertCart(@RequestBody Cart cart, HttpSession session)//
	{
		Inventory inventoryItem = inventoryService.getByBookId(cart.getBookId());
		if(session.getAttribute("id")!= null) 
		{
			if( inventoryItem.getQuantity() > 0) 
			{
				Customer cust = new Customer();
				cust.setCustomerID(1);
				cust.setCustomerID((int)session.getAttribute("id"));
				cart.setCustomerId(cust);
				
				List<Cart> cartList = cartService.getBooksByCustomrId(cust);
				for(Cart cart1 :cartList) 
				{
					if(cart1.getBookId().getBookID()== cart.getBookId().getBookID() && cart1.getCustomerId().getCustomerID() == cart.getCustomerId().getCustomerID()) 
					{
						return HttpStatus.NOT_MODIFIED;
					}
				}
				
				cart.setBook_id(cart.getBookId());
				cart.setQuantity(1);
				cartService.insertOrModifyCart(cart);
				return HttpStatus.OK;
			}
			return HttpStatus.NOT_FOUND;
		}
		return HttpStatus.NOT_MODIFIED;
	}
	
	 // Modify a cart item
	@PutMapping(value="/", consumes="application/json")
	public HttpStatus modifyCart(@RequestBody Cart cart)
	{
		cartService.insertOrModifyCart(cart);
		return HttpStatus.OK;
	}
	
	// Delete a cart item by cartId
	@DeleteMapping("/{cartId}")
	public HttpStatus deletecart(@PathVariable int cartId)
	{
		if(cartService.deleteCartByCartId(cartId))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	
	// Increase the quantity of a book in the cart
	@GetMapping(value="/increase/{cartId}/{bookId}",produces="application/json")
	public HttpStatus increaseCart(@PathVariable int cartId,@PathVariable int bookId)
	{
		Cart cart = new Cart();
		Book book = new Book();
		book.setBookID(bookId);
		cart.setBook_id(book);
		Inventory inventoryItem = inventoryService.getByBookId(cart.getBookId());
		if( inventoryItem.getQuantity() > 0) 
		{
			cartService.increaseQuantity(cartId, bookId);
			return HttpStatus.OK;
		}
		else 
		{
			return HttpStatus.NOT_FOUND;
		}	
	}
	
	// Decrease the quantity of a book in the cart
	@GetMapping(value="/decrease/{cartId}/{bookId}",produces="application/json")
	public HttpStatus decreaseCart(@PathVariable int cartId,@PathVariable int bookId)
	{
		Cart cart = cartService.getCartByCartId(cartId);
		if( cart.getQuantity() == 1) 
		{
			cartService.deleteCartByCartId(cartId);
			return HttpStatus.NOT_FOUND;
		}
		else 
		{
			cartService.decreaseQuantity(cartId, bookId);
			return HttpStatus.OK;
		}
	}
}
