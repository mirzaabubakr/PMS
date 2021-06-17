const mongoose = require('mongoose');

const schema = mongoose.Schema;
const cptaSchema = new schema({
  _id: { type: Number },
  Name: { type: String },
  Req: { type: String },
  Date: { type: String },
  ReqName: { type: String },
  Description: { type: String },
  Reason: { type: String },
  Impact: { type: String },
  status: { type: String },
});

module.exports = mongoose.model("Cpat", cptaSchema);