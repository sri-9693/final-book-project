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
import com.kks.Project.entity.Admin;
import com.kks.Project.entity.PopularBooks;
import com.kks.Project.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//Allow cross-origin requests from http://localhost:4200 with credentials
@CrossOrigin(origins = {"http://localhost:4200"}, allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class AdminController 
{
	
	 @Autowired
	 private AdminService adminService;
	
	 // Retrieve all admins
	 @GetMapping
	 public ResponseEntity<List<Admin>> getAllAdmins() 
	 {
	     List<Admin> adminList = adminService.getAllAdmins();
	     if (!adminList.isEmpty())
	         return new ResponseEntity<List<Admin>>(adminList, HttpStatus.OK);
	     return new ResponseEntity<List<Admin>>(adminList, HttpStatus.NOT_FOUND);
	 }
	
	 // Retrieve an admin by adminId
	 @GetMapping(value = "/{adminId}", produces = "application/json")
	 public ResponseEntity<Admin> getAdminByAdminId(@PathVariable int adminId) 
	 {
	     Admin admin = adminService.getAdminByAdminId(adminId);
	     if (admin != null)
	         return new ResponseEntity<Admin>(admin, HttpStatus.OK);
	     return new ResponseEntity<Admin>(admin, HttpStatus.NOT_FOUND);
	 }
	
	 // Insert or modify an admin
	 @PostMapping(value = "/", consumes = "application/json")
	 public HttpStatus insertOrModifyAdmin(@RequestBody Admin admin) 
	 {
	     adminService.insertOrModifyAdmin(admin);
	     return HttpStatus.OK;
	 }
	
	 // Modify an admin
	 @PutMapping(value = "/", consumes = "application/json")
	 public HttpStatus modifyAdmin(@RequestBody Admin admin) 
	 {
	     adminService.insertOrModifyAdmin(admin);
	     return HttpStatus.OK;
	 }
	
	 // Delete an admin by adminId
	 @DeleteMapping("/{adminId}")
	 public HttpStatus deleteAdmin(@PathVariable int adminId) 
	 {
	     if (adminService.deleteAdminByAdminId(adminId))
	         return HttpStatus.OK;
	     return HttpStatus.NOT_FOUND;
	 }
	
	 // Authenticate an admin and create a session
	 @PostMapping(value = "/login", consumes = "application/json")
	 public boolean countOfValidAdmin(@RequestBody Admin admin, HttpServletRequest request) 
	 {
	     Integer id = adminService.countOfAdmin(admin.getEmail(), admin.getPassword());
	     if (id != null) 
	     {
	         HttpSession session = request.getSession(true);
	         session.setAttribute("id", id);
	         System.out.println(session.getAttribute("id"));
	     }
	     return id != null;
	 }
	
	 // Insert an admin
	 @PostMapping(value = "/signup", consumes = "application/json")
	 public HttpStatus insertAdmin(@RequestBody Admin admin) 
	 {
	     adminService.insertOrModifyAdmin(admin);
	     return HttpStatus.OK;
	 }
	
	 // Logout and invalidate the session
	 @GetMapping("/logout")
	 public String logout(HttpSession session) 
	 {
	     if (session.getAttribute("id") != null) 
	     {
	         session.invalidate(); // Invalidate (destroy) the session
	     }
	     System.out.println(session.getAttribute("id"));
	     return "redirect:/login"; // Redirect to the login page or any other appropriate URL
	 }
	
	 // Retrieve popular books
	 @GetMapping("/popularbooks")
	 public List<PopularBooks> getPopularBooks() 
	 {
	     return adminService.getPopularBooks();
	 }
}