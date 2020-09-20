package servicepackage.data;


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
    public String htmlLink;
    public String Message;
//    public DateTime startDateTime;
//    public DateTime endDateTime;
}
