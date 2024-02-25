package com.cbs.democbs.domain.data;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** User: ayoade_farooq@yahoo.com Date: 15/09/2021 Time: 18:28 */
@Data
public class CustomPageResponse<T> {
  private int totalPages;

  private int size;

  private long totalElements;

  private boolean hasNext;

  private boolean hasPrevious;

  private List<T> content = new ArrayList<>();

  public static <T> CustomPageResponse<T> resolvePageResponse(Page<T> objectPage) {
    CustomPageResponse<T> customPageResponse = new CustomPageResponse<>();
    customPageResponse.setContent(objectPage.getContent());
    customPageResponse.setSize((int) Math.min(objectPage.getSize(), objectPage.getTotalElements()));
    customPageResponse.setHasNext(objectPage.hasNext());
    customPageResponse.setHasPrevious(objectPage.hasPrevious());
    customPageResponse.setTotalElements(objectPage.getTotalElements());
    customPageResponse.setTotalPages(objectPage.getTotalPages());
    return customPageResponse;
  }

  public static <T> CustomPageResponse<T> resolvePageResponse(
      List<T> objectList, long totalElements, Pageable pageable) {
    CustomPageResponse<T> customPageResponse = new CustomPageResponse<>();
    boolean hasNext = pageable.getOffset() + pageable.getPageSize() < totalElements;
    boolean hasPrevious = pageable.getOffset() > 0;
    int totalPages = (int) (totalElements / pageable.getPageSize());
    customPageResponse.setContent(objectList);
    customPageResponse.setSize((int) totalElements);
    customPageResponse.setHasNext(hasNext);
    customPageResponse.setHasPrevious(hasPrevious);
    customPageResponse.setTotalElements(totalElements);
    customPageResponse.setTotalPages(totalPages);
    return customPageResponse;
  }

  public static <T> CustomPageResponse<T> resolvePageResponse(
      List<T> content,
      long totalElements,
      int totalPages,
      int size,
      boolean hasNext,
      boolean hasPrevious) {
    CustomPageResponse<T> customPageResponse = new CustomPageResponse<>();
    customPageResponse.setContent(content);
    customPageResponse.setSize(size);
    customPageResponse.setHasNext(hasNext);
    customPageResponse.setHasPrevious(hasPrevious);
    customPageResponse.setTotalElements(totalElements);
    customPageResponse.setTotalPages(totalPages);
    return customPageResponse;
  }
}
