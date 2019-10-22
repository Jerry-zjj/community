package com.zjj.community.community.controller;
import com.zjj.community.community.dto.AccessTokenDTO;
import com.zjj.community.community.dto.GithubUser;
import com.zjj.community.community.mapper.UserMapper;
import com.zjj.community.community.model.User;
import com.zjj.community.community.provider.GithubProvider;
import com.zjj.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientsecret;
    @Value("${github.redirect.uri}")
    private String redirecturi;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request, HttpServletResponse response){
        try {
            AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
            accessTokenDTO.setCode(code);
            accessTokenDTO.setClient_id(clientId);
            accessTokenDTO.setClient_secret(clientsecret);
            accessTokenDTO.setRedirect_uri(redirecturi);
            accessTokenDTO.setState(state);
            String accessToken = githubProvider.getAccessToken(accessTokenDTO);
            GithubUser githubUser = githubProvider.getUser(accessToken);
            if (githubUser!=null&&githubUser.getId()!=null) {
                User user=new User();
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setName(githubUser.getName());
                user.setAvatarUrl(githubUser.getAvatarUrl());
                userService.createOrUpdete(user);
                response.addCookie(new Cookie("token",token));
                //request.getSession().setAttribute("user", githubUser);
                return "redirect:/";
            }else{
                return "redirect:/";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie=  new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
