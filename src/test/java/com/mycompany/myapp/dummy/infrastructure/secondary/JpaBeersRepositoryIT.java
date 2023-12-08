package com.mycompany.myapp.dummy.infrastructure.secondary;

import static com.mycompany.myapp.dummy.domain.BeersIdentityFixture.*;
import static com.mycompany.myapp.dummy.domain.beer.BeersFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.mycompany.myapp.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@IntegrationTest
class JpaBeersRepositoryIT {

  @Autowired
  private JpaBeersRepository beers;

  @Test
  void shouldSaveAndGetBeer() {
    beers.saveAndFlush(BeerEntity.from(beer()));

    assertThat(beers.findById(cloackOfFeathersId().get()).get().toDomain()).usingRecursiveComparison().isEqualTo(beer());
  }
}
