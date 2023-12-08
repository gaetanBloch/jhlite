package com.mycompany.myapp.shared.pagination.domain;

import java.util.List;

import com.mycompany.myapp.shared.pagination.domain.Jhlite110Page.Jhlite110PageBuilder;

public final class Jhlite110PagesFixture {

  private Jhlite110PagesFixture() {}

  public static Jhlite110Page<String> page() {
    return pageBuilder().build();
  }

  public static Jhlite110PageBuilder<String> pageBuilder() {
    return Jhlite110Page.builder(List.of("test")).currentPage(2).pageSize(10).totalElementsCount(21);
  }
}
