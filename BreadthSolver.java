/**
 * BreadthSolver.java
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
import java.util.LinkedList;

public class BreadthSolver extends AbstractSolver {
	private Queue<State> openQueue;

	public BreadthSolver() {
		super();

		this.openQueue = new LinkedList<State>();
	}

	@Override
	public void add(State state) {
		this.openQueue.add(state);
	}

	@Override
	public State get() {
		return this.openQueue.poll();
	}

	@Override
	public boolean isVisitedAll() {
		return this.openQueue.isEmpty();
	}
}