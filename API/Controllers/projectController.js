const httpStatusCode = require('http-status-codes'); // Http StatusCodes

const Project = require('../Models/ProjectModel');

module.exports ={
  async CreateProject(req, res){

    Project.create(req.body).then((project)=>{
        
      return res.status(httpStatusCode.CREATED).json(
        {
          Message: 'Project Created Successfully',
          Data: project
        });
    }).catch(err =>  {
      console.log(err);
      return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({
        Message: 'Project can not be created, Please try again',
        Error: err.details
      });
    });
  },

  async getAllProjects(req,res) {
    await Project.find({})
    .then(projects => {
      return res.status(httpStatusCode.OK).json({Message: 'All Project', projects});
    })
    .catch(err => {
      return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Internal Server Error', error: err});
    });
  }, 

  async updateProject(req, res){
    await Project.updateOne({
      _id: req.params.id
    },
      req.body
    ).then(() => {
      res.status(httpStatusCode.OK).json({ Message: 'Project Updated Successfully' });
    }).catch( err => {
      res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Error Updating Project' });
    });
  },

  async deleteProject(req, res){
    await Project.deleteOne({
      _id: req.params.id
    }).then(() => {
      res.status(httpStatusCode.OK).json({ Message: 'Project Deleted Successfully' });
    }).catch( err => {
      res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Error Deleting Project' });
    });
  }
}