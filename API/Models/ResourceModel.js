const mongoose = require('mongoose');

const schema = mongoose.Schema;
const resourceSchema = new schema({
  _id: { type: Number },
  rName: { type: String },
  rType: { type: String },
  rStatus: { type: String },
  rDescription: { type: String },
  status: { type: String },
});

module.exports = mongoose.model("Resource", resourceSchema);