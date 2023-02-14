package com.microservices.mailer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailBody {

    @NonNull
    private String subject;
    @NonNull
    private String body;
}
