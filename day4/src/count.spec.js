const { countXmases, countMasesInAnX } = require("./count.js");

describe("countXmases", () => {
    it("should count the number of XMAS' in a smaller grid", () => {
        const grid = [
            "XMAS",
            "XMAS"
        ];
        expect(countXmases(grid)).toBe(2);
    });

    it("should count the number of XMAS' in a larger grid", () => {
        const grid = [
            "MMMSXXMASM",
            "MSAMXMSMSA",
            "AMXSXMAAMM",
            "MSAMASMSMX",
            "XMASAMXAMM",
            "XXAMMXXAMA",
            "SMSMSASXSS",
            "SAXAMASAAA",
            "MAMMMXMMMM",
            "MXMXAXMASX"
        ];
        expect(countXmases(grid)).toBe(18);
    });
});

describe("countMasesInAnX", () => {
    it("should count the number of Mases in an X", () => {
        const grid = [
            "MAS",
            "AAA",
            "MSS"
        ];

        expect(countMasesInAnX(grid)).toBe(1);
    });
});
