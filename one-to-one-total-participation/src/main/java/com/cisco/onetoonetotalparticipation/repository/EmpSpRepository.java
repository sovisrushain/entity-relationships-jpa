package com.cisco.onetoonetotalparticipation.repository;

import com.cisco.onetoonetotalparticipation.model.Spouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpSpRepository extends JpaRepository<Spouse, String> {
}
