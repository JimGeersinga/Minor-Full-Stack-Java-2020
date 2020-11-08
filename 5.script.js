var number = Math.random();
var x_stmnt;
if (number > 0.7) {
    x_stmnt = 1;
} else {
    x_stmnt = 0;
}

var x_expr = number > 0.7 ? 1 : 0;

console.assert(x_stmnt === x_expr);