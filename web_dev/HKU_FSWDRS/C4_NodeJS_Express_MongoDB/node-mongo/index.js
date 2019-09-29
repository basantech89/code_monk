const MongoClient = require('mongodb').MongoClient;
const assert = require('assert');
const dboper = require('./operations');

const url = 'mongodb://localhost:27017/';
const dbname = 'conFusion';

// using promises now
MongoClient.connect(url).then((client) => {
    
    console.log('Connnected correctly to server');

    const db = client.db(dbname);

    dboper.insertDocument(db, { name: "Vadonut", description: "Test" }, 'dishes') 
    .then((result) => {
        console.log('Insert Document:\n', result.ops);

        return dboper.findDocument(db, 'dishes')
    }) 
    .then((docs) => {
        console.log('Found Documents:\n', docs);
        
        // don't need to specify entire document in the 2nd param, specify just one field then that
        // will find the document that matches this particular field
        // 3rd param is the field which needs to be updated in that record
        return dboper.updateDocument(db, {name: 'Vadonut'}, {description: 'Updated Test'}, 'dishes')
    }) 
    .then((result) => {
        console.log('Updated document:\n', result.result);

        return dboper.findDocument(db, 'dishes')
    }) 
    .then((docs) => {
        console.log('Found Documents:\n', docs);

        return db.dropCollection('dishes')
    }) 
    .then((result) => {
        console.log('Droppped Collection ', result);

        client.close();
    })
    .catch(err => console.log(err));
})
.catch(err => console.log(err));


// 2nd param is the callback
// MongoClient.connect(url, (err, client) => {

//     //  check err is not null, if not null then show it on the screen
//     assert.equal(err, null);

//     // if err is null, then it means that we have connected properly to the server
//     console.log('Connnected correctly to server');

//     const db = client.db(dbname);

//     dboper.insertDocument(db, { name: "Vadonut", description: "Test" }, 'dishes', 
//     (result) => {
//         console.log('Insert Document:\n', result.ops);

//         dboper.findDocument(db, 'dishes', 
//         (docs) => {
//             console.log('Found Documents:\n', docs);
            
//             // don't need to specify entire document in the 2nd param, specify just one field then that
//             // will find the document that matches this particular field
//             // 3rd param is the field which needs to be updated in that record
//             dboper.updateDocument(db, {name: 'Vadonut'}, {description: 'Updated Test'}, 'dishes', 
//             (result) => {

//                 console.log('Updated document:\n', result.result);

//                 dboper.findDocument(db, 'dishes', 
//                 (docs) => {
//                     console.log('Found Documents:\n', docs);

//                     db.dropCollection('dishes', 
//                     (result) => {
//                         console.log('Droppped Collection ', result);

//                         client.close();
//                     });
//                 });
//             });
//         });
//     });

//     // const collection = db.collection('dishes');

//     // collection.insertOne({ "name": "Uthappizza", "description": "test" }, (err, result) => {
//     //     assert.equal(err, null);
        
//     //     console.log('After Insert:\n');
//     //     console.log("ops is ", result.ops); // ops says how many operations carried out successfully (ops - 1 operation)

//     //     // empty object supplied to find function to search for all the records in colllection,
//     //     // so all the record will be returned 
//     //     collection.find({}).toArray((err, docs) => {
//     //         assert.equal(err, null);
            
//     //         console.log('Found:\n');
//     //         console.log(docs);

//     //         db.dropCollection('dishes', (err, result) => {
//     //             assert.equal(err, null);

//     //             client.close();
//     //         });
//     //     })
//     // })

// })