package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String contents;
}
