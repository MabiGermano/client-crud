package br.com.challenge.clientcrud.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.challenge.clientcrud.exceptions.ClientNotFoundException;
import br.com.challenge.clientcrud.models.Client;
import br.com.challenge.clientcrud.repositories.ClientRepository;
import br.com.challenge.clientcrud.services.ClientService;

@SpringBootTest
public class ClientServiceTest {

	@Autowired
	ClientService clientService;
	
	@MockBean
	ClientRepository clientRepository;
	
	@Test
	public void createClientThenSuccess() {
		
		Client client = new Client();
		client.setName("client 1");	
		client.setBirthDate(LocalDate.of(1988, 9, 25));
		client.setPhoneNumber("81 965825478");
		
		Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenAnswer(i -> {
			Client clientToReturn = i.getArgument(0);
			clientToReturn.setId(1L);
			return clientToReturn;
		});
		
		Client returnedClient = clientService.create(client);
		assertThat(returnedClient).isEqualTo(client);
	}
	
	@Test
	public void listClientsThenSuccess() {
		
		List<Client> clientList = new ArrayList<Client>();
		clientList.add(new Client(1L, "Client 81", LocalDate.of(1989, 2, 5), "81 986548215"));
		clientList.add(new Client(2L, "Client 82", LocalDate.of(1990, 7, 11), "81 985685625"));
		clientList.add(new Client(3L, "Client 83", LocalDate.of(1991, 5, 20), "81 952645823"));
		clientList.add(new Client(4L, "Client 84", LocalDate.of(1987, 1, 8), "81 985682415"));
		
		Mockito.when(clientRepository.findAll())
		.thenReturn(clientList);
		
		List<Client> returnedList = clientService.list();
	
		assertThat(returnedList).isNotEmpty();
	}
	
	@Test
	public void listClientsThenReturnEmptyList() {
		
		List<Client> clientList = new ArrayList<Client>();
		
		Mockito.when(clientRepository.findAll())
		.thenReturn(clientList);
		
		List<Client> returnedList = clientService.list();
		
		assertThat(returnedList).isEmpty();
	}
	
	@Test
	public void findOneClientThenSuccess() throws ClientNotFoundException{

		Long id = 1L;
		Client client= new Client(1L, "Client 81", LocalDate.of(1989, 2, 5), "81 986548215");
	
		Mockito.when(clientRepository.findById(id)).thenReturn(Optional.of(client));
		Client returnedClient = clientService.findOne(id);
		assertThat(returnedClient).usingRecursiveComparison().isEqualTo(client);
	}
	
	@Test
	public void findOneClientThenThrowsException() {

		Long id = 2L;
		Mockito.when(clientRepository.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(ClientNotFoundException.class, () -> {
			clientService.findOne(id);
		});
	} 
	
	
}
