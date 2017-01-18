package controller;

public class AvlNo {
	protected int altura;
	protected int key;
	protected AvlNo esq, dir, pai;

	public AvlNo(int Key) {
		this(Key, null, null);
	}

	public AvlNo() {
	}
	
	public AvlNo(int key, AvlNo es, AvlNo di) {
		this.key = key;
		esq = es;
		dir = di;
		altura = 0;
	}

}
