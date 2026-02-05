package com.meditrack.planning.dto;

import lombok.Data;

@Data
public class StaffVO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
