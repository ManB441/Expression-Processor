package application;

public class Letter {
	private char letter;
	private AVL<NewWord> tree;

	public Letter(char letter) {

		this.letter = letter;
		this.tree = new AVL <>() ;
	}

	public char getLetter() {
		return letter;
	}

	public void setLetter(char latter) {
		this.letter = latter;
	}

	public AVL<NewWord> getTree() {
		return tree;
	}

	
	@Override
	public String toString() {
		return "Latter [latter=" + letter + ", tree=" + tree + "]";
	}

}
