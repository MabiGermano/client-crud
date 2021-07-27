package br.com.challenge.clientcrud.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.challenge.clientcrud.exceptions.ClientNotFoundException;
import br.com.challenge.clientcrud.models.Client;
import br.com.challenge.clientcrud.services.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
@RequestMapping("api/v1/client")
public class ClientController {

	@Autowired
	ClientService clientService;
	
	@PostMapping
	@ApiOperation(value = "Creating client")
	@ApiResponses({
		@ApiResponse( code= HttpServletResponse.SC_CREATED, message = "Creating a client", response = Long.class )
	})
	public ResponseEntity<Long> create (@RequestBody Client client) {
		Client clientResponse = clientService.create(client);
		return ResponseEntity.status(HttpStatus.CREATED).body(clientResponse.getId());
	}
	
	@GetMapping
	@ApiOperation(value = "Listing clients")
	@ApiResponses({
		@ApiResponse( code= HttpServletResponse.SC_OK, message = "Listing clients", response = List.class),
		@ApiResponse( code= HttpServletResponse.SC_NO_CONTENT, message = "No clients were found", response = List.class)
	})
	public ResponseEntity<List<Client>> list() {
		List<Client> returnedList = clientService.list();
		if(returnedList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(returnedList);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Find a client by id")
	@ApiResponses({
		@ApiResponse( code= HttpServletResponse.SC_OK, message = "Find client by id", response = Client.class),
		@ApiResponse( code= HttpServletResponse.SC_NOT_FOUND, message = "No client was found", response = String.class)
	})
	public ResponseEntity<Object> findOne(@PathVariable(name = "id") Long id) {
		try {
			Client client = clientService.findOne(id);
			return ResponseEntity.ok(client);
		} catch (ClientNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
