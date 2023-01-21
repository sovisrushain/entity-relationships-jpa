package com.cisco.onetoonetotalparticipation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    private String empId;
    private String empName;
    private String empAddress;
    private String spId;
    private String spName;
}
