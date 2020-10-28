/**
 * TheFifteenPuzzleSolver.java
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

public abstract class AbstractState implements State, Comparable<State> {
	private State parent = null;
	private int distance = 0;

	public AbstractState(State parent) {
		this.parent = parent;
		if (parent != null) {
			this.distance = parent.getDistance() + 1;
		}
	}

	@Override
	public State getParent() {
		return this.parent;
	}

	@Override
	public int getDistance() {
		return this.distance;
	}

	@Override
	public int compareTo(State inputState) {
		Integer currentScore = this.getDistance() + this.getHeuristic();
		Integer inputStateScore = inputState.getDistance()
				+ inputState.getHeuristic();
		return currentScore.compareTo(inputStateScore);
	}
}
