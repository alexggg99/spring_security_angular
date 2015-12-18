package bonvio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mil on 03.04.15.
 */
@Entity
@Table (name = "user")
public class User{

    public User() {
    }

    public User(String password, String username, String firstName, String lastName, long vkId) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idVk = vkId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Role authority;

    @NotEmpty
    @JsonIgnore
    public String password;

    @NotEmpty
    public String username;

    boolean accountNonExpired = true;
    boolean accountNonLocked = true;
    boolean credentialsNonExpired = true;
    boolean enabled = true;

    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;
    private Date dateCreate;
    private Long idVk;
    private String restoreToken;
    @Temporal(TemporalType.TIMESTAMP)
    private Date restoreTokenExpiredDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Long getIdVk() {
        return idVk;
    }

    public void setIdVk(Long idVk) {
        this.idVk = idVk;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRestoreToken() {
        return restoreToken;
    }

    public void setRestoreToken(String restoreToken) {
        this.restoreToken = restoreToken;
    }

    public Date getRestoreTokenExpiredDate() {
        return restoreTokenExpiredDate;
    }

    public void setRestoreTokenExpiredDate(Date restoreTokenExpiredDate) {
        this.restoreTokenExpiredDate = restoreTokenExpiredDate;
    }
}
