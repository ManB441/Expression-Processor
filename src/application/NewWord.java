package application;

public class NewWord implements Comparable<NewWord> {
	private String word;
	private String inArabic;
	private String inenglish;
	private String sentence;
	private String type;

	public NewWord(String word, String inenglish, String inArabic, String sentence, String type) {

		this.word = word;
		this.inArabic = inArabic;
		this.inenglish = inenglish;
		this.sentence = sentence;
		this.type = type;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getInArabic() {
		return inArabic;
	}

	public void setInArabic(String inArabic) {
		this.inArabic = inArabic;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInenglish() {
		return inenglish;
	}

	public void setInenglish(String inenglish) {
		this.inenglish = inenglish;
	}

	@Override
	public String toString() {
		return word + ";" + inenglish + ";" + inArabic + ";" + sentence + ";" + type;
	}

	@Override
	public int compareTo(NewWord Other) {

		return this.word.compareToIgnoreCase(Other.word);
	}

}
