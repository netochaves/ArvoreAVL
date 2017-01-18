
package controller;

public class AvlArvore {
	public AvlNo raiz;
	/* construtor */
	public AvlArvore() {
		raiz = null;
	}
	/**
	 * Função para verificar se a arvore está vazia;
	 */
	public boolean isEmpty() {
		return raiz == null;
	}

	/**
	 * Função que retorna a altura da arvore
	 * @param atual: O no da arvore que vai ser verificado a altura
	 */
	private static int altura(AvlNo atual) {
		if (atual == null) {
			return -1;
		}

		if (atual.esq == null && atual.dir == null) {
			return 0;

		} else if (atual.esq == null) {
			atual.altura = 1 + altura(atual.dir);
			return atual.altura;
		} else if (atual.dir == null) {
			atual.altura = 1 + altura(atual.esq);
			return atual.altura;
		} else {
			atual.altura = 1 + max(altura(atual.esq), altura(atual.dir));
			return atual.altura;
		}

	}

	/**
	 * Função que retorna o maior valor entre dois inteiros
	 * @param es: valor esquerdo
	 * @param di: valor direito
	 * @return o maior valor
	 */
	private static int max(int es, int di) {
		return es > di ? es : di;
	}

	/** 
	 * Função quer verifica o fator de balanceamento de um no
	 * @param o no a ser verificado
	 * @return o fator de balanceamento do no
	 */
	public int getFator(AvlNo t) {
		return altura(t.esq) - altura(t.dir);
	}
	/**
	 * Função para fazer a chamada da função inserir a partir de um inteiro
	 * @param x: o valor a ser inserido 
	 */
	public boolean inserir(int x) {
		raiz = inserir(x, raiz);
		return true;
	}
	/**
	 * Função para inserir um no na arvore
	 * @param x: valor do no
	 * @param inserir: raiz da arvore
	 * @return raiz da arvore balanceada
	 */
	public AvlNo inserir(int x, AvlNo inserir) {
		if (inserir == null)
			inserir = new AvlNo(x);
		else if (x < inserir.key)
			inserir.esq = inserir(x, inserir.esq);
		else if (x > inserir.key)
			inserir.dir = inserir(x, inserir.dir);
		inserir = balancear(inserir);
		return inserir;
	}
	/**
	 * Função que balanceiar a arvore a partir de um no
	 * @param t: no a ser balanceado
	 * @return o no balanceado
	 */
	public AvlNo balancear(AvlNo t) {
		if (getFator(t) == 2) {
			if (getFator(t.esq) > 0)
				t = RotacaoDir(t);
			else
				t = RotacaoDuplaDir(t);
		} else if (getFator(t) == -2) {
			if (getFator(t.dir) < 0)
				t = RotacaoEsq(t);
			else
				t = RotacaoDuplaEsq(t);
		}
		t.altura = max(altura(t.esq), altura(t.dir)) + 1;
		return t;
	}

	/**
	 * Função que faz uma rotação simples para a direita
	 * @param x2: no na ser rotacionado 
	 */
	private static AvlNo RotacaoDir(AvlNo no) {
		AvlNo no1 = no.esq;
		no.esq = no1.dir;
		no1.dir = no;
		no.altura = max(altura(no.esq), altura(no.dir)) + 1;
		no1.altura = max(altura(no1.esq), altura(no1.dir)) + 1;
		return no1;
	}

	/** 
	 * Função que faz uma rotação simples para a esquerda
	 * @param x1: no a ser rotacionado  
	*/
	private static AvlNo RotacaoEsq(AvlNo no) {
		AvlNo no1 = no.dir;
		no.dir = no1.esq;
		no1.esq = no;
		no.altura = max(altura(no.esq), altura(no.dir)) + 1;
		no1.altura = max(altura(no1.dir), no.altura) + 1;
		return no1;
	}

	/** Função que faz uma rotação dupla para a direita 
	 * 	@param x3: no a ser rotacionado
	 * */
	private static AvlNo RotacaoDuplaDir(AvlNo no) {
		no.esq = RotacaoEsq(no.esq);
		return RotacaoDir(no);
	}

	/** Função que faz uma rotação dupla para a esquerda 
	 * 	@param x1: no a ser rotacionado
	 * */
	private static AvlNo RotacaoDuplaEsq(AvlNo no) {
		no.dir = RotacaoDir(no.dir);
		return RotacaoEsq(no);
	}
	/* chama a função procurar */
	public AvlNo procurar(int el) {
		return procurar(raiz, el);
	}
	/** 
	 * Função que procura um no na arvore
	 * @param p: no a ser procurado
	 * @param x: valor do no
	 * @return null
	 */
	protected AvlNo procurar(AvlNo p, int x) {
		while (p != null) {
			if (x == p.key)
				return p;
			else if (x < p.key)
				p = p.esq;
			else
				p = p.dir;
		}
		return null;
	}
	/* chama a função remove */
	public boolean remove(int x) {
		raiz = remove(raiz, x);
		return true;
	}
	/** Procura a folha mais a esquerda da arvore
	 * @param no:raiz
	 *  */
	public AvlNo ValorMin(AvlNo no) {
		AvlNo t = no;
		while (t.esq != null)
			t = t.esq;
		return t;
	}
	/** 
	 * Função que remove um no da arvore
	 * @param raiz: raiz da arvore
	 * @param key: valor do no a ser removido
	 * @return retorna a raiz balanceada
	 * */
	public AvlNo remove(AvlNo raiz, int key) {
		if (raiz == null)
			return raiz;
		//pecorre a arvore ate achar o no
		if (key < raiz.key)
			raiz.esq = remove(raiz.esq, key);
		else if (key > raiz.key)
			raiz.dir = remove(raiz.dir, key);
		//Achou!!!!
		else {
			//O no temum filho ou nenhum filho
			if ((raiz.esq == null) || (raiz.dir == null)) {
				AvlNo aux = null;
				if (aux == raiz.esq)
					aux = raiz.dir;
				else
					aux = raiz.esq;
				//O no tem um filho
				if (aux == null) {
					aux = raiz;
					raiz = null;
				}
				//o no não tem filho
				else
					raiz = aux;

			} else {
				//O no tem dois filhos
				//Guarda o menor valor da subarvore direita
				AvlNo aux = ValorMin(raiz.dir);
				//copia o valor
				raiz.key = aux.key;
				//Remove o no
				raiz.dir = remove(raiz.dir, aux.key);
			}
		}
		if (raiz == null)
			return raiz;

		raiz.altura = max(altura(raiz.esq), altura(raiz.dir)) + 1;
		//Faz o balanceamento do no
		int fb = getFator(raiz);
		if (fb > 1 && getFator(raiz.esq) >= 0)
			return RotacaoDir(raiz);

		if (fb > 1 && getFator(raiz.esq) < 0) {
			return RotacaoDuplaEsq(raiz);
		}

		if (fb < -1 && getFator(raiz.dir) <= 0)
			return RotacaoEsq(raiz);

		if (fb < -1 && getFator(raiz.dir) > 0) {
			return RotacaoDuplaDir(raiz);
		}
		raiz = balancear(raiz);
		return raiz;
	}
	/**
	 * Função que procura o pai de um no na arvore
	 * @param x: valor do no a ser procurado
	 * @return null
	 */
	protected AvlNo procurarpai(int x) {
		AvlNo p = raiz;
		AvlNo prev = null;
		while (p != null && !(p.key == x)) { // acha o nó p com a chave x
			prev = p;
			if (p.key < x)
				p = p.dir;
			else
				p = p.esq;
		}
		if (p != null && p.key == x)
			return prev;
		return null;
	}
}