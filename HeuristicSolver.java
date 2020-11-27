/**
 * HeuristicSolver.java
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

package Solvers;

import States.State;
import java.util.Queue;
import java.util.PriorityQueue;

public class HeuristicSolver extends AbstractSolver {
	private Queue<State> openQueue;

	/** Конструктор. */
	public HeuristicSolver() {
		super();

		this.openQueue = new PriorityQueue<State>();
	}

	/**
	 * Метод добавляет переданное состояние в очередь.
	 * Входной параметр:
	 * @param state - состояние, которое необходимо добавить в очередь.
	 */
	@Override
	public void add(State state) {
		this.openQueue.add(state);
	}

	/**
	 * Метод возвращает состояние с вершины очереди, удаляя его из нее.
	 * Возвращает:
	 * @return состояние с вершины очереди.
	 */
	@Override
	public State get() {
		return this.openQueue.poll();
	}

	/**
	 * Метод, позволяющий узнать пуста ли очередь.
	 * Выходной параметр:
	 * @return true - пуста;
	 *         false - не пуста.
	 */
	@Override
	public boolean isVisitedAll() {
		return this.openQueue.isEmpty();
	}
}