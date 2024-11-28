/*
 * ===================================================================================
 * File:        User.java
 * Description: Defines the contract for a User entity, specifying the methods
 *              required to manage user attributes such as ID, email, name,
 *              password, and role.
 *
 * Author:      Kenneth (KvasirSG)
 * Created:     2024-11-28
 * Updated:     2024-11-28
 * Version:     1.0
 *
 * License:     MIT License
 *
 * Notes:       - This interface should be implemented by classes representing
 *                concrete user entities, such as system administrators or regular users.
 *              - Ensure proper validation of sensitive fields like password when
 *                implementing this interface.
 * ===================================================================================
 */
package f24c2c1.projektkalkulering.model;

public interface User {
    long getId();
    void setId(long id);

    String getEmail();
    void setEmail(String email);

    String getName();
    void setName(String name);

    String getPassword();
    void setPassword(String password);

    String getRole();
    void setRole(String role);
}

