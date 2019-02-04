const assert = require('assert');

exports.insertDocument = (db, document, collection, callback) => {
    const coll = db.collection(collection);
    // using promises to avoid callback hell problem
    /*coll.insert(document, (err, result) => {
        // assert checks if the error is not null
        // if the error is not null then this will print out the info and quit the application
        assert.equal(err, null);
        // result is a property (JS object), n tells how many documents have been inserted
        console.log("Inserted " + result.result.n + " documents into the collection " + collection);
        // pass the result back to our calling function
        callback(result);
    });*/
    // insert will return a promise, so we will return that promise from this function
    return coll.insert(document);
};

exports.findDocuments = (db, collection, callback) => {
    const coll = db.collection(collection);
/*
    coll.find({}).toArray((err, docs) => {
        assert.equal(err, null);
        callback(docs);
    });
*/
    return coll.find({}).toArray();
};

exports.removeDocument = (db, document, collection, callback) => {
    const coll = db.collection(collection);
    // delete the first document that matches
/*
    coll.deleteOne(document, (err, result) => {
        assert.equal(err, null);
        console.log("Removed the document ", document); // used comma after the string to print out the JS object correctly
        callback(result);
    });
*/
    return coll.deleteOne(document);
};

exports.updateDocument = (db, document, update, collection, callback) => {
    const coll = db.collection(collection);
/*
    coll.updateOne(document, { $set: update }, null, (err, result) => {
        assert.equal(err, null);
        console.log("Updated the document with ", update);
        callback(result);
    });
*/
    return coll.updateOne(document, { $set: update }, null);
};