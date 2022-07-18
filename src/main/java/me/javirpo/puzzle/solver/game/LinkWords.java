package me.javirpo.puzzle.solver.game;

import lombok.Setter;
import me.javirpo.puzzle.solver.Unscrambleletters;
import me.javirpo.puzzle.solver.helper.BoardIterator;
import me.javirpo.puzzle.solver.helper.Line;
import me.javirpo.puzzle.solver.helper.Point;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LinkWords extends Game {
	private String originalLetters;
	private String letters;
	private Map<Integer, Collection<String>> wordsPerSize = new HashMap<>();
	private Map<Integer, Set<Line>> linesPerSize = new TreeMap<>();
	private Set<Line> lines = new HashSet<>();

	@Override
	protected void run() throws IOException {
		createBoard();
		readRows();
		readMissingLetters();

		String opt;
		while (!isDone()) {
			printBoard();
			System.out.println();
			System.out.println("-1. Solve");
			System.out.println("-2. Put");
			System.out.print("Write: ");
			opt = sc.nextLine();
			switch (opt) {
			case "-1":
				solve();
				break;
			case "-2":
				readHelp();
				break;
			}
		}
		System.out.println("Finish ...");
		System.out.println();
		printBoard();
	}

	private void readMissingLetters() {
		int lettersCount = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if(boardNumbers[i][j] == 1) {
					lettersCount++;
				}
			}
		}

		System.out.println();
		while (true) {
			System.out.print("Reading Missing Letters: ");

			String line = sc.nextLine();
			if (line.length() == lettersCount) {
				letters = line;
				originalLetters = line;
				break;
			}
			System.out.println("Letters length '"+ line + "'-'" + line.length() + "' doesn't match with letters count '" + lettersCount + "'");
		}
	}

	private void readHelp() {
		System.out.println();
		System.out.print("Row,Col,Letter: ");

		String[] line = sc.nextLine().split(",");
		int row = Integer.parseInt(line[0]);
		int col = Integer.parseInt(line[1]);
		char newChar = line[2].charAt(0);
		putLetter(row, col, newChar);
	}

	private void solve() throws IOException {
		boolean checkAgain = true;
		while (checkAgain) {
			checkAgain = solveIterator();
		}
		if(!isDone()) {
			forceSolve();
		}
	}

	private boolean solveIterator() throws IOException {
		boolean checkAgain = false;

		BoardIterator iterator = new BoardIterator(boardNumbers, boardLetters, rows, cols);
		while (iterator.hasNext()) {
			Line line = iterator.next();
			lines.add(line);

			int size = line.length() + 1;
			StringBuilder pattern = new StringBuilder();
			StringBuilder letterSearch = new StringBuilder(letters);

			String initialPattern = Optional.of(letters)
					.map(String::toCharArray)
					.map(ArrayUtils::toObject)
					.map(Arrays::asList)
					.map(HashSet::new)
					.map(set -> StringUtils.join(set, ""))
					.orElse("");

			for (Point p : line) {
				checkLetter(p.getRow(), p.getCol(), initialPattern, pattern, letterSearch);
			}

			checkAgain |= search(line, size, pattern, letterSearch);

			printBoard();
		}

		return checkAgain;
	}

	private void checkLetter(int row, int col, String initialPattern, StringBuilder pattern, StringBuilder letterSearch) {
		if (boardLetters[row][col] == ' ') {
			pattern.append('[');
			pattern.append(initialPattern);
			pattern.append(']');
		} else {
			pattern.append(boardLetters[row][col]);
			letterSearch.append(boardLetters[row][col]);
		}
	}

	private boolean search(Line line, int size, StringBuilder pattern, StringBuilder letterSearch) throws IOException {
		boolean checkAgain = false;

		Collection<String> words;
		Collection<String> previousWords = wordsPerSize.get(line.length());
		if (previousWords.isEmpty()) {
			Map<Integer, Collection<String>> map = Unscrambleletters.getWordsWithCache(letterSearch.toString());
			words = Unscrambleletters.getAndFilter(map, size, pattern.toString());
		} else {
			words = previousWords;
		}

		if (words.size() == 1) {
			checkAgain = solveLetters(line, words.iterator().next());
		} else if (words.size() > 1) {
			checkAgain = lookForSimilarities(line, size, words);
		}
		wordsPerSize.put(line.length(), words);
		linesPerSize.compute(line.length(), (k, v) -> {
			if (v == null) {
				v = new HashSet<>();
			}
			v.add(line);
			return v;
		});
		return checkAgain;
	}

	private boolean lookForSimilarities(Line line, int size, Collection<String> words) {
		boolean checkAgain = false;
		StringBuilder sb = new StringBuilder(size);

		String firstWord = words.iterator().next();
		for (int i = 0; i < firstWord.length(); i++) {
			char c = firstWord.charAt(i);
			boolean same = true;
			for (String word : words) {
				if (c != word.charAt(i)) {
					same = false;
					break;
				}
			}
			if (same) {
				sb.append(c);
				checkAgain = true;
			} else {
				sb.append(' ');
			}
		}

		if (checkAgain) {
			checkAgain = solveLetters(line, sb.toString());
		}

		return checkAgain;
	}

	private boolean solveLetters(Line line, String word) {
		boolean checkAgain = false;
		int k = 0;
		for (Point p : line) {
			char c = word.charAt(k);
			int row = p.getRow();
			int col = p.getCol();
			if (c != ' ' && boardLetters[row][col] == ' ') {
				putLetter(row, col, c);

				checkAgain = true;
			}
			k++;
		}

		return checkAgain;
	}

	private void putLetter(int row, int col, char c) {
		boardLetters[row][col] = c;

		letters = letters.replaceFirst("" + c, "");
	}

	private void forceSolve() {
		Set<String> wordsToDelete = new HashSet<>();
		Map<Integer, Collection<String>> possibilities = new HashMap<>(wordsPerSize.size());

		while (true) {
			String newLetters = originalLetters;
			Map<Integer, Collection<String>> clonedWordsPerSize = cloneWords(wordsPerSize);
			removeWords(wordsToDelete, clonedWordsPerSize);
			if (clonedWordsPerSize.isEmpty()) {
				break;
			}

			String firstWord = null;
			for (Line line : lines) {
			}
		}
	}

	private Map<Integer, Collection<String>> cloneWords(Map<Integer, Collection<String>> wordsPerSize) {
		Map<Integer, Collection<String>> clone = new HashMap<>(wordsPerSize.size());
		for (Map.Entry<Integer, Collection<String>> entry : wordsPerSize.entrySet()) {
			clone.put(entry.getKey(), new ArrayList<>(entry.getValue()));
		}
		return clone;
	}

	private void removeWords(Set<String> wordsToDelete, Map<Integer, Collection<String>> clonedWordsPerSize) {
		for (String word : wordsToDelete) {
			clonedWordsPerSize.computeIfPresent(word.length(), (k, v) -> {
				v.remove(word);
				return v.isEmpty() ? null : v;
			});
		}
	}

	public static void main(String[] args) throws IOException {
		new LinkWords()._run();
	}
}
