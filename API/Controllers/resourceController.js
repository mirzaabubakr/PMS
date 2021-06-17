const httpStatusCode = require('http-status-codes'); // Http StatusCodes

const Resource = require('../Models/ResourceModel');

module.exports ={
  async CreateResource(req, res){

    Resource.create(req.body).then((resource)=>{
        
      return res.status(httpStatusCode.CREATED).json(
        {
          Message: 'Resource Created Successfully',
          Data: resource
        });
    }).catch(err =>  {
      return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({
        Message: 'Resource can not be created, Please try again',
        Error: err.details
      });
    });
  },

  async getAllResources(req,res) {
    await Resource.find({})
    .then(resources => {
      return res.status(httpStatusCode.OK).json({Message: 'All Resource', resources});
    })
    .catch(err => {
      return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Internal Server Error', error: err});
    });
  }, 

  async updateResource(req, res){
    await Resource.updateOne({
      _id: req.params.id
    },
      req.body
    ).then(() => {
      res.status(httpStatusCode.OK).json({ Message: 'Resource Updated Successfully' });
    }).catch( err => {
      res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Error Updating Resource' });
    });
  },

  async deleteResource(req, res){
    await Resource.deleteOne({
      _id: req.params.id
    }).then(() => {
      res.status(httpStatusCode.OK).json({ Message: 'Resource Deleted Successfully' });
    }).catch( err => {
      res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Error Deleting Resource' });
    });
  }
}