const { wordIterator } = require("./word-iterator.js");

describe("WordIterator", () => {
    it("should iterate over words in all 8 directions from each position", () => {
        const grid = [
            "ABC",
            "DEF", 
            "GHI"
        ];

        // From position (0,0), should get words in all 8 directions
        const iterator = wordIterator(grid);
        const words = [];
        for (let i = 0; i < 8; i++) {
            const word = iterator.next();
            if (word.done) break;
            words.push(word.value);
        }

        expect(words).toContain("ADG"); // down
        expect(words).toContain("A");   // bounded cases (diagonal down-left, left, up-left, up, up-right)
        expect(words).toContain("ABC"); // right
        expect(words).toContain("AEI"); // diagonal down-right
    });

    it("should handle grid boundaries", () => {
        const grid = [
            "AB",
            "CD"
        ];

        const iterator = wordIterator(grid);
        const words = [];
        while (true) {
            const word = iterator.next();
            if (word.done) break;
            words.push(word.value);
        }

        // Should not go out of bounds
        expect(words.every(w => w.length <= 4)).toBe(true);
    });

    it("should return undefined when iteration complete", () => {
        const grid = [
            "A",
            "B"
        ];

        const iterator = wordIterator(grid);
        while (!iterator.next().done) {
            // Consume all words
        }

        expect(iterator.next().value).toBeUndefined();
    });
});
