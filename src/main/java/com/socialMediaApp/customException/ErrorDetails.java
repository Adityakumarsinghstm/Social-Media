package com.socialMediaApp.customException;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
@AllArgsConstructor
@Getter
public class ErrorDetails {
    private LocalDate timeStamp;
    private String message;
    private String details;
}
