package com.mycompany.myapp.shared.pagination.domain;

import static com.mycompany.myapp.shared.pagination.domain.Jhlite110PagesFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.shared.error.domain.MissingMandatoryValueException;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class Jhlite110PageTest {

  @Test
  void shouldGetEmptySinglePageWithoutContent() {
    Jhlite110Page<String> page = Jhlite110Page.singlePage(null);

    assertEmptyPage(page);
  }

  @Test
  void shouldGetEmptySinglePageFromBuilderWithoutContent() {
    Jhlite110Page<?> page = Jhlite110Page.builder(null).build();

    assertEmptyPage(page);
  }

  private void assertEmptyPage(Jhlite110Page<?> page) {
    assertThat(page.content()).isEmpty();
    assertThat(page.currentPage()).isZero();
    assertThat(page.pageSize()).isZero();
    assertThat(page.totalElementsCount()).isZero();
  }

  @Test
  void shouldGetSinglePage() {
    Jhlite110Page<String> page = Jhlite110Page.singlePage(List.of("test", "dummy"));

    assertSinglePage(page);
  }

  @Test
  void shouldGetSinglePageFromBuilderWithContentOnly() {
    Jhlite110Page<String> page = Jhlite110Page.builder(List.of("test", "dummy")).build();

    assertSinglePage(page);
  }

  private void assertSinglePage(Jhlite110Page<String> page) {
    assertThat(page.content()).containsExactly("test", "dummy");
    assertThat(page.currentPage()).isZero();
    assertThat(page.pageSize()).isEqualTo(2);
    assertThat(page.totalElementsCount()).isEqualTo(2);
    assertThat(page.pageCount()).isEqualTo(1);
  }

  @Test
  void shouldGetFullPage() {
    Jhlite110Page<String> page = pageBuilder().build();

    assertThat(page.content()).containsExactly("test");
    assertThat(page.currentPage()).isEqualTo(2);
    assertThat(page.pageSize()).isEqualTo(10);
    assertThat(page.totalElementsCount()).isEqualTo(21);
    assertThat(page.pageCount()).isEqualTo(3);
  }

  @Test
  void shouldNotMapWithoutMapper() {
    assertThatThrownBy(() -> pageBuilder().build().map(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("mapper");
  }

  @Test
  void shouldMapPage() {
    Jhlite110Page<String> page = pageBuilder().build().map(entry -> "hey");

    assertThat(page.content()).containsExactly("hey");
    assertThat(page.currentPage()).isEqualTo(2);
    assertThat(page.pageSize()).isEqualTo(10);
    assertThat(page.totalElementsCount()).isEqualTo(21);
    assertThat(page.pageCount()).isEqualTo(3);
  }

  @Test
  void shouldNotBeLastForFirstPage() {
    assertThat(pageBuilder().currentPage(0).build().isNotLast()).isTrue();
  }

  @Test
  void shouldBeLastWithOnePage() {
    assertThat(Jhlite110Page.singlePage(List.of("d")).isNotLast()).isFalse();
  }

  @Test
  void shouldBeLastPageWithoutContent() {
    Jhlite110Page<Object> page = Jhlite110Page.builder(List.of()).currentPage(0).pageSize(1).totalElementsCount(0).build();
    assertThat(page.isNotLast()).isFalse();
  }

  @Test
  void shouldBeLastForLastPage() {
    assertThat(pageBuilder().currentPage(2).build().isNotLast()).isFalse();
  }

  @Test
  void shouldGetPageFromElements() {
    Jhlite110Page<String> page = Jhlite110Page.of(List.of("hello", "java", "world"), new Jhlite110Pageable(1, 1));

    assertThat(page.currentPage()).isEqualTo(1);
    assertThat(page.hasNext()).isTrue();
    assertThat(page.hasPrevious()).isTrue();
    assertThat(page.pageCount()).isEqualTo(3);
    assertThat(page.pageSize()).isEqualTo(1);
    assertThat(page.content()).containsExactly("java");
  }

  @Test
  void shouldGetEmptyPageFromOutOfBoundElements() {
    Jhlite110Page<String> page = Jhlite110Page.of(List.of("hello", "java", "world"), new Jhlite110Pageable(4, 1));

    assertThat(page.currentPage()).isEqualTo(4);
    assertThat(page.hasNext()).isFalse();
    assertThat(page.hasPrevious()).isTrue();
    assertThat(page.pageCount()).isEqualTo(3);
    assertThat(page.pageSize()).isEqualTo(1);
    assertThat(page.content()).isEmpty();
  }

  @Test
  void shouldGetPageWithLessThanExpectedElements() {
    Jhlite110Page<String> page = Jhlite110Page.of(List.of("hello", "java", "world"), new Jhlite110Pageable(0, 4));

    assertThat(page.currentPage()).isZero();
    assertThat(page.hasNext()).isFalse();
    assertThat(page.hasPrevious()).isFalse();
    assertThat(page.pageCount()).isEqualTo(1);
    assertThat(page.pageSize()).isEqualTo(4);
    assertThat(page.content()).hasSize(3);
  }
}
