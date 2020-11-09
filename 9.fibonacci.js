function fibonacci(num) {
    if (num <= 0) return 0;
    if (num === 1) return 1;
  
    return fibonacci(num - 1) + fibonacci(num - 2);
}

var fibonacci2 = function(num) {
    if (num <= 0) return 0;
    if (num === 1) return 1;
  
    return fibonacci2(num - 1) + fibonacci2(num - 2);
};