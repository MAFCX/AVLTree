/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avlteste;

/**
 *
 * @author MarcioAriani
 */
public class AvlTeste {

    class Node {

        int valor, altura;
        Node direita, esquerda;

        Node(int d) {
            valor = d;
            altura = 1;
        }
    }

    Node raiz;

    //Função responsável por obter o altura da arvore
    int altura(Node no) {
        if (no == null) {
            return 0;
        }
        return no.altura;
    }

    //Função responsável por obter o maior valor de dois
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    //Função responsável por fazer a rotação a direita da arvore
    Node rotacaoDireita(Node y) {
        Node x = y.esquerda;
        Node k = x.direita;

        //Realiza a rotação
        x.direita = y;
        y.esquerda = k;

        //Atualiza a altura da arvore
        y.altura = max(altura(y.esquerda), altura(y.direita) + 1);
        x.altura = max(altura(y.esquerda), altura(x.direita) + 1);

        //Retorna uma nova raiz
        return x;
    }

    //Função responsável por fazer a rotação a esquerda da arvore
    Node rotacaoEsquerda(Node x) {
        Node y = x.direita;
        Node k = y.esquerda;

        //Realiza a rotação
        y.esquerda = x;
        x.direita = k;

        //Atuliza a altura da arvore
        x.altura = max(altura(x.esquerda), altura(x.direita) + 1);
        x.altura = max(altura(y.esquerda), altura(y.direita) + 1);

        //Returna uma nova raiz
        return y;
    }

    //Obtem o balanceamento do nó passado como parametro
    int Balanco(Node no) {
        if (no == null) {
            return 0;
        }
        return altura(no.esquerda) - altura(no.direita);
    }

    //Realiza a inserção de um nó na arvore
    Node insercaoNo(Node node, int valor) {
        if (node == null) {
            return (new Node(valor));
        }

        if (valor < node.valor) {
            node.esquerda = insercaoNo(node.esquerda, valor);
        } else if (valor > node.valor) {
            node.direita = insercaoNo(node.direita, valor);
        } else {
            return node;
        }

        //Atualiza a altura dos nós ancetrais
        node.altura = 1 + max(altura(node.esquerda), altura(node.direita));

        //Verifica atraves de um balanceamento se o no ancestral esta desbalanceado
        int balance = Balanco(node);

        //Caso o nó verificado tenha se tornado desbalanceado
        //verifica quatro casos
        //Primeiro caso -> Rotação Simples a Direita
        if (balance > 1 && valor < node.esquerda.valor) {
            return rotacaoDireita(node);
        }

        //Segundo caso -> Rotação Simples a Esquerda
        if (balance < -1 && valor > node.direita.valor) {
            return rotacaoEsquerda(node);
        }

        //Terceiro caso -> Rotação Dupla a Direita
        if (balance > 1 && valor > node.esquerda.valor) {
            node.esquerda = rotacaoEsquerda(node.direita);
            return rotacaoDireita(node);
        }

        //Último caso -> Rotação Dupla a Esquerda
        if (balance < -1 && valor < node.direita.valor) {
            node.direita = rotacaoDireita(node.direita);
            return rotacaoEsquerda(node);
        }

        //Caso o nó não tenha sido alterado retorna o mesmo
        return node;
    }

    //Retorna o menor valor
    Node minValorNo(Node no) {
        Node noAtual = no;

        //Verifica até a parte de baixo da arvore 
        //para encontrar o menor valor da esquerda
        while (noAtual.esquerda != null) {
            noAtual = noAtual.esquerda;
        }
        return noAtual;
    }

    //Realiza a remoção de um nó da arvore
    Node remocaoNo(Node raiz, int valor) {
        if (raiz == null) {
            return raiz;
        }

        //Se o valor que será deletado for menor que 
        //o valor da raiz, então o valor esta na subarvore da esquerda
        if (valor < raiz.valor) {
            raiz.esquerda = remocaoNo(raiz.esquerda, valor);
        } //Se o valor que será deletado for maior que
        //o valor da raiz, então o valor esta na subarvore da direita
        else if (valor > raiz.valor) {
            raiz.direita = remocaoNo(raiz.direita, valor);
        } //Se o valor que será deletado é o mesmo da raiz, então 
        //o nó foi encontrado
        else {
            //Verifica se o nó tem somente um filho ou nenhum
            if ((raiz.esquerda == null) || (raiz.direita == null)) {
                Node tempo = null;
                if (tempo == raiz.esquerda) {
                    tempo = raiz.direita;
                } else {
                    tempo = raiz.esquerda;
                }

                //No caso de não ter nenhum filho
                if (tempo == null) {
                    tempo = raiz;
                    raiz = null;
                    //No caso de ter apenas um filho
                } else {
                    //Copia o conteudo do filho não vazio
                    raiz = tempo;
                }
            } else {
                //Nó com dois filhos - pega o nó sucessor em ordem, 
                //com o menor valor na subarvore da direita
                Node temp = minValorNo(raiz.direita);

                //Copia o nó sucessor de menor valor encontrado em ordem
                raiz.valor = temp.valor;

                //Realiza a remoção do nó encontrado
                raiz.direita = remocaoNo(raiz.direita, temp.valor);
            }
        }

        //Se a arvore tiver apenas um nó então retorna o mesmo
        if (raiz == null) {
            return raiz;
        }

        //Atualiza a altura do nó atual
        raiz.altura = max(altura(raiz.esquerda), altura(raiz.direita)) + 1;

        //Obtem o balanceamento do nó, verifica se o nó ficou
        //desbalanceado
        int balance = Balanco(raiz);

        //Se o nó não estiver balanceado, então verifica quatro casos
        //Primeiro caso -> Rotação Simples a Esquerda
        if (balance > 1 && Balanco(raiz.esquerda) >= 0) {
            return rotacaoDireita(raiz);
        }

        //Segundo caso -> Rotação Simples a Direita
        if (balance < -1 && Balanco(raiz.direita) <= 0) {
            return rotacaoEsquerda(raiz);
        }

        //Terceiro caso -> Rotação Dupla a Esquerda
        if (balance > 1 && Balanco(raiz.esquerda) < 0) {
            raiz.esquerda = rotacaoEsquerda(raiz.esquerda);
            return rotacaoDireita(raiz);
        }

        //Último caso -> Rotação Dupla a Direita
        if (balance < -1 && Balanco(raiz.direita) > 0) {
            raiz.direita = rotacaoDireita(raiz.direita);
            return rotacaoEsquerda(raiz);
        }

        return raiz;
    }

    //Imprimi a arvore em pre ordem
    void preOrder(Node no) {
        if (no != null) {
            System.out.println(no.valor + " ");
            preOrder(no.esquerda);
            preOrder(no.direita);
        }
    }

    //Imprimi a arvore em ordem
    void emOrdem(Node no) {
        if (no != null) {
            emOrdem(no.esquerda);
            System.out.println(no.valor + " ");
            emOrdem(no.direita);
        }
    }

    //Imprimi a arvore pos ordem
    void posOrdem(Node no) {
        if (no != null) {
            posOrdem(no.esquerda);
            posOrdem(no.direita);
            System.out.println(no.valor + " ");
        }
    }

    public static void main(String[] args) {
        AvlTeste arvore = new AvlTeste();

        arvore.raiz = arvore.insercaoNo(arvore.raiz, 9);
        arvore.raiz = arvore.insercaoNo(arvore.raiz, 5);
        arvore.raiz = arvore.insercaoNo(arvore.raiz, 10);
        arvore.raiz = arvore.insercaoNo(arvore.raiz, 0);
        arvore.raiz = arvore.insercaoNo(arvore.raiz, 6);
        arvore.raiz = arvore.insercaoNo(arvore.raiz, 11);
        arvore.raiz = arvore.insercaoNo(arvore.raiz, 1);
        arvore.raiz = arvore.insercaoNo(arvore.raiz, 2);

        System.out.println("Arvore AVL");

        //arvore.preOrder(arvore.raiz);
        //arvore.emOrdem(arvore.raiz);
        arvore.posOrdem(arvore.raiz);

        arvore.raiz = arvore.remocaoNo(arvore.raiz, 10);

        System.out.println("Arvore AVL após remoção");

        //arvore.preOrder(arvore.raiz);
        //arvore.emOrdem(arvore.raiz);
        arvore.posOrdem(arvore.raiz);
    }

}
