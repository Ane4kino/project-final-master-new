package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.BaseHandler;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.profile.ProfileTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.login.internal.web.UserTestData.USER_MAIL;
import static com.javarush.jira.login.internal.web.UserTestData.user;
import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static com.javarush.jira.project.internal.web.ProjectTestData.PARENT_PROJECT_ID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProfileRestControllerTest extends AbstractControllerTest {
    @Test
    @WithUserDetails(value = "user@mail.com")
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/profile"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mailNotifications").isArray())
                .andExpect(jsonPath("$.contacts").isArray());
    }



}



