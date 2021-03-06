package com.participants_bot_service.data;

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