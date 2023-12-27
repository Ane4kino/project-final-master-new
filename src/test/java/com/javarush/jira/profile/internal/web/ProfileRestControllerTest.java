package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.BaseHandler;
import com.javarush.jira.profile.ProfileTo;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.profile.internal.web.ProfileTestData.getInvalidTo;
import static com.javarush.jira.profile.internal.web.ProfileTestData.getUpdatedTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProfileRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL_PROFILE = BaseHandler.REST_URL + "/profile";

    @Test
    @WithUserDetails(value = "user@gmail.com")
    void get() throws Exception {
//        AuthUser authUser = new AuthUser(user);

        perform(MockMvcRequestBuilders.get(REST_URL_PROFILE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mailNotifications").isArray())
                .andExpect(jsonPath("$.contacts").isArray());
    }
    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_PROFILE))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = "user@gmail.com")
    void update() throws Exception {
        ProfileTo updatedTo = getUpdatedTo();

        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatedTo)))
                .andExpect(status().isNoContent());
    }
    @Test
    @WithUserDetails(value = "user@gmail.com")
    void updateWithInvalidData() throws Exception {
        ProfileTo invalidTo = getInvalidTo();

        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalidTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateWithoutAuthentication() throws Exception {
        ProfileTo updatedTo = getUpdatedTo();

        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatedTo)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = "user@gmail.com")
    void updateInvalid() throws Exception {
        ProfileTo invalidTo = getInvalidTo();

        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalidTo)))
                .andExpect(status().isUnprocessableEntity());
    }

}



