package com.example.springbootgen.service;

import com.example.springbootgen.model.GenDatabase;

import java.util.List;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/25 16:59
 */
public interface IGenDatabaseService {

    Long insertGenDatabase(GenDatabase genDatabase);

    Long updateGenDatabase(GenDatabase genDatabase);

    List<GenDatabase> selectGenDatabase(GenDatabase genDatabase);

    GenDatabase selectGenDatabaseById(Long id);
}
