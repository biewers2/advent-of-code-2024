// Directions to iterate over in the character grid to form words.
const DIRECTIONS = [
    [1, 0],   // Down
    [1, 1],   // Down-right
    [0, 1],   // Right
    [-1, 1],  // Up-right
    [-1, 0],  // Up
    [-1, -1], // Up-left
    [0, -1],  // Left
    [1, -1],  // Down-left
];

/**
 * An iterator that yields 4 (or less) letter words from a grid of characters.
 * @param {string[]} grid The grid of characters.
 * @returns {Iterator<string>} An iterator that yields 4 (or less) letter words from the grid.
 */
function wordIterator(grid) {
    // The cursor's position and direction.
    // The "direction" is the direction in the grid to read the next word (i.e. up, up-right, right, etc.).
    const cursor = { 
        row: 0,
        col: 0,
        directionIndex: -1 // Start a step back to initially advance the cursor.
    };

    // Marked 'true' when the end of the grid is reached.
    // Useful for preventing the cursor from advancing past the end of the grid.
    let doneAdvancingCursor = false; 

    // Advances the cursor to the next direction, or position if all directions have been iterated.
    // Returns 'false' if the end of the grid is reached.
    const advanceCursor = () => {
        if (doneAdvancingCursor) return false;

        cursor.directionIndex += 1;
        if (cursor.directionIndex == DIRECTIONS.length) {
            cursor.directionIndex = 0;
            cursor.col++;
            if (cursor.col >= grid[cursor.row].length) {
                cursor.col = 0;
                cursor.row++;
                if (cursor.row >= grid.length) {
                    doneAdvancingCursor = true;
                }
            }
        }

        return !doneAdvancingCursor;
    };

    // Returns the coordinates of the next word.
    const nextCoordinates = () => {
        const coords = [];
        const [drow, dcol] = DIRECTIONS[cursor.directionIndex];
        for (let len = 0; len < 4; len++) {
            const r = cursor.row + drow * len;
            const c = cursor.col + dcol * len;

            if (r < 0 || r >= grid.length || c < 0 || c >= grid[r].length) {
                break;
            }

            coords.push([r, c]);
        }
        return coords;
    };

    // Returns the next word from the grid.
    const nextWord = () =>
        nextCoordinates()
            .map(([r, c]) => grid[r][c])
            .join("");

    return {
        next() {
            if (advanceCursor()) {
                return { value: nextWord() };
            } else {
                return { done: true };
            }
        },

        [Symbol.iterator]() {
            return this;
        }
    }
}

module.exports = { wordIterator };
