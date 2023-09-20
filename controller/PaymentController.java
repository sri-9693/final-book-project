package com.kks.Project.controller;
//import java.util.List;
import java.util.Optional;
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
import com.kks.Project.entity.Payment;
import com.kks.Project.service.PaymentService;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = {"http://localhost:4200"},allowCredentials = "true")
@RestController
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	
	// Get all payments for a specific customer (by customer ID from session)
	@GetMapping
	public ResponseEntity<Optional<Payment>> getAllPayments(HttpSession session)
	{
		Optional<Payment> paymentList=null;
		if(session.getAttribute("id")!=null) 
		{
			
			paymentList = paymentService.getAllPayments((int)session.getAttribute("id"));
			if(paymentList.isPresent())
				return new ResponseEntity<Optional<Payment>>(paymentList,HttpStatus.OK);
		}
		// If there are no payments or if the session ID is not present, return NOT_FOUND
		return new ResponseEntity<Optional<Payment>>(paymentList,HttpStatus.NOT_FOUND);
	}
	
	// Get a payment by payment ID
	@GetMapping(value="/{paymentId}", produces="application/json")
	public ResponseEntity<Payment> getPaymentByPaymentId(@PathVariable int paymentId)
	{
		Payment a = paymentService.getPaymentByPaymentId(paymentId);
		if(a!=null)
			return new ResponseEntity<Payment>(a, HttpStatus.OK);
		return new ResponseEntity<Payment>(a,HttpStatus.NOT_FOUND);
	}
	
	// Insert a new payment
	@PostMapping(value="/", consumes="application/json")
	public HttpStatus insertPayment(@RequestBody Payment payment)
	{
		paymentService.insertOrModifyPayment(payment);
		return HttpStatus.OK;
	}
	
	// Modify an existing payment
	@PutMapping(value="/", consumes="application/json")
	public HttpStatus modifyPayment(@RequestBody Payment payment)
	{
		paymentService.insertOrModifyPayment(payment);
		return HttpStatus.OK;
	}
	
	// Delete a payment by payment ID
	@DeleteMapping("/{paymentId}")
	public HttpStatus deletepayment(@PathVariable int paymentId)
	{
		if(paymentService.deletePaymentByPaymentId(paymentId))
			return HttpStatus.OK;
		// If the payment is not found, return NOT_FOUND
		return HttpStatus.NOT_FOUND;
	}
	
	// Make a payment with a coupon code and amount
	@GetMapping("/makepayment/{couponCode}/{amount}")
	public void makePayment(@PathVariable String couponCode,@PathVariable double amount,HttpSession session) {
		System.out.println(session.getAttribute("id")+ " " +couponCode+ " " +amount);
		if(session.getAttribute("id")!= null) {
			paymentService.makingPayment((int)session.getAttribute("id"), amount, couponCode);
		}
	}

}
