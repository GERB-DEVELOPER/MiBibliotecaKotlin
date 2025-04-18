package data.viewmodel.ui.components.screens;

import java.util.Scanner;

public class Test {

    private int contador;

    public Test() {
        this.contador = 0;
    }

    public void incrementar() {
        contador++;
        System.out.println("Contador incrementado: " + contador);
    }

    public void decrementar() {
        contador--;
        System.out.println("Contador decrementado: " + contador);
    }

    public void resetear() {
        contador = 0;
        System.out.println("Contador reseteado.");
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Incrementar");
            System.out.println("2. Decrementar");
            System.out.println("3. Resetear");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> incrementar();
                case 2 -> decrementar();
                case 3 -> resetear();
                case 4 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 4);
    }

    public static void main(String[] args) {
        Test app = new Test();
        app.mostrarMenu();
    }
}
