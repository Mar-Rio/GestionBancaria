package gestionbancaria;

import java.util.*;

public class Principal {

    static Scanner sc = new Scanner(System.in, "ISO-8859-1");//ADMITE ACENTO Y Ñ
    static final String RED = "\033[031m";
    static final Banco BANK = new Banco("Triodos", "00000001A");
//NIF NECESARIO PARA REGALO AL CREAR CUENTA

    public static void main(String[] args) {
        String respuesta;
        do {
            try {
                respuesta = menu1();

                switch (respuesta) {
                    case "1" ->
                        usarPersona();
                    case "2" ->
                        crearCuenta();
                    case "3" -> {
                        CuentaBancaria cuenta = usarCuenta();
                        if (cuenta != null) {
                            operaciones(cuenta);
                        }
                    }
                    case "4" -> {
                        System.out.println("Gracias por usar nuestra"
                                + " aplicacion");
                        return;
                    }
                    default ->
                        System.out.println(RED + "Debe seleccionar un "
                                + "numero correcto");
                }
            } catch (InputMismatchException e) {
                System.out.println(RED + "Debe introducir  un número");
                System.out.println();
            }
        } while (true);
    }

    public static String menu1() {
        String respuesta;
        System.out.println("BANCO:" + BANK.getNombre());
        System.out.println("1-Introducir datos personales.");
        System.out.println("2-Crear nueva Cuenta Bancaria.");
        System.out.println("3-Usar cuenta registrada.");
        System.out.println("4-Salir.");
        respuesta = sc.nextLine();
        return respuesta;
    }

    public static void crearCuenta() {
        boolean noMasTitulares = false;
        String respuesta;
        CuentaBancaria cuenta = BANK.crearCuenta(usarPersona());
        System.out.println("Se ha creado la cuenta correctamente con el "
                + "numero de cuenta: " + cuenta.getNUMCUENTA() + " y con el "
                + "titular: " + cuenta.getTITULARES() + "\n"
                + "Su saldo de regalo es " + cuenta.getSaldo());
        do {
            System.out.println("\n¿Quiere añadir otro titular?(Si/No)");
            respuesta = sc.nextLine();
            if (respuesta.equalsIgnoreCase("SI")) {
                if (cuenta.setTitular(usarPersona())) {
                    System.out.println("Nuevo titular añadido.\n");
                } else {
                    System.out.println(RED + "Titular ya existe.\n");
                }
            } else if (respuesta.equalsIgnoreCase("NO")) {
                noMasTitulares = true;
                System.out.println(RED + "No se han añadido mas titulares\n");
            } else {
                System.out.println(RED + "Introduzca Si o No");
            }
        } while (!noMasTitulares);
        System.out.println("Bienvenido a nuestro banco.\n");
    }

    public static CuentaBancaria usarCuenta() {
        CuentaBancaria cuenta = null;//NO LLAMAR A OPERACIONES CON CUENTA NULL
        long numeroCuenta = 0;
        boolean numeroCorrecto = false;
        System.out.println("CUENTAS EXISTENTES:");
        BANK.getCuentasBancarias().entrySet().forEach(pareja -> {
            System.out.println(pareja.getKey() + " " + pareja.getValue());
        });
        do {
            try {
                System.out.println("Introduzca Numero de Cuenta: ");
                numeroCuenta = Long.parseLong(sc.nextLine());
                numeroCorrecto = true;
            } catch (NumberFormatException e) {
                System.out.println(RED + "Introduzca numero");
            }
            if (BANK.localizaCC(numeroCuenta) != null) {
                cuenta = BANK.localizaCC(numeroCuenta);
            } else {
                System.out.println(RED + "Lo sentimos, la cuenta no existe");
            }

        } while (!numeroCorrecto);

        return cuenta;
    }

    public static String menu2(CuentaBancaria cuenta) {

        String respuesta;
        System.out.println("CUENTA BANCARIA: " + cuenta.getNUMCUENTA() + "-"
                + cuenta.getTITULARES() + "/Saldo: " + cuenta.getSaldo());
        System.out.println("1-Ingresar dinero.");
        System.out.println("2-Sacar dinero.");
        System.out.println("3-Informacion cuenta.");
        System.out.println("4-Autorizar.");
        System.out.println("5-Desautorizar.");
        System.out.println("6-Listar movimientos de cuenta.");
        System.out.println("7-Eliminar titular.");
        System.out.println("8-Añadir titular.");
        System.out.println("9-Volver al menú anterior.\n");
        respuesta = sc.nextLine();

        return respuesta;
    }

    public static void operaciones(CuentaBancaria cuenta) {
        String respuesta;
        do {
            try {
                respuesta = menu2(cuenta);

                switch (respuesta) {
                    case "1" ->
                        ingresar(cuenta);
                    case "2" ->
                        sacar(cuenta);
                    case "3" ->
                        verInformacion(cuenta);
                    case "4" ->
                        autorizacion(cuenta);
                    case "5" ->
                        desautorizacion(cuenta);
                    case "6" ->
                        listaDeMovimientos(cuenta);
                    case "7" ->
                        eliminarTitular(cuenta);
                    case "8" ->
                        añadirTitular(cuenta);
                    case "9" -> {
                        return;
                    }
                    default ->
                        System.out.println(RED + "Debe seleccionar un "
                                + "numero correcto");
                }

            } catch (InputMismatchException e) {
                System.out.println(RED + "Debe introducir  un número");
                System.out.println();
            }
        } while (true);

    }

    public static void ingresar(CuentaBancaria cuenta) {
        double cantidad = 0;
        boolean ingresoOK = false;
        String nif,
                concepto,
                tipoFecha;
        boolean numerico = false,
                nifCorrecto = false;
        do {//SOLO NUMEROS
            System.out.println("¿Cuánto dinero desea ingresar?");
            try {
                cantidad = Double.parseDouble(sc.nextLine());
                numerico = true;
            } catch (NumberFormatException e) {
                System.out.println(RED + "Introduzca cantidad numerica");
            }
        } while (!numerico);
        do {//NIF VALIDO
            System.out.println("Indique su DNI: ");
            nif = sc.nextLine();
            if (!nif.matches("\\d{8}[a-zA-ZÑñ]")) {
                System.out.println(RED + "Introduzca NIF valido");
            } else {
                nifCorrecto = true;
            }
        } while (!nifCorrecto);
        System.out.println("Indique concepto: ");
        concepto = sc.nextLine();
        System.out.println("Quiere introducir fecha manualmente(Si/No)?: ");
        tipoFecha = sc.nextLine();
        if (tipoFecha.equalsIgnoreCase("si")) {
            ingresoOK = cuenta.ingreso(introducirFecha(), concepto, nif,
                    cantidad);
        } else if (tipoFecha.equalsIgnoreCase("no")) {
            ingresoOK = cuenta.ingreso(concepto, nif, cantidad);
        } else {
            System.out.println("TONTICO, NO VOY A HACER OTRO DO-WHILE. "
                    + "YA LO TIENES EN SACAR DINERO.\n");
            ingresoOK = cuenta.ingreso(concepto, nif, cantidad);
        }

        if (ingresoOK) {
            System.out.println("La persona con nif " + nif + " ha ingresado: "
                    + cantidad + "€ .Su saldo total es de: " + cuenta.getSaldo()
                    + "€\n");
        } else {
            System.out.println(RED + "Ingrese cantidad mayor que cero.\n");
        }
        System.out.println();
    }

    public static void sacar(CuentaBancaria cuenta) {
        double cantidad = 0;
        boolean[] reintegroOK = new boolean[2];
        boolean numerico = false,
                nifCorrecto = false,
                siNo = false;
        String nif,
                concepto,
                tipoFecha;
        do {//NUMERO VALIDO
            try {
                System.out.println("¿Cuánto dinero desea sacar?");
                cantidad = Double.parseDouble(sc.nextLine());
                numerico = true;
            } catch (NumberFormatException e) {
                System.out.println("\033[031mIntroducir cantidad numerica.");
            }
        } while (!numerico);
        do {//NIF DE TITULAR O AUTORIZADO
            System.out.println("Indique su DNI: ");
            nif = sc.nextLine();
            if (CuentaBancaria.existe(cuenta.getTITULARES(), nif) != null
                    || CuentaBancaria.existe(cuenta.getAUTORIZADOS(), nif)
                    != null) {
                nifCorrecto = true;
            } else {
                System.out.println(RED + "Retirada solo posible para titulares "
                        + "y autorizados.\n");
            }
        } while (!nifCorrecto);
        System.out.println("Indique concepto: ");
        concepto = sc.nextLine();
        do {
            System.out.println("Quiere introducir fecha manualmente(Si/No)?: ");
            tipoFecha = sc.nextLine();
            if (tipoFecha.equalsIgnoreCase("si")) {
                reintegroOK = cuenta.reintegro(introducirFecha(), concepto, nif,
                        cantidad);
                siNo = true;
            } else if (tipoFecha.equalsIgnoreCase("no")) {
                reintegroOK = cuenta.reintegro(concepto, nif, cantidad);
                siNo = true;
            } else {
                System.out.println(RED + "Introducir Si o No.");
            }
        } while (!siNo);
        if (!reintegroOK[0]) {//MAYOR QUE CERO
            System.out.println(RED + "Ingrese cifra positiva.\n");
        } else {
            if (!reintegroOK[1]) {//SALDO SUFICIENTE
                System.out.println(RED + "No hay saldo suficiente.\n");
            } else {
                System.out.println("Se ha sacado de su cuenta: " + cantidad
                        + " €.Su saldo es de: " + cuenta.getSaldo()
                        + " €\n");
            }
        }
        System.out.println();
    }

    public static void verInformacion(CuentaBancaria cuenta) {
        System.out.println("Aqui tiene la informacion solicitada");
        System.out.println(cuenta.informacionCuenta());
        System.out.println("");
    }

    public static void autorizacion(CuentaBancaria cuenta) {
        System.out.println("Introduzca persona para autorizar.");
        Persona paraAutorizar = usarPersona();

        if (cuenta.autorizar(paraAutorizar)) {
            System.out.println("La persona " + paraAutorizar
                    + " ha sido autorizada.\n");
        } else {
            System.out.println(RED + paraAutorizar + " ya fue autorizada.\n");
        }
    }

    public static void desautorizacion(CuentaBancaria cuenta) {
        System.out.println("Introduzca NIF para desautorizar: ");
        for (Persona autorizado : cuenta.getAUTORIZADOS()) {
            System.out.println(autorizado);
        }
        String paraDesautorizar = usarPersona().getNif();
        if (cuenta.desautorizar(paraDesautorizar)) {
            System.out.println("La persona con NIF " + paraDesautorizar
                    + " acaba de ser desautorizada.\n");
        } else {
            System.out.println("La persona con NIF " + paraDesautorizar
                    + " no estaba previamente autorizada.\n");
        }
    }

    public static void listaDeMovimientos(CuentaBancaria cuenta) {
        System.out.println(cuenta.getHistorial());
    }

    public static void eliminarTitular(CuentaBancaria cuenta) {
        System.out.println("Introduzca nif para eliminar titular: ");
        for (Persona titular : cuenta.getTITULARES()) {
            System.out.println(titular);

        }
        boolean[] eliminado = cuenta.eliminarTitular(usarPersona().getNif());
        if (!eliminado[0]) {
            System.out.println(RED + "La cuenta no puede quedar con cero "
                    + "titulares.\n");
        }
        if (!eliminado[1]) {
            System.out.println(RED + "No se ha eliminado a ningun titular.\n");

        } else {
            System.out.println("El titular ha sido eliminado.\n");
        }
    }

    public static void añadirTitular(CuentaBancaria cuenta) {
        boolean añadido = cuenta.setTitular(usarPersona());
        if (añadido) {
            System.out.println("El titular ha sido añadido correctamente.\n");
        } else {
            System.out.println("Esta persona ya era previamente titular.\n");
        }
    }

    public static int[] introducirFecha() {
        boolean numerico = false;
        int[] fecha = new int[5];
        do {
            try {
                System.out.println("año(4 numeros)?");
                fecha[0] = Integer.parseInt(sc.nextLine());
                System.out.println("mes(De 1 a 12?");
                fecha[1] = Integer.parseInt(sc.nextLine());
                System.out.println("dia( De 1 a 31)?");
                fecha[2] = Integer.parseInt(sc.nextLine());
                System.out.println("hora(De 0 a 23)?");
                fecha[3] = Integer.parseInt(sc.nextLine());
                System.out.println("minuto(De 0 a 59)?");
                fecha[4] = Integer.parseInt(sc.nextLine());
                numerico = true;
            } catch (NumberFormatException e) {
                System.out.println(RED + "Introduzca numeros enteros.");
            }
        } while (!numerico);
        return fecha;
    }

    public static String pedirNif() {
        boolean nifValido = false;
        String nif = "";
        do {
            System.out.println("Introduzca NIF:");
            nif = sc.nextLine().toUpperCase();//CONVIERTE A MAYUSCULA NIF
            //BUSCA 8 NUMEROS Y 1 LETRA DE A->Z + Ñ
            if (nif.matches("\\d{8}[A-ZÑ]")) {
                nifValido = true;
            } else {
                System.out.println(RED + "Introduzca NIF valido");
            }
        } while (!nifValido);
        return nif;
    }

    public static String pedirNombre() {
        //COMPROBAR QUE TIENE MINIMO 3 PALABRAS SEPARADAS POR ESPACIOS
        //INCLUYE ACENTOS Y "Ñ"
        boolean nombreValido = false;
        String nombre = "";
        do {
            System.out.println("Introduzca su nombre completo:");
            nombre = sc.nextLine();

            if (nombre.matches("[a-zA-ZÀ-ÿ\u00f1\u00d1]+"// Ññ Y VOCAL ACENTUADA
                    + "(\\s[a-zA-ZÀ-ÿ\u00f1\u00d1]+){2,}")) {
                nombreValido = true;
            } else {
                System.out.println(RED + "Introduzca nombre y dos apellidos");
            }
        } while (!nombreValido);
        return nombre;
    }

    public static Persona usarPersona() {
        Persona persona = null;
        String nif,
                nombre,
                cambiarNombre;
        boolean correcto = false;
        nif = pedirNif();
        if (BANK.nifRegistrado(nif) != null) {//PERSONA REGISTRADA EN BANCO
            do {
                System.out.println("Esta persona ya es cliente registrado.\n"
                        + "¿Quiere utilizar un nombre distinto(Y/N)?:");
                cambiarNombre = sc.nextLine();
                if (cambiarNombre.equalsIgnoreCase("Y")) {
                    nombre = pedirNombre();
                    correcto = true;
                    System.out.println("Persona creada con nuevo nombre.\n");
                    persona = BANK.crearPersona(nif, nombre);
                } else if (cambiarNombre.equalsIgnoreCase("N")) {
                    System.out.println("Continua con el cliente registrado.\n "
                            + "previamente.");
                    correcto = true;
                    persona = BANK.nifRegistrado(nif);
                } else {
                    System.out.println(RED + "Introduzca Y o N");
                }
            } while (!correcto);
        } else {
            nombre = pedirNombre();
            System.out.println("Persona registrada correctamente.\n");
            persona = BANK.crearPersona(nif, nombre);
        }
        return persona;
    }
}
