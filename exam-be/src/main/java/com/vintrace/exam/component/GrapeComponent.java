package com.vintrace.exam.component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GrapeComponent {

    private double percentage;
    private int year;
    private String variety;
    private String region;
}
