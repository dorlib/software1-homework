package il.ac.tau.cs.sw1.ex8.tfIdf;

import java.util.List;
import java.util.Map;

import il.ac.tau.cs.sw1.ex8.TesterUtils;


public class FileIndexTester {
	
	public static final String INPUT_FOLDER_1 = "./resources/hw8/input_1";
	public static final String INPUT_FOLDER_2 = "./resources/hw8/input_2";
	public static final String INPUT_FOLDER_3 = "./resources/hw8/input_3";
	
	public static final double EPS = 0.01;


	public static void main(String[] args){
		testTFIDF(); // covers questions 2-4
		testSortingBySignificance(); // covers questions 5
		testCosineSimilarity(); // covers questions 6
		testSortingByCosineSimilarity(); //covers question 7
	}
	
	public static void testTFIDF(){
		FileIndex fIndex = new FileIndex();
		fIndex.indexDirectory(INPUT_FOLDER_1);
		
		try{
			if (fIndex.getCountInFile("it", "s1.txt") != 1){
				TesterUtils.printErrorNum(1);
			}
			if (fIndex.getNumOfUniqueWordsInFile("s1.txt") != 6){
				TesterUtils.printErrorNum(2);
			}
			if (fIndex.getNumOfFilesInIndex() != 3){
				TesterUtils.printErrorNum(3);

			}
		}
		catch(Exception exp){
			TesterUtils.printErrorNum(4);
		}
		
		
		try{
			fIndex.getCountInFile("it", "s4.txt");
			TesterUtils.printErrorNum(5, "Exception should be thrown", "no exception");
		}
		catch(FileIndexException exp){
		}
		

		
		//TF
		String[] files = {"s1.txt", "s2.txt", "s3.txt", "s1.txt"};
		String[] words = {"going", "going", "going", "am"};
		double[] tfValues = {0.16666, 0.16666, 0.125, 0};
		
		for (int i =0; i < files.length; i++){
			try{
				double tf = fIndex.getTF(words[i], files[i]);
				TesterUtils.checkDoubleValueAndPrintError(tfValues[i],tf, EPS, 6+i*2); //errors 6,8,10,12
			}
			catch (Exception exp){
				TesterUtils.printErrorNum(6+i*2 + 1, "no exception", ""+exp.getClass()); //errors 7,9,11,13
			}
		}
		
		//IDF
		TesterUtils.checkDoubleValueAndPrintError(0,fIndex.getIDF("going"), EPS, 14);

		TesterUtils.checkDoubleValueAndPrintError(0.405,fIndex.getIDF("i"),  EPS, 15);

		TesterUtils.checkDoubleValueAndPrintError(1.09, fIndex.getIDF("it"),  EPS, 16);
		
		System.out.println("done!");
	}
	
	public static void testSortingBySignificance(){
		FileIndex fIndex = new FileIndex();
		fIndex.indexDirectory(INPUT_FOLDER_2);
		try{
			String fileName = "rocky_raccoon.txt";
			if (fIndex.getCountInFile("Rocky", fileName) != 10){
				TesterUtils.printErrorNum(21);
			}
			
			if (fIndex.getCountInFile("raccoon", fileName) != 3){
				TesterUtils.printErrorNum(22);
			}
			
			if (fIndex.getCountInFile("room", fileName) != 4){
				TesterUtils.printErrorNum(23);
			}

			List<Map.Entry<String, Double>> res = fIndex.getTopKMostSignificantWords(fileName, 7);
			//7 most significant words appear only in rocky_raccoon. 
			//[rocky=0.10160573189823605, he=0.05056456205791939, his=0.05056456205791939, room=0.040642292759294416, boy=0.030481719569470815, only=0.030481719569470815, raccoon=0.030481719569470815]
			if (!res.get(0).getKey().equals("rocky")){
				TesterUtils.printErrorNum(24);
			}
			if (!res.get(1).getKey().equals("he")){
				TesterUtils.printErrorNum(25);
			}
			if (!res.get(6).getKey().equals("raccoon")){
				TesterUtils.printErrorNum(26);
			}
			/* correct values are under definition of res. The following test should pass is the since they consider a 
			different of 0.01 between the expected value and the result you got */
			TesterUtils.checkDoubleValueAndPrintError(0.0931, res.get(0).getValue(), 0.01, 261);
			TesterUtils.checkDoubleValueAndPrintError(0.0446, res.get(1).getValue(), 0.01, 262);
			TesterUtils.checkDoubleValueAndPrintError(0.0446, res.get(2).getValue(), 0.01, 263);
			
			res = fIndex.getTopKMostSignificantWords("something.txt", 4);
			if (res.size() != 4){
				TesterUtils.printErrorNum(27);

			}
			if (!res.get(0).getKey().equals("something")){
				TesterUtils.printErrorNum(28);
			}
			if (!res.get(1).getKey().equals("believe")){
				TesterUtils.printErrorNum(29);
			}
			if (!res.get(2).getKey().equals("she")){ //more frequent than "believe" but appears in more documents
				TesterUtils.printErrorNum(30);
			}
		}
		catch(Exception exp){
			TesterUtils.printErrorNum(31);
		}
	}
	
	public static void testCosineSimilarity(){
		FileIndex fIndex = new FileIndex();
		fIndex.indexDirectory(INPUT_FOLDER_3);
		try{
			//f1 tf: [a=0.2, b=0.4, c=0.2, d=0.2]
			//f2 tf: [a=0,   b=0.4, c=0.4, d=0.2]
			//idf:   [a=0.6931, b=0.6931, c=0.287, d=0]
			//tf-idf vec for f1 = [0.1386, 0.2773, 0.0575, 0]
			//tf-idf vec for f2 = [0, 0.2773, 0.1151, 0]
			double sim = fIndex.getCosineSimilarity("f1.txt", "f2.txt");
			TesterUtils.checkDoubleValueAndPrintError(0.8821, sim, EPS, 41);
		}
		catch (Exception exp){
			TesterUtils.printErrorNum(42);
		}
	}
	
	public static void testSortingByCosineSimilarity(){
		FileIndex fIndex = new FileIndex();
		fIndex.indexDirectory(INPUT_FOLDER_2);
		try{
			List<Map.Entry<String, Double>> res = fIndex.getTopKClosestDocuments("buy_me_love.txt", 10);
			for(Map.Entry<String, Double> pair : res){
				System.out.println(pair);
			}
		}
		catch (Exception exp){
			TesterUtils.printErrorNum(52);
		}
	}

}
