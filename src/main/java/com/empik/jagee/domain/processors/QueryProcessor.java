package com.empik.jagee.domain.processors;

import com.empik.jagee.domain.queries.results.ErrorQueryResult;
import com.empik.jagee.domain.queries.IQuery;
import com.empik.jagee.domain.queries.results.IQueryResult;
import com.empik.jagee.domain.sideeffects.ISideEffect;
import lombok.Setter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryProcessor {

    @Autowired
    @Setter
    private List<IProcessor> processors;

    @Autowired
    @Setter
    private List<ISideEffect> sideEffects;

    public IQueryResult processQuery(IQuery query) {

        val result = processSingleQuery(query);

        processSideEffects(query, result);

        return result;
    }

    private IQueryResult processSingleQuery(IQuery query) {

        val processor = processors.stream()
                .filter(p -> p.canProcess(query))
                .findFirst();

        if (processor.isPresent())
            return processor.get().process(query);

        return ErrorQueryResult.builder()
                .message("Bad query")
                .build();
    }

    private void processSideEffects(IQuery query, IQueryResult queryResult) {

        val resut = sideEffects.stream()
                .filter(s -> s.hasSideEffect(query, queryResult))
                .collect(Collectors.toList());

        sideEffects.stream()
                .filter(s -> s.hasSideEffect(query, queryResult))
                .forEach(s -> s.doSideEffect(query, queryResult));
    }
}
