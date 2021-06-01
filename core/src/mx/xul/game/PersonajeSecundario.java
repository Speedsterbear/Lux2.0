package mx.xul.game;

/*
Esta clase puede representar a los personajes secundarios que se deban animar.
Por el momento solo la Luz Final es animada, así que se usa principalmente para ese personaje.
Autor: Carlos Arroyo
 */

import com.badlogic.gdx.graphics.Texture;

public class PersonajeSecundario extends ObjetoAnimado{
    public PersonajeSecundario(Texture textura, float x, float y, int column, int row, float duracion, int tipo) {
        super(textura, x, y, column, row, duracion, tipo);
    }
}
