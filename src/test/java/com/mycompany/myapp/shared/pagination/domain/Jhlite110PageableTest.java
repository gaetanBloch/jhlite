package com.mycompany.myapp.shared.pagination.domain;

import static org.assertj.core.api.Assertions.*;

import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.shared.error.domain.NumberValueTooHighException;
import com.mycompany.myapp.shared.error.domain.NumberValueTooLowException;
import org.junit.jupiter.api.Test;

@UnitTest
class Jhlite110PageableTest {

  @Test
  void shouldNotBuildWithNegativePage() {
    assertThatThrownBy(() -> new Jhlite110Pageable(-1, 10))
      .isExactlyInstanceOf(NumberValueTooLowException.class)
      .hasMessageContaining("page");
  }

  @Test
  void shouldNotBuildWithPageSizeAtZero() {
    assertThatThrownBy(() -> new Jhlite110Pageable(0, 0))
      .isExactlyInstanceOf(NumberValueTooLowException.class)
      .hasMessageContaining("pageSize");
  }

  @Test
  void shouldNotBuildWithPageSizeOverHundred() {
    assertThatThrownBy(() -> new Jhlite110Pageable(0, 101))
      .isExactlyInstanceOf(NumberValueTooHighException.class)
      .hasMessageContaining("pageSize");
  }

  @Test
  void shouldGetFirstPageInformation() {
    Jhlite110Pageable pageable = new Jhlite110Pageable(0, 15);

    assertThat(pageable.page()).isZero();
    assertThat(pageable.pageSize()).isEqualTo(15);
    assertThat(pageable.offset()).isZero();
  }

  @Test
  void shouldGetPageableInformation() {
    Jhlite110Pageable pageable = new Jhlite110Pageable(2, 15);

    assertThat(pageable.page()).isEqualTo(2);
    assertThat(pageable.pageSize()).isEqualTo(15);
    assertThat(pageable.offset()).isEqualTo(30);
  }
}
