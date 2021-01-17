package com.empik.jagee.github;

import com.empik.jagee.github.dto.GithubUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "github", url = "https://api.github.com")
public interface GitHubUserClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{login}", produces = "application/json")
    GithubUserDto getUserByLogin(@PathVariable("login") String login);
}
