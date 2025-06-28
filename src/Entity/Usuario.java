/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L; // Para consistencia en la serialización

    String username;
    String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
