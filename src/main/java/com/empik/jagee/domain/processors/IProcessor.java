package com.empik.jagee.domain.processors;

import com.empik.jagee.domain.queries.IQuery;
import com.empik.jagee.domain.queries.results.IQueryResult;

public interface IProcessor {

    boolean canProcess(IQuery query);
    IQueryResult process(IQuery query);
}
