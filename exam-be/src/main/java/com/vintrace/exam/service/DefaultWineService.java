package com.vintrace.exam.service;

import com.vintrace.exam.model.Wine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface DefaultWineService {

    public List<Wine> loadAllWine() throws IOException;
    public Wine searchWine(String lotCode, String desc,
                           String owner, String productState, String tankCode)
            throws IOException;
}
