public class SomeThing implements Runnable {
	private int first;
	private int last;
	private int[][] matrix;
	private int[] res;
	private int[] b;
	private int[][] matrix2;		

	SomeThing(int k, int n, int[][] matrix, int[] b) {			//k и n - начало и конец диапазона подсчета детерминантов в каждом вычислителе
		first = k;
		last = n;
		this.matrix = matrix;
		this.b = b;
	}

	public void run() {
		matrix2 = new int[matrix.length][matrix.length];		//массив, в котором будет храниться копия матрицы A
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix.length; j++) {
				matrix2[i][j] = matrix[i][j];
			}
		
		res = new int[matrix.length];
		MatrixCalculation obj = new MatrixCalculation();
																//на данном этапе считаются детерминанты (n - k +1) матриц, соответствующих данному потоку
		for (int i = first; i <= last; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = b[j];
			}
			res[i] = obj.CalculateMatrix(matrix);				//результаты вычисления детерминантов записываются в массив res
			
			for (int k = 0; k < matrix.length; k++)
				for (int l = 0; l < matrix.length; l++) {
					matrix[k][l] = matrix2[k][l];
				}
		}
	}

	public int[] resultArray() {
		return res;
	}
}