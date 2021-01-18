package gestionbancaria;

public class Persona {

    private final String NIF;
    private String nombre;

    public Persona(String nif, String nombre) {
        this.NIF = nif;
        this.nombre = nombre;
    }

    public String getNif() {
        return NIF;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    boolean igual(Persona person) {
        return person.getNif().equalsIgnoreCase(NIF);
    }

    boolean igual(String nif) {
        return NIF.equalsIgnoreCase(nif);
    }

    @Override
    public String toString() {
        return nombre + " (" + NIF + ")";
    }
}
