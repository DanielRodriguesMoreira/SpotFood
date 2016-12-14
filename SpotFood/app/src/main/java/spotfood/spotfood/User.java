/*
 * SpotFood - 2016
 *
 * Authors:
 *          -> Daniel Moreira   nº21240321
 *          -> Hugo Santos      nº21220702
 *          -> Tiago Santos     nº21230530
 *          -> Carlos Zambrano  nº 21260582
 *          -> Selman Ay        nº21260599
 */

package spotfood.spotfood;

/** This class is used to "store" all the information about an User */
public class User {
    private String idUser;
    private String username;
    private String password;
    private String role;

    public User(){ }

    public User(String idUser, String username, String password, String role) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
