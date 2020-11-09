const T = teller(5).metStapGrootte(2);
console.log(T.volgende()); // 7
console.log(T.volgende()); // 9
console.log(T.volgende()); // 11

function teller(start) {
    return {
        metStapGrootte : function(value) {
            return {
                volgende: function (){
                    return start += value;
                }
            };
        }
    };
}