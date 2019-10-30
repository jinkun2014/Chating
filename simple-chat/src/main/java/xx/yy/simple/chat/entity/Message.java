package xx.yy.simple.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    /**
     * 群聊
     */
    public final static int TYPE_ALL = 0;

    /**
     * 聊天类型
     */
    private int type;

    private String from;
    private String to;
    private String content;
    private Long time;
}
