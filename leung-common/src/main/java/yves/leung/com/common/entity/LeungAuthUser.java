package yves.leung.com.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class LeungAuthUser implements Serializable {

    private String username;

    private String password;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked= true;

    private boolean credentialsNonExpired= true;

    private boolean enabled= true;
}
