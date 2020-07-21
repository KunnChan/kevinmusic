package com.music.kevinmusic.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.music.kevinmusic.domain.Customer;
import com.music.kevinmusic.domain.StatusEntity;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(exclude = {"roles", "authorities"})
public class User extends StatusEntity implements UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min = 3, message = "*Your username must have at least 3 characters")
    @NotEmpty(message = "*Please provide your username")
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Length(min = 3, message = "*Your name must have at least 3 characters")
    @NotEmpty(message = "*Please provide your name")
    private String name;

    private String note;

    @Length(min = 5, message = "*Your phone must have at least 5 characters")
    @NotEmpty(message = "*Please provide your phone")
    @Column(unique = true)
    private String phone;

    @Length(min = 3, message = "*Your password must have at least 3 characters")
    @NotEmpty(message = "*Please provide your password")
    @JsonIgnore
    private String password;

    @Builder.Default
    private Double point = 0d;

    private Date dob;

    @Singular
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private Set<Role> roles;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .map((authority -> new SimpleGrantedAuthority(authority.getPermission())))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Builder.Default
    private Boolean accountNonExpired = true;

    @Builder.Default
    private Boolean accountNonLocked = true;

    @Builder.Default
    private Boolean credentialsNonExpired = true;

    @Builder.Default
    private Boolean enabled = true;

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
