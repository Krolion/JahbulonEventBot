package com.server_service.servicepackage.data;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    public long message_id;
    public long participants_chat_id;
    public long orgs_chat_id;
    public String text;
}
