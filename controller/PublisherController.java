package com.kks.Project.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kks.Project.entity.Publisher;
import com.kks.Project.service.PublisherService;

@RestController
@RequestMapping("/publisher")
public class PublisherController {
	@Autowired
	private PublisherService publisherService;
	
	// Get all publishers
	@GetMapping
	public ResponseEntity<List<Publisher>> getAllPublishers()
	{
		List<Publisher> publisherList = publisherService.getAllPublishers();
		if(publisherList.size() != 0)
			return new ResponseEntity<List<Publisher>>(publisherList,HttpStatus.OK);
		// If there are no publishers, return NOT_FOUND
		return new ResponseEntity<List<Publisher>>(publisherList,HttpStatus.NOT_FOUND);
	}
	
	// Get a publisher by publisher ID
	@GetMapping(value="/{publisherId}", produces="application/json")
	public ResponseEntity<Publisher> getPublisherByPublisherId(@PathVariable int publisherId)
	{
		Publisher a = publisherService.getPublisherByPublisherId(publisherId);
		if(a!=null)
			return new ResponseEntity<Publisher>(a, HttpStatus.OK);
		return new ResponseEntity<Publisher>(a,HttpStatus.NOT_FOUND);
	}
	
	// Insert a new publisher
	@PostMapping(value="/", consumes="application/json")
	public HttpStatus insertPublisher(@RequestBody Publisher publisher)
	{
		publisherService.insertOrModifyPublisher(publisher);
		return HttpStatus.OK;
	}
	
	// Modify an existing publisher
	@PutMapping(value="/", consumes="application/json")
	public HttpStatus modifyPublisher(@RequestBody Publisher publisher)
	{
		publisherService.insertOrModifyPublisher(publisher);
		return HttpStatus.OK;
	}
	
	// Delete a publisher by publisher ID
	@DeleteMapping("/{publisherId}")
	public HttpStatus deletePublisher(@PathVariable int publisherId)
	{
		if(publisherService.deletePublisherByPublisherId(publisherId))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
}
