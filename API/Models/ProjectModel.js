const mongoose = require('mongoose');

const schema = mongoose.Schema;
const projectSchema = new schema({
  _id: { type: Number },
  pName: { type: String },
  pType: { type: String },
  pStatus: { type: String },
  pDescription: { type: String },
  status: { type: String }
});

module.exports = mongoose.model("Project", projectSchema);