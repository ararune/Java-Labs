package com.example.lab4.Services;

import com.example.lab4.Entities.Address;
import com.example.lab4.Entities.Client;
import com.example.lab4.Repositories.AddressRepo;
import com.example.lab4.Repositories.ClientRepo;
import com.example.lab4.Repositories.DeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ClientService {
    @Autowired
    private AddressRepo addressRepository;
    @Autowired
    private ClientRepo clientRepository;
    @Autowired
    private DeviceRepo deviceRepository;

    public Iterable<Client> getAllClients() { return clientRepository.findAll(); }


    public Client getClientById(Long id) throws Exception{

        Client Client = clientRepository.findById(id).orElse(null);
        if(Client == null) {
            throw new Exception("Could not find Client");
        }

        return Client;
    }

    public Client createClient(Long addressId, Client newClient) throws Exception {
        Address address = addressRepository.findById(addressId).orElse(null);
        if (address == null) {
            throw new Exception("Address does not exists");
        }
        Client client = clientRepository.findClientByAddress(address);
        if(client != null){
            throw new Exception("Client with address already exists");
        }

        newClient.setAddress(address);

        return clientRepository.save(newClient);

    }

    public Client updateClient(Long id, Client updatedClient) throws Exception {
        Client oldClient = clientRepository.findById(id).orElse(null);
        if(oldClient == null) {
            throw new Exception("Could not find Client");
        }
        updatedClient.setId(oldClient.getId());
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
