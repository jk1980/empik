package com.empik.jagee.rest;

import com.empik.jagee.domain.processors.QueryProcessor;
import com.empik.jagee.domain.queries.UserQuery;
import com.empik.jagee.domain.queries.results.ErrorQueryResult;
import com.empik.jagee.domain.queries.results.UserQueryResult;
import com.empik.jagee.rest.dtos.UserDto;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private QueryProcessor queryProcessor;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{login}")
    public ResponseEntity getUserByLogin(@PathVariable("login") String login)
    {
        log.info("Query for login {}", login);

        val user = queryProcessor.processQuery(new UserQuery(login));

        if(user instanceof UserQueryResult)
            return ResponseEntity.ok(modelMapper.map(user, UserDto.class));

        if(user instanceof ErrorQueryResult)
            return ResponseEntity.badRequest().body(((ErrorQueryResult) user).getMessage());

        log.error("Can't match query result");

        return ResponseEntity.badRequest().body("Something goes wrong...");
    }
}
