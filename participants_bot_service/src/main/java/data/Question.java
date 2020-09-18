package data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Question {
    public long message_id;
    public long chat_id;
    public String text;
}
