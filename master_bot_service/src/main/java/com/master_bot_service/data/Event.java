package com.master_bot_service.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    public long eventId;
    public long participantsChatId;
    public long orgsChatId;
    public String htmpLink;
    public String message;
//    public DateTime
}
