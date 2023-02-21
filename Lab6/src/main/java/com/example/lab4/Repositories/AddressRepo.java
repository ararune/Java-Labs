package com.example.lab4.Repositories;

import com.example.lab4.Entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
    Page<Address> findByCountryContaining(String country, String city, Pageable pageable);
    List<Address> findByCountryContaining(String country, Sort sort);
}
