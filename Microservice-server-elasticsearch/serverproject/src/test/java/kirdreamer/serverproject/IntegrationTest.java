package kirdreamer.serverproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import kirdreamer.serverproject.model.PostRequestModel;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationTest {
    private PostRequestModel postRequestModel;

    @Value("${userPath}")
    private String userPath;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @Order(1)
    @Description("Checking the lack of any data on server")
    void checkLackDataOnServer() throws Exception {
        this.mockMvc.perform(get(userPath))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @Order(2)
    @Description("Loading new user on the server")
    void loadNewUser() throws Exception {
        postRequestModel = new PostRequestModel();
        postRequestModel.setFirstName("Alex").setLastName("Brown").setEmail("ab@gmail.com").build();

        postRequest(mapper.writeValueAsString(postRequestModel));
    }

    @Test
    @Order(3)
    @Description("Checking the new user on the server")
    void checkNewUser() throws Exception {
        this.mockMvc.perform(get(userPath + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"firstName\":\"Alex\",\"lastName\":\"Brown\",\"email\":\"ab@gmail.com\",\"id\":\"1\"}"));

        this.mockMvc.perform(get(userPath))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"firstName\":\"Alex\",\"lastName\":\"Brown\",\"email\":\"ab@gmail.com\",\"id\":\"1\"}]"));
    }

    @Test
    @Order(4)
    @Description("Loading false users")
    void loadFalseUsers() throws Exception {
        postRequestModel = new PostRequestModel();
        postRequestModel.setFirstName("Alex").setLastName("Brown").setEmail("ab@gmail.").build();

        this.mockMvc.perform(post(userPath).content(mapper.writeValueAsString(postRequestModel))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

        postRequestModel = new PostRequestModel();
        postRequestModel.setFirstName("Alex").setLastName("").setEmail("ab@gmail.com").build();
        String json = mapper.writeValueAsString(postRequestModel);
        this.mockMvc.perform(post(userPath).content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Order(5)
    @Description("Checking the lack of false users")
    void checkLackFalseUsers() throws Exception {
        this.mockMvc.perform(get(userPath + "/2"))
                .andDo(print())
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get(userPath))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"firstName\":\"Alex\",\"lastName\":\"Brown\",\"email\":\"ab@gmail.com\",\"id\":\"1\"}]"));
    }

    @Test
    @Order(6)
    @Description("Deleting user from the middle of the data by id")
    void deleteUserFromMiddle() throws Exception {
        postRequestModel = new PostRequestModel();
        postRequestModel.setFirstName("Alex").setLastName("Brown").setEmail("ab@gmail.com").build();

        postRequest(mapper.writeValueAsString(postRequestModel));
        postRequest(mapper.writeValueAsString(postRequestModel));
        postRequest(mapper.writeValueAsString(postRequestModel));

        this.mockMvc.perform(get(userPath + "/2"))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get(userPath + "/4"))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(delete(userPath + "/2"));

        this.mockMvc.perform(get(userPath + "/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    @Description("Loading new user on the server in the middle of the list to feel all id")
    void loadNewUserInMiddle() throws Exception {
        postRequestModel = new PostRequestModel();
        postRequestModel.setFirstName("Alex").setLastName("Brown").setEmail("ab@gmail.com").build();

        postRequest(mapper.writeValueAsString(postRequestModel));

        this.mockMvc.perform(get(userPath + "/2"))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get(userPath + "/5"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    @Description("Updating the old User")
    void patchOldUser() throws Exception {
        postRequestModel = new PostRequestModel();
        postRequestModel.setFirstName("Alex").setLastName("White").setEmail("aw@gmail.com").build();

        this.mockMvc.perform(patch(userPath + "/1").content(mapper.writeValueAsString(postRequestModel))
                .contentType(MediaType.APPLICATION_JSON)
        );

        this.mockMvc.perform(get(userPath + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"firstName\":\"Alex\",\"lastName\":\"White\",\"email\":\"aw@gmail.com\",\"id\":\"1\"}"));
    }

    private void postRequest(String json) throws Exception {
        this.mockMvc.perform(post(userPath).content(json)
                .contentType(MediaType.APPLICATION_JSON)
        );
    }
}