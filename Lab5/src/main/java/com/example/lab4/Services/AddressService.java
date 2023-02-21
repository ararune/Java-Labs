package com.example.lab4.Services;

import com.example.lab4.Entities.Address;
import com.example.lab4.Repositories.AddressRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepo addressRepository;

    public Iterable<Address> getAllAddresses() { return addressRepository.findAll(); }

    public Address getAddressesById(Long id) throws Exception{

        Address address = addressRepository.findById(id).orElse(null);
        if(address == null) {
            throw new Exception("No such address found.");
        }

        return address;
    }


    public Address createAddress(Address newAddress){ return addressRepository.save(newAddress); }

    public Address updateAddress(Long id, Address updatedAddress) throws Exception {
        Address currentAddress = addressRepository.findById(id).orElse(null);
        if(currentAddress == null) {
            throw new Exception("No such address found");
        }
        updatedAddress.setId(currentAddress.getId());
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
