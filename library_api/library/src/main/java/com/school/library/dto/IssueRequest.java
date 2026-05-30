package com.school.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueRequest {

    @NotNull(message = "Book id is required")
    private Long bookId;

    @NotNull(message = "Member id is required")
    private Long memberId;
}
