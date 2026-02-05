package com.meditrack.planning.dto;

import com.meditrack.planning.model.ResourceType;
import lombok.Data;

@Data
public class ResourceDTO {
    private Long id;
    private String name;
    private ResourceType type;
    private boolean available;
}
