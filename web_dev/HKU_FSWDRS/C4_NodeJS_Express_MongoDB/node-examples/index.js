/*var rect = {
    perimeter: (x, y) => 2 * (x + y),
    area: (x, y) => x * y
};*/
var rect = require('./rectangle');

function solveRect(l, b) {
    console.log("Solving for rectangle with  l = " + l + " and b = " + b);
    // the callback (3rd parameter) will be executed after 2 seconds of delay
    rect(l, b, (err, rectangle) => {
        if (err)
            console.log("Error: " + err.message);
        else {
            // not sending in any parameters to area bcoz l and b have been already passed in and available to use
            // already bcoz of the JS closures
            console.log("The area of rectangle of dimensions l, b = " + l + ", " + b + " is " + rectangle.area());
            console.log("The perimeter of rectangle of dimensions l, b = " + l + ", " + b + " is " + rectangle.perimeter());
        }
    });
    console.log("This statement is after the call to rect");
}

solveRect(2, 4);
solveRect(3, 5);
solveRect(0, 5);
solveRect(-3, 5);