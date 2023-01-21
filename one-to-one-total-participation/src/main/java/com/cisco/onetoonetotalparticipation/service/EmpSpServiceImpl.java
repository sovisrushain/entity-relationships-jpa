package com.cisco.onetoonetotalparticipation.service;

import com.cisco.onetoonetotalparticipation.dto.RequestDTO;
import com.cisco.onetoonetotalparticipation.model.Employee;
import com.cisco.onetoonetotalparticipation.model.Spouse;
import com.cisco.onetoonetotalparticipation.repository.EmpSpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpSpServiceImpl implements EmpSpService {

    private final EmpSpRepository empSpRepository;
    @Override
    public void saveEmployeeData(RequestDTO requestDTO) {

        Spouse spouse = new Spouse();
        spouse.setId(requestDTO.getSpId());
        spouse.setName(spouse.getName());

        Employee employee = new Employee();
        employee.setId(requestDTO.getEmpId());
        employee.setName(requestDTO.getEmpName());
        employee.setAddress(requestDTO.getEmpAddress());
        employee.setSpouse(spouse);

        empSpRepository.save(spouse);
    }
}
