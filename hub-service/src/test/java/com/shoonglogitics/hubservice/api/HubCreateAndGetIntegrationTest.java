package com.shoonglogitics.hubservice.api;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@Disabled
@Transactional
@ActiveProfiles("test")
@SpringBootTest(properties = {
        "spring.cache.type=simple"})
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class HubCreateAndGetIntegrationTest {

    private static final String BASE_PATH = "/api/v1/hubs";

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    private String createPayload() throws Exception {
        record Req(String name, String address, double latitude, double longitude, String hubType) {}
        return om.writeValueAsString(new Req(
                "광주 허브",
                "광주광역시 북구 용봉로 77",
                35.1751,
                126.9057,
                "NORMAL"
        ));
    }

    private UUID extractHubId(String apiResponseJson) throws Exception {
        JsonNode root = om.readTree(apiResponseJson);
        JsonNode data = root.path("data");
        String id = data.hasNonNull("hubId") ? data.path("hubId").asText() : data.path("id").asText();
        assertThat(id)
                .withFailMessage("응답에서 hub 식별자(data.hubId 또는 data.id)를 찾을 수 없습니다. 응답=%s", apiResponseJson)
                .isNotBlank();
        return UUID.fromString(id);
    }

    @Test
    @DisplayName("허브 생성 후, 반환된 ID로 단건 조회가 성공한다")
    void create_then_get_by_id() throws Exception {
        // 1) 생성
        String createRes = mvc.perform(
                        post(BASE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createPayload()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("광주 허브"))
                .andExpect(jsonPath("$.data.address").value("광주광역시 북구 용봉로 77"))
                .andExpect(jsonPath("$.data.latitude").value(35.1751))
                .andExpect(jsonPath("$.data.longitude").value(126.9057))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID hubId = extractHubId(createRes);

        String getRes = mvc.perform(get(BASE_PATH + "/{hubId}", hubId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(result -> {
                    String body = result.getResponse().getContentAsString();
                    JsonNode data = om.readTree(body).path("data");
                    String found = data.hasNonNull("hubId") ? data.path("hubId").asText() : data.path("id").asText();
                    assertThat(found).isEqualTo(hubId.toString());
                })
                .andExpect(jsonPath("$.data.name").value("광주 허브"))
                .andExpect(jsonPath("$.data.address").value("광주광역시 북구 용봉로 77"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode data = om.readTree(getRes).path("data");
        if (data.hasNonNull("latitude")) {
            assertThat(data.path("latitude").asDouble()).isEqualTo(35.1751);
        }
        if (data.hasNonNull("longitude")) {
            assertThat(data.path("longitude").asDouble()).isEqualTo(126.9057);
        }
        if (data.hasNonNull("hubType")) {
            assertThat(data.path("hubType").asText()).isEqualTo("NORMAL");
        }
    }
}
