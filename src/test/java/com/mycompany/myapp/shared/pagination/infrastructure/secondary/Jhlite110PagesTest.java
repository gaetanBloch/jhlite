package com.mycompany.myapp.shared.pagination.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.shared.error.domain.MissingMandatoryValueException;
import com.mycompany.myapp.shared.pagination.domain.Jhlite110Page;
import com.mycompany.myapp.shared.pagination.domain.Jhlite110Pageable;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UnitTest
class Jhlite110PagesTest {

  @Test
  void shouldNotBuildPageableFromNullJhlite110Pageable() {
    assertThatThrownBy(() -> Jhlite110Pages.from(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("pagination");
  }

  @Test
  void shouldBuildPageableFromJhlite110Pageable() {
    Pageable pagination = Jhlite110Pages.from(pagination());

    assertThat(pagination.getPageNumber()).isEqualTo(2);
    assertThat(pagination.getPageSize()).isEqualTo(15);
    assertThat(pagination.getSort()).isEqualTo(Sort.unsorted());
  }

  @Test
  void shouldNotBuildWithoutSort() {
    assertThatThrownBy(() -> Jhlite110Pages.from(pagination(), null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("sort");
  }

  @Test
  void shouldBuildPageableFromJhlite110PageableAndSort() {
    Pageable pagination = Jhlite110Pages.from(pagination(), Sort.by("dummy"));

    assertThat(pagination.getPageNumber()).isEqualTo(2);
    assertThat(pagination.getPageSize()).isEqualTo(15);
    assertThat(pagination.getSort()).isEqualTo(Sort.by("dummy"));
  }

  private Jhlite110Pageable pagination() {
    return new Jhlite110Pageable(2, 15);
  }

  @Test
  void shouldNotConvertFromSpringPageWithoutSpringPage() {
    assertThatThrownBy(() -> Jhlite110Pages.from(null, source -> source))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("springPage");
  }

  @Test
  void shouldNotConvertFromSpringPageWithoutMapper() {
    assertThatThrownBy(() -> Jhlite110Pages.from(springPage(), null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("mapper");
  }

  @Test
  void shouldConvertFromSpringPage() {
    Jhlite110Page<String> page = Jhlite110Pages.from(springPage(), Function.identity());

    assertThat(page.content()).containsExactly("test");
    assertThat(page.currentPage()).isEqualTo(2);
    assertThat(page.pageSize()).isEqualTo(10);
    assertThat(page.totalElementsCount()).isEqualTo(30);
  }

  private PageImpl<String> springPage() {
    return new PageImpl<>(List.of("test"), PageRequest.of(2, 10), 30);
  }
}
