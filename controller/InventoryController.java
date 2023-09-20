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
import com.kks.Project.entity.Inventory;
import com.kks.Project.service.InventoryService;

@CrossOrigin(origins = {"http://localhost:4200"},allowCredentials = "true")
@RestController
@RequestMapping("inventory")
public class InventoryController {
	@Autowired
	private InventoryService inventoryService;
	
	// Retrieve all inventory items
	@GetMapping
	public ResponseEntity<List<Inventory>> getAllInventorys()
	{
		List<Inventory> inventoryList = inventoryService.getAllInventorys();
		if(inventoryList.size() != 0)
			return new ResponseEntity<List<Inventory>>(inventoryList,HttpStatus.OK);
		return new ResponseEntity<List<Inventory>>(inventoryList,HttpStatus.NOT_FOUND);
	}
	
	// Retrieve an inventory item by inventoryId
	@GetMapping(value="/{inventoryId}", produces="application/json")
	public ResponseEntity<Inventory> getInventoryByInventoryId(@PathVariable int inventoryId)
	{
		Inventory inventory = inventoryService.getInventoryByInventoryId(inventoryId);
		if(inventory!=null)
			return new ResponseEntity<Inventory>(inventory, HttpStatus.OK);
		return new ResponseEntity<Inventory>(inventory,HttpStatus.NOT_FOUND);
	}
	
	// Insert an inventory item
	@PostMapping(value="/", consumes="application/json")
	public HttpStatus insertInventory(@RequestBody Inventory inventory)
	{
		inventoryService.insertOrModifyInventory(inventory);
		return HttpStatus.OK;
	}
	
	 // Modify an inventory item
	@PutMapping(value="/", consumes="application/json")
	public HttpStatus modifyInventory(@RequestBody Inventory inventory)
	{
		inventoryService.insertOrModifyInventory(inventory);
		return HttpStatus.OK;
	}
	
	// Delete an inventory item by inventoryId
	@DeleteMapping("/{inventoryId}")
	public HttpStatus deleteinventory(@PathVariable int inventoryId)
	{
		if(inventoryService.deleteInventoryByInventoryId(inventoryId))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}

}
