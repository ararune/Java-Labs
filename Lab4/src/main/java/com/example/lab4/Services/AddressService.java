package com.example.lab4.Services;

import com.example.lab4.Entities.Address;
import com.example.lab4.Repositories.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AddressService {
    @Autowired
    private AddressRepo addressRepository;

    public Iterable<Address> getAllAddresses() { return addressRepository.findAll(); }

    public Address getAddressesById(Long id) throws Exception{

        Address address = addressRepository.findById(id).orElse(null);
        if(address == null) {
            throw new Exception("Could not find Address");
        }

        return address;
    }

    public Address createAddress(Address newAddress){ return addressRepository.save(newAddress); }

    public Address updateAddress(Long id, Address updatedAddress) throws Exception {
        Address oldAddress = addressRepository.findById(id).orElse(null);
        if(oldAddress == null) {
            throw new Exception("Could not find Address");
        }
        updatedAddress.setId(oldAddress.getId());
        return addressRepository.save(updatedAddress);
    }

    public void deleteAddress(Long id) throws Exception{
        Address deleteAddress = addressRepository.findById(id).orElse(null);
        if(deleteAddress == null) {
            throw new Exception("Could not find Address");
        }

        addressRepository.delete(deleteAddress);
    }
}
