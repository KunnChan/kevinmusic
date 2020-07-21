package com.music.kevinmusic.controller;
/*
 * Created by kunnchan on 18/07/2020
 * package :  com.music.kevinmusic.controller
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class BaseIT {

    @Autowired
    WebApplicationContext wac;

    protected MockMvc mockMvc;

    private static final String CLIENT_ID = "kcrock";
    private static final String CLIENT_SECRET = "securekc";
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    protected String getAccessToken(String username, String password) throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", CLIENT_ID);
        params.add("username", username);
        params.add("password", password);

        ResultActions result = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(CLIENT_ID, CLIENT_SECRET))
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    protected static String getJsonString(Object object){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static Stream<Arguments> getStreamAdminCustomer() {
        return Stream.of(Arguments.of("admin" , "admin"),
                Arguments.of("customer", "customer"));
    }

    public static Stream<Arguments> getStreamAllUsers() {
        return Stream.of(Arguments.of("admin" , "admin"),
                Arguments.of("customer", "customer"),
                Arguments.of("user", "user"));
    }

    public static Stream<Arguments> getStreamNotAdmin() {
        return Stream.of(Arguments.of("customer", "customer"),
                Arguments.of("user", "user"));
    }
}
