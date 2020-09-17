package data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Question {
    public int message_id;
    public int chat_id;
    public String text;
}
