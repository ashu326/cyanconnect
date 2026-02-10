package com.cyanconnode.connect.repository;

import com.cyanconnode.connect.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long>
{
}
