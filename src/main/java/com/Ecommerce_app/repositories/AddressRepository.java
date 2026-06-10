package com.Ecommerce_app.repositories;

import com.Ecommerce_app.entities.Address;
import com.Ecommerce_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository
        extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);
}
