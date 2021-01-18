package com.empik.jagee.domain.processors;

import com.empik.jagee.domain.queries.IQuery;
import com.empik.jagee.domain.queries.UserQuery;
import com.empik.jagee.domain.queries.results.UserQueryResult;
import com.empik.jagee.github.GitHubUserClient;
import com.empik.jagee.github.dto.GithubUserDto;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserQueryProcessorTest {

    private UserQueryProcessor processor;

    @Mock
    private GitHubUserClient gitHubUserClient;

    @BeforeEach
    private void setup()
    {
        processor = new UserQueryProcessor();

        processor.setGitHubUserClient(gitHubUserClient);
    }

    @Test
    public void canProcessShouldReturnFalseForUnknownQuery()
    {
        // Act
        val result = processor.canProcess(new UnknownQuery());

        // Assert
        assertFalse(result);
    }

    @Test
    public void canProcessShouldReturnTrueForUserQuery()
    {
        // Arrange
        val query = new UserQuery("testUser");

        // Act
        val result = processor.canProcess(query);

        // Assert
        assertTrue(result);
    }

    @Test
    public void canProcessShouldReturnFalseForNullUser()
    {
        // Arrange
        val query = new UserQuery(null);

        // Act
        val result = processor.canProcess(query);

        // Assert
        assertFalse(result);
    }

    @Test
    public void calculationsTest()
    {
        // Arrange
        val query = new UserQuery("testUser");

        val gitHubUser = new GithubUserDto();
        gitHubUser.setFollowers(10);
        gitHubUser.setPublic_repos(5);

        when(gitHubUserClient.getUserByLogin("testUser"))
                .thenReturn(gitHubUser);

        // Act
        val result = processor.process(query);

        // Assert
        assertEquals(BigDecimal.valueOf(4.2), ((UserQueryResult)result).getCalculations());
    }

    @Test
    public void calculationsDivideByZeroTest()
    {
        // Arrange
        val query = new UserQuery("testUser");

        val gitHubUser = new GithubUserDto();
        gitHubUser.setFollowers(0);
        gitHubUser.setPublic_repos(15);

        when(gitHubUserClient.getUserByLogin("testUser"))
                .thenReturn(gitHubUser);

        // Act
        val result = processor.process(query);

        // Assert
        assertNull(((UserQueryResult)result).getCalculations());
    }

    private class UnknownQuery implements IQuery {}
}