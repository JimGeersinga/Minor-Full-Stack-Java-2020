var foo = function () {
    this.prop1 = "foo_prop1";
    this.prop2 = "foo_prop2";
};

var bar = function() {
    this.prop1 = "bar_prop1";
    this.prop2 = "bar_prop2";
};
bar.prototype = foo;

console.log(new bar().prop1);
console.log(new bar().prop2);
console.log(new foo().prop1);
console.log(new foo().prop2);