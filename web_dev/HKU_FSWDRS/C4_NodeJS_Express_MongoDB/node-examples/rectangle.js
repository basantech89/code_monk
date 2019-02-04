// a node module exporting two values
// exports.perimeter = (x, y) => 2 * (x + y);
// exports.area = (x, y) => x * y;

module.exports = (x, y, callback) => {
    if (x <= 0 || y <= 0) {
        // delay before the callback function is called, simulate the heavy workload env
        // callback takes two parameters, first parameter is an error and second is the return value
        setTimeout(() =>
            // string that is passed in to below error object is attached to message property of error object
            callback(new Error("Rectangle dimensions should be greater than zero: l, b = " + x + ", " + y), null),
            2000);
    }
    else {
        setTimeout(() =>
            callback(null, {
                // no need to pass x and y to below functions because of JS closures
                // x and y will are available to us because they are defined in exports variable value (function) as a parameter
                perimeter: () => 2 * (x + y),
                area: () => x * y
            }),
            2000);
    }
};