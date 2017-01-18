package controller;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class Label extends JFrame {

	static JTextArea texto;
	static AvlArvore avl;
	private JButton b, b1, b2, b3;
	static String[] lines = null;

	public static void mostrar() {
		if (avl.isEmpty()) {
			System.out.println("Árvore vazia!");
			return;
		}
		texto.append(avl.raiz.key + "   (raiz " + "FB: " + avl.getFator(avl.raiz) + ")" + "\n");
		String separator = String.valueOf("  |____");
		mostrarSubArvore(avl.raiz.esq, separator, texto);
		mostrarSubArvore(avl.raiz.dir, separator, texto);
	}

	public static void mostrarSubArvore(AvlNo No, String separator, JTextArea area) {
		if (No != null) {
			AvlNo pai = avl.procurarpai(No.key);
			if (No.equals(pai.esq) == true) {
				area.append(
						separator + No.key + "   (" + "pai: " + pai.key + " - FB: " + avl.getFator(No) + ")" + "\n");
			} else {
				area.append(
						separator + No.key + "   (" + "pai: " + pai.key + " - FB: " + avl.getFator(No) + ")" + "\n");
			}
			mostrarSubArvore(No.esq, "     " + separator, area);
			mostrarSubArvore(No.dir, "     " + separator, area);
		}
	}

	public Label() {
		super("                                                       Arvore AVL");
		Container c = getContentPane();
		Container c2 = new JPanel();
		b = new JButton("Escolher Arquivo");
		b1 = new JButton("Adicionar Manualmente");
		b2 = new JButton("Excluir");
		b3 = new JButton("Pesquisar");

		b.addActionListener(new AdicionarArquivo());
		b1.addActionListener(new AdicionarManual());
		b2.addActionListener(new Excluir());
		b3.addActionListener(new Pesquisar());

		texto = new JTextArea();
		texto.setEditable(false);

		c2.setLayout(new GridLayout(4, 1));
		c2.add(b);
		c2.add(b1);
		c2.add(b2);
		c2.add(b3);

		c.add(BorderLayout.EAST, c2);
		Font font = new Font("serif", Font.CENTER_BASELINE, 16);
		texto.setFont(font);
		c.add(BorderLayout.CENTER, texto);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Label();
		avl = new AvlArvore();
	}

	class AdicionarArquivo implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			LookAndFeel originalLaf = UIManager.getLookAndFeel();
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				JFileChooser chooser = new JFileChooser();
				UIManager.setLookAndFeel(originalLaf);
				chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				try {
					lines = RetornaNumeros(file.getAbsolutePath());
					for (String s : lines) {
						int x = Integer.parseInt(s);
						avl.inserir(x);
					}
					texto.setText("");
					Label.mostrar();
				} catch (Exception erro) {
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
		}

	}

	class AdicionarManual implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				String line = JOptionPane
						.showInputDialog("Digite os elementos que deseja inserir separados por ';' e um '.' no final");
				String[] lines2 = null;
				lines2 = line.split("[;.]");
				for (String s : lines2) {
					int x = Integer.parseInt(s);
					avl.inserir(x);
				}
				texto.setText("");
				Label.mostrar();
			} catch (Exception erro) {
			}
		}
	}

	class Excluir implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {

				String noS = JOptionPane.showInputDialog("Digite o elemento que deseja remover");
				int no = Integer.parseInt(noS);
				if (avl.procurar(no) != null) {
					JOptionPane.showMessageDialog(null, "Elemento: " + no, "Elemento Removido",
							JOptionPane.INFORMATION_MESSAGE);
					avl.remove(no);
					texto.setText("");
					Label.mostrar();
				} else {
					JOptionPane.showMessageDialog(null, "Elemento não encontrado", "Not Found",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception erro) {
			}

		}

	}

	class Pesquisar implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				AvlNo x = new AvlNo();
				AvlNo x1 = new AvlNo();
				String noS = JOptionPane.showInputDialog("Digite o elemento que deseja encontrar");
				int no = Integer.parseInt(noS);
				x = avl.procurar(no);
				x1 = avl.procurarpai(no);
				if (x != avl.raiz) {
					String s;
					String s1;
					if (x.esq == null)
						s = "";
					else
						s = x.esq.key + " FB" + avl.getFator(x.esq);
					if (x.dir == null)
						s1 = "";
					else
						s1 = x.dir.key + "FB" + avl.getFator(x.dir) + "\n";
					JOptionPane.showMessageDialog(null,
							"Elemento: " + x.key + " FB" + " " + avl.getFator(x) + "\n" + " Pai: " + x1.key + " FB"
									+ " " + avl.getFator(x1) + "\n" + " Filhos: " + s + "\n" + "              " + s1,
							"Elemento Encontrado", JOptionPane.INFORMATION_MESSAGE);
				} else if (x == avl.raiz) {
					String s;
					String s1;
					if (x.esq == null)
						s = "";
					else
						s = x.esq.key + " FB" + avl.getFator(x.esq);
					if (x.dir == null)
						s1 = "";
					else
						s1 = x.dir.key + " FB" + avl.getFator(x.dir) + "\n";
					JOptionPane
							.showMessageDialog(null,
									"Elemento: " + x.key + " FB" + " " + avl.getFator(x) + "\n" + " Filhos: " + s + "\n"
											+ "              " + s1,
									"Elemento Encontrado", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Elemento não encontrado", "Not Found",
							JOptionPane.ERROR_MESSAGE);
				}

			} catch (Exception erro) {
			}
		}
	}

	public String[] RetornaNumeros(String caminho) {
		Path path = Paths.get(caminho);
		Charset utf8 = StandardCharsets.UTF_8;
		try (BufferedReader r = Files.newBufferedReader(path, utf8)) {
			String line = null;
			String[] lines = null;
			while ((line = r.readLine()) != null) {
				lines = line.split("[;.] *| +");
			}
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
