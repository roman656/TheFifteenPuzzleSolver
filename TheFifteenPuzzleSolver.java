/* -*- Mode: C; indent-tabs-mode: t; c-basic-offset: 4; tab-width: 4 -*-  */
/*
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

public class TheFifteenPuzzleSolver {
	public static void main(String[] args) {
		State rootState;

		try {
			rootState = new NineCellsState(null);
		}
		catch (IndexOutOfBoundsException exception) {
			System.out.print("In main(String[]):\n" + exception.getMessage());
			return;
		}

		System.out.println("Root:\n" + rootState);

		for (State obj : rootState.getPossibleMoves()) {
			System.out.println("Distance: " + obj.getDistance());
			System.out.println(obj);
		}
	}
}