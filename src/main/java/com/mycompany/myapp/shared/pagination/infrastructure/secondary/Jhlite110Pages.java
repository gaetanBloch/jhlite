package com.mycompany.myapp.shared.pagination.infrastructure.secondary;

import com.mycompany.myapp.shared.error.domain.Assert;
import com.mycompany.myapp.shared.pagination.domain.Jhlite110Page;
import com.mycompany.myapp.shared.pagination.domain.Jhlite110Pageable;
import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class Jhlite110Pages {

  private Jhlite110Pages() {}

  public static Pageable from(Jhlite110Pageable pagination) {
    return from(pagination, Sort.unsorted());
  }

  public static Pageable from(Jhlite110Pageable pagination, Sort sort) {
    Assert.notNull("pagination", pagination);
    Assert.notNull("sort", sort);

    return PageRequest.of(pagination.page(), pagination.pageSize(), sort);
  }

  public static <S, T> Jhlite110Page<T> from(Page<S> springPage, Function<S, T> mapper) {
    Assert.notNull("springPage", springPage);
    Assert.notNull("mapper", mapper);

    return Jhlite110Page
      .builder(springPage.getContent().stream().map(mapper).toList())
      .currentPage(springPage.getNumber())
      .pageSize(springPage.getSize())
      .totalElementsCount(springPage.getTotalElements())
      .build();
  }
}
