package com.shoonglogitics.hubservice.presentation;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;

import com.shoonglogitics.hubservice.application.dto.HubResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoonglogitics.hubservice.application.HubService;
import com.shoonglogitics.hubservice.application.dto.HubSummary;
import com.shoonglogitics.hubservice.domain.entity.Hub;
import com.shoonglogitics.hubservice.domain.vo.HubType;
import com.shoonglogitics.hubservice.presentation.dto.request.CreateHubRequest;


@WebMvcTest(HubController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HubControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private HubService hubService;

    @Test
    @DisplayName("POST /api/v1/hubs - 허브 생성 성공")
    void createHub_Success() throws Exception {
        // given
        CreateHubRequest request = new CreateHubRequest(
                "서울특별시 센터",
                "서울특별시 송파구 송파대로 55",
                37.5665,
                126.9780,
                HubType.NORMAL
        );

        UUID hubId = UUID.randomUUID();
        Hub hub = Hub.create("서울특별시 센터", "서울특별시 송파구 송파대로 55", 37.5665, 126.9780, HubType.NORMAL);


        given(hubService.createHub(any())).willReturn(hub);

        // when & then
        mockMvc.perform(post("/api/v1/hubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("서울특별시 센터"))
                .andExpect(jsonPath("$.data.address").value("서울특별시 송파구 송파대로 55"))
                .andExpect(jsonPath("$.data.latitude").value(37.5665))
                .andExpect(jsonPath("$.data.longitude").value(126.9780));

        // verify
        then(hubService).should(times(1)).createHub(any());
    }

    @Test
    @DisplayName("GET /api/v1/hubs/{hubId} - 허브 단건 조회 성공")
    void getHub_Success() throws Exception {
        // given
        UUID hubId = UUID.randomUUID();
        Hub hub = Hub.create(
                "서울특별시 센터",
                "서울특별시 송파구 송파대로 55",
                37.5665,
                126.9780,
                HubType.NORMAL
        );

        given(hubService.getHub(hubId)).willReturn(HubResult.from(hub));

        // when & then
        mockMvc.perform(get("/api/v1/hubs/{hubId}", hubId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("서울특별시 센터"))
                .andExpect(jsonPath("$.data.address").value("서울특별시 송파구 송파대로 55"))
                .andExpect(jsonPath("$.data.latitude").value(37.5665))
                .andExpect(jsonPath("$.data.longitude").value(126.9780))
                .andExpect(jsonPath("$.data.hubType").value("NORMAL"));

        // verify
        then(hubService).should(times(1)).getHub(hubId);
    }

    @Test
    @DisplayName("GET /api/v1/hubs - 허브 전체 조회 성공")
    void getHubs_Success() throws Exception {
        // given
        List<HubSummary> hubs = List.of(
                new HubSummary("서울특별시 센터", "서울특별시 송파구 송파대로 55", "NORMAL"),
                new HubSummary("경기 남부 센터", "경기 남부 센터 주소 000", "NORMAL"),
                new HubSummary("대전광역시 센터", "대전광역시 센터 주소 123123", "NORMAL")
        );

        given(hubService.getAllHubs()).willReturn(hubs);

        // when & then
        mockMvc.perform(get("/api/v1/hubs"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.hubs").isArray())
                .andExpect(jsonPath("$.data.hubs.length()").value(3))
                .andExpect(jsonPath("$.data.hubs[0].name").value("서울특별시 센터"))
                .andExpect(jsonPath("$.data.hubs[1].name").value("경기 남부 센터"))
                .andExpect(jsonPath("$.data.hubs[2].name").value("대전광역시 센터"));

        // verify
        then(hubService).should(times(1)).getAllHubs();
    }
}
