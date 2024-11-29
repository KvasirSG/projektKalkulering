/*
 * ===================================================================================
 * File:        UserImpl.java
 * Description: Implements the User interface, representing a concrete implementation
 *              of a user entity with attributes such as ID, email, name, password, and role.
 *
 * Author:      Kenneth (KvasirSG)
 * Created:     2024-11-27
 * Updated:     2024-11-28
 * Version:     1.0
 *
 * License:     MIT License
 *
 * Notes:       - This class provides default behavior for the User interface methods.
 *              - Sensitive fields like password should be handled carefully to
 *                ensure security and privacy.
 * ===================================================================================
 */
package f24c2c1.projektkalkulering.model;

public class UserImpl implements User {
    private long id;
    private String email;
    private String name;
    private String password;
    private String role;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {

    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }
}