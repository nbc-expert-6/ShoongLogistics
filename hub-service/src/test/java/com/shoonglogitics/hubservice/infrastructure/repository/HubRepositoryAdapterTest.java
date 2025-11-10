package com.shoonglogitics.hubservice.infrastructure.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.hubservice.domain.entity.Hub;
import com.shoonglogitics.hubservice.domain.vo.HubType;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class HubRepositoryAdapterTest {

	@Autowired
	private HubRepositoryAdapter hubRepositoryAdapter;

	@Autowired
	private JpaHubRepository jpaHubRepository;

	@AfterEach
	void tearDown() {
		jpaHubRepository.deleteAll();
	}

	@Test
	@DisplayName("허브를 저장할 수 있다")
	void save() {
		// given
		Hub hub = Hub.create(
			"서울특별시 센터",
			"서울특별시 송파구 송파대로 55",
			37.5665,
			126.9780,
			HubType.NORMAL
		);

		// when
		Hub savedHub = hubRepositoryAdapter.save(hub);

		// then
		assertThat(savedHub).isNotNull();
		assertThat(savedHub.getId()).isNotNull();
		assertThat(savedHub.getName()).isEqualTo("서울특별시 센터");
		assertThat(savedHub.getAddress().getValue()).isEqualTo("서울특별시 송파구 송파대로 55");
		assertThat(savedHub.getLatitude()).isEqualTo(37.5665);
		assertThat(savedHub.getLongitude()).isEqualTo(126.9780);
		assertThat(savedHub.getHubType()).isEqualTo(HubType.NORMAL);
	}

	@Test
	@DisplayName("허브를 ID로 조회할 수 있다")
	void findById() {
		// given
		Hub hub = Hub.create(
			"경기남부 센터",
			"경기도 수원시 영통구 광교로 156",
			37.2830,
			127.0446,
			HubType.CENTRAL
		);
		Hub savedHub = hubRepositoryAdapter.save(hub);

		// when
		Optional<Hub> foundHub = hubRepositoryAdapter.findById(savedHub.getId());

		// then
		assertThat(foundHub).isPresent();
		assertThat(foundHub.get().getId()).isEqualTo(savedHub.getId());
		assertThat(foundHub.get().getName()).isEqualTo("경기남부 센터");
		assertThat(foundHub.get().isCentralHub()).isTrue();
	}

	@Test
	@DisplayName("모든 허브를 조회할 수 있다")
	void findAll() {
		// given
		Hub hub1 = Hub.create(
			"서울 센터",
			"서울특별시 강남구 테헤란로 152",
			37.5048,
			127.0489,
			HubType.NORMAL
		);
		Hub hub2 = Hub.create(
			"부산 센터",
			"부산광역시 해운대구 센텀중앙로 79",
			35.1681,
			129.1313,
			HubType.NORMAL
		);
		hubRepositoryAdapter.save(hub1);
		hubRepositoryAdapter.save(hub2);

		// when
		List<Hub> hubs = hubRepositoryAdapter.findAll();

		// then
		assertThat(hubs).hasSize(2);
		assertThat(hubs)
			.extracting("name")
			.containsExactlyInAnyOrder("서울 센터", "부산 센터");
	}

	@Test
	@DisplayName("허브를 소프트 삭제할 수 있다")
	void softDelete() {
		// given
		Hub hub = Hub.create(
			"인천 센터",
			"인천광역시 연수구 컨벤시아대로 69",
			37.3847,
			126.6583,
			HubType.NORMAL
		);
		Hub savedHub = hubRepositoryAdapter.save(hub);
		Long userId = 1L;

		// when
		savedHub.deactivate(userId);
		Hub deletedHub = hubRepositoryAdapter.save(savedHub);

		// then
		assertThat(deletedHub.isDeleted()).isTrue();
		assertThat(deletedHub.getDeletedAt()).isNotNull();
		assertThat(deletedHub.getDeletedBy()).isEqualTo(userId);
	}

}
