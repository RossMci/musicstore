/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music.business;

/**
 *
 * @author ross1
 */
public class Admin {
    private String Username;
    private String Password;
    private String Rolename;


    public Admin() {
        Username = "";
        Password = "";
        Rolename = "";
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getRolename() {
        return Rolename;
    }

    public void setRolename(String Rolename) {
        this.Rolename = Rolename;
    }
    
}
