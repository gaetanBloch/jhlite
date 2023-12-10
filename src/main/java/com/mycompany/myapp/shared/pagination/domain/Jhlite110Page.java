package com.mycompany.myapp.shared.pagination.domain;

import com.mycompany.myapp.shared.collection.domain.Jhlite110Collections;
import com.mycompany.myapp.shared.error.domain.Assert;
import java.util.List;
import java.util.function.Function;

public class Jhlite110Page<T> {

  private static final int MINIMAL_PAGE_COUNT = 1;

  private final List<T> content;
  private final int currentPage;
  private final int pageSize;
  private final long totalElementsCount;

  private Jhlite110Page(Jhlite110PageBuilder<T> builder) {
    content = Jhlite110Collections.immutable(builder.content);
    currentPage = builder.currentPage;
    pageSize = buildPageSize(builder.pageSize);
    totalElementsCount = buildTotalElementsCount(builder.totalElementsCount);
  }

  private int buildPageSize(int pageSize) {
    if (pageSize == -1) {
      return content.size();
    }

    return pageSize;
  }

  private long buildTotalElementsCount(long totalElementsCount) {
    if (totalElementsCount == -1) {
      return content.size();
    }

    return totalElementsCount;
  }

  public static <T> Jhlite110Page<T> singlePage(List<T> content) {
    return builder(content).build();
  }

  public static <T> Jhlite110PageBuilder<T> builder(List<T> content) {
    return new Jhlite110PageBuilder<>(content);
  }

  public static <T> Jhlite110Page<T> of(List<T> elements, Jhlite110Pageable pagination) {
    Assert.notNull("elements", elements);
    Assert.notNull("pagination", pagination);

    List<T> content = elements.subList(
      Math.min(pagination.offset(), elements.size()),
      Math.min(pagination.offset() + pagination.pageSize(), elements.size())
    );

    return builder(content).currentPage(pagination.page()).pageSize(pagination.pageSize()).totalElementsCount(elements.size()).build();
  }

  public List<T> content() {
    return content;
  }

  public int currentPage() {
    return currentPage;
  }

  public int pageSize() {
    return pageSize;
  }

  public long totalElementsCount() {
    return totalElementsCount;
  }

  public int pageCount() {
    if (totalElementsCount > 0) {
      return (int) Math.ceil(totalElementsCount / (float) pageSize);
    }

    return MINIMAL_PAGE_COUNT;
  }

  public boolean hasPrevious() {
    return currentPage > 0;
  }

  public boolean hasNext() {
    return isNotLast();
  }

  public boolean isNotLast() {
    return currentPage + 1 < pageCount();
  }

  public <R> Jhlite110Page<R> map(Function<T, R> mapper) {
    Assert.notNull("mapper", mapper);

    return builder(content().stream().map(mapper).toList())
      .currentPage(currentPage)
      .pageSize(pageSize)
      .totalElementsCount(totalElementsCount)
      .build();
  }

  public static class Jhlite110PageBuilder<T> {

    private final List<T> content;
    private int currentPage;
    private int pageSize = -1;
    private long totalElementsCount = -1;

    private Jhlite110PageBuilder(List<T> content) {
      this.content = content;
    }

    public Jhlite110PageBuilder<T> pageSize(int pageSize) {
      this.pageSize = pageSize;

      return this;
    }

    public Jhlite110PageBuilder<T> currentPage(int currentPage) {
      this.currentPage = currentPage;

      return this;
    }

    public Jhlite110PageBuilder<T> totalElementsCount(long totalElementsCount) {
      this.totalElementsCount = totalElementsCount;

      return this;
    }

    public Jhlite110Page<T> build() {
      return new Jhlite110Page<>(this);
    }
  }
}
