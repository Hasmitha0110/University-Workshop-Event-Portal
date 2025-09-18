package com.ruhuna.uniplus.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruhuna.uniplus.dto.EventCreateRequest;
import com.ruhuna.uniplus.models.Admin;
import com.ruhuna.uniplus.repositories.AdminRepository;
import com.ruhuna.uniplus.repositories.EventRepository;
import com.ruhuna.uniplus.security.JwtUtil;
import com.ruhuna.uniplus.services.EventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EventController.class)
@AutoConfigureMockMvc(addFilters = false)
class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private EventService eventService;

    @MockBean
    private JwtUtil jwtUtil;


    @Test
    @WithMockUser(username = "admin@uni.lk")
    @DisplayName("Reject invalid input: missing title and past date")
    void rejectInvalidInput_missingTitleAndPastDate() throws Exception {
        EventCreateRequest req = new EventCreateRequest();
        req.setVenue("Main Hall");
        req.setEventDate(LocalDate.now().minusDays(1)); // past date

        Mockito.when(adminRepository.findByEmail("admin@uni.lk"))
                .thenReturn(Optional.of(dummyAdmin()));

        mvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    private Admin dummyAdmin() {
        Admin a = new Admin();
        a.setAdminId(1L);
        a.setEmail("admin@uni.lk");
        a.setName("Admin");
        return a;
    }
}
