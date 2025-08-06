//Helper class that lets spring security use my application's user objects for login and access control.
// tells spring security how to get the user's email,passwords and role so system can check who the user is and what they are allowed to do.
//Adapts applications's User entity to spring security's authentication system.
//Implements UserDetails interface,allowing spring security to use my custom user data like email password for authentication and authorization.
//This class provides user credintials and authorities in the format expected by spring secuirty during login and access control checks.


package com.marketmaven.MarketMaven.Ecommerce.security;//Package for the class

//Imports required for user entity,Lombok,spring security and collections.
import com.marketmaven.MarketMaven.Ecommerce.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;//represents a permission or role assigned to user,which spring security uses to check what actions the user is allowed to perform.
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Implements GrantedAuthority interface.represents a user's role or authority in a simple way,allowing spring secuirty to check what permissions the user has during authentication and authorization.
import org.springframework.security.core.userdetails.UserDetails;//provides methods to get the username,pw and user authorities,account status .

import java.util.Collection;
import java.util.List;

@Data//Generates getters and setters ,toString,equals,hash code
@Builder //enable builder pattern for object creation.

public class AuthUser implements UserDetails {
    private User user;//reference to the application's user entity
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));//returns a list containing all the user's role asa simple granted authority.
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
    //returns the user's pw

    @Override
    public String getUsername() {
        return user.getEmail() ;
    }
    //returns the user's email as username

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //checks if the user's account is still valid or not

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //checks if the user's account is not locked (can be accessed)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //checks if the user's credintials are still valid and not expired.

    @Override
    public boolean isEnabled() {
        return true;
    }
    //checks whether the user's account is enabled.
}

