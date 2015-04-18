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
		
		System.out.println("���������: Ax = b");
		System.out.println("������� A:");
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
		System.out.println("������� b:");
		for(i = 0; i < b.length; i++){
			System.out.print(b[i] + " ");
		}
		System.out.println("");

		int koeff = MatrixSize / NumberOfThreads;			//�����������, ������� ����������, ������� ������������� ����� ��������� � ����� �����������
		Thread[] myThready = new Thread[NumberOfThreads];
		SomeThing[] newSmth = new SomeThing[NumberOfThreads];

		MatrixCalculation calc = new MatrixCalculation();
		int det = calc.CalculateMatrix(matrix);

		for (i = 0; i < NumberOfThreads - 1; i++) {
			newSmth[i] = new SomeThing(koeff * i, koeff * (i + 1) - 1, matrix, b);
		}
 
		newSmth[NumberOfThreads - 1] = new SomeThing(koeff * (NumberOfThreads - 1), MatrixSize - 1, matrix, b); //����� ���������� ���, ��� � ��������� ������ ���������� 
																												//������(��� ������) ����������, ��� � ��������� �������, 
																												//��� � ��������� ������ �������

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
			transf = newSmth[i].resultArray();								//���������� ������ ������� ����������� ������������ �� ��������� ������ transf, 
																			//������ ���������� ������� ���������� ����������� �������������� ������ transf
			for (j = i * koeff; j < koeff * (i + 1); j++) {
				result[j] = (double)transf[j - i * koeff] / (double)det;	//�� ������� transf �� ��������� ���������� ���������� ������������ � ������ result
			}
		}

		transf = newSmth[NumberOfThreads - 1].resultArray();
	
		for (j = (NumberOfThreads - 1) * koeff; j <= MatrixSize - 1; j++) {
			result[j] = (double)transf[j] / (double)det;
		}

		System.out.print("���������: x = (");
		for (i = 0; i < MatrixSize; i++) {
			System.out.print(result[i]);
			if (i != MatrixSize - 1) {
				System.out.print(", ");
			}
		}
		System.out.println(")");
	}
}