const { wordIterator } = require("./word-iterator.js");
const { xIterator } = require("./x-iterator.js");

function countXmases(grid) {
    let count = 0;
    for (const word of wordIterator(grid)) {
        if (word === "XMAS") {
            count++;
        }
    }
    return count;
}

function countMasesInAnX(grid) {
    let count = 0;
    for (const x of xIterator(grid)) {
        if (isMasTLBR(x) && isMasTRBL(x)) {
            count++;
        }
    }
    return count;
}

function isMasTLBR(x) {
    return (`${x[0][0]}${x[1][1]}${x[2][2]}` === "MAS" || `${x[2][2]}${x[1][1]}${x[0][0]}` === "MAS");
}

function isMasTRBL(x) {
    return (`${x[0][2]}${x[1][1]}${x[2][0]}` === "MAS" || `${x[2][0]}${x[1][1]}${x[0][2]}` === "MAS");
}

module.exports = { 
    countXmases,
    countMasesInAnX
 };
