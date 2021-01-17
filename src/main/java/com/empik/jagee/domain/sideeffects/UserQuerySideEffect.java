package com.empik.jagee.domain.sideeffects;

import com.empik.jagee.db.RequestRepository;
import com.empik.jagee.db.entity.RequestEntity;
import com.empik.jagee.domain.queries.IQuery;
import com.empik.jagee.domain.queries.UserQuery;
import com.empik.jagee.domain.queries.results.IQueryResult;
import lombok.val;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserQuerySideEffect implements ISideEffect {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public boolean hasSideEffect(IQuery query, IQueryResult queryResult) {

        if (!(query instanceof UserQuery))
            return false;

        return Strings.isNotBlank(((UserQuery) query).getLogin());
    }

    @Override
    public void doSideEffect(IQuery query, IQueryResult queryResult) {

        val request = requestRepository.findByLogin(((UserQuery)query).getLogin())
                .orElse(createDefaultRequest(((UserQuery)query).getLogin()));

        request.setRequestCount(request.getRequestCount() + 1);

        requestRepository.save(request);
    }

    private RequestEntity createDefaultRequest(String login) {

        val result = new RequestEntity();

        result.setLogin(login);
        result.setRequestCount(0);

        return result;
    }
}
