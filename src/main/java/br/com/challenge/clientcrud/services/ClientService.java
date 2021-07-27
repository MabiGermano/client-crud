package br.com.challenge.clientcrud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.challenge.clientcrud.exceptions.ClientNotFoundException;
import br.com.challenge.clientcrud.models.Client;
import br.com.challenge.clientcrud.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;

	public Client create(Client client) {
		return clientRepository.save(client);
	}

	public List<Client> list() {
		return clientRepository.findAll();
	}

	public Client findOne(Long id) {
		Optional<Client> clientFound = clientRepository.findById(id);
		if (clientFound.isEmpty()) {
			throw new ClientNotFoundException("No Client Was Found");
		}
		return clientFound.get();
	}
}
