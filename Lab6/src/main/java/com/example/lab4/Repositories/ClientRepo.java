package com.example.lab4.Repositories;

import com.example.lab4.Entities.Address;
import com.example.lab4.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    Client findClientByAddress(Address address);
}
