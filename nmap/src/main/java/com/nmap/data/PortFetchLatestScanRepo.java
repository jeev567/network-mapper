package com.nmap.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmap.model.PortFetchLatestScan;

@Repository
public interface PortFetchLatestScanRepo extends JpaRepository<PortFetchLatestScan, String> {
	List<PortFetchLatestScan> findByHostname(String hostName);

}
