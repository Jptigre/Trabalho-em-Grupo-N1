//ALUNOS : JÚLIO CÉZAR MENEZES DOS SANTOS,KAIO LEVI SILVA DE SOUZA,JOÃO PEDRO RODRIGUES PARPINELLI, 

import java.util.Scanner;
import java.util.Random;

abstract class Entidade {
    protected String nome;
    protected int vida;
    protected int vidaMaxima;
    protected int ataque;
    protected int defesa;
    protected int nivel;

    public Entidade(String nome, int vida, int ataque, int defesa) {
        this.nome = nome;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = 1;
    }

    public int calcularDano(Random random) {
        return this.ataque + random.nextInt(10);
    }

    public void receberDano(int dano) {
        int danoFinal = Math.max(1, dano - (this.defesa / 3));
        this.vida -= danoFinal;
        if (this.vida < 0) this.vida = 0;
        System.out.println(this.nome + " sofreu " + danoFinal + " de dano. Vida: " + this.vida);
    }

    public boolean estaVivo() {
        return this.vida > 0;
    }
}
class Jogador extends Entidade {
    private int experiencia;

    public Jogador(String nome) {
        super(nome, 150, 20, 15);
        this.experiencia = 0;
    }

    public void ganharExperiencia(int pontos) {
        this.experiencia += pontos;
        System.out.println("Voce ganhou pontos de consciencia ambiental!");
        if (this.experiencia >= 100) {
            subirDeNivel();
        }
    }

    private void subirDeNivel() {
        this.nivel++;
        this.experiencia = 0;
        this.vidaMaxima += 30;
        this.vida = vidaMaxima;
        this.ataque += 12;
        this.defesa += 8;
        System.out.println("\n--- EVOLUCAO: Voce agora e Nivel " + this.nivel + " ---");
    }
}

// 3. Classe Inimigo
class Inimigo extends Entidade {
    private String recompensa;

    public Inimigo(String nome, int vida, int ataque, int defesa, String recompensa) {
        super(nome, vida, ataque, defesa);
        this.recompensa = recompensa;
    }

    public String getRecompensa() {
        return recompensa;
    }
}

public class JogoRPG {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("--- ECO-RPG: A ULTIMA CHANCE ---");
        System.out.print("Nome do Ativista: ");
        String nomeHeroi = scanner.nextLine();
        
        Jogador jogador = new Jogador(nomeHeroi);

        Inimigo[] jornada = {
            new Inimigo("Queimadas na Amazonia", 70, 15, 5, "Mudas de Arvores Nativas"),
            new Inimigo("Poluicao dos Oceanos", 90, 18, 10, "Tecnologia de Filtragem"),
            new Inimigo("Lideres Globais", 180, 25, 20, "Tratado de Sustentabilidade Real")
        };

        for (int i = 0; i < jornada.length; i++) {
            if (jogador.estaVivo()) {
                if (i == jornada.length - 1) {
                    System.out.println("\n--- ALERTA: CONFRONTO FINAL COM OS LIDERES GLOBAIS ---");
                }
                
                realizarCombate(jogador, jornada[i], scanner, random);
                
                if (jogador.estaVivo()) {
                    System.out.println("\nSucesso! Voce obteve: " + jornada[i].getRecompensa());
                    jogador.ganharExperiencia(100);
                }
            }
        }

        if (jogador.estaVivo()) {
            System.out.println("\nParabens! O planeta tem uma nova chance gracas a voce.");
        } else {
            System.out.println("\nFim de jogo. O ecossistema entrou em colapso.");
        }
    }

    public static void realizarCombate(Jogador p, Inimigo i, Scanner s, Random r) {
        System.out.println("\nIniciando combate contra: " + i.nome);

        while (p.estaVivo() && i.estaVivo()) {
            System.out.println("\n(1) Atacar | (2) Recuperar Energia");
            int escolha = s.nextInt();

            if (escolha == 1) {
                i.receberDano(p.calcularDano(r));
            } else {
                p.vida = Math.min(p.vidaMaxima, p.vida + 25);
                System.out.println("Voce descansou e recuperou vida.");
            }

            if (i.estaVivo()) {
                p.receberDano(i.calcularDano(r));
            }
        }
    }
}