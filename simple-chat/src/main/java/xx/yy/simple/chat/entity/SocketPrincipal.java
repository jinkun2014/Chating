package xx.yy.simple.chat.entity;

import lombok.Data;

import java.security.Principal;

@Data
public class SocketPrincipal implements Principal {

    private String name;
    private String token;


    public SocketPrincipal() {
    }

    public SocketPrincipal(String name) {
        this.name = name;
    }

    public SocketPrincipal(String name, String token) {
        this.name = name;
        this.token = token;
    }

    @Override
    public String getName() {
        return name;
    }
}
