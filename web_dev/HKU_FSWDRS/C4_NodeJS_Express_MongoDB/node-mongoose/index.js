const mongoose = require('mongoose');

const Dishes = require('./models/dishes');

const url = 'mongodb://localhost:27017/conFusion';
const connect = mongoose.connect(url);

connect.then((db) => {
    console.log('Connected correctly to server');

    // var newDish = Dishes({
    //     name: 'Uthappizza',
    //     description: 'test'
    // });

    // newDish.save()
    // .then((dish) => ...)
    Dishes.create({
        name: 'Uthappizza3',
        description: 'test'
    })
    .then((dish) => {
        console.log(dish);

        // exec will ensure that this is executed and that will return a promise
        // return Dishes.find({}).exec();
        return Dishes.findByIdAndUpdate(dish._id, {
            $set: { description: 'updated test' }
        }, { new: true }).exec(); 
        // the flag new: true means that once the update of dishes is complete
        // then this will return the updated dish
    })
    // .then((dishes) => {
    .then((dish) => {
        console.log(dish);

        dish.comments.push({
            rating: 5,
            comment: 'I\'m getting a sinking feeling !!',
            author: 'Leonardo di Carpaccio'
        });

        return dish.save();
    })
    .then((dish) => {
        console.log(dish);

        return Dishes.remove({});
    })
    .then(() => {
        return mongoose.connection.close();
    })
    .catch(err => console.log(err));
});
