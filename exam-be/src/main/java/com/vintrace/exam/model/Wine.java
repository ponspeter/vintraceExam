package com.vintrace.exam.model;

import com.vintrace.exam.component.GrapeComponent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Wine implements Serializable {

    private static final long serialVersionUID = -6892434771833441384L;

    private Set<GrapeComponent> components;
    private String lotCode;
    private double volume;
    private String description;
    private String tankCode;
    private String productState;
    private String ownerName;
}
