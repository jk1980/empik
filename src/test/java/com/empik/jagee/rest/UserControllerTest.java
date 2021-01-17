package com.empik.jagee.rest;

import com.empik.jagee.db.RequestRepository;
import com.empik.jagee.domain.processors.QueryProcessor;
import com.empik.jagee.domain.processors.UserQueryProcessor;
import com.empik.jagee.domain.sideeffects.UserQuerySideEffect;
import com.empik.jagee.github.GitHubUserClient;
import com.empik.jagee.github.dto.GithubUserDto;
import com.empik.jagee.rest.dtos.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { UserController.class, QueryProcessor.class,
        UserQueryProcessor.class, UserQuerySideEffect.class, ModelMapper.class })
@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GitHubUserClient gitHubUserClient;

    @MockBean
    private RequestRepository requestRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void happyPath_Test() throws Exception {

        // Arrange
        val gitHubUser = new GithubUserDto();

        gitHubUser.setId("583231");
        gitHubUser.setLogin("testuser");
        gitHubUser.setName("Test User");
        gitHubUser.setType("User");
        gitHubUser.setAvatar_url("https://avatars3.githubusercontent.com/u/583231?v=4");
        gitHubUser.setCreated_at("2011-01-25T18:44:36Z");
        gitHubUser.setFollowers(3456);
        gitHubUser.setPublic_repos(8);

        when(gitHubUserClient.getUserByLogin("testuser")).thenReturn(gitHubUser);

        // Act
        val result = mockMvc.perform(MockMvcRequestBuilders.get("/users/testuser")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        val contentAsString = result
                .getResponse()
                .getContentAsString();

        val response = objectMapper.readValue(contentAsString, UserDto.class);

        assertEquals("583231", response.getId());
        assertEquals("testuser", response.getLogin());
        assertEquals("Test User", response.getName());
        assertEquals("User", response.getType());
        assertEquals("https://avatars3.githubusercontent.com/u/583231?v=4", response.getAvatarUrl());
        assertEquals("2011-01-25T18:44:36Z", response.getCreatedAt());
        assertEquals("0.017361111111111112", response.getCalculations());

        // Assert - side effect
        verify(requestRepository, times(1)).save(any());
    }

    @Test
    public void whenZeroFollowers_then_nullCalculation_Test() throws Exception {

        // Arrange
        val gitHubUser = new GithubUserDto();

        gitHubUser.setId("583231");
        gitHubUser.setLogin("testuser");
        gitHubUser.setName("Test User");
        gitHubUser.setType("User");
        gitHubUser.setAvatar_url("https://avatars3.githubusercontent.com/u/583231?v=4");
        gitHubUser.setCreated_at("2011-01-25T18:44:36Z");
        gitHubUser.setFollowers(0);
        gitHubUser.setPublic_repos(8);

        when(gitHubUserClient.getUserByLogin("testuser")).thenReturn(gitHubUser);

        // Act
        val result = mockMvc.perform(MockMvcRequestBuilders.get("/users/testuser")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        val contentAsString = result
                .getResponse()
                .getContentAsString();

        val response = objectMapper.readValue(contentAsString, UserDto.class);

        assertEquals("583231", response.getId());
        assertEquals("testuser", response.getLogin());
        assertEquals("Test User", response.getName());
        assertEquals("User", response.getType());
        assertEquals("https://avatars3.githubusercontent.com/u/583231?v=4", response.getAvatarUrl());
        assertEquals("2011-01-25T18:44:36Z", response.getCreatedAt());
        assertNull(response.getCalculations());

        // Assert - side effect
        verify(requestRepository, times(1)).save(any());
    }

    @Test
    public void whenNoUser_then_badRequestAndErrorMessage_Test() throws Exception {

        // Act
        val result = mockMvc.perform(MockMvcRequestBuilders.get("/users/unknownuser")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Assert
        val contentAsString = result
                .getResponse()
                .getContentAsString();

        assertEquals("Can't retrive user",contentAsString);

        // Assert - side effect
        verify(requestRepository, times(1)).save(any());
    }
}