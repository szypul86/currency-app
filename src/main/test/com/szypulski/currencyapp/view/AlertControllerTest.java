package com.szypulski.currencyapp.view;

import static com.szypulski.currencyapp.TestUtils.CONTENT_TYPE_JSON;
import static com.szypulski.currencyapp.TestUtils.convertToJsonBytes;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.szypulski.currencyapp.TestBase;
import com.szypulski.currencyapp.model.dto.AlertDto;
import com.szypulski.currencyapp.model.entity.User;
import com.szypulski.currencyapp.model.enums.AlertType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class AlertControllerTest extends TestBase {

  @Test
  @WithMockUser(roles = "USER")
  void whenSaveReturnsOk() throws Exception {
    saveDefaultMoneys();
    User user = userService.save(createDtoUser("ziom@test.com", List.of("USER", "ADMIN")));

    AlertDto alertToBeSaved = AlertDto.builder()
        .alertType(AlertType.OVER)
        .alertValue(1.123)
        .userId(user.getId())
        .to("USD")
        .build();

    mockMvc.perform(MockMvcRequestBuilders.post("/alerts")
        .accept("application/json;charset=UTF-8")
        .content(convertToJsonBytes(alertToBeSaved))
        .contentType(CONTENT_TYPE_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.alertValue").value(1.123));
  }

  @Test
  @WithMockUser(roles = "USER")
  void whenSaveWithNotExistingMoneyReturnsNotFound() throws Exception {
    saveDefaultMoneys();
    User user = userService.save(createDtoUser("ziom@test.com", List.of("USER", "ADMIN")));

    AlertDto alertToBeSaved = AlertDto.builder()
        .alertType(AlertType.OVER)
        .alertValue(1.123)
        .userId(user.getId())
        .to("HUN")
        .build();

    mockMvc.perform(MockMvcRequestBuilders.post("/alerts")
        .accept("application/json;charset=UTF-8")
        .content(convertToJsonBytes(alertToBeSaved))
        .contentType(CONTENT_TYPE_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(roles = "USER")
  void whenSaveWithNotExistingUserReturnsNotFound() throws Exception {
    saveDefaultMoneys();
    User user = userService.save(createDtoUser("ziom@test.com", List.of("USER", "ADMIN")));

    AlertDto alertToBeSaved = AlertDto.builder()
        .alertType(AlertType.OVER)
        .alertValue(1.123)
        .userId(2L)
        .to("USD")
        .build();

    mockMvc.perform(MockMvcRequestBuilders.post("/alerts")
        .accept("application/json;charset=UTF-8")
        .content(convertToJsonBytes(alertToBeSaved))
        .contentType(CONTENT_TYPE_JSON))
        .andExpect(status().isNotFound());
  }


  @Test
  @WithMockUser(roles = "USER", username = "ziom@test.com")
  void whenFindByIdWithCorrectUserReturnsOk() throws Exception {
    saveDefaultMoneys();
    User user = userService.save(createDtoUser("ziom@test.com", List.of("USER", "ADMIN")));
    AlertDto alertToBeSaved = AlertDto.builder()
        .alertType(AlertType.OVER)
        .alertValue(1.123)
        .userId(user.getId())
        .to("USD")
        .build();
    AlertDto alertDto = currencyAlertService.save(alertToBeSaved);

    mockMvc.perform(MockMvcRequestBuilders.get("/alerts/" + alertDto.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.alertValue").value(1.123));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void whenFindByIdWithAdminReturnsOk() throws Exception {
    saveDefaultMoneys();
    User user = userService.save(createDtoUser("ziom@test.com", List.of("USER")));
    AlertDto alertToBeSaved = AlertDto.builder()
        .alertType(AlertType.OVER)
        .alertValue(1.123)
        .userId(user.getId())
        .to("USD")
        .build();
    AlertDto alertDto = currencyAlertService.save(alertToBeSaved);

    mockMvc.perform(MockMvcRequestBuilders.get("/alerts/" + alertDto.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.alertValue").value(1.123));
  }

  @Test
  @WithMockUser(roles = "USER", username = "poziom@test.com")
  void whenFindByIdWithWrongUserReturnsForbidden() throws Exception {
    saveDefaultMoneys();
    User user = userService.save(createDtoUser("ziom@test.com", List.of("USER", "ADMIN")));
    AlertDto alertToBeSaved = AlertDto.builder()
        .alertType(AlertType.OVER)
        .alertValue(1.123)
        .userId(user.getId())
        .to("USD")
        .build();
    AlertDto alertDto = currencyAlertService.save(alertToBeSaved);

    mockMvc.perform(MockMvcRequestBuilders.get("/alerts/" + alertDto.getId()))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "USER", username = "ziom@test.com")
  void whenUpdateByIdWithCorrectUserReturnsOk() throws Exception {
    saveDefaultMoneys();
    User user = userService.save(createDtoUser("ziom@test.com", List.of("USER", "ADMIN")));
    AlertDto alertToBeSaved = AlertDto.builder()
        .alertType(AlertType.OVER)
        .alertValue(1.123)
        .userId(user.getId())
        .to("USD")
        .build();

    AlertDto alertDto = currencyAlertService.save(alertToBeSaved);

    alertDto.setAlertType(AlertType.UNDER);
    alertDto.setTo("PLN");
    alertDto.setAlertValue(2.0);

    mockMvc.perform(MockMvcRequestBuilders.put("/alerts/" + alertDto.getId())
        .accept("application/json;charset=UTF-8")
        .content(convertToJsonBytes(alertDto))
        .contentType(CONTENT_TYPE_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.alertValue").value(2.0))
        .andExpect(jsonPath("$.to").value("PLN"))
        .andExpect(jsonPath("$.alertType").value("UNDER"))
    ;
  }

  @Test
  @WithMockUser(roles = "USER", username = "ziom@test.com")
  void whenUpdateByIdWithWrongUserReturnsForbidden() throws Exception {
    saveDefaultMoneys();
    User user = userService.save(createDtoUser("poziom@test.com", List.of("USER", "ADMIN")));
    AlertDto alertToBeSaved = AlertDto.builder()
        .alertType(AlertType.OVER)
        .alertValue(1.123)
        .userId(user.getId())
        .to("USD")
        .build();

    AlertDto alertDto = currencyAlertService.save(alertToBeSaved);
    alertDto.setAlertType(AlertType.UNDER);
    alertDto.setTo("PLN");
    alertDto.setAlertValue(2.0);

    mockMvc.perform(MockMvcRequestBuilders.put("/alerts/" + alertDto.getId())
        .accept("application/json;charset=UTF-8")
        .content(convertToJsonBytes(alertDto))
        .contentType(CONTENT_TYPE_JSON))
        .andExpect(status().isForbidden());
    ;
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void whenUpdateByIdWithAdminReturnsOk() throws Exception {
    saveDefaultMoneys();
    User user = userService.save(createDtoUser("ziom@test.com", List.of("USER", "ADMIN")));
    AlertDto alertToBeSaved = AlertDto.builder()
        .alertType(AlertType.OVER)
        .alertValue(1.123)
        .userId(user.getId())
        .to("USD")
        .build();

    AlertDto alertDto = currencyAlertService.save(alertToBeSaved);

    alertDto.setAlertType(AlertType.UNDER);
    alertDto.setTo("PLN");
    alertDto.setAlertValue(2.0);

    mockMvc.perform(MockMvcRequestBuilders.put("/alerts/" + alertDto.getId())
        .accept("application/json;charset=UTF-8")
        .content(convertToJsonBytes(alertDto))
        .contentType(CONTENT_TYPE_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.alertValue").value(2.0))
        .andExpect(jsonPath("$.to").value("PLN"))
        .andExpect(jsonPath("$.alertType").value("UNDER"));
  }



  @Test
  @WithMockUser(roles = "USER", username = "ziom@test.com")
  void whenUpdateByIdWithCorrectUserButNotExistingCurrencyReturnsNotFound() throws Exception {
    saveDefaultMoneys();
    User user = userService.save(createDtoUser("ziom@test.com", List.of("USER", "ADMIN")));
    AlertDto alertToBeSaved = AlertDto.builder()
        .alertType(AlertType.OVER)
        .alertValue(1.123)
        .userId(user.getId())
        .to("USD")
        .build();

    AlertDto alertDto = currencyAlertService.save(alertToBeSaved);

    alertDto.setAlertType(AlertType.UNDER);
    alertDto.setTo("HUN");
    alertDto.setAlertValue(2.0);

    mockMvc.perform(MockMvcRequestBuilders.put("/alerts/" + alertDto.getId())
        .accept("application/json;charset=UTF-8")
        .content(convertToJsonBytes(alertDto))
        .contentType(CONTENT_TYPE_JSON))
        .andExpect(status().isNotFound())
    ;
  }

  @Test
  @WithMockUser(roles = "USER", username = "ziom@test.com")
  void whenUpdateByIdByChangingUserReturnsForbidden() throws Exception {
    saveDefaultMoneys();
    User user = userService.save(createDtoUser("ziom@test.com", List.of("USER", "ADMIN")));
    AlertDto alertToBeSaved = AlertDto.builder()
        .alertType(AlertType.OVER)
        .alertValue(1.123)
        .userId(user.getId())
        .to("USD")
        .build();

    AlertDto alertDto = currencyAlertService.save(alertToBeSaved);
    User user2 = userService.save(createDtoUser("poziom@test.com", List.of("USER", "ADMIN")));
    alertDto.setUserId(user2.getId());
    alertDto.setAlertType(AlertType.UNDER);
    alertDto.setTo("PLN");
    alertDto.setAlertValue(2.0);

    mockMvc.perform(MockMvcRequestBuilders.put("/alerts/" + alertDto.getId())
        .accept("application/json;charset=UTF-8")
        .content(convertToJsonBytes(alertDto))
        .contentType(CONTENT_TYPE_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "USER", username = "ziom@test.com")
  void whenDeleteByIdWithCorrectUserReturnsOk() throws Exception {
    saveDefaultMoneys();
    User user = userService.save(createDtoUser("ziom@test.com", List.of("USER", "ADMIN")));
    AlertDto alertToBeSaved = AlertDto.builder()
        .alertType(AlertType.OVER)
        .alertValue(1.123)
        .userId(user.getId())
        .to("USD")
        .build();
    AlertDto alertDto = currencyAlertService.save(alertToBeSaved);

    mockMvc.perform(MockMvcRequestBuilders.delete("/alerts/" + alertDto.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.alertValue").value(1.123));
  }

  @Test
  @WithMockUser(roles = "ADMIN", username = "ziom@test.com")
  void whenDeleteByIdWithAdminReturnsOk() throws Exception {
    saveDefaultMoneys();
    User user = userService.save(createDtoUser("ziom@test.com", List.of("USER", "ADMIN")));
    AlertDto alertToBeSaved = AlertDto.builder()
        .alertType(AlertType.OVER)
        .alertValue(1.123)
        .userId(user.getId())
        .to("USD")
        .build();
    AlertDto alertDto = currencyAlertService.save(alertToBeSaved);

    mockMvc.perform(MockMvcRequestBuilders.delete("/alerts/" + alertDto.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.alertValue").value(1.123));
  }

  @Test
  @WithMockUser(roles = "USER", username = "ziom@test.com")
  void whenDeleteByIdWithWrongUserReturnsOk() throws Exception {
    saveDefaultMoneys();
    User user = userService.save(createDtoUser("poziom@test.com", List.of("USER", "ADMIN")));
    AlertDto alertToBeSaved = AlertDto.builder()
        .alertType(AlertType.OVER)
        .alertValue(1.123)
        .userId(user.getId())
        .to("USD")
        .build();
    AlertDto alertDto = currencyAlertService.save(alertToBeSaved);

    mockMvc.perform(MockMvcRequestBuilders.delete("/alerts/" + alertDto.getId()))
        .andExpect(status().isForbidden());
  }







}