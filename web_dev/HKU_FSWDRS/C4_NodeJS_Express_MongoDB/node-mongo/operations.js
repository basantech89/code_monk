const assert = require('assert');

exports.insertDocument = (db, document, collection) => {
    const coll = db.collection(collection);
    // call to insert function returns a promise, so we will now return that promise from this function
    return coll.insert(document);
    // uncommenting below code to implement promise
    // coll.insert(document, (err, result) => {
    //     assert.equal(err, null);
    //     // n tells us how many documents have been inserted
    //     console.log('result is ', result);
    //     console.log("Inserted " + result.result.n + " documents into the collection " + collection);
    //     callback(result);
    // })
};

exports.findDocument = (db, collection) => {
    const coll = db.collection(collection);
    return coll.find({}).toArray();
    // coll.find({}).toArray((err, docs) => {
    //     assert.equal(err, null);
    //     callback(docs);
    // })
};

exports.removeDocument = (db, document, collection) => {
    const coll = db.collection(collection);
    return coll.deleteOne(document);
    // coll.deleteOne(document, (err, result) => {
    //     assert.equal(err, null);
    //     // since document is a JS object, we have specified , instead of + in below console log 
    //     // so the document will be printed out
    //     console.log('Remove the dooucment ', document);
    //     callback(result);
    // })
};

exports.updateDocument = (db, document, update, collection) => {
    const coll = db.collection(collection);
    // 2nd param is which field of document that needs to be updated
    return coll.updateOne(document, { $set: update }, null);
    // coll.updateOne(document, { $set: update }, null, (err, result) => {
    //     assert.equal(err, null);
    //     console.log("Updated the document with ", update);
    //     callback(result);
    // })
}