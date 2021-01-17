package com.empik.jagee.github.dto;

import lombok.Data;

@Data
public class GithubUserDto {

    private String login;               //"login": "octocat",
    private String id;                  //"id": 583231,
    private String node_id;             //"node_id": "MDQ6VXNlcjU4MzIzMQ==",
    private String avatar_url;          //"avatar_url": "https://avatars3.githubusercontent.com/u/583231?v=4",
    private String gravatar_id;         //"gravatar_id": "",
    private String url;                 //"url": "https://api.github.com/users/octocat",
    private String html_url;            //"html_url": "https://github.com/octocat",
    private String followers_url;       //"followers_url": "https://api.github.com/users/octocat/followers",
    private String following_url;       //"following_url": "https://api.github.com/users/octocat/following{/other_user}",
    private String gists_url;           //"gists_url": "https://api.github.com/users/octocat/gists{/gist_id}",
    private String starred_url;         //"starred_url": "https://api.github.com/users/octocat/starred{/owner}{/repo}",
    private String subscriptions_url;   //"subscriptions_url": "https://api.github.com/users/octocat/subscriptions",
    private String organizations_url;   //"organizations_url": "https://api.github.com/users/octocat/orgs",
    private String repos_url;           //"repos_url": "https://api.github.com/users/octocat/repos",
    private String events_url;          //"events_url": "https://api.github.com/users/octocat/events{/privacy}",
    private String received_events_url; //"received_events_url": "https://api.github.com/users/octocat/received_events",
    private String type;                //"type": "User",
    private Boolean site_admin;         //"site_admin": false,
    private String name;                //"name": "The Octocat",
    private String company;             //"company": "@github",
    private String blog;                //"blog": "https://github.blog",
    private String location;            //"location": "San Francisco",
    private String email;               //"email": null,
    private String hireable;            //"hireable": null,
    private String bio;                 //"bio": null,
    private String twitter_username;    //"twitter_username": null,
    private Integer public_repos;       //"public_repos": 8,
    private Integer public_gists;       //"public_gists": 8,
    private Integer followers;          //"followers": 3456,
    private Integer following;          //"following": 9,
    private String created_at;          //"created_at": "2011-01-25T18:44:36Z",
    private String updated_at;          //"updated_at": "2021-01-07T11:51:30Z"
}
