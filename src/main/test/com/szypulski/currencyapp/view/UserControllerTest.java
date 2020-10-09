package com.szypulski.currencyapp.view;

import static com.szypulski.currencyapp.TestUtils.CONTENT_TYPE_JSON;
import static com.szypulski.currencyapp.TestUtils.convertToJsonBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.szypulski.currencyapp.TestBase;
import com.szypulski.currencyapp.model.dto.UserDto;
import com.szypulski.currencyapp.model.entity.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


class UserControllerTest extends TestBase {

  @Autowired
  private BCryptPasswordEncoder encoder;

  @Test
  void whenSaveLocalUserReturnsOk() throws Exception {
    UserDto userDto = createDtoUser("user@mail.pl", List.of("USER"));

    mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .accept("application/json;charset=UTF-8")
        .content(convertToJsonBytes(userDto))
        .contentType(CONTENT_TYPE_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(userDto.getEmail()));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void whenFindAllPagedWithAdminRoleReturnsOk() throws Exception {

    for (int i = 0; i < 30; i++) {
      UserDto userDto = createDtoUser(i + "user@email.pl", List.of("USER"));
      userService.save(userDto);
    }

    int pageNumber = 0;
    int pageSize = 10;

    mockMvc.perform(MockMvcRequestBuilders.get("/users")
        .param("page", String.valueOf(pageNumber))
        .param("size", String.valueOf(pageSize))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(10)))
        .andExpect(jsonPath("$.[0].email").value(0 + "user@email.pl"));
  }

  @Test
  @WithMockUser(roles = "USER")
  void whenFindAllPagedwithUserRoleReturnsForbiden() throws Exception {

    for (int i = 0; i < 30; i++) {
      UserDto userDto = createDtoUser(i + "user@email.pl", List.of("USER"));
      userService.save(userDto);
    }

    int pageNumber = 0;
    int pageSize = 10;

    mockMvc.perform(MockMvcRequestBuilders.get("/users")
        .param("page", String.valueOf(pageNumber))
        .param("size", String.valueOf(pageSize))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "USER", username = "user@email.pl")
  void whenFindDtoByIdWithCorrectUserReturnsOk() throws Exception {
    UserDto userDto = createDtoUser("user@email.pl", List.of("USER"));
    User savedUser = userService.save(userDto);

    mockMvc.perform(MockMvcRequestBuilders.get("/users/" + savedUser.getId())
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(savedUser.getId()));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void whenFindDtoByIdWithRoleAdminReturnsOk() throws Exception {
    UserDto userDto = createDtoUser("user@email.pl", List.of("USER"));
    User savedUser = userService.save(userDto);

    mockMvc.perform(MockMvcRequestBuilders.get("/users/" + savedUser.getId())
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(savedUser.getId()));
  }

  @Test
  @WithMockUser(roles = "USER", username = "other-user@email.pl")
  void whenFindDtoByIdWithOtherUserReturnsForbidden() throws Exception {
    UserDto userDto = createDtoUser("user@email.pl", List.of("USER"));
    User savedUser = userService.save(userDto);

    mockMvc.perform(MockMvcRequestBuilders.get("/users/" + savedUser.getId())
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "USER", username = "user@email.pl")
  void whenUpdateByIdWithCorrectUserReturnsOk() throws Exception {
    UserDto userDto = createDtoUser("user@email.pl", List.of("USER"));
    User savedUser = userService.save(userDto);
    userDto.setEmail("changed" + userDto.getEmail());
    userDto.setUserName("changed" + userDto.getUserName());
    userDto.setPassword(encoder.encode("changed"));

    mockMvc.perform(MockMvcRequestBuilders.put("/users/" + savedUser
        .getId()).accept("application/json;charset=UTF-8")
        .content(convertToJsonBytes(userDto))
        .contentType(CONTENT_TYPE_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(userDto.getEmail()))
        .andExpect(jsonPath("$.userName").value(userDto.getUserName()))
        .andExpect(jsonPath("$.active").value(userDto.isActive()));

    User updatedUser = userRepository.findByEmail(userDto.getEmail()).orElseThrow();
    assertThat(encoder.matches("changed", updatedUser.getPassword()));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void whenUpdateByIdWithRoleAdminReturnsOk() throws Exception {
    UserDto userDto = createDtoUser("user@email.pl", List.of("USER"));
    User savedUser = userService.save(userDto);
    userDto.setEmail("changed" + userDto.getEmail());
    userDto.setUserName("changed" + userDto.getUserName());
    userDto.setPassword(encoder.encode("changed"));

    mockMvc.perform(MockMvcRequestBuilders.put("/users/" + savedUser
        .getId()).accept("application/json;charset=UTF-8")
        .content(convertToJsonBytes(userDto))
        .contentType(CONTENT_TYPE_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(userDto.getEmail()))
        .andExpect(jsonPath("$.userName").value(userDto.getUserName()))
        .andExpect(jsonPath("$.active").value(userDto.isActive()));

    User updatedUser = userRepository.findByEmail(userDto.getEmail()).orElseThrow();
    assertThat(encoder.matches("changed", updatedUser.getPassword()));
  }

  @Test
  @WithMockUser(roles = "USER", username = "other-user@email.pl")
  void whenUpdateByIdWithOtherUserReturnsForbidden() throws Exception {
    UserDto userDto = createDtoUser("user@email.pl", List.of("USER"));
    User savedUser = userService.save(userDto);
    userDto.setEmail("changed" + userDto.getEmail());
    userDto.setUserName("changed" + userDto.getUserName());
    userDto.setPassword(encoder.encode("changed"));

    mockMvc.perform(MockMvcRequestBuilders.put("/users/" + savedUser
        .getId()).accept("application/json;charset=UTF-8")
        .content(convertToJsonBytes(userDto))
        .contentType(CONTENT_TYPE_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "USER", username = "user@email.pl")
  void whenDeleteByIdWithCorrectUserReturnsOk() throws Exception {
    UserDto userDto = createDtoUser("user@email.pl", List.of("USER"));
    User savedUser = userService.save(userDto);

    mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + savedUser
        .getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(userDto.getEmail()));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void whenDeleteByIdWithAdminReturnsOk() throws Exception {
    UserDto userDto = createDtoUser("user@email.pl", List.of("USER"));
    User savedUser = userService.save(userDto);

    mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + savedUser
        .getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(userDto.getEmail()));
  }

  @Test
  @WithMockUser(roles = "USER", username = "other-user@email.pl")
  void whenDeleteByIdWithOtherUserReturnsForbidden() throws Exception {
    UserDto userDto = createDtoUser("user@email.pl", List.of("USER"));
    User savedUser = userService.save(userDto);

    mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + savedUser
        .getId()))
        .andExpect(status().isForbidden());
  }
}