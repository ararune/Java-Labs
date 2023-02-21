package com.example.lab4.Controllers;

import com.example.lab4.Entities.Address;
import com.example.lab4.Repositories.AddressRepo;
import com.example.lab4.Services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;
    private final AddressRepo addressRepository;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    @GetMapping("/display")
    public String getAllAddresses(Model model, @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        try {
            List<Sort.Order> orders = new ArrayList<Sort.Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Address> add;
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Address> pageAddresses;
            if (country == null)
                pageAddresses = addressRepository.findAll(pagingSort);
            else
                pageAddresses = addressRepository.findByCountryContaining(country, city, pagingSort);

            add = pageAddresses.getContent();

//            Iterable<Address> addresses = addressService.getAllAddresses();
            model.addAttribute("addresses", pageAddresses);
            return "address";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
        @GetMapping(value = "/all")
    public Iterable<Address> getAll() { return addressService.getAllAddresses(); }

    @GetMapping("/address")
    public ResponseEntity<Map<String, Object>> getAllAddressesPage(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        try {
            List<Sort.Order> orders = new ArrayList<Sort.Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Address> add;
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Address> pageAddresses;
            if (country == null)
                pageAddresses = addressRepository.findAll(pagingSort);
            else
                pageAddresses = addressRepository.findByCountryContaining(country, city, pagingSort);

            add = pageAddresses.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("addresses", add);
            response.put("currentPage", pageAddresses.getNumber());
            response.put("totalItems", pageAddresses.getTotalElements());
            response.put("totalPages", pageAddresses.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/page/{page}")
    public Iterable<Address> getPage(@PathVariable String page) {
        return addressRepository.findAll(PageRequest.of(Integer.parseInt(page), 5, Sort.by("id").ascending()));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Address> createAddress(@RequestBody Address newAddress) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(newAddress));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
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
    public ResponseEntity<Address> updateAddress(@PathVariable("id") Long id, @RequestBody Address updatedAddress) {
        Optional<Address> addressData = addressRepository.findById(id);
        if (addressData.isPresent()) {
            Address address = addressData.get();
            address.setCity(updatedAddress.getCity());
            address.setCountry(updatedAddress.getCountry());
            address.setStreet(updatedAddress.getStreet());
            return new ResponseEntity<>(addressRepository.save(address), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        try {
            addressService.deleteAddress(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Address deleted");
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity<HttpStatus> deleteAllAddresses() {
        try {
            addressRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}

