package com.empik.jagee.domain.sideeffects;

import com.empik.jagee.domain.queries.IQuery;
import com.empik.jagee.domain.queries.results.IQueryResult;

public interface ISideEffect {

    boolean hasSideEffect(IQuery query, IQueryResult queryResult);
    void doSideEffect(IQuery query, IQueryResult queryResult);
}
