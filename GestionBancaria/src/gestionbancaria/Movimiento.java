package gestionbancaria;

import java.time.LocalDateTime;

public class Movimiento {
// LOS MOVIMIENTOS SON RELATIVOS AL BANCO(TOTALES), NO A LA CUENTA =(
    private final int movimientoNumero;
    private final LocalDateTime fecha;
    private final double cantidad,
            saldoActual;
    private final String concepto, nif;
    private static int contador = 0;

    public Movimiento(String concepto, String nif, Double cantidad, Double saldoActual) {
        movimientoNumero = ++contador;
        this.fecha = LocalDateTime.now();
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.saldoActual = saldoActual;
        this.nif = nif;
    }

    public Movimiento(int[] fecha, String concepto, String nif, Double cantidad,
            Double saldoActual) {
        movimientoNumero = ++contador;
        this.fecha = LocalDateTime.of(fecha[0], fecha[1], fecha[2], fecha[3],
                fecha[4]);
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.saldoActual = saldoActual;
        this.nif = nif;
    }

    public String getNif() {
        return nif;
    }

    @Override
    public String toString() {
        return movimientoNumero + ". Fecha:" + fecha + "...Cantidad:" + cantidad
                + "...Concepto:" + concepto + "...Saldo: " + saldoActual;
//      return fecha + "  " + cantidad + "euros  " + concepto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public double getCantidad() {
        return cantidad;
    }

    public String getConcepto() {
        return concepto;
    }

}
