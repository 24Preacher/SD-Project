package Utilizador;

import java.util.Scanner;

public class Menu {
    private int opcao;
    private Scanner in;

    public Menu() {
        in = new Scanner(System.in);
        this.opcao = 0;
    }

    public int getOpcao() {
        return opcao;
    }

    public void setOpcao(int op) {
        this.opcao = op;
    }

    public void setIn(Scanner in) {
        this.in = in;
    }

    public void apresentarMenu() {
        switch (opcao) {
            case 0:
                System.out.println("************* MENU ****************\n" +
                        "* 1 - Iniciar Sessao              *\n" +
                        "* 2 - Registar Utilizador         *\n" +
                        "* 0 - Sair                        *\n" +
                        "***********************************\n");
                break;
        }
        System.out.println("Opção:");
    }

    public int lerOpcao() {
        int n;
        try {
            n = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("\n>Valor inválido\n");
            n = -1;
        }
        return n;
    }

    public Integer op() {
        int opcao = lerOpcao();
        if (opcao == 0) {
            while (opcao < 0 || opcao > 2) {
                System.out.println("Escolha uma opção: ");
                opcao = lerOpcao();
            }
        }
        return opcao;
    }

    public String lerString(String msg) {
        System.out.println(msg);
        return in.nextLine();
    }


}
