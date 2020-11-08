function foo(x) {
  if (x > 4) {
    throw new Error('x > 4')
  }

  x++

  x *= 100

  return Math.floor(x)
}
  
[1, 2, 3, 4, 5].map(foo)




var foo2 = function foo(x) {
  if (x > 4) {
    throw new Error('x > 4');
  }

  x++;

  x *= 100;

  return Math.floor(x);
};

[1, 2, 3, 4, 5].map(foo2);