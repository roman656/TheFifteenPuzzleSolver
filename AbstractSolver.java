/**
 * AbstractSolver.java
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
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Collections;

public abstract class AbstractSolver implements Solver {
	private Set<State> visited;

	/**
	 * Конструктор.
	 */
	public AbstractSolver() {
		this.visited = new HashSet<State>();
	}

	@Override
	public Iterable<State> solve(State initialState) {
		if (initialState.isSolvable()) {
			this.add(initialState);

			while (!this.isVisitedAll()) {
				State state = this.get();

				if (!this.visited.contains(state)) {
					if (state.isSolution()) {
						return findPath(state);
					}

					this.visited.add(state);
					state.getPossibleMoves().forEach(this::add);
				}
			}
		}

		return Collections.emptyList();
	}

	/**
	 * Метод, определяющий полный путь до определенного состояния.
	 * Входной параметр:
	 * @param state - состояние, до которого необходимо проложить путь.
	 * Возвращает:
	 * @return связный список, содержащий все состояния, начиная от корневого,
	 * заканчивая переданным в метод.
	 */
	private LinkedList<State> findPath(State state) {
		LinkedList<State> path = new LinkedList<State>();

		for (State currentState = state; currentState != null;
				currentState = currentState.getParent()) {
			path.addFirst(currentState);
		}

		return path;
	}

	protected Set<State> getVisited() {
		return this.visited;
	}

	protected abstract void add(State state);
	protected abstract State get();
	protected abstract boolean isVisitedAll();
}