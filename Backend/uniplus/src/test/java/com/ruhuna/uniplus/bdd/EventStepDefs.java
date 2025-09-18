package com.ruhuna.uniplus.bdd;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruhuna.uniplus.models.Admin;
import com.ruhuna.uniplus.repositories.AdminRepository;
import io.cucumber.java.en.*;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class EventStepDefs extends CucumberSpringConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;
    private int lastStatus;

    @Given("an admin exists with email {string} and password {string}")
    public void an_admin_exists(String email, String password) {
        adminRepository.findByEmail(email).ifPresent(a -> adminRepository.deleteById(a.getAdminId()));
        Admin admin = new Admin();
        admin.setName("BDD Admin");
        admin.setEmail(email);
        admin.setPassword(encoder.encode(password));
        admin.setRegNo("EG0000");
        admin.setNic("NIC0000");
        admin.setContact("0710000000");
        adminRepository.save(admin);
    }

    @When("I login with email {string} and password {string}")
    public void i_login(String email, String password) throws Exception {
        String payload = objectMapper.writeValueAsString(Map.of("email", email, "password", password));
        MvcResult res = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andReturn();
        lastStatus = res.getResponse().getStatus();
        if (lastStatus == 200) {
            JsonNode node = objectMapper.readTree(res.getResponse().getContentAsString());
            jwtToken = node.get("token").asText();
        }
    }

    @Then("I should receive a JWT token")
    public void i_should_receive_a_jwt_token() {
        Assertions.assertThat(lastStatus).isEqualTo(200);
        Assertions.assertThat(jwtToken).isNotBlank();
    }

    @Given("I am logged in as {string} with password {string}")
    public void i_am_logged_in_as(String email, String password) throws Exception {
        i_login(email, password);
        Assertions.assertThat(jwtToken).isNotBlank();
    }

    @When("I create an event with:")
    public void i_create_an_event_with(io.cucumber.datatable.DataTable table) throws Exception {
        Map<String, String> row = table.asMaps().get(0);
        LocalDate eventDate = LocalDate.now().plusDays(Integer.parseInt(row.get("eventDate").split(" ")[0]));
        var body = Map.of(
                "title", row.get("title"),
                "venue", row.get("venue"),
                "eventDate", eventDate.toString(),
                "description", "BDD test event",
                "imageUrl", ""
        );
        String json = objectMapper.writeValueAsString(body);

        MvcResult res = mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(json))
                .andReturn();
        lastStatus = res.getResponse().getStatus();
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer expected) {
        Assertions.assertThat(lastStatus).isEqualTo(expected);
    }
}
