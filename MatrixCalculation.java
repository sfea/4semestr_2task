public class MatrixCalculation {

	public MatrixCalculation() {
	}

	// рекурсивная функция - вычисляет значение опредеsлителя. Если на входе
	// определитель 2х2 - просто вычисляем (крест-на-крест),
	// иначе раскладываем на миноры. Для каждого минора вычисляем его
	// определитель, рекурсивно вызывая ту же функцию..
	public int CalculateMatrix(int[][] matrix) {
		int calcResult = 0;
		if (matrix.length == 2) {
			calcResult = matrix[0][0] * matrix[1][1] - matrix[1][0]
					* matrix[0][1];
		} else {
			int koeff = 1;
			for (int i = 0; i < matrix.length; i++) {
				if (i % 2 == 1) { // проверка на четность для определение
									// коэффициента перед слагаемым
					koeff = -1;
				} else {
					koeff = 1;
				}
				;
				calcResult += koeff * matrix[0][i]
						* this.CalculateMatrix(this.GetMinor(matrix, 0, i));
			}
		}
		return calcResult;
	}

	// функция, которая возвращает нужный минор. На входе - определитель, из
	// которого надо достать минор
	// и номера строк-столбцов, которое надо вычеркнуть.,
	private int[][] GetMinor(int[][] matrix, int row, int column) {
		int minorLength = matrix.length - 1;
		int[][] minor = new int[minorLength][minorLength];
		int dI = 0;// эти переменные для того, чтобы "пропускать" ненужные нам
					// строку и столбец
		int dJ = 0;
		for (int i = 0; i <= minorLength; i++) {
			dJ = 0;
			for (int j = 0; j <= minorLength; j++) {
				if (i == row) {
					dI = 1;
				} else {
					if (j == column) {
						dJ = 1;
					} else {
						minor[i - dI][j - dJ] = matrix[i][j];
					}
				}
			}
		}
		return minor;
	}
}