// Coordinates of a 3x3 grid relative to its center.
const COORDINATES_3x3_FORM_CENTER = [
    [[-1, -1], [-1, 0], [-1, 1]],
    [[0, -1], [0, 0], [0, 1]],
    [[1, -1], [1, 0], [1, 1]]
];

/**
 * An iterator that yields full 3x3 grids.
 * @param {string[]} grid The grid of characters.
 * @returns {Iterator<string[][]>} An iterator that yields a full 3x3 grid of characters.
 */
function xIterator(grid) {
    // The cursor's position.
    // The cursor starts a row and column ahead to get full 3x3 grids.
    const cursor = {
        row: 1,
        col: 0, // Start a step back
    };

    // Marked 'true' when the end of the grid is reached.
    // Useful for preventing the cursor from advancing past the end of the grid.
    let doneAdvancingCursor = false;

    // Advances the cursor to the next position, or marks the end of the grid if the end is reached.
    const advanceCursor = () => {
        if (doneAdvancingCursor) return false;

        cursor.col++;
        if (cursor.col >= grid[cursor.row].length - 1) {
            cursor.col = 0;
            cursor.row++;
            if (cursor.row >= grid.length - 1) {
                doneAdvancingCursor = true;
            }
        }

        return !doneAdvancingCursor;
    };

    // Yields the next 3x3 grid.
    const nextX = () => {
        return COORDINATES_3x3_FORM_CENTER.map((coords) =>
            coords.map(([dr, dc]) =>
                grid[cursor.row + dr][cursor.col + dc]
            )
        );
    }

    return {
        // Yields the next 3x3 grid.
        next() {
            if (advanceCursor()) {
                return { value: nextX() };
            } else {
                return { done: true };
            }
        },

        [Symbol.iterator]() {
            return this;
        }
    }
}

module.exports = { xIterator };
