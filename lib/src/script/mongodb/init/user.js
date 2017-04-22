// To use if not using mongo shell directly
//var connection = new Mongo("http://127.0.0.1:27017/");
//var db = connection.getDB("apibackendspringboottemplate");

db.user.drop();
db.createCollection("user", {autoIndexId:true});
db.user.createIndex({"userName":1},{unique:true});
