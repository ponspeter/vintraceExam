package com.vintrace.exam.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Data
@Component
@Configuration("log")
public class LogProperties {

    private String afterRequestPrefix;
    private String beforeRequestPrefix;
    private String afterResponsePrefix;
    private String logHeader;
}
