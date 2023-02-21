package com.example.lab4.Controllers;

import com.example.lab4.Entities.Address;
import com.example.lab4.Services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping(value = "/getAddresses")
    public Iterable<Address> getAll() { return addressService.getAllAddresses(); }

    @PostMapping(value = "/saveAddress")
    public ResponseEntity<Address> createAddress(@RequestBody Address newAddress) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(newAddress));
    }

    @GetMapping("/{id}")
    public Address getById(@PathVariable Long id) {
        Address address;
        try {
            address = addressService.getAddressesById(id);
        } catch (Exception ex) {
            return null;
        }
        return address;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody Address updatedAddress) {
        ResponseEntity response;
        try {
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body(addressService.updateAddress(id, updatedAddress));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        try{
            addressService.deleteAddress(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted address");
    }

}