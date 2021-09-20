package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.organisatie.OrganisatieRepository;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieServiceImpl;
import com.eindwerk.SnelGeboekt.user.UserRepository;
import com.eindwerk.SnelGeboekt.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock LoginController loginController;
    @Mock OrganisatieRepository organisatieRepository;
    @Mock UserRepository userRepository;
    @InjectMocks OrganisatieServiceImpl organisatieService;
    @InjectMocks UserServiceImpl userService;
    private MockMvc mockMvc;


    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    /**
     * Test login
     * @throws Exception
     */
    @Test
    void shouldRedirectToSettingsPage() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder formLogin = formLogin()
                .user("john.doe@hotmail.com")
                .password("test123");

        mockMvc.perform(formLogin)
                .andExpect(authenticated().withUsername("john.doe@hotmail.com"))
                .andExpect(redirectedUrl("/instellingen"));
    }
}