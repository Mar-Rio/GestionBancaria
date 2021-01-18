package gestionbancaria;

import java.util.*;

public class CuentaBancaria {

    private final long NUMCUENTA;
    private final Set<Persona> TITULARES = new HashSet<>();
    private final Set<Persona> AUTORIZADOS = new HashSet<>();
    private double saldo;
    private final Deque<Movimiento> HISTORIAL = new ArrayDeque<>();
    private static long contador = 1000;

    public CuentaBancaria(Persona titular) {
        this.NUMCUENTA = contador++;
        this.TITULARES.add(titular);
        this.saldo = 0;
    }

    public long getNUMCUENTA() {
        return NUMCUENTA;
    }

    public Set<Persona> getTITULARES() {
        return TITULARES;
    }

    public Set<Persona> getAUTORIZADOS() {
        return AUTORIZADOS;
    }

    public boolean setTitular(Persona nuevoTitular) {
        boolean diferenteTitular = false;
        if (existe(getTITULARES(), nuevoTitular.getNif()) == null) {
            diferenteTitular = TITULARES.add(nuevoTitular);
        }
        return diferenteTitular;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean[] eliminarTitular(String nif) {
        boolean[] existe = {false,false};
        if (TITULARES.size() > 1) {
            existe[0] = true;
            existe[1] = TITULARES.remove(existe(getTITULARES(), nif));
        }
        return existe;
    }
//METODO esTitular() SUSTITUIDO POR EXISTE (TITULARES Y TITULARES).
    public static Persona existe(Set<Persona> lista, String dni) {
        for (Persona siAutorizada : lista) {
            if (siAutorizada.igual(dni)) {
                return siAutorizada;
            }
        }
        return null;
    }

    boolean autorizar(Persona autorizado) {
        boolean autorizadoOK = false;
        if (existe(getAUTORIZADOS(), autorizado.getNif()) == null) {
            autorizadoOK = AUTORIZADOS.add(autorizado);
        }
        return autorizadoOK;
    }

    boolean desautorizar(String dni) {
        return AUTORIZADOS.remove(existe(getAUTORIZADOS(), dni));
    }

    public String verAutorizados() {
        String lista = "";
        if (!AUTORIZADOS.isEmpty()) {
            for (Persona autorizado : AUTORIZADOS) {
                lista = lista + "La persona " + autorizado 
                        + " está autorizada.\n";
            }
        } else {
            lista = "No hay personas autorizadas";
        }
        return lista;
    }

    public String getHistorial() {
        String listado = "";
        for (Movimiento movimiento : HISTORIAL) {
            listado += movimiento + "\n";
        }
        return listado;
    }

    public boolean ingreso(String concepto, String nif,
            double ingreso) {
        boolean ingresoCorrecto = false;
        if (ingreso > 0) {
            saldo = saldo + ingreso;
            ingresoCorrecto = HISTORIAL.offerFirst(new Movimiento(concepto, nif,
                    ingreso, saldo));
        }
        return ingresoCorrecto;
    }

    public boolean ingreso(int[] fecha, String concepto, String nif,
            double ingreso) {
        boolean ingresoCorrecto = false;
        if (ingreso > 0) {
            saldo = saldo + ingreso;
            ingresoCorrecto = HISTORIAL.offerFirst(new Movimiento(fecha,
                    concepto, nif, ingreso, saldo));

        }
        return ingresoCorrecto;
    }

    public boolean[] reintegro(String concepto, String nif,
            double reintegro) {
        boolean[] suficienteYPositivo = {false, false};
        if (reintegro > 0) {
            suficienteYPositivo[0] = true;//INGRESO POSITIVO
            if (saldo >= reintegro) {
                saldo = saldo - reintegro;
                suficienteYPositivo[1]//SALDO SUFICIENTE
                        = HISTORIAL.offerFirst(new Movimiento(concepto, nif,
                                -reintegro, saldo));
            }
        }
        return suficienteYPositivo;
    }

    public boolean[] reintegro(int[] fecha, String concepto, String nif,
            double reintegro) {
        boolean[] suficienteYPositivo = {false, false};
        if (reintegro > 0) {
            suficienteYPositivo[0] = true;
            if (saldo >= reintegro) {
                saldo = saldo - reintegro;
                suficienteYPositivo[1]
                        = HISTORIAL.offerFirst(new Movimiento(fecha, concepto,
                                nif, -reintegro, saldo));
            }
        }
        return suficienteYPositivo;
    }

    public String informacionCuenta() {
        String resultado = "Nº cuenta: " + NUMCUENTA + ".\n";
        resultado += "Titulares: " + getTITULARES() + ".\n";
        if (!getAUTORIZADOS().isEmpty()) {
            resultado += "Personas autorizadas: " + getAUTORIZADOS()
                    + "\n";
        }
        resultado += "Saldo: " + String.format("%.2f", saldo) + "€";
        return resultado;
    }

    @Override
    public String toString() {
        return " (" + NUMCUENTA + ") de " + TITULARES;
    }
}
