package gestionbancaria;

import static gestionbancaria.CuentaBancaria.existe;//BUSCA EN PERSONAS
import java.util.*;
import java.util.Map.Entry;

public class Banco {

    private String nombre; 
    private final String nifBanco;//PARA INGRESAR REGALO DE SALDO
    private final Map<Long, CuentaBancaria> cuentasBancarias = new HashMap<>();
    private final Set<Persona> personas = new HashSet<>();

    public Banco(String nombre, String nifBanco) {
        this.nombre = nombre;
        this.nifBanco = nifBanco;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNifBanco() {
        return nifBanco;
    }

    public CuentaBancaria crearCuenta(Persona persona) {
        CuentaBancaria cuenta = new CuentaBancaria(persona);
        cuentasBancarias.put(cuenta.getNUMCUENTA(), cuenta);
        cuenta.ingreso("regalo de saldo", getNifBanco(), (int) (Math.random()
                * 50 + 1));
        return cuenta;//PARA INFORMACION DE REGALO DE SALDO
    }

    public CuentaBancaria localizaCC(long ncuenta) {
        for (Entry<Long, CuentaBancaria> pareja : cuentasBancarias.entrySet()) {
            if (pareja.getKey() == ncuenta) {
                return pareja.getValue();
            }
        }
        return null;
    }

    public Persona nifRegistrado(String nif) {
        return existe(personas, nif);
    }

    public Persona crearPersona(String nif, String nombre) {
        Persona persona = new Persona(nif, nombre);
        personas.add(persona);
        return persona;
    }

    public Map<Long, CuentaBancaria> getCuentasBancarias() {
        return cuentasBancarias;
    }
}
