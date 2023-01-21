package com.cisco.onetoonetotalparticipation.service;

import com.cisco.onetoonetotalparticipation.dto.RequestDTO;
import com.cisco.onetoonetotalparticipation.model.Employee;
import com.cisco.onetoonetotalparticipation.model.Spouse;
import com.cisco.onetoonetotalparticipation.repository.EmpSpRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpSpServiceImpl implements EmpSpService {

    private final EmpSpRepository empSpRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;


    @Override
    public void saveEmployeeData(RequestDTO requestDTO) {

        Employee employee = new Employee();
        employee.setId(requestDTO.getEmpId());
        employee.setName(requestDTO.getEmpName());
        employee.setAddress(requestDTO.getEmpAddress());

        Spouse spouse = new Spouse();
        spouse.setId(requestDTO.getSpId());
        spouse.setName(spouse.getName());
        spouse.setEmployee(employee);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.persist(employee);
        entityManager.getTransaction().commit();
    }
}
