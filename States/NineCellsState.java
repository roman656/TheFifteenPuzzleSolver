/**
 * NineCellsState.java
 * Copyright (C) 2020 Roman S <romanstrah@mail.ru>
 * 
 * TheFifteenPuzzleSolver is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TheFifteenPuzzleSolver is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package States;

import static java.lang.Math.sqrt;
import static java.lang.Math.round;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;

/** Класс состояния пятнашек с размерностью поля 3 на 3. */
public class NineCellsState extends AbstractState {

	/** Количество ячеек в игровом поле (размер массива игрового поля). */
	private static final int SIZE = 9;

	/**
	 * Количество псевдослучайных перестановок элементов игрового поля,
	 * при его инициализации.
	 */
	private static final int RANDOM_SWAPS_AMOUNT = 20;

	/** Состояние игрового поля, при котором игра считается завершенной. */
	private static final int[] GAME_FIELD_SOLUTION = {
			1, 2, 3,
			4, 5, 6,
			7, 8, 0};

	/** Массив, хранящий текущее состояние игрового поля. */
	private int[] gameField;

	/**
	 * Индекс пустой ячейки на игровом поле.
	 * Пустой ячейкой считается элемент игрового поля, чье значение равно 0.
	 */
	private int emptyCellIndex = 0;

	/**
	 * Конструктор, задающий массив с текущим состоянием игрового поля,
	 * псевдослучайным образом.
	 * Входной параметр:
	 * @param parent - родительское состояние.
	 * Если текущее состояние первое, то передать null.
	 */
	public NineCellsState(State parent) throws IndexOutOfBoundsException {
		super(parent);

		Random random = new Random();
		this.gameField = new int[this.SIZE];

		/** Заполнение игрового поля начальными значениями. */
		for (int i = 0; i < this.SIZE; i++) {
			this.gameField[i] = i;
		}

		/** Перемешивание элементов игрового поля. */
		try {
			for (int i = 0; i < this.RANDOM_SWAPS_AMOUNT; i++) {
				swap(random.nextInt(this.SIZE), random.nextInt(this.SIZE));
			}
		}
		catch (IndexOutOfBoundsException exception) {
			throw new IndexOutOfBoundsException("In NineCellsState(State)"
					+ " -> shuffling elements error:\n"
					+ exception.getMessage());
		}

		/** Определение индекса пустой ячейки. */
		for (int i = 0; i < this.SIZE; i++) {
			if (this.gameField[i] == 0) {
				this.emptyCellIndex = i;
				break;
			}
		} 
	}

	/**
	 * Конструктор, использующий переданный массив, для инициализации
	 * текущего состояния игрового поля.
	 * Входные параметры:
	 * @param parent - родительское состояние;
	 * @param initGameField - массив с состоянием игрового поля.
	 * Если текущее состояние первое, то передать null.
	 */
	public NineCellsState(State parent, int[] initGameField)
			throws IllegalArgumentException {
		super(parent);

		try {
			this.setGameField(initGameField);
		}
		catch (IllegalArgumentException exception) {
			throw new IllegalArgumentException("In NineCellsState(State, int[])"
					+ " -> incorrect second parameter:\n"
					+ exception.getMessage());
		}
	}

	/**
	 * Метод, определяющий принадлежность индекса ячейки,
	 * диапазону допустимых в текущем игровом поле.
	 * Входной параметр:
	 * @param cellIndex - индекс ячейки, подлежащий проверке.
	 * Возвращает:
	 * @return true - индекс входит в диапазон допустимых;
	 *         false - не входит.
	 */
	private boolean isValidCellIndex(int cellIndex) {
		return (cellIndex >= 0) && (cellIndex < this.SIZE);
	}

	/**
	 * Метод, меняющий местами 2 любые ячейки в текущем игровом поле.
	 * Входные параметры:
	 * @param cellIndexA - индекс первой ячейки;
	 * @param cellIndexB - индекс второй ячейки.
	 * В случае некорректных входных параметров будет выброшено соответствующее
	 * исключение.
	 */
	public void swap(int cellIndexA, int cellIndexB)
			throws IndexOutOfBoundsException {

		/** Проверка корректности входных параметров. */
		if (!isValidCellIndex(cellIndexA) || !isValidCellIndex(cellIndexB)) {
			throw new IndexOutOfBoundsException("In swap(int, int): "
					+ "incorrect input index(es).\n");
		}

		if (cellIndexA != cellIndexB) {
			int temp = this.gameField[cellIndexA];
			this.gameField[cellIndexA] = this.gameField[cellIndexB];
			this.gameField[cellIndexB] = temp;

			/** Переопределение индекса пустой ячейки, при необходимости. */
			if (this.emptyCellIndex == cellIndexA) {
				this.emptyCellIndex = cellIndexB;
			}
			else if (this.emptyCellIndex == cellIndexB) {
				this.emptyCellIndex = cellIndexA;
			}
		}
	}

	/**
	 * Геттер, возвращающий количество ячеек в текущем игровом поле.
	 * Возвращает:
	 * @return размер массива игрового поля.
	 */
	public int getSize() {
		return this.SIZE;
	}

	/**
	 * Геттер, возвращающий количество псевдослучайных перестановок
	 * элементов игрового поля, при его инициализации.
	 * Возвращает:
	 * @return количество псевдослучайных перестановок.
	 */
	public int getRandomSwapsAmount() {
		return this.RANDOM_SWAPS_AMOUNT;
	}

	/**
	 * Геттер, возвращающий массив с состоянием игрового поля,
	 * при котором игра считается завершенной.
	 * Возвращает:
	 * @return массив с состоянием игрового поля, при котором игра
	 * считается завершенной.
	 */
	public int[] getGameFieldSolution() {
		return this.GAME_FIELD_SOLUTION;
	}

	/** 
	 * Геттер, возвращающий массив с текущим состоянием игрового поля.
	 * Возвращает:
	 * @return массив с текущим состоянием игрового поля.
	 */
	public int[] getGameField() {
		return this.gameField;
	}

	/** 
	 * Геттер, возвращающий индекс пустой ячейки на игровом поле.
	 * Возвращает:
	 * @return индекс пустой ячейки на игровом поле.
	 */
	public int getEmptyCellIndex() {
		return this.emptyCellIndex;
	}

	/**
	 * Сеттер, заменяющий массив с текущим состоянием игрового поля.
	 * Входной параметр:
	 * @param newGameField - массив с новым состоянием игрового поля.
	 * Входной массив будет скопирован. В случае некорректного входного
	 * параметра будет выброшено соответствующее исключение.
	 * Индекс пустой ячейки будет определен автоматически.
	 */
	public void setGameField(int[] newGameField)
			throws IllegalArgumentException {
		
		/** Проверка корректности входного параметра. */
		if ((newGameField == null) || (newGameField.length != this.SIZE)) {
			throw new IllegalArgumentException("In setGameField(int[]): "
					+ "incorrect input array.\n");
		}

		/**
		 * Проверка на то, что во входном массиве нет повторяющихся
		 * и/или неверных элементов.
		 */
		int[] tempBuffer = new int[this.SIZE];

		for (int i = 0; i < this.SIZE; i++) {
			if ((newGameField[i] >= 0) && (newGameField[i] < this.SIZE)
					&& (tempBuffer[newGameField[i]] == 0)) {
				tempBuffer[newGameField[i]] += 1;
			}
			else {
				throw new IllegalArgumentException("In setGameField(int[]): "
						+ "incorrect input array values.\n");
			}
		}

		/** Копирование входного массива. */
		this.gameField = newGameField.clone();

		/** Определение индекса пустой ячейки. */
		for (int i = 0; i < this.SIZE; i++) {
			if (this.gameField[i] == 0) {
				this.emptyCellIndex = i;
				break;
			}
		}
	}

	/**
	 * Метод конвертирует индекс элемента массива с состоянием игрового поля
	 * в соответствующие индексы строки и столбца (координаты).
	 * Входной параметр:
	 * @param cellIndex - индекс ячейки.
	 * Возвращает:
	 * @return массив, перый элемент которого соответствует индексу строки,
	 * в которой находится ячейка, а второй - индексу столбца.
	 */
	private int[] gameFieldIndexToCoordinates(int cellIndex) {

		/** Размерность текущего игрового поля. */
		int gameFieldDimension = (int) round(sqrt(this.SIZE));

		/** Координаты ячейки. */
		int[] coordinates = new int[2];

		/** Индекс соответствующей строки. */
		coordinates[0] = cellIndex / gameFieldDimension;

		/** Индекс соответствующего столбца. */
		coordinates[1] = cellIndex % gameFieldDimension;

		return coordinates;
	}

	/**
	 * Метод конвертирует индексы строки и столбца (координаты) в
	 * соответствующий индекс элемента массива с состоянием игрового поля.
	 * Входной параметр:
	 * @param coordinates - массив, перый элемент которого соответствует
	 * индексу строки, в которой находится ячейка, а второй - индексу столбца.
	 * Возвращает:
	 * @return индекс ячейки.
	 */
	private int coordinatesToGameFieldIndex(int[] coordinates) {

		/** Размерность игрового поля. */
		int gameFieldDimension = (int) round(sqrt(this.SIZE));

		return coordinates[0] * gameFieldDimension + coordinates[1];
	}

	/**
	 * Метод изменяет координаты ячейки.
	 * Входные параметры:
	 * @param coordinates - массив, перый элемент которого соответствует
	 * индексу строки, в которой находится ячейка, а второй - индексу столбца;
	 * @param rowDelta - изменение индекса строки ячейки;
	 * @param columnDelta - изменение индекса столбца ячейки.
	 * Возвращает:
	 * @return измененные координаты.
	 */
	private int[] changeCoordinates(int[] coordinates, int rowDelta,
			int columnDelta) throws IndexOutOfBoundsException {

		/** Размерность игрового поля. */
		int gameFieldDimension = (int) round(sqrt(this.SIZE));

		int[] newCoordinates = coordinates.clone();

		if ((rowDelta != 0) || (columnDelta != 0)) {
			newCoordinates[0] += rowDelta;
			newCoordinates[1] += columnDelta;

			/**
			 * В случае выхода за допустимые границы будет выброшено
			 * исключение.
			 */
			if ((newCoordinates[0] >= gameFieldDimension)
					|| (newCoordinates[1] >= gameFieldDimension)
					|| (newCoordinates[0] < 0) || (newCoordinates[1] < 0)) {
				throw new IndexOutOfBoundsException("In changeCoordinates"
						+ "(int[], int, int): incorrect second and/or third"
						+ " input parameter(s).\n");
			}
		}

		return newCoordinates;
	}

	/**
	 * Метод возвращает набор состояний, которые можно получить из текущего
	 * за один ход.
	 * Возвращает:
	 * @return набор состояний.
	 */
	@Override
	public Iterable<State> getPossibleMoves() {
		ArrayList<State> moves = new ArrayList<State>();

		/**
		 * Для более удобных манипуляций с индексом пустой ячейки
		 * преобразуем его в соответствующие координаты.
		 */
		int[] emptyCellCoordinates = 
				gameFieldIndexToCoordinates(this.emptyCellIndex);

		/**
		 * Пытаемся совершить ход по всем четырем возможным направлениям.
		 * В случае выхода координаты за границы игрового поля будет выброшено
		 * исключение IndexOutOfBoundsException. Таким образом, данный ход
		 * будет отменен как невозможный. Обрабатывать это исключение
		 * не нужно, так как это не ошибка.
		 */
		try {
			NineCellsState temp = new NineCellsState(this, this.gameField);
			temp.swap(temp.getEmptyCellIndex(),
					coordinatesToGameFieldIndex(
					changeCoordinates(emptyCellCoordinates, 1, 0)));
			moves.add(temp);
		}
		catch (IndexOutOfBoundsException exception) {}

		try {
			NineCellsState temp = new NineCellsState(this, this.gameField);
			temp.swap(temp.getEmptyCellIndex(),
					coordinatesToGameFieldIndex(
					changeCoordinates(emptyCellCoordinates, -1, 0)));
			moves.add(temp);
		}
		catch (IndexOutOfBoundsException exception) {}

		try {
			NineCellsState temp = new NineCellsState(this, this.gameField);
			temp.swap(temp.getEmptyCellIndex(),
					coordinatesToGameFieldIndex(
					changeCoordinates(emptyCellCoordinates, 0, 1)));
			moves.add(temp);
		}
		catch (IndexOutOfBoundsException exception) {}

		try {
			NineCellsState temp = new NineCellsState(this, this.gameField);
			temp.swap(temp.getEmptyCellIndex(),
					coordinatesToGameFieldIndex(
					changeCoordinates(emptyCellCoordinates, 0, -1)));
			moves.add(temp);
		}
		catch (IndexOutOfBoundsException exception) {}

		return moves;
	}

	/**
	 * Метод проверяет, совпадает ли текущее состояние игрового поля
	 * с финальным.
	 * Возвращает:
	 * @return true - совпадает;
	 *         false - не совпадает.
	 */
	@Override
	public boolean isSolution() {
		return Arrays.equals(this.gameField, this.GAME_FIELD_SOLUTION);
	}

	/**
	 * Метод проверяет, имеет ли текущее состояние игрового поля решение.
	 * Данный алгоритм работает верно ТОЛЬКО для пятнашек 4 на 4.
	 * Во всех остальных ситуациях будет возвращен true.
	 * Возвращает:
	 * @return true - имеет;
	 *         false - не имеет.
	 */
	@Override
	public boolean isSolvable() {

		/**
		 * Если текущее состояние уже является решением, возвращаем true.
		 * На этом этапе производится проверка, применим ли данный алгоритм.
		 */
		if (this.isSolution() || (this.SIZE != 16)) {
			return true;
		}

		/**
		 * Количество пар элементов массива игрового поля,
		 * в которых первый элемент с большим значением
		 * (сравнение ведется относительно правильного порядка элементов)
		 * предшествует элементу с меньшим.
		 */
		int sum = 0;

		/** Номер строки, содержащей пустую ячейку (номер = индекс + 1). */
		int emptyCellRowNumber = 
				gameFieldIndexToCoordinates(this.emptyCellIndex)[0] + 1;

		for (int i = 0; i < (this.SIZE - 1); i++) {
			for (int j = i + 1; j < this.SIZE; j++) {
				if ((this.gameField[i] > this.gameField[j])
						&& (j != this.emptyCellIndex)) {
					sum++;
				}
			}
		}

		return ((sum + emptyCellRowNumber) % 2 == 0);
	}

	/**
	 * Метод, вычисляющий эвристическую оценку текущего состояния игрового поля.
	 * Подсчитываются элементы, находящиеся не на "своих" местах.
	 * Возвращает:
	 * @return эвристическая оценка состояния.
	 */
	@Override
	public int getHeuristic() {
		int wrongElementsAmount = 0;

		for (int i = 0; i < this.SIZE; i++) {
			if (this.gameField[i] != this.GAME_FIELD_SOLUTION[i]) {
				wrongElementsAmount++;
			}
		}

		return wrongElementsAmount;
	}

	/**
	 * Метод создает копию текущего игрового состояния.
	 * Возвращает:
	 * @return копию текущего игрового состояния.
	 * Исключение IllegalArgumentException не может быть выброшено на данном
	 * этапе. В качестве второго параметра передается ранее проверенный массив:
	 * изменить игровое поле могут только оба конструктора и метод
	 * setGameField(). Так как происходит копирование уже существующего объекта,
	 * значит его конструктор уже был вызван, и соответствующее исключение
	 * было бы выброшено на более раннем этапе. То же касается и setGameField().
	 */
	public NineCellsState copy() {
		return new NineCellsState(this.getParent(), this.gameField);
	}

	/**
	 * Метод создает строку, содержащую разделительную линию игрового поля.
	 * Входной параметр:
	 * @param gameFieldDimension - размерность игрового поля.
	 * Возвращает:
	 * @return строку, содержащую разделительную линию.
	 */
	private String gameFieldHorizontalLineToString(int gameFieldDimension) {
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < gameFieldDimension; i++) {
			buffer.append("----");
		}
		buffer.append("-\n");

		return buffer.toString();
	}

	/**
	 * Метод создает строку, содержащую текущее состояние игрового поля
	 * в виде таблицы.
	 * Возвращает:
	 * @return строку, содержащую текущее состояние игрового поля.
	 */	
	private String gameFieldToString() {
		StringBuffer buffer = new StringBuffer();

		/** Размерность игрового поля (3 на 3; 4 на 4; и т. д.). */
		int gameFieldDimension = (int) round(sqrt(this.SIZE));

		/** Добавление верхней линии таблицы. */
		buffer.append(gameFieldHorizontalLineToString(gameFieldDimension));

		for (int i = 0; i < this.SIZE; i++) {

			/** Добавление текущей ячейки игрового поля. */
			buffer.append("| " + this.gameField[i] + " ");

			/**
			 * Переход на следующую строку таблицы и
			 * добавление разделительной линии.
			 */
			if ((i + 1) % gameFieldDimension == 0) {
				buffer.append("|\n" +
						gameFieldHorizontalLineToString(gameFieldDimension));
			}
		}

		return buffer.toString();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("States.NineCellsState:\n");
		buffer.append("Empty cell\'s id: " + this.emptyCellIndex + "\n");
		buffer.append("Game field:\n");
		buffer.append(gameFieldToString());

		return buffer.toString();
	}

	@Override
	public boolean equals(Object obj) {

		/** Объект равен самому себе. */
		if (this == obj) {
			return true;
		}

		/**
		 * Проверка на то, что:
		 * - сравниваем два объекта одного класса.
		 * - сравниваем не с null.
		 */
		if (obj == null || (this.getClass() != obj.getClass())) {
			return false;
		}

		NineCellsState temp = (NineCellsState) obj;
		return Arrays.equals(this.gameField, temp.getGameField());
	}

	@Override
	public int hashCode() {
		int result = this.gameField[0];

		/**
		 * Для сокращения числа коллизий при вычислении хэш-кода
		 * промежуточный результат умножается на нечетное простое число.
		 * Чаще всего используется число 29 или 31.
		 */
		for (int i = 1; i < this.SIZE; i++) {
			result = 31 * result + this.gameField[i];
		}
		
		return result;
	}
}
