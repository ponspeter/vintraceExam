package com.vintrace.exam.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vintrace.exam.model.Wine;
import com.vintrace.exam.predicate.WinePredicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WineService implements DefaultWineService {

    @Override
    public List<Wine> loadAllWine() throws IOException {
        List<Wine> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        Resource[] resources = resolver.getResources("classpath*:data/*.json") ;
        for (Resource resource: resources){
            list.add(objectMapper.readValue( resource.getInputStream(), Wine.class));
        }
        return list;
    }

    @Override
    public Wine searchWine(String lotCode, String desc, String owner, String productState, String tankCode) throws IOException {
        try {
            return loadAllWine()
                    .stream()
                    .filter(WinePredicate.byLotCode(lotCode)
                            .or(WinePredicate.byOwner(owner))
                            .or(WinePredicate.byTankCode(tankCode))
                    )
                    .collect(Collectors.toList())
                    .get(0);
        } catch (Exception e) {
            return Wine.builder().build();
        }
    }


}
