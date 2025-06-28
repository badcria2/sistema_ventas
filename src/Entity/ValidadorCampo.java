/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import javax.swing.text.JTextComponent;

public class ValidadorCampo implements DocumentListener {

    private final JTextComponent campo;
    private final String regex;
    private final String mensaje;

    public ValidadorCampo(JTextComponent campo, String regex, String mensaje) {
        this.campo = campo;
        this.regex = regex;
        this.mensaje = mensaje;
    }

    private void validar() {
        String texto = campo.getText();
        if (!texto.matches(regex)) {
            campo.setBackground(new Color(255, 200, 200)); // rojo claro
            campo.setToolTipText("Valor inválido: " + mensaje);
        } else {
            campo.setBackground(Color.WHITE);
            campo.setToolTipText(null);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        validar();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        validar();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        validar();
    }
}
