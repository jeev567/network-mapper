package com.nmap.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmap.modal.PortFetchThreshold;

@Repository
public interface PortThresholdRepo extends JpaRepository<PortFetchThreshold,String> {
	List<PortFetchThreshold> findByHostname(String hostName);

}
