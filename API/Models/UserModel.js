const mongoose = require('mongoose');

const schema = mongoose.Schema;
const userSchema = new schema({
  Name: { type: String },
  Email: { type: String },
  PhoneNumber: { type: String },
  Password:{type: String},
  Address: { type: String},
});

module.exports = mongoose.model("User", userSchema);