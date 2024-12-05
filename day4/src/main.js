const fs = require("fs");
const { countXmases, countMasesInAnX } = require("./count.js");

function main() {
    const grid = fs.readFileSync("input.txt", "utf8").split("\n");
    console.log(countXmases(grid));
    console.log(countMasesInAnX(grid));
}

module.exports = { main };
