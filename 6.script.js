var arr = Array(50).fill(null);

var i;
var arr2_stmnt = [];
for (i = 0; i < arr.length; i += 1) {
  var val = Math.random();
  if (val < 0.3) continue;
  val = Math.floor(val * 100);
  arr2_stmnt.push(val);
}
console.log(arr2_stmnt);

var arr2_expr = arr.map(x => Math.floor(Math.random() * 100)).filter((x) => x >= 30);
console.log(arr2_expr);