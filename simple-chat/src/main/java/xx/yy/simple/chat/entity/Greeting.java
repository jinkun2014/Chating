package xx.yy.simple.chat.entity;

import lombok.Data;

@Data
public class Greeting {

    private String loginId;
    private String content;
    private String token;
    private Integer userCount;

    public Greeting() {
    }

    public Greeting(String loginId, String content) {
        this.loginId = loginId;
        this.content = content;
    }
}
