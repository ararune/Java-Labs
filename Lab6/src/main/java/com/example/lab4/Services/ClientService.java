package com.example.lab4.Services;

import com.example.lab4.Entities.Address;
import com.example.lab4.Entities.Client;
import com.example.lab4.Repositories.AddressRepo;
import com.example.lab4.Repositories.ClientRepo;
import com.example.lab4.Repositories.DeviceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class ClientService {

    private final AddressRepo addressRepository;

    private final ClientRepo clientRepository;


    public Iterable<Client> getAllClients() { return clientRepository.findAll(); }


    public Client getClientById(Long id) throws Exception{

        Client Client = clientRepository.findById(id).orElse(null);
        if(Client == null) {
            throw new Exception("No such client found.");
        }

        return Client;
    }

    public Client createClient(Long addressId, Client newClient) throws Exception {
        Address address = addressRepository.findById(addressId).orElse(null);
        if (address == null) {
            throw new Exception("No such address found");
        }
        Client client = clientRepository.findClientByAddress(address);
        if(client != null){
            throw new Exception("Client with address already exists.");
        }

        newClient.setAddress(address);

        return clientRepository.save(newClient);

    }

    public Client updateClient(Long id, Client updatedClient) throws Exception {
        Client currentClient = clientRepository.findById(id).orElse(null);
        if(currentClient == null) {
            throw new Exception("No such client found.");
        }
        updatedClient.setId(currentClient.getId());
        return clientRepository.save(updatedClient);
    }

    public void deleteClient(Long id) throws Exception{
        Client deleteClient = clientRepository.findById(id).orElse(null);
        if(deleteClient == null) {
            throw new Exception("Could not find Client");
        }

        clientRepository.delete(deleteClient);
    }

}
