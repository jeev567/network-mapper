package com.nmap.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmap.model.Port;

@Repository
public interface PortRepo extends JpaRepository<Port,Long> {
	List<Port> findByHostname(String hostName);
	List<Port> findByHostnameAndScanIdGreaterThanEqual(String hostName,Integer threshold);

}
