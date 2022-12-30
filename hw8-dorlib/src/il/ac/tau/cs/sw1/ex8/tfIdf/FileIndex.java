package il.ac.tau.cs.sw1.ex8.tfIdf;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.IHistogram;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.*;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	private boolean isInitialized = false;
	
	//Add members here
	Set<String> words;
	HashMap<String, IHistogram<String>> filesMap;


	public FileIndex() {
		words = new HashSet<String>();
		filesMap = new HashMap<String, IHistogram<String>>();
	}

	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 * @pre: isInitialized() == false;
	 */
  	public void indexDirectory(String folderPath) { //Q1
		//This code iterates over all the files in the folder. add your code wherever is needed

		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		for (File file : listFiles) {
			// for every file in the folder
			if (file.isFile()) {
				/*******************/
				// add your code here
				/*******************/
				IHistogram<String> histogram = new HashMapHistogram<>();
				try {
					List<String> words = FileUtils.readAllTokens(file);
					for (String word : words) {
						histogram.addItem(word);
						this.words.add(word);
					}
				} catch (IOException err) {
					System.out.printf("IO exception: %s", err);
				} catch (IOError err) {
					System.out.printf("IO Error: %s", err);
				}

				filesMap.put(file.getName(), histogram);
			}
		}
		/*******************/
		// add your code here
		/*******************/

		isInitialized = true;
	}
	
	
	
	// Q2
  	
	/* @pre: isInitialized() */
	public int getCountInFile(String word, String fileName) throws FileIndexException{
		IHistogram<String> histogram = filesMap.get(fileName);
		if (histogram == null) {
			throw new FileIndexException("file not found");
		}

		int res = histogram.getCountForItem(word.toLowerCase());
		return res;
	}
	
	/* @pre: isInitialized() */
	public int getNumOfUniqueWordsInFile(String fileName) throws FileIndexException{
		IHistogram<String> histogram = filesMap.get(fileName);
		if (histogram == null) {
			throw new FileIndexException("file not found");
		}

		return histogram.getItemsSet().size();
	}
	
	/* @pre: isInitialized() */
	public int getNumOfFilesInIndex(){
		return filesMap.size();
	}

	
	/* @pre: isInitialized() */
	public double getTF(String word, String fileName) throws FileIndexException{ // Q3
		IHistogram<String> histogram = this.filesMap.get(fileName);
		if (histogram == null) {
			throw new FileIndexException("file not found");
		}

		int numOfRep = histogram.getCountForItem(word.toLowerCase());
		int numOfWords = histogram.getCountsSum();

		return calcTF(numOfRep , numOfWords);
	}
	
	/* @pre: isInitialized() 
	 * @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getIDF(String word){ //Q4
		int numOfDocs = this.filesMap.size();
		int numOfDocsContainingWord = 0;

		for (IHistogram<String> histogram : this.filesMap.values()) {
			if (histogram.getCountForItem(word.toLowerCase()) > 0) {
				numOfDocsContainingWord++;
			}
		}

		return calcIDF(numOfDocs, numOfDocsContainingWord);
	}
	

	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfUniqueWordsInFile(fileName)
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 * 		$ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKMostSignificantWords(String fileName, int k) throws FileIndexException{ //Q5
		IHistogram<String> histogram = filesMap.get(fileName);
		if (histogram == null) {
			throw new FileIndexException("file not found");
		}

		List<Map.Entry<String, Double>> list = listOfSortHistogram(histogram, fileName);

		return list.subList(0, k);
	}
	
	/* @pre: isInitialized() */
	public double getCosineSimilarity(String fileName1, String fileName2) throws FileIndexException{ //Q6
		IHistogram<String> histogram = filesMap.get(fileName1);
		if (histogram == null) {
			throw new FileIndexException("file not found");
		}

		histogram = filesMap.get(fileName2);
		if (histogram == null) {
			throw new FileIndexException("file not found");
		}

		double up = 0;
		double down = 0;
		double totalOfFile1TFIDFSquared = 0;
		double totalOfFile2TFIDFSquared = 0;


		for (String word : words) {
			double file1TFIDF = getTFIDF(word, fileName1);
			double file2TFIDF = getTFIDF(word, fileName2);

			up += file1TFIDF * file2TFIDF;
			totalOfFile1TFIDFSquared += file1TFIDF * file1TFIDF;
			totalOfFile2TFIDFSquared += file2TFIDF * file2TFIDF;
		}

		down = Math.sqrt(totalOfFile1TFIDFSquared * totalOfFile2TFIDFSquared);

		return up / down ;
	}


	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfFilesInIndex()-1
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 * 		$ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKClosestDocuments(String fileName, int k) throws FileIndexException{ //Q6
		IHistogram<String> histogram = filesMap.get(fileName);
		if (histogram == null) {
			throw new FileIndexException("file not found");
		}

		List<Map.Entry<String, Double>> list = new ArrayList<>();
		Iterator<String> iterator = filesMap.keySet().iterator();

		if (iterator.hasNext()) {
			String next = iterator.next();
			Map.Entry<String, Double> file = new AbstractMap.SimpleEntry<String, Double>(next, getCosineSimilarity(next, fileName));
			list.add(file);
		}

		out: while (iterator.hasNext()) {
			String file = iterator.next();
			double similarity = getCosineSimilarity(file, fileName);

			for (int i = 0; i < list.size(); i++) {
				Map.Entry<String, Double> e = list.get(i);
				if (e.getValue() < similarity) {
					Map.Entry<String, Double> en = new AbstractMap.SimpleEntry<String, Double>(file, similarity);
					list.add(i , en);
					continue out;
				}

				if (e.getValue() == similarity) {
					if (e.getKey().compareTo(file) > 0) {
						Map.Entry<String, Double> en = new AbstractMap.SimpleEntry<String, Double>(file, similarity);
						list.add(i , en);
						continue out;
					}
				}
			}

			Map.Entry<String, Double> en = new AbstractMap.SimpleEntry<String, Double>(file, similarity);
			list.add(en);
		}

		return list.subList(1, k+1);
	}

	
	
	//add private methods here, if needed
	private List<Map.Entry<String, Double>> listOfSortHistogram(IHistogram<String> histogram, String fileName) throws FileIndexException {
		List<Map.Entry<String, Double>> list = new ArrayList<>();
		Iterator<Map.Entry<String, Integer>> iterator = histogram.iterator();

		if (iterator.hasNext()) {
			Map.Entry<String, Integer> next = iterator.next();
			Map.Entry<String, Double> entry = new AbstractMap.SimpleEntry<String, Double>(next.getKey(), getTFIDF(next.getKey(), fileName));
			list.add(entry);
		}

		out: while (iterator.hasNext()) {
			Map.Entry<String, Integer> entry = iterator.next();
			double TFIDF = getTFIDF(entry.getKey(), fileName);

			for (int i = 0; i < list.size(); i++) {
				Map.Entry<String, Double> e = list.get(i);
				if (getTFIDF(e.getKey(), fileName) < TFIDF) {
					Map.Entry<String, Double> en = new AbstractMap.SimpleEntry<String, Double>(entry.getKey(), TFIDF);
					list.add(i , en);
					continue out;
				}

				if (getTFIDF(e.getKey(), fileName) == TFIDF) {
					if (e.getKey().compareTo(entry.getKey()) > 0) {
						Map.Entry<String, Double> en = new AbstractMap.SimpleEntry<String, Double>(entry.getKey(), TFIDF);
						list.add(i , en);
						continue out;
					}
				}
			}

			Map.Entry<String, Double> en = new AbstractMap.SimpleEntry<String, Double>(entry.getKey(), TFIDF);
			list.add(en);
		}

		return list;
	}

	
	/*************************************************************/
	/********************* Don't change this ********************/
	/*************************************************************/
	
	public boolean isInitialized(){
		return this.isInitialized;
	}
	
	/* @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getTFIDF(String word, String fileName) throws FileIndexException{
		return this.getTF(word, fileName)*this.getIDF(word);
	}
	
	private static double calcTF(int repetitionsForWord, int numOfWordsInDoc){
		return (double)repetitionsForWord/numOfWordsInDoc;
	}
	
	private static double calcIDF(int numOfDocs, int numOfDocsContainingWord){
		return Math.log((double)numOfDocs/numOfDocsContainingWord);
	}
	
}
