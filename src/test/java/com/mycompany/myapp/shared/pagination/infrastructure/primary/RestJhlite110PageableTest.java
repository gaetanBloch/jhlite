package com.mycompany.myapp.shared.pagination.infrastructure.primary;

import static com.mycompany.myapp.BeanValidationAssertions.*;
import static org.assertj.core.api.Assertions.*;

import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.shared.pagination.domain.Jhlite110Pageable;
import org.junit.jupiter.api.Test;

@UnitTest
class RestJhlite110PageableTest {

  @Test
  void shouldConvertToDomain() {
    Jhlite110Pageable pageable = pageable().toPageable();

    assertThat(pageable.page()).isEqualTo(1);
    assertThat(pageable.pageSize()).isEqualTo(15);
  }

  @Test
  void shouldNotValidateWithPageUnderZero() {
    RestJhlite110Pageable pageable = pageable();
    pageable.setPage(-1);

    assertThatBean(pageable).hasInvalidProperty("page");
  }

  @Test
  void shouldNotValidateWithSizeAtZero() {
    RestJhlite110Pageable pageable = pageable();
    pageable.setPageSize(0);

    assertThatBean(pageable).hasInvalidProperty("pageSize").withParameter("value", 1L);
  }

  @Test
  void shouldNotValidateWithPageSizeOverHundred() {
    RestJhlite110Pageable pageable = pageable();
    pageable.setPageSize(101);

    assertThatBean(pageable).hasInvalidProperty("pageSize");
  }

  private RestJhlite110Pageable pageable() {
    return new RestJhlite110Pageable(1, 15);
  }
}
