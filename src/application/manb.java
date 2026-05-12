package application;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class manb {
	private DLL<Letter> letters;
	private int totalWordCount;

	public manb() {
		letters = new DLL<>();
		for (char ch = 'A'; ch <= 'Z'; ch++) {
			letters.addLast(new Letter(ch));
		}
		totalWordCount = 0;
	}

	public boolean addword(NewWord word) {// logn
		if (word.getWord() == null || word.getWord().isEmpty()) {
			return false;
		}
		if (word.getInenglish() == null || word.getInenglish().isEmpty()) {
			return false;
		}
		if (word.getInArabic() == null || word.getInArabic().isEmpty()) {
			return false;
		}

		char ch = Character.toUpperCase(word.getWord().charAt(0));
		if (ch < 'A' || ch > 'Z') {
			return false;
		}

		int index = ch - 'A';
		Letter letterNode = letters.getIndex(index);
		AVL<NewWord> tree = letterNode.getTree();

		if (tree.search(word) != null) {
			return false;
		}

		tree.insert(word);
		totalWordCount++;
		return true;
	}

	public NewWord searchInEnglish(String word) {// logn
		if (word == null || word.isEmpty()) {
			return null;
		}

		char ch = Character.toUpperCase(word.charAt(0));
		if (ch < 'A' || ch > 'Z') {
			return null;
		}

		int index = ch - 'A';
		Letter letterNode = letters.getIndex(index);
		AVL<NewWord> tree = letterNode.getTree();

		NewWord key = new NewWord(word, "", "", "", "");
		return tree.search(key);
	}

	public boolean updateData(String englishWord, NewWord newData) {
		NewWord existingWord = searchInEnglish(englishWord);
		if (existingWord == null) {
			return false;
		}

		existingWord.setInenglish(newData.getInenglish());
		existingWord.setInArabic(newData.getInArabic());
		existingWord.setSentence(newData.getSentence());
		existingWord.setType(newData.getType());

		return true;
	}

	public NewWord searchArabic(String arabicWord) {
		if (arabicWord == null || arabicWord.isEmpty()) {
			return null;
		}

		for (int i = 0; i < 26; i++) {
			Letter letterNode = letters.getIndex(i);
			AVL<NewWord> tree = letterNode.getTree();
			BSTNode<NewWord> current = tree.getMinNode();

			while (current != null) {
				NewWord word = current.getElement();
				if (word.getInArabic().equals(arabicWord)) {
					return word;
				}
				current = tree.getInorderSuccessor(current);
			}
		}
		return null;
	}

	public void loadFromFile(File file) {
		if (file == null) {
			return;
		}

		try (Scanner sc = new Scanner(file, "UTF-8")) {
			int loaded = 0;
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (line.isEmpty()) {
					continue;
				}

				String[] parts = line.split(";");
				if (parts.length < 5) {
					continue;
				}

				NewWord word = new NewWord(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(),
						parts[4].trim());

				if (addword(word)) {
					loaded++;
				}
			}
			totalWordCount = loaded;
		} catch (Exception e) {
			System.err.println("Error loading file: " + e.getMessage());
		}
	}

	public void saveToFile(File file) {
		if (file == null) {
			return;
		}

		try (PrintWriter pw = new PrintWriter(file, "UTF-8")) {
			for (int i = 0; i < 26; i++) {
				Letter letterNode = letters.getIndex(i);
				AVL<NewWord> tree = letterNode.getTree();
				BSTNode<NewWord> current = tree.getMinNode();

				while (current != null) {
					pw.println(current.getElement().toString());
					current = tree.getInorderSuccessor(current);
				}
			}
		} catch (Exception e) {
			System.err.println("Error saving file: " + e.getMessage());
		}
	}

	public boolean deleteWord(String englishWord) {
		if (englishWord == null || englishWord.isEmpty()) {
			return false;
		}

		char ch = Character.toUpperCase(englishWord.charAt(0));
		if (ch < 'A' || ch > 'Z') {
			return false;
		}

		int index = ch - 'A';
		Letter letterNode = letters.getIndex(index);
		AVL<NewWord> tree = letterNode.getTree();

		NewWord key = new NewWord(englishWord, "", "", "", "");

		if (tree.search(key) == null) {
			return false;
		}

		tree.delete(key);
		totalWordCount--;
		return true;
	}

	public String EnglishToArabic(String text) {
		StringBuilder result = new StringBuilder();
		String[] words = text.split("\\s+");

		for (String word : words) {
			String cleanWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
			if (cleanWord.isEmpty()) {
				continue;
			}

			NewWord dictWord = searchInEnglish(cleanWord);
			if (dictWord != null) {
				result.append(dictWord.getInArabic()).append(" ");
			} else {
				result.append("[").append(word).append("?] ");
			}
		}

		return result.toString().trim();
	}

	public String ArabicToEnglish(String text) {
		StringBuilder result = new StringBuilder();
		String[] words = text.split("\\s+");

		for (String word : words) {
			NewWord dictWord = searchArabic(word);
			if (dictWord != null) {
				result.append(dictWord.getType()).append(" ");
			} else {
				result.append("[").append(word).append("?] ");
			}
		}

		return result.toString().trim();
	}

	public String Randomsentences(int count, String language) {//count
		StringBuilder result = new StringBuilder();
		Random random = new Random();

		int totalNouns = countWordsByType("noun");
		int totalVerbs = countWordsByType("verb");

		if (totalNouns < 2 || totalVerbs == 0) {
			return "Need at least two nouns and one verb to generate sentences.";
		}

		for (int i = 0; i < count; i++) {
			int subjectIndex = random.nextInt(totalNouns);
			int verbIndex = random.nextInt(totalVerbs);
			int objectIndex;

			do {
				objectIndex = random.nextInt(totalNouns);
			} while (objectIndex == subjectIndex);

			NewWord subject = getWordByTypeAndIndex("noun", subjectIndex);
			NewWord verb = getWordByTypeAndIndex("verb", verbIndex);
			NewWord object = getWordByTypeAndIndex("noun", objectIndex);

			if (subject != null && verb != null && object != null) {
				if (language.equalsIgnoreCase("English")) {
					result.append((i + 1)).append(". ").append(subject.getWord()).append(" ").append(verb.getWord())
							.append(" ").append(object.getWord());
				} else {
					result.append((i + 1)).append(". ").append(verb.getInArabic()).append(" ")
							.append(subject.getInArabic()).append(" ").append(object.getInArabic());
				}
				result.append("\n");
			}
		}

		return result.toString();
	}

	private int countWordsByType(String targetType) {
		int count = 0;

		for (int i = 0; i < 26; i++) {
			Letter letterNode = letters.getIndex(i);
			AVL<NewWord> tree = letterNode.getTree();
			BSTNode<NewWord> current = tree.getMinNode();

			while (current != null) {
				String type = current.getElement().getType().toLowerCase();
				if (type.contains(targetType)) {
					count++;
				}
				current = tree.getInorderSuccessor(current);
			}
		}

		return count;
	}

	private NewWord getWordByTypeAndIndex(String targetType, int targetIndex) {
		int currentIndex = 0;

		for (int i = 0; i < 26; i++) {
			Letter letterNode = letters.getIndex(i);
			AVL<NewWord> tree = letterNode.getTree();
			BSTNode<NewWord> current = tree.getMinNode();

			while (current != null) {
				NewWord word = current.getElement();
				String type = word.getType().toLowerCase();

				if (type.contains(targetType)) {
					if (currentIndex == targetIndex) {
						return word;
					}
					currentIndex++;
				}
				current = tree.getInorderSuccessor(current);
			}
		}

		return null; // لا يجب أن يحدث
	}

	public String countWordsbytheLetter() {
		StringBuilder sb = new StringBuilder();
		sb.append("WORDS PER LETTER\n");
		sb.append("====================\n");

		for (int i = 0; i < 26; i++) {
			Letter letterNode = letters.getIndex(i);
			AVL<NewWord> tree = letterNode.getTree();

			int count = 0;
			BSTNode<NewWord> current = tree.getMinNode();
			while (current != null) {
				count++;
				current = tree.getInorderSuccessor(current);
			}

			sb.append(String.format("Letter %c: %3d words\n", letterNode.getLetter(), count));
		}
		sb.append("====================\n");
		sb.append(String.format("Total: %d words\n", totalWordCount));

		return sb.toString();
	}

	public String countWordbyTypes() {
		int noun = 0, verb = 0, adjective = 0, other = 0;

		for (int i = 0; i < 26; i++) {
			Letter letterNode = letters.getIndex(i);
			AVL<NewWord> tree = letterNode.getTree();
			BSTNode<NewWord> current = tree.getMinNode();

			while (current != null) {
				NewWord word = current.getElement();
				String type = word.getType().toLowerCase();

				if (type.contains("noun"))
					noun++;
				else if (type.contains("verb"))
					verb++;
				else if (type.contains("adjective"))
					adjective++;
				else
					other++;

				current = tree.getInorderSuccessor(current);
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append("WORD TYPES DISTRIBUTION\n");
		sb.append("==========================\n");
		sb.append(String.format("Nouns:        %3d\n", noun));
		sb.append(String.format("Verbs:        %3d\n", verb));
		sb.append(String.format("Adjectives:   %3d\n", adjective));
		sb.append(String.format("Others:       %3d\n", other));
		sb.append("==========================\n");
		sb.append(String.format("Total:        %3d\n", noun + verb + adjective + other));

		return sb.toString();
	}

	public String getTreeHeights() {
		StringBuilder sb = new StringBuilder();
		sb.append("AVL TREE HEIGHTS\n");
		sb.append("===================\n");

		for (int i = 0; i < 26; i++) {
			Letter letterNode = letters.getIndex(i);
			AVL<NewWord> tree = letterNode.getTree();
			int height = tree.getHeight();

			sb.append(String.format("Tree %c: Height = %d\n", letterNode.getLetter(), height));
		}

		return sb.toString();
	}

	public String printWordsForoneLetter(char letter) {
		letter = Character.toUpperCase(letter);
		if (letter < 'A' || letter > 'Z')
			return "Invalid letter.";

		int index = letter - 'A';
		Letter letterNode = letters.getIndex(index);
		AVL<NewWord> tree = letterNode.getTree();

		BSTNode<NewWord> current = tree.getMinNode();
		if (current == null) {
			return "No words for letter " + letter;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("WORDS FOR LETTER ").append(letter).append("\n");
		sb.append("================================\n");

		while (current != null) {
			NewWord word = current.getElement();
			sb.append(String.format("%-15s | %-20s | %-10s\n", word.getWord(), word.getInenglish(), word.getType()));

			current = tree.getInorderSuccessor(current);
		}

		return sb.toString();
	}

	public String printAllWords() {
		StringBuilder sb = new StringBuilder();
		sb.append("FULL DICTIONARY (A-Z)\n");
		sb.append("==========================\n\n");

		for (int i = 0; i < 26; i++) {
			Letter letterNode = letters.getIndex(i);
			char letter = letterNode.getLetter();
			AVL<NewWord> tree = letterNode.getTree();

			BSTNode<NewWord> current = tree.getMinNode();
			if (current == null) {
				continue;
			}

			sb.append("[").append(letter).append("]\n");
			sb.append("--------------------------------------------------\n");

			while (current != null) {
				NewWord word = current.getElement();
				sb.append(String.format("%-15s | EN: %-25s | AR: %s\n", word.getWord(), word.getInenglish(),
						word.getInArabic()));

				current = tree.getInorderSuccessor(current);
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	public int getTotalWordCount() {
		return totalWordCount;
	}
}