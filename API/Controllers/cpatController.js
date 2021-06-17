const httpStatusCode = require('http-status-codes'); // Http StatusCodes

const Cpat = require('../Models/CptaModel');

module.exports ={
  async CreateCpat(req, res){

    Cpat.create(req.body).then((cpat)=>{
        
      return res.status(httpStatusCode.CREATED).json(
        {
          Message: 'Cpat Created Successfully',
          Data: cpat
        });
    }).catch(err =>  {
      console.log(err);
      return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({
        Message: 'Cpat can not be created, Please try again',
        Error: err.details
      });
    });
  },

  async getAllCpats(req,res) {
    await Cpat.find({})
    .then(cpats => {
      return res.status(httpStatusCode.OK).json({Message: 'All Cpat', cpats});
    })
    .catch(err => {
      return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Internal Server Error', error: err});
    });
  }, 

  async updateCpat(req, res){
    await Cpat.updateOne({
      _id: req.params.id
    },
      req.body
    ).then(() => {
      res.status(httpStatusCode.OK).json({ Message: 'Cpat Updated Successfully' });
    }).catch( err => {
      res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Error Updating Cpat' });
    });
  },

  async deleteCpat(req, res){
    await Cpat.deleteOne({
      _id: req.params.id
    }).then(() => {
      res.status(httpStatusCode.OK).json({ Message: 'Cpat Deleted Successfully' });
    }).catch( err => {
      res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Error Deleting Cpat' });
    });
  }
}