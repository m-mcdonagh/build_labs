package cerulean.project.models;

import cerulean.project.services.UserRoles;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document
public class Account implements UserDetails {

    @Id
    private String _id;

    @Indexed(unique = true)
    private String username;
    private String password;

    @Indexed(unique = true)
    private String email;

    private List<String> assignedLabs_ids;
    private List<String> createdLabs_ids;
    private List<String> createdParts_ids;

    private List<GrantedAuthority> authorities;

    public Account(String username, String password, String email) {
        this(ObjectId.get().toString(), username, password, email, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Account(String _id, String username, String password, String email, List<String> assignedLabs_ids, List<String> createdLabs_ids, List<String> createdParts_ids) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.assignedLabs_ids = assignedLabs_ids;
        this.createdLabs_ids = createdLabs_ids;
        this.createdParts_ids = createdParts_ids;
        this.authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserRoles.ROLE_USER));
    }

    public String get_id() {
        return _id;
    }

    public String getUsername() {
        return username;
    }

    // Unimplemented functionality
    // Assume accounts cannot expire, be disabled, and require no verification
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
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getAssignedLabs_ids() {
        return assignedLabs_ids;
    }

    public List<String> getCreatedLabs_ids() {
        return createdLabs_ids;
    }

    public List<String> getCreatedParts_ids() {
        return createdParts_ids;
    }


}
