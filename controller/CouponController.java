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
import com.kks.Project.entity.Coupon;
import com.kks.Project.service.CouponService;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = {"http://localhost:4200"},allowCredentials = "true")
@RestController
@RequestMapping("/coupon")
public class CouponController {
	@Autowired
	private CouponService couponService;
	
	// Retrieve all coupons
	@GetMapping
	public ResponseEntity<List<Coupon>> getAllCoupons()
	{
		List<Coupon> couponList = couponService.getAllCoupons();
		if(couponList.size() != 0)
			return new ResponseEntity<List<Coupon>>(couponList,HttpStatus.OK);
		return new ResponseEntity<List<Coupon>>(couponList,HttpStatus.NOT_FOUND);
	}
	
	// Retrieve a coupon by couponId
	@GetMapping(value="/{couponId}", produces="application/json")
	public ResponseEntity<Coupon> getCouponByCouponId(@PathVariable int couponId)
	{
		Coupon coupon = couponService.getCouponByCouponId(couponId);
		if(coupon!=null)
			return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
		return new ResponseEntity<Coupon>(coupon,HttpStatus.NOT_FOUND);
	}
	
	 // Retrieve coupon discount by couponCode
	@GetMapping(value="/couponcode/{couponCode}", produces="application/json")
	public Coupon getCouponDiscount(@PathVariable String couponCode,HttpSession session)
	{
		if(session.getAttribute("id")!= null) {
			Coupon coupon = couponService.getCouponByCouponCode(couponCode);
			if(coupon!=null)
				return coupon;
			return coupon;
		}
		return new Coupon();
	}
	
	 // Insert a coupon
	@PostMapping(value="/", consumes="application/json")
	public HttpStatus insertCoupon(@RequestBody Coupon coupon)
	{
		couponService.insertOrModifyCoupon(coupon);
		return HttpStatus.OK;
	}
	
	// Modify a coupon
	@PutMapping(value="/", consumes="application/json")
	public HttpStatus modifyCoupon(@RequestBody Coupon coupon)
	{
		couponService.insertOrModifyCoupon(coupon);
		return HttpStatus.OK;
	}
	
	// Delete a coupon by couponId
	@DeleteMapping("/{couponId}")
	public HttpStatus deletecoupon(@PathVariable int couponId)
	{
		if(couponService.deleteCouponByCouponId(couponId))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
}
