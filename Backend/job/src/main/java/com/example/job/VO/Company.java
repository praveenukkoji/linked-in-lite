package com.example.job.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private Long companyId;
    private String companyName;
    private String location;
    private String email;
    
}
