package com.nmap.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmap.modal.Port;

@Repository
public interface PortRepo extends JpaRepository<Port,Long> {

}
