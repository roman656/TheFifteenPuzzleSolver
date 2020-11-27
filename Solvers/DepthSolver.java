/**
 * DepthSolver.java
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
import java.util.Stack;

public class DepthSolver extends AbstractSolver {
	private Stack<State> openStack;

	public DepthSolver() {
		super();

		this.openStack = new Stack<State>();
	}

	@Override
	public void add(State state) {
		if (!this.getVisited().contains(state)) {
			this.openStack.push(state);
		}
	}

	@Override
	public State get() {
		return this.openStack.pop();
	}

	@Override
	public boolean isVisitedAll() {
		return this.openStack.empty();
	}
}
