package com.datn.topfood.data.model;

import java.util.Collection;
import javax.persistence.*;
import com.datn.topfood.util.enums.AccountRole;
import com.datn.topfood.util.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account extends Base implements UserDetails {
    @Column(unique = true, length = 30)
    private String username;
    @Column(length = 72)
    private String password;
    @Column(unique = true, length = 15)
    private String phoneNumber;
    @Column(unique = true, length = 50)
    private String email;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @Enumerated(EnumType.STRING)
    private AccountRole role;

    public Account(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
