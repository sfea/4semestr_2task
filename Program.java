public class Program {
	static SomeThing mThing;

	public static void main(String[] args) {
		int NumberOfThreads = 2;
		int MatrixSize = 4;
		int i = 0;
		int j = 0;
		int[][] matrix = new int[MatrixSize][MatrixSize];
		int[] b = new int[MatrixSize];
		int[] transf = new int[MatrixSize];
		double[] result = new double[MatrixSize];

		matrix[0][0] = 1;
		matrix[0][1] = 0;
		matrix[0][2] = 0;
		matrix[0][3] = 0;

		matrix[1][0] = 0;
		matrix[1][1] = 1;
		matrix[1][2] = 0;
		matrix[1][3] = 0;
		
		matrix[2][0] = 0;
		matrix[2][1] = 0;
		matrix[2][2] = 1;
		matrix[2][3] = 0;
		
		matrix[3][0] = 0;
		matrix[3][1] = 0;
		matrix[3][2] = 0;
		matrix[3][3] = 1;
		
		System.out.println("”равнение: Ax = b");
		System.out.println("ћатрица A:");
		for (i = 0; i < MatrixSize; i++){
			for(j = 0; j < MatrixSize; j++){
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("");
		}
		
		b[0] = 1;
		b[1] = 1;
		b[2] = 1;
		b[3] = 1;
		System.out.println("—толбец b:");
		for(i = 0; i < b.length; i++){
			System.out.print(b[i] + " ");
		}
		System.out.println("");

		int koeff = MatrixSize / NumberOfThreads;			//коэффициент, который показывает, сколько детерминантов будет посчитано в одном вычислителе
		Thread[] myThready = new Thread[NumberOfThreads];
		SomeThing[] newSmth = new SomeThing[NumberOfThreads];

		MatrixCalculation calc = new MatrixCalculation();
		int det = calc.CalculateMatrix(matrix);

		for (i = 0; i < NumberOfThreads - 1; i++) {
			newSmth[i] = new SomeThing(koeff * i, koeff * (i + 1) - 1, matrix, b);
		}
 
		newSmth[NumberOfThreads - 1] = new SomeThing(koeff * (NumberOfThreads - 1), MatrixSize - 1, matrix, b); //может получитьс€ так, что в последнем потоке происходит 
																												//больше(или меньше) вычислений, чем в остальных потоках, 
																												//что и учитывает данна€ строчка

		for (i = 0; i < NumberOfThreads; i++) {
			myThready[i] = new Thread(newSmth[i]);
			myThready[i].start();
		}

		for (i = 0; i < NumberOfThreads; i++) {
			try {
				myThready[i].join();
			} catch (InterruptedException s) {
				System.out.println("Interrupted");
			}
		}

		for (i = 0; i < NumberOfThreads - 1; i++) {
			transf = newSmth[i].resultArray();								//результаты работы каждого вычислител€ записываютс€ во временный массив transf, 
																			//причем результаты каждого следующего вычислител€ перезаписывают массив transf
			for (j = i * koeff; j < koeff * (i + 1); j++) {
				result[j] = (double)transf[j - i * koeff] / (double)det;	//из массива transf мы добавл€ем результаты вычислени€ детерминанта в массив result
			}
		}

		transf = newSmth[NumberOfThreads - 1].resultArray();
	
		for (j = (NumberOfThreads - 1) * koeff; j <= MatrixSize - 1; j++) {
			result[j] = (double)transf[j] / (double)det;
		}

		System.out.print("–езультат: x = (");
		for (i = 0; i < MatrixSize; i++) {
			System.out.print(result[i]);
			if (i != MatrixSize - 1) {
				System.out.print(", ");
			}
		}
		System.out.println(")");
	}
}