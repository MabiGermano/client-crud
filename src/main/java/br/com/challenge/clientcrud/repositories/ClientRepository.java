package br.com.challenge.clientcrud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.challenge.clientcrud.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

}
