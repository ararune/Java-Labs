package com.example.lab4.Controllers;

import com.example.lab4.Entities.Client;
import com.example.lab4.Services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    public ClientService clientService;

    @GetMapping(value = "/getClients")
    public Iterable<Client> getAll() { return clientService.getAllClients(); }

    @PostMapping("/saveClient")
    public ResponseEntity createClient(@PathVariable Long addressId, @RequestBody Client newClient) {
        ResponseEntity response;
        try{
            response = ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(addressId, newClient));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id) {
        Client client;
        try {
            client = clientService.getClientById(id);
        } catch (Exception ex) {
            return null;
        }
        return client;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        ResponseEntity response;
        try {
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body(clientService.updateClient(id, updatedClient));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        try{
            clientService.deleteClient(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted Client");
    }

}