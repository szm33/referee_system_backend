package pl.lodz.p.it.referee_system.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "username", unique = true)
    private String username;
    private String password;
    private String email;
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive;
    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Referee referee;
    @NotNull
    @Version
    @Column
    private long version;
    @Column(name = "reset_link")
    private String resetLink;


    public Account() {
    }
    public Account(Long id , String username, String password, String email, boolean isActive, Referee referee) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
        this.referee = referee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.referee.getRole().getName()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
