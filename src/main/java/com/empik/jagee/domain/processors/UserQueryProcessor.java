package com.empik.jagee.domain.processors;

import com.empik.jagee.domain.queries.IQuery;
import com.empik.jagee.domain.queries.results.ErrorQueryResult;
import com.empik.jagee.domain.queries.results.IQueryResult;
import com.empik.jagee.domain.queries.UserQuery;
import com.empik.jagee.domain.queries.results.UserQueryResult;
import com.empik.jagee.github.GitHubUserClient;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class UserQueryProcessor implements IProcessor {

    @Autowired
    private GitHubUserClient gitHubUserClient;

    @Override
    public boolean canProcess(IQuery query) {

        if (!(query instanceof UserQuery))
            return false;

        return Strings.isNotBlank(((UserQuery) query).getLogin());
    }

    @Override
    public IQueryResult process(IQuery query) {

        val userQuery = (UserQuery) query;

        try {
            val gitHubUser = gitHubUserClient.getUserByLogin(userQuery.getLogin());

            return UserQueryResult.builder()
                    .id(gitHubUser.getId())
                    .login(gitHubUser.getLogin())
                    .name(gitHubUser.getName())
                    .type(gitHubUser.getType())
                    .avatarUrl(gitHubUser.getAvatar_url())
                    .createdAt(gitHubUser.getCreated_at())
                    .calculations(calculate(gitHubUser.getFollowers(), gitHubUser.getPublic_repos()))
                    .build();
        } catch (Exception ex) {

            log.warn("Exception when getting user {}, {}", userQuery.getLogin(), ex.getMessage());

            return ErrorQueryResult.builder()
                    .message("Can't retrive user")
                    .build();
        }
    }

    private BigDecimal calculate(Integer followers, Integer publicRepos)
    {
        if(followers == 0)
            return null;

        val result = 6.0 / followers * (2 + publicRepos);

        return BigDecimal.valueOf(result);
    }
}
