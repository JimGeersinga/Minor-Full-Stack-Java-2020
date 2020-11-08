console.log(5 == new Number(5)); // true
console.log(new Number(5) == new Number(5)); // false
console.log(5 === new Number(5)); // false
console.log(new Number(5) === new Number(5)); // false
console.log(5 == [5]); // true
console.log(5 === [5]); // false
console.log(0 == false); // true
console.log(1 == true); // true
console.log(2 == true); // false
console.log('' == false); // true
console.log('0' == false); // true
console.log('' ? true : false); // false
console.log('0' ? true : false); // true
console.log(false == null); // false
console.log(false == undefined); // false
console.log(null == undefined); // true